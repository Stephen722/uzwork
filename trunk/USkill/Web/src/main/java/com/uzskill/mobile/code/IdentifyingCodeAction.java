package com.uzskill.mobile.code;

import com.light.app.ApplicationContext;
import com.light.record.Record;
import com.uzskill.mobile.action.MobileBaseAction;

/**
 * Identifying code action
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class IdentifyingCodeAction extends MobileBaseAction {
	
	/**
	 * generate image code
	 */
	public void image() {
		try {
			IdentifyingCodeHelper.generateImageCode(response, getAccessTokenId(), getInRecord());
		} 
		catch (Exception e) {
			handleAjaxError("Failed to generate image code", e);
		}
	}
	
	/**
	 * generate SMS code
	 */
	public void SMS() {
		try {
			Record inRecord = getInRecord();
			String tokenId = getAccessTokenId();
			// verify the image code firstly
//			Record imageRecord = IdentifyingCodeHelper.verifyImageCode(tokenId, inRecord);
//			boolean success = imageRecord.getBooleanValue(SUCCESS);
//			String message = imageRecord.getStringValue(MESSAGE);
//			if(success) {
//				// generate SMS code
//				Record record = IdentifyingCodeHelper.generateSMSCode(tokenId, inRecord);
//				success = record.getBooleanValue(SUCCESS);
//				message = record.getStringValue(MESSAGE);
//			}
			
			// generate SMS code
			Record record = IdentifyingCodeHelper.generateSMSCode(tokenId, inRecord);
			boolean success = record.getBooleanValue(SUCCESS);
			String message = record.getStringValue(MESSAGE);
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message));
		} 
		catch (Exception e) {
			handleAjaxError("Failed to generate SMS code", e);
		}
	}
	
	/**
	 * verify image code
	 */
	public void verifyImage() {
		try {
			Record record = IdentifyingCodeHelper.verifyImageCode(getAccessTokenId(), getInRecord());
			boolean success = record.getBooleanValue(SUCCESS);
			String message = record.getStringValue(MESSAGE);
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message));
		}
		catch(Exception e) {
			handleAjaxError("Failed to verify image code", e);
		}
	}
	
	/**
	 * verify SMS code
	 */
	public void verifySMS() {
		try {
			Record record = IdentifyingCodeHelper.verifySMSCode(getAccessTokenId(), getInRecord());
			boolean success = record.getBooleanValue(SUCCESS);
			String message = record.getStringValue(MESSAGE);
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message));
		}
		catch(Exception e) {
			handleAjaxError("Failed to verify SMS code", e);
		}
	}

}
