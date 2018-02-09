package com.light.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DES加密工具类
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class DESUtils {
	
	private static final String DES = "DES";
	private static final String DEFAULT_KEY = "UZWORK_DES_KEY_FOR_IDS";
	private static Key KEY = getKey(DEFAULT_KEY.getBytes());
	private static final Logger logger = LogManager.getLogger(DESUtils.class);

	/**
	 * 加密字符串
	 *
	 * @param enString 需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encrypt(String enString) {
		String rtn = "";
		try {
			Cipher encryptCipher = Cipher.getInstance(DES);
			encryptCipher.init(Cipher.ENCRYPT_MODE, KEY);
			byte[] enByte = encryptCipher.doFinal(enString.getBytes());
			rtn = byteArr2HexStr(enByte);
		} catch (Exception e) {
			logger.error("Failed to encrypt :" + enString, e);
		}
		return rtn;
	}

	/**
	 * 解密字符串
	 *
	 * @param deString 需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String deString) {
		String rtn = "";
		try {
			Cipher decryptCipher = Cipher.getInstance(DES);
			decryptCipher.init(Cipher.DECRYPT_MODE, KEY);
			byte[] deByte = decryptCipher.doFinal(hexStr2ByteArr(deString));
			rtn = new String(deByte);
		} catch (Exception e) {
			logger.error("Failed to decrypt :" + deString, e);
		}
		return rtn;
	}

	/**
	 * 取得解密后的整形ID
	 *
	 * @param deString 需解密的字符串
	 * @return 解密后的字符串
	 */
	public static int getIntDecrypt(String deString) {
		int id = 0;
		if(!StringUtils.isBlank(deString)) {
			id = StringUtils.getInt(decrypt(deString));
		}
		return id; 
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 *
	 * @param arrBTmp 构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private static Key getKey(byte[] arrBTmp) {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new SecretKeySpec(arrB, DES);
		return key;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串
	 *
	 * @param arrB 需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	private static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 *
	 * @param inString 需要转换的字符串
	 * @return 转换后的byte数组
	 */
	private static byte[] hexStr2ByteArr(String inString) {
		byte[] arrB = inString.getBytes();
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
}