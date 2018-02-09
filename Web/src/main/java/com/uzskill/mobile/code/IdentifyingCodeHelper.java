package com.uzskill.mobile.code;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.light.identifying.IdentifyingCodeType;
import com.light.identifying.ImageCodeProcessor;
import com.light.identifying.ShortMessageProcessor;
import com.light.identifying.bean.IdentifyingCode;
import com.light.record.Record;
import com.light.utils.StringUtils;
import com.light.utils.ValidationUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.user.UserQuery;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * Identifying code helper
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-15
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class IdentifyingCodeHelper {
	
	private static final String IMAGE_CODE = "ImageCode";
	private static final String SMS_CODE = "SMSCode";
		
	/**
	 * generate image code
	 * 
	 * @param response
	 * @param tokenId
	 * @param inRecord
	 * 
	 * @throws IOException
	 */
	public static void generateImageCode(HttpServletResponse response, String tokenId, Record inRecord) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		String codeType = inRecord.getStringValue(CommonFields.CODE_TYPE);
		if(isValidCodeType(codeType)) {
			IdentifyingCode imageCode = ImageCodeProcessor.generateImageCode(4);
			AccessTokenProcessor.getInstance().setCode(tokenId, codeType + IMAGE_CODE, imageCode);
			// output image
			ImageCodeProcessor.sendOutImageCode(response.getOutputStream(), imageCode.getCode());
		}
		else {
			PrintWriter wri = response.getWriter();
			wri.write("invalid request");
			wri.flush();
			wri.close();
		}
	}
	
	/**
	 * verify image code
	 * 
	 * @param tokenId
	 * @param inRecord
	 * 
	 * @return success/message record
	 */
	public static Record verifyImageCode(String tokenId, Record inRecord) {
		boolean success = false;
		String messageKey = "mobile.code.image.empty";
		
		Record rtnRecord = new Record();
		String imageCode = inRecord.getStringValue(CommonFields.IMAGE_CODE);
		String codeType = inRecord.getStringValue(CommonFields.CODE_TYPE);
		if(isValidCodeType(codeType)) {
			if(!StringUtils.isBlank(imageCode)) {
				IdentifyingCode usImageCode = AccessTokenProcessor.getInstance().getCode(tokenId, codeType + IMAGE_CODE);
				if(usImageCode == null || !usImageCode.isValid()) {
					success = false;
					messageKey = "mobile.code.image.expired";
				}
				else if(usImageCode.getCode().equalsIgnoreCase(imageCode)) {
					success = true;
					messageKey = "mobile.code.image.valid";
				}
				else {
					success = false;
					messageKey = "mobile.code.image.invalid";
				}
			}
		}
		else {
			success = false;
			messageKey = "mobile.code.type.invalid";
		}
		rtnRecord.setValue("success", success);
		rtnRecord.setValue("message", messageKey);
		return rtnRecord;
	}
	
	/**
	 * generate SMS code
	 * 
	 * @param tokenId
	 * @param inRecord
	 * 
	 * @return success/message record
	 */
	public static Record generateSMSCode(String tokenId, Record inRecord) {
		Record rtnRecord = new Record();
		boolean success = false;
		String messageKey = "mobile.code.SMS.send.empty";
		String cellPhone = inRecord.getStringValue(UserQuery.PHONE);
		String codeType = inRecord.getStringValue(CommonFields.CODE_TYPE);
		if(isValidCodeType(codeType)) {
			if(!StringUtils.isBlank(cellPhone) && ValidationUtils.isPhone(cellPhone)) {
				// 判断是否可以生成新的短信验证码
				boolean isOkGeneratSMSCode = true, isFirstCode = true;
				IdentifyingCode tokenSMSCode = AccessTokenProcessor.getInstance().getCode(tokenId, codeType + SMS_CODE);
				if(tokenSMSCode != null) {
					isFirstCode = false;
					isOkGeneratSMSCode = ShortMessageProcessor.getInstance().isOkGenerateNewShortMessageCode(tokenSMSCode);
				}
				
				if(isOkGeneratSMSCode) {
					IdentifyingCode SMSCode = ShortMessageProcessor.getInstance().generateShortMessageCode(true, 4);
					if(SMSCode != null && !StringUtils.isBlank(SMSCode.getCode())) {
						boolean sendSMSCode = ShortMessageProcessor.getInstance().sendShortMessageCode(cellPhone, SMSCode.getCellPhone(), IdentifyingCodeType.REGISTER);
						if(sendSMSCode) {
							success = true;
							messageKey = "mobile.code.SMS.send.success";
							
							// set the times of sending SMS
							if(!isFirstCode) {
								SMSCode.setTimes(SMSCode.getTimes() + 1);
							}
							// cache the SMS code
							AccessTokenProcessor.getInstance().setCode(tokenId, codeType + SMS_CODE, SMSCode);
						}
					}
				}
				else {
					success = false;
					messageKey = "mobile.code.SMS.send.duplicated";
				}
			}
			else {
				success = false;
				messageKey = "mobile.code.SMS.send.cellPhone.invalid";
			}
		}
		else {
			success = false;
			messageKey = "mobile.code.type.invalid";
		}
		rtnRecord.setValue("success", success);
		rtnRecord.setValue("message", messageKey);
		return rtnRecord;
	}
	
	
	/**
	 * verify SMS code
	 * 
	 * @param tokenId
	 * @param inRecord
	 * 
	 * @return success/message record
	 */
	public static Record verifySMSCode(String tokenId, Record inRecord) {
		boolean success = false;
		String messageKey = "mobile.code.SMS.empty";
		Record rtnRecord = new Record();
		
		String SMSCode = inRecord.getStringValue(CommonFields.SMS_CODE);
		String codeType = inRecord.getStringValue(CommonFields.CODE_TYPE);
		if(isValidCodeType(codeType)) {
//			if(!StringUtils.isBlank(SMSCode)) {
				IdentifyingCode usSMSCode = AccessTokenProcessor.getInstance().getCode(tokenId, codeType + SMS_CODE);
//				if(usSMSCode == null || !usSMSCode.isValid()) {
//					success = false;
//					messageKey = "mobile.code.SMS.expired";
//				}
//				else if(usSMSCode.getCode().equalsIgnoreCase(SMSCode)) {
//					success = true;
//					messageKey = "mobile.code.SMS.valid";
//				}
//				else {
//					success = false;
//					messageKey = "mobile.code.SMS.invalid";
//				}
//			}
			// Testing
			if("work".equalsIgnoreCase(SMSCode)) {
				success = true;
				messageKey = "mobile.code.SMS.valid";
			}
			else {
				success = false;
				messageKey = "mobile.code.SMS.invalid";
			}
		}
		else {
			success = false;
			messageKey = "mobile.code.type.invalid";
		}
		
		rtnRecord.setValue("success", success);
		rtnRecord.setValue("message", messageKey);
		return rtnRecord;
	}
	
	/**
	 * check the identifying code type
	 * 
	 * @param codeType
	 * 
	 * @return true/false
	 */
	private static boolean isValidCodeType(String codeType) {
		boolean rtn =  false;
		if(!StringUtils.isBlank(codeType)) {
			IdentifyingCodeType currentType = IdentifyingCodeType.valueOf(codeType.toUpperCase());
			if(currentType != null) {
				rtn = true;
			}
		}
		return rtn;
	}
}
