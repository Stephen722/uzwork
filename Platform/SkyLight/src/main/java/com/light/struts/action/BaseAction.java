package com.light.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.light.app.ApplicationContext;
import com.light.exception.ApplicationException;
import com.light.exception.ExceptionHelper;
import com.light.record.JSONRecord;
import com.light.record.Record;
import com.opensymphony.xwork2.ActionContext;

/**
 * 基础Action类
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public abstract class BaseAction implements ServletRequestAware, ServletResponseAware {

	protected HttpSession session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected static final String SUCCESS = "success";
	protected static final String MESSAGE = "message";

	private static final Logger logger = LogManager.getLogger(BaseAction.class);
	
	/**
	 * 取得Request parameters
	 * 
	 * @return Record
	 */
	protected Record getInRecord(){
		Record inputRecord = new Record();
		// 从Request的到的参数都是String类型的，request.getParameterMap().getValue(name)和
		// request.getParameterValues(name)都将返回一个String[]。
		Enumeration<String> paramEn = request.getParameterNames();
		while(paramEn.hasMoreElements()) {
			String paramName = (String) paramEn.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if(paramValues.length > 1) {
				inputRecord.setValue(paramName, paramValues);
			}
			else {
				inputRecord.setValue(paramName, paramValues[0]);
			}
		}
		return inputRecord;
	}

	/**
	 * 输出JSON对象
	 * 
	 * @param obj
	 * @throws IOException
	 */
    protected void writeJSONResponse() throws IOException {
		writeJSONResponse(true, "", null);
	}
	
	/**
	 * 输出JSON对象
	 * 
	 * @param value
	 * @throws IOException
	 */
    protected void writeJSONResponse(Object value) throws IOException {
		writeJSONResponse(true, "", value);
	}
	
	/**
	 * 输出JSON对象
	 * 
	 * @param success
	 * @param obj
	 * @throws IOException
	 */
    protected void writeJSONResponse(boolean success, String message) throws IOException {
		writeJSONResponse(success, message, null);
	}

	/**
	 * 输出JSON对象
	 * 
	 * @param success
	 * @param message
	 * @param value
	 * @deprecated please use writeJSONResponse(code, message, value)
	 * @throws IOException
	 */
    protected void writeJSONResponse(boolean success, String message, Object value) throws IOException {
		int code = success ? 1 : 0;
    	JSONRecord jSONRecord = new JSONRecord(code, message, value);

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		PrintWriter wri = response.getWriter();
		wri.write(JSON.toJSONString(jSONRecord, SerializerFeature.WriteEnumUsingToString, SerializerFeature.DisableCircularReferenceDetect));
		wri.flush();
		wri.close();
	}
	
	/**
	 * 输出JSON对象
	 * 
	 * @param code
	 * @param obj
	 * @throws IOException
	 */
    protected void writeJSONResponse(int code, String message) throws IOException {
		writeJSONResponse(code, message, null);
	}

	/**
	 * 输出JSON对象
	 * 
	 * @param code
	 * @param message
	 * @param value
	 * @throws IOException
	 */
    protected void writeJSONResponse(int code, String message, Object value) throws IOException {
		JSONRecord jSONRecord = new JSONRecord(code, message, value);

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		PrintWriter wri = response.getWriter();
		wri.write(JSON.toJSONString(jSONRecord, SerializerFeature.WriteEnumUsingToString, SerializerFeature.DisableCircularReferenceDetect));
		wri.flush();
		wri.close();
	}
    
	/**
	 * 处理Ajax异常，以Json的形式输出异常类信息。
	 * success表示处理是否成功（程序运行到此处已经说明处理失败），如果处理失败，ajax请求和jqGrid将显示该message.
	 * 
	 * @param messageKey
	 * @param detailMessage
	 * @param e
	 */
	protected void handleAjaxError(String detailMessage, Exception e) {
		String messageKey = "applicationException.ajax.error";
		ApplicationException ae = ExceptionHelper.getInstance().handleException(messageKey, detailMessage, e);
		try {
			logger.error(ae.getMessage(), ae);
			writeJSONResponse(false, ApplicationContext.getInstance().getMessage(ae.getMessageKey()));
		}
		catch (IOException ioe) {
			throw new ApplicationException("Error in handling error for ajax.", ioe);
		}
	}
	
	/**
	 * 取得ServletContext。
	 * 
	 * @return ServletContext
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 取得ActionContext。
	 * 
	 * @return ActionContext
	 */
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}
	
	/**
	 * 实现ServletRequestAware的方法。
	 * 
	 * @param request
	 */
    public void setServletRequest(HttpServletRequest request) { 
        this.request = request;
        this.session = request.getSession();
    } 
    
	/**
	 * 实现ServletResponseAware的方法。
	 * 
	 * @param response
	 */
    public void setServletResponse(HttpServletResponse response) { 
    	this.response = response; 
    }

}
