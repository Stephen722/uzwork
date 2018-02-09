package com.light.identifying;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.light.app.ApplicationContext;
import com.light.identifying.bean.IdentifyingCode;
import com.light.utils.StringUtils;
/**
 * 发送短信相关逻辑处理类
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-31
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ShortMessageProcessor {

	private static final Logger logger = LogManager.getLogger(ShortMessageProcessor.class);
	private static ShortMessageProcessor instance;
	private static final String BEAN_NAME = "ShortMessageProcessor";
	private static final String SIGN_NAME = "UZWORK";
	private static final String APPLICATION_SMS_ENDPOINT = "application.sms.endpoint";
	private static String SMSEndPoint;
	
	private ShortMessageProcessor() {
		
	}

	/**
	 * 返回一个ShortMessageProcessor实例。
	 *
	 * @return ShortMessageProcessor
	 */
	public static final ShortMessageProcessor getInstance() {
		if (instance == null) {
			synchronized (ShortMessageProcessor.class) {
				if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (ShortMessageProcessor) ApplicationContext.getInstance().getBean(BEAN_NAME);
					} else {
						instance = new ShortMessageProcessor();
					}
					logger.info("Initialized an instance of ShortMessageProcessor.");
					
					SMSEndPoint = ApplicationContext.getInstance().getProperty(APPLICATION_SMS_ENDPOINT, "");
					if(StringUtils.isBlank(SMSEndPoint)) {
						logger.error("Application SMS Endpoint is missing.");
					}
				}
			}
		}
		return instance;
	}
	
	/**
	 * 判断是否可以生成新的短信验证码。
	 * 如果同一个手机号在同一个token中请求同一类短信验证码超过三次，则不再发送验证码。
	 * 
	 * @param code
	 * @return
	 */
	public boolean isOkGenerateNewShortMessageCode(IdentifyingCode code) {
		boolean rtn = false;
		if(code == null) {
			rtn = true;
		}
		else if(code.getTimes() > 3) {
			rtn = false;
			logger.warn("System already generated SMS message more than 3 times in short time for:"+code.getCellPhone());
		}
		else if(code.gtMinutes(1) || !code.isValid()) {
			rtn = true;
		}
		
		return rtn;
	}
	
	/**
	 * 生成验证码
	 * 
	 * @param number 是否只含有数字
	 * @param length 验证码长度
	 * @return 验证码
	 */
	public IdentifyingCode generateShortMessageCode(boolean number, int length) {
		IdentifyingCode idCode = new IdentifyingCode();
		idCode.setLength(length);
		
		String code = "";
		String scope = number ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = scope.length();
		boolean bDone = true;
		do {
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = scope.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				code += scope.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		// make sure the length is always correct 
		if(code.length() > length) {
			code = code.substring(0, length);
		}
			
		idCode.setCode(code);
		idCode.setGeneratedTime(System.currentTimeMillis());
		idCode.setTimes(1);
		return idCode;
	}
	
	/**
	 * 发送验证码
	 * 
	 * @param cellPhone 手机号
	 * @param code 验证码
	 * @param messageType 短信类型
	 * @return 验证码是否发送成功
	 */
	public boolean sendShortMessageCode(String cellPhone, String code, IdentifyingCodeType messageType) {
		// asynchronous invoke
		new Thread() {  
            public void run() {
            	String SMSTemplate = "SMS_75750158";
        		switch (messageType) {
        			case REGISTER: SMSTemplate = "SMS_75750158"; break;
        			case FORGET_PASSWORD: SMSTemplate = "SMS_75890170"; break;
        			default: break;
        		}
            	publishSMS(SMSTemplate, code, cellPhone);
            }  
	    }.start();
		
		return true;
	}

	private void publishSMS(String SMSTemplate, String code, String cellPhone) {
		MNSClient client = null;
		try {
			// 1. 获取主题引用
	        CloudAccount account = new CloudAccount("LTAIzd0Muk0fTvOo", "7N6Tl1NCZeQTyt7QhcPXhv8gWdBgkz", SMSEndPoint);
	        client = account.getMNSClient();
	        CloudTopic topic = client.getTopicRef("sms.topic-cn-beijing");
	        // 2. 设置SMS消息体（必须）
	        RawTopicMessage msg = new RawTopicMessage();
	        msg.setMessageBody("sms-message");
	        // 3 生成SMS消息属性
	        MessageAttributes messageAttributes = new MessageAttributes();
	        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
	        // 3.1 设置发送短信的签名（SMSSignName）
	        batchSmsAttributes.setFreeSignName(SIGN_NAME);
	        // 3.2 设置发送短信使用的模板（SMSTempateCode）
	        batchSmsAttributes.setTemplateCode(SMSTemplate);
	        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
	        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
	        smsReceiverParams.setParam("SMSCode", code);
	        // smsReceiverParams.setParam("$YourSMSTemplateParamKey2", "$value2");
	        // 3.4 增加接收短信的号码
	        batchSmsAttributes.addSmsReceiver(cellPhone, smsReceiverParams);
	        // batchSmsAttributes.addSmsReceiver(cellPhone2, smsReceiverParams);
	        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            logger.debug(ret.toString());
        } 
		catch (ServiceException se) {
            logger.error(se.getErrorCode() + se.getRequestId(), se);
        }
		catch (Exception e) {
        		logger.error(e.getMessage());
        }
		finally {
			client.close();
		}
	}
}