package com.uzskill.base.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.exception.ApplicationException;
import com.light.exception.ConfigurationException;
import com.light.exception.ExceptionHelper;
import com.light.utils.DateUtils;
import com.light.utils.StringUtils;

/**
 * FileUploadProcessor 文件上传类
 * 上传根目录可以通过参数application.upload.dir来配置；该类 支持以下三种附件上传：
 * 1、用户头像，所有用户头像都将被上传到 user目录中，该目录可通过application.upload.user.dir来配置，默认路径为根目录下的user目录
 * 2、项目附件，所有项目附件将被上传到 attachment目录中，该目录可通过application.upload.attachment.dir来配置，默认路径为根目录下的attachment目录
 * 3、个人作品图片，所有个人作品图片都将被上传到 works目录中，该目录可通过application.upload.works.dir来配置，默认路径为根目录下的image目录
 * 对于上传文件大小的限制：
 * 1、项目附件不超过5M
 * 2、用户头像不超过2M，用户头像长宽固定为250px
 * 3、个人作品图片不超过3M，最大长/宽不超过1000px
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-31
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class FileUploadProcessor {
	private static final Logger logger = LogManager.getLogger(FileUploadProcessor.class);
	
	public static final String UPLOAD_TYPE_USER = "user";
	public static final String UPLOAD_TYPE_WORKS = "works";
	public static final String UPLOAD_TYPE_ATTACHMENT = "attachment";
	
	private static String USER_DIR;
	private static String WORKS_IMAGE_DIR;
	private static String ATTACHMENT_DIR;
	private static final String SEPARATOR = File.separator;
	public static final String MINUS_SEPARATOR = "-";
	private static final String IMAGE_EXT = "png,jpg,jpeg,gif";
	private static final String ATTACHMENT_EXT = "doc,docx,ppt,pptx,xls,xlsx,pdf,txt,zip,rar";
	private static final long ATTACHMENT_SIZE = 5120000l;
	private static final int USER_IMAGE_MAX_WD = 500; // 与前端选择用户头像的长宽保持一致
	
	private static final int USER_IMAGE_WD = 250;
	private static final long WORKS_IMAGE_MAX_SIZE = 3096000l;
	private static final long USER_IMAGE_MAX_SIZE = 2048000l;
	
	private static final String APPLICATION_UPLOAD_DIR = "application.upload.dir";
	private static final String APPLICATION_UPLOAD_USER_DIR = "application.upload.user.dir";
	private static final String APPLICATION_UPLOAD_WORKS_IMAGE_DIR = "application.upload.works.dir";
	private static final String APPLICATION_UPLOAD_ATTACHMENT_DIR = "application.upload.attachment.dir";
	
	static {
		// 获取配置的上传根目录、用户、个人作品图片目录和项目附件目录；默认的用户、个人作品图片目录和附件目录为上传根目录的user和attachment子目录
		String uploadDirectory = ApplicationContext.getInstance().getProperty(APPLICATION_UPLOAD_DIR, "");
		if(!StringUtils.isBlank(uploadDirectory)) {
			if(!uploadDirectory.endsWith(SEPARATOR)) {
				uploadDirectory = uploadDirectory + SEPARATOR;
			}
			String userDirDefault = uploadDirectory + "user" + SEPARATOR;
			String worksDirDefault = uploadDirectory + "works" + SEPARATOR;
			String attachmentDirDefault = uploadDirectory + "attachment" + SEPARATOR;
			USER_DIR = ApplicationContext.getInstance().getProperty(APPLICATION_UPLOAD_USER_DIR, userDirDefault);
			WORKS_IMAGE_DIR = ApplicationContext.getInstance().getProperty(APPLICATION_UPLOAD_WORKS_IMAGE_DIR, worksDirDefault);
			ATTACHMENT_DIR = ApplicationContext.getInstance().getProperty(APPLICATION_UPLOAD_ATTACHMENT_DIR, attachmentDirDefault);
			
			makeDirectory(USER_DIR);
			makeDirectory(WORKS_IMAGE_DIR);
			makeDirectory(ATTACHMENT_DIR);
		}
		else {
			String errMessage = "The upload directory is empty, please check the application configuration.";
			logger.error(errMessage);
			ConfigurationException ce = new ConfigurationException(errMessage);
			throw ce;
		}
	}
	
	/**
	 * 上传附件
	 * 
	 * @param file
	 * @param fileName
	 * 
	 * @return 已上传文件的年月路径和名称
	 */
	public static String uploadFile(File file, String fileName) {
		String targetFilePath = "";
		// 验证附件的有效性
		if(file.isFile()) {
			long fileSize = file.length();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(!StringUtils.isBlank(extension) && validExtension(ATTACHMENT_EXT, extension) && ATTACHMENT_SIZE >= fileSize) {
				// 完整路径 = 根目录 + 年月目录
				String targetDir = ATTACHMENT_DIR + getInnerDirectory();
				makeDirectory(targetDir);
				// 上传附件
				FileInputStream fileIn = null;
				FileOutputStream fileOut = null;
				String targetFileName = getTargetFileName(extension);
				try {
					fileIn = new FileInputStream(file);
					fileOut = new FileOutputStream(targetDir + targetFileName);
			        byte[] buf = new byte[1024];         
			        int length = 0;
			        while ((length = fileIn.read(buf)) != -1) {
			        	fileOut.write(buf, 0, length);
			        }	         
			        fileIn.close();
			        fileOut.close();
			        // 新生成的附件年月目录和名称
			        targetFilePath = getInnerDirectory() + targetFileName;
				}
				catch(Exception e) {
					String errMessage = "Failed to upload file with name: " + fileName;
					logger.error(errMessage, e);
					ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
					throw ae;
				}
				finally {
					try {
						if (fileIn != null) {
							fileIn.close();
							fileIn = null;
						}
						if (fileOut != null) {
							fileOut.close();
							fileOut = null;
						}
					}
					catch (Exception e) {
						String errMessage = "Failed to close input/output stream while uploading file with name: " + fileName;
						logger.error(errMessage, e);
						ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
						throw ae;
					}
				}
			}
		}
		return targetFilePath;
	}
	
	/**
	 * 上传用户头像
	 * 
	 * @param image
	 * @param fileName
	 * @param dir
	 * @param maxWidth
	 * @param maxHeight
	 * @param imageSize
	 * @return 已上传文件的年月路径和名称
	 */
	public static String uploadUserImage(File image, String fileName, double x, double y, double rorate, double ratio) {
		String targetFilePath = "";
		String dir = USER_DIR;
		long imageSize = USER_IMAGE_MAX_SIZE;
		
		// 验证头像的有效性
		if(image.isFile()) {
			long fileSize = image.length();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(!StringUtils.isBlank(extension) && validExtension(IMAGE_EXT, extension) && imageSize >= fileSize) {
				// 完整路径 = 根目录 + 年月目录
				String targetDir = dir + getInnerDirectory();
				makeDirectory(targetDir);
				// 原图临时保存路径
				String targetSrcDir = dir + SEPARATOR + "src" + SEPARATOR + getInnerDirectory();
				makeDirectory(targetSrcDir);
				// 上传原图
				FileInputStream imageIn = null;
				FileOutputStream imageOut = null;
				String targetFileName = getTargetFileName(extension);
				
				String userImageName = targetDir + targetFileName;
				String userImageSrcName = targetSrcDir + targetFileName;
				try {
					imageIn = new FileInputStream(image);
					imageOut = new FileOutputStream(userImageSrcName);
					// 读取图像的长和宽
					BufferedImage bfImage = ImageIO.read(imageIn);
					int width = bfImage.getWidth();
					int height = bfImage.getHeight();
					
					Image srcImage = ImageIO.read(image);
					int newWidth = width, newHeight = height;
					
					double scale = (double) height / width;
					// 默认的缩放比例计算中USER_IMAGE_MAX_WD必须和前端的宽度保持一直，才保证这里的计算结果和前端一致。
					if(ratio == 0.0d) {
						// 因为前端图片打开时取长/宽最大者缩放，所以这里也取最大值来计算缩放比例
						if(width > height) {
							ratio = (double)USER_IMAGE_MAX_WD / width;
						}
						else {
							ratio = (double)USER_IMAGE_MAX_WD / height;
						}
					}
					// 当截图时候图片被缩放，则需要依据缩放的比例生成新的图片。
					newWidth = (int)(width * ratio);
					newHeight = (int)(height * ratio);
					
					// 保证缩放后的图片长宽最短的不小于USER_IMAGE_WD
					if(newWidth <= USER_IMAGE_WD) {
						if (newWidth >= newHeight) {
							newHeight = USER_IMAGE_WD;
							newWidth = (int) (newHeight / scale);
						}
						else {
							newWidth = USER_IMAGE_WD;
							newHeight = (int) (newWidth * scale);
						}
					}
					else {
						if (width >= height) {
							if(newHeight < USER_IMAGE_WD) {
								newHeight = USER_IMAGE_WD;
							}
							newWidth = (int) (newHeight / scale);
						}
					}
					
					// 重新计算缩放比例
					double newRatio = ratio;
					if(newWidth > newHeight) {
						newRatio = (double)newWidth / width;
					}
					else {
						newRatio = (double)newHeight / height;
					}
					int newX = (int)(x * newRatio);
					int newY = (int)(y * newRatio);
					
					// 根据原图生成新的图像
					BufferedImage tagBfImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
					tagBfImage.getGraphics().drawImage(srcImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
					ImageIO.write(tagBfImage, extension, imageOut);
					imageOut.close();
					
					// 裁剪图片
					FileOutputStream cutImageOut = new FileOutputStream(userImageName);
					BufferedImage cutInImage = ImageIO.read(new File(userImageSrcName));
					BufferedImage cutOutImage = cutInImage.getSubimage(newX, newY, USER_IMAGE_WD, USER_IMAGE_WD);
					ImageIO.write(cutOutImage, extension, cutImageOut);
					cutImageOut.close();
			        // 新生成的图像年月目录和名称
			        targetFilePath = getInnerDirectory() + targetFileName;
				}
				catch(Exception e) {
					String errMessage = "Failed to upload user image with name: " + fileName;
					logger.error(errMessage, e);
					ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
					throw ae;
				}
				finally {
					try {
						// 删除原图
						File srcImage = new File(userImageSrcName);
						srcImage.delete();
						
						if (imageIn != null) {
							imageIn.close();
							imageIn = null;
						}
						if (imageOut != null) {
							imageOut.close();
							imageOut = null;
						}
					}
					catch (Exception e) {
						String errMessage = "Failed to close input/output stream or delete src image while uploading image with name: " + fileName;
						logger.error(errMessage, e);
						ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
						throw ae;
					}
				}
			}
		}
		return targetFilePath;
	}

	/**
	 * 上传作品图像，不支持缩放功能
	 * 
	 * @param image
	 * @param fileName
	 * @param x
	 * @param y
	 * @param cWidth
	 * @param cHeight
	 * @return 已上传文件的年月路径和名称
	 */
	public static String uploadWorksImage(File image, String fileName, double x, double y, double cWidth, double cHeight) {
		String targetFilePath = "";
		String dir = WORKS_IMAGE_DIR;
		long imageSize = WORKS_IMAGE_MAX_SIZE;
		
		// 验证头像的有效性
		if(image.isFile()) {
			long fileSize = image.length();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(!StringUtils.isBlank(extension) && validExtension(IMAGE_EXT, extension) && imageSize >= fileSize) {
				// 完整路径 = 根目录 + 年月目录
				String targetDir = dir + getInnerDirectory();
				makeDirectory(targetDir);
				// 原图临时保存路径
				String targetSrcDir = dir + SEPARATOR + "src" + SEPARATOR + getInnerDirectory();
				makeDirectory(targetSrcDir);
				// 上传原图
				FileInputStream imageIn = null;
				FileOutputStream imageOut = null;
				String targetFileName = getTargetFileName(extension);
				
				String worksImageName = targetDir + targetFileName;
				String worksImageSrcName = targetSrcDir + targetFileName;
				try {
					imageIn = new FileInputStream(image);
					imageOut = new FileOutputStream(worksImageSrcName);
					// 读取图像的长和宽
					BufferedImage bfImage = ImageIO.read(imageIn);
					int width = bfImage.getWidth();
					int height = bfImage.getHeight();
					
					// 根据原图生成新的图像
					Image srcImage = ImageIO.read(image);
					BufferedImage tagBfImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					tagBfImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
					ImageIO.write(tagBfImage, extension, imageOut);
					imageOut.close();
					
					// 裁剪图片
					FileOutputStream cutImageOut = new FileOutputStream(worksImageName);
					BufferedImage cutInImage = ImageIO.read(new File(worksImageSrcName));
					BufferedImage cutOutImage = cutInImage.getSubimage((int)x, (int)y, (int)cWidth, (int)cHeight);
					ImageIO.write(cutOutImage, extension, cutImageOut);
					cutImageOut.close();
			        // 新生成的图像年月目录和名称
			        targetFilePath = getInnerDirectory() + targetFileName;
				}
				catch(Exception e) {
					String errMessage = "Failed to upload works image with name: " + fileName;
					logger.error(errMessage, e);
					ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
					throw ae;
				}
				finally {
					try {
						// 删除原图
						File srcImage = new File(worksImageSrcName);
						srcImage.delete();
						
						if (imageIn != null) {
							imageIn.close();
							imageIn = null;
						}
						if (imageOut != null) {
							imageOut.close();
							imageOut = null;
						}
					}
					catch (Exception e) {
						String errMessage = "Failed to close input/output stream or delete src image while uploading image with name: " + fileName;
						logger.error(errMessage, e);
						ApplicationException ae = ExceptionHelper.getInstance().handleException(errMessage, e);
						throw ae;
					}
				}
			}
		}
		return targetFilePath;
	}
	/**
	 * 返回年月组成的相对文件路径。
	 * 年月和文件名之间以“-”而不是以文件分隔符拼接，目的是在下载时候该路径以参数的形式传递的时候文件分隔符会带来问题。
	 *  
	 * @return String
	 */
	private static String getInnerDirectory(){
		String year = String.valueOf(DateUtils.getYear());
		String month = String.valueOf(DateUtils.getMonth());
		String innerDirectory = year + SEPARATOR + month + SEPARATOR;
		return innerDirectory;
	}
	
	/**
	 * 取得唯一的文件名
	 * 
	 * @param extension
	 * @return
	 */
	private static String getTargetFileName(String extension) {
		String curTime = String.valueOf(System.currentTimeMillis());
		Random random = new Random();
		String fileName = curTime + String.valueOf(random.nextInt(100000));
		String targetName = fileName + "." + extension;
		return targetName;
	}
	
	/**
	 * 判断后缀是否有效
	 * 
	 * @param allowExt
	 * @param fillExt
	 * @return
	 */
	private static boolean validExtension(String allowExt, String fillExt) {
		boolean rtn = false;
		String[] allExts = StringUtils.delimiteToArray(allowExt);
		for(String ext : allExts) {
			if(ext.equalsIgnoreCase(fillExt)) {
				rtn = true;
				break;
			}
		}
		return rtn;
	}
	
	/**
	 * 检查文件目录是否存在，如果不存在就创建。
	 *  
	 * @param path
	 */
	private static void makeDirectory(String path){
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 根据类型返回文件路径
	 * 
	 * @param type
	 * @param fileName
	 * @return 文件路径
	 */
	public static String getUploadPath(String type) {
		String path = "";
		if(type.equalsIgnoreCase(UPLOAD_TYPE_USER)) {
			path = USER_DIR;
		}
		else if(type.equalsIgnoreCase(UPLOAD_TYPE_WORKS)) {
			path = WORKS_IMAGE_DIR;
		}
		else if(type.equalsIgnoreCase(UPLOAD_TYPE_ATTACHMENT)) {
			path = ATTACHMENT_DIR;
		}
		return path;
	}
	
	/**
	 * 删除文件
	 * 
	 * @param type
	 * @param attachmentStr
	 * @return
	 */
	public static boolean deleteFile(String type, String attachmentStr) {
		boolean rtn = false;
		
		String filePath = getUploadPath(type);
		if(!StringUtils.isBlank(filePath)) {
			String attachment = attachmentStr;
			// 下载文件格式为2016-12-19582732983.jpg，年月格式中间的“-”应该被替换为文件路径分隔符
			if(attachmentStr.contains(FileUploadProcessor.MINUS_SEPARATOR)) {
				attachment = attachmentStr.replace(FileUploadProcessor.MINUS_SEPARATOR, File.separator);
			}
			
			File file = new File(filePath + attachment);
			rtn = file.delete();
		}
		return rtn;
	}
}
