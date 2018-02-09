package com.light.struts.mapper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;

import com.light.app.ApplicationContext;
import com.light.utils.StringUtils;
import com.opensymphony.xwork2.config.ConfigurationManager;

/**
 * 扩展DefaultActionMapper类。
 * 1)从Request中取得method参数用来设置ActionMapping中的method，method名称默认为process。
 * 2)系统中不采用Struts2的标签，所有在此忽略相关处理。 
 * 3)系统URL默认采用两种方式，无后缀和以struts.action.extension指定的后缀。
 *   无后缀有两种指定方式：
 *   a. application.dynamic.namespace.map配置的namespace下所有的action
 *   b. application.dynamic.action.map配置的所有action
 *   其中，以b的方式还可以配置多个参数，其格式为：namespace/action:[parameter1,parameter2,parameter3]
 *   对应的URL格式：namespace/action/parameterValue1/parameterValue2/parameterValue3
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class StrutsActionMapper extends DefaultActionMapper {
	// 自定义的method名称
	private static final String ACTION_METHOD_NAME = "application.action.method.name";
	// 取得配置的namespace，这些namespace下面的所有action都以无后缀的形式访问
	// 只要配置过namespace，那么其下一级(不是多级)的子目录也应该视作以无后缀访问，如：已经配置namespace为mobile，那么mobile/user也应该可以被访问
	private static final String DYNAMIC_NAMESPACE_MAP = "application.dynamic.namespace.map";
	// 取得配置的action，包括他们对于的参数配置信息
	private static final String DYNAMIC_ACTION_MAP = "application.dynamic.action.map";
	private static String method_name; 					      // 方法参数名称
	private static List<String> dynamicNamespaceList;         // 动态name space
	private static Map<String, String> dynamicActionMap;      // 动态action

	static {
		synchronized (StrutsActionMapper.class) {
			method_name = ApplicationContext.getInstance().getProperty(ACTION_METHOD_NAME, "process");
			dynamicNamespaceList = new ArrayList<String>();
			dynamicActionMap = new Hashtable<String, String>();
			String dynamicNamespace = ApplicationContext.getInstance().getProperty(DYNAMIC_NAMESPACE_MAP, "");
			String dynamicAction = ApplicationContext.getInstance().getProperty(DYNAMIC_ACTION_MAP, "");
			if (dynamicNamespace.length() > 0) {
				String[] nsMappers = dynamicNamespace.split(";");
				for (String nsMapper : nsMappers) {
					if (!StringUtils.isBlank(nsMapper)) {
						dynamicNamespaceList.add("/"+nsMapper);
					}
				}
			}
			
			if (dynamicAction.length() > 0) {
				String[] actionsMappers = dynamicAction.split(";");
				for (String actionsMapper : actionsMappers) {
					if (!StringUtils.isBlank(actionsMapper)) {
						String[] keyValue = actionsMapper.split(":");
						if (keyValue != null) {
							String parms = "";
							if (keyValue.length > 1){
								parms = keyValue[1];
								// 多个参数用[]来配置的，所以需要特殊处理。
								if(parms.startsWith("[")){
									parms = parms.substring(1);
								}
								if(parms.endsWith("]")){
									parms = parms.substring(0, parms.length()-1);
								}
							}
							// 没有参数的Action默认参数为空
							dynamicActionMap.put(keyValue[0].trim(), parms.trim());
						}
					}
				}
			}
		}
	}

	public StrutsActionMapper() {
		
	}
	
	protected boolean alwaysSelectFullNamespace = false;
	
	/**
	 * 取得当前Request对于的ActionMapping。
	 * 
	 * @param request
	 * @param configManager
	 * @return ActionMapping
	 */
	public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
		ActionMapping mapping = new ActionMapping();
		String uri = RequestUtils.getUri(request);
		// 解决带有sessionid的uri里所包含的“;”。
		int indexOfSemicolon = uri.indexOf(";");
		uri = (indexOfSemicolon > -1) ? uri.substring(0, indexOfSemicolon) : uri;
		// 在去掉后缀之前判断是否有后缀，供后续逻辑使用
		boolean hasExt = hasExtension(uri);
		
		uri = dropExtension(uri, mapping);
		if (uri == null) {
			return null;
		}
		
		// 对于无后缀的URI，必须先处理URI中参数部分，返回一个不包含参数的URI，继续后续处理
		if(!hasExt) {
			uri = handleParameter(request, uri);	
			if(uri == null){
				return null;
			}
		}
		
		parseNameAndNamespace(uri, mapping, configManager);
	    handleSpecialParameters(request, mapping);
	    mapping = parseActionName(mapping);
	    if (mapping.getName() == null) {
			return null;
		}
	    
    	// 判断是否为已配置的Action和Namespace
	    if(handleNonConfiguredAction(hasExt, mapping) == null) {
	    	return null;
	    }
	    		
		handleMethod(request, mapping);
		return mapping;
	}

	/**
	 * 系统可根据application.dynamic.action.map指定特殊的URL。
	 * 如：domain/product/123, domain/product/123/, domain/namespace/product/123；
	 * 对于这种形式的URI，系统解析后将去dynamicMap中寻找是否有对应的action，如果有则做相应的参数处理；如果dynamicMap
	 * 中有product，那么上述URI将被解析为domain/product 和 request.setAttribute(dynamicMap.get("product"), "123")。
	 * 
	 * 系统支持多个参数配置，其配置格式为：actionName:[param1,param2,param3]，对应的URL格式：actionName/value1/value2/value3。
	 * 注意此处的actionName包含namespace名称和action名称。
	 * 
	 * @param request
	 * @param uri
	 * @return String 除掉参数后的namespace和action
	 */
	private String handleParameter(HttpServletRequest request, String uri) {
		String tUri = uri;
		// uri=/namespace/actionName/value1/value2/value3
		if (tUri.endsWith("/")) {
			tUri = tUri.substring(0, tUri.length() - 1);
		}
		if (tUri.startsWith("/")) {
			tUri = tUri.substring(1);
		}
		// 取得包含namespace和action的字符串，形如：/namespace/actionName，前后无“/”。
		String nsActionName = getURIWithoutParameter(tUri);
		if(nsActionName == null) {
			return null;
		}
		// 当有参数时候，处理参数。
		if(tUri.length() >= nsActionName.length() + 1){
			// 从namespace和action组合字符串中取得action
			String actionName = nsActionName.substring(nsActionName.lastIndexOf("/") + 1);
			// 取得参数名和值，注意值一定要从原来URI中取得。
			String paramName = (String) dynamicActionMap.get(actionName);
			String paramValue = tUri.substring(tUri.indexOf(actionName) + actionName.length() + 1);
			// 如果参数名含有","，说明是多个参数。
			if(paramName != null){
				if (paramName.indexOf(",") > -1) {
					parseMultiParameter(request, paramName, paramValue);
				}
				else {
					request.setAttribute(paramName, paramValue);
				}
			}
		}
		
		// 一定要加上前置"/"
		return "/" + nsActionName;
	}
	
	/**
	 * 系统所有无后缀的action必须是预先配置过的，否则不允许以无后缀形式访问。
	 * 
	 * @param hasExt
	 * @param mapping
	 * @return String
	 */
	private String handleNonConfiguredAction(boolean hasExt, ActionMapping mapping) {
		boolean configuredAction = dynamicActionMap.containsKey(mapping.getName());
		boolean configuredNamespace = false;
		if(!StringUtils.isBlank(mapping.getNamespace())) {
			String tempNS = mapping.getNamespace();
			// 只要配置过namespace，那么其下一级(不是多级)的子目录也应该视作以无后缀访问，如：已经配置namespace为/mobile，那么/mobile/user也应该可以被访问
			// 注意：此处的namespace的前面是含有“/”
			if(tempNS.lastIndexOf("/") > 0) {
				tempNS = tempNS.substring(0, tempNS.lastIndexOf("/"));
			}
			configuredNamespace = dynamicNamespaceList.contains(tempNS);
		}
		// 如果配置了该namespace或者action为无后缀访问，则不允许带后缀访问
		if(hasExt) {
			if(configuredAction || configuredNamespace) {
				return null;	
			}
		}
		else {
			// 如果配置的namespace中包含当前namespace，则其下所有action只允许以无后缀形式访问。
			String namespace = mapping.getNamespace();
		    if(StringUtils.isBlank(namespace) || "/".equals(namespace)) {
		    	if(!configuredAction){
					return null;
				}
		    }
		    else {
		    	if(!configuredNamespace && !configuredAction) {
		    		return null;
		    	}
		    }
		}
		return mapping.getName();
	}
	
	/**
	 * 解析多个参数的Uri，参数名以","分割，参数值以"/"分割。缺省的参数值为""。
	 * 
	 * @param request
	 * @param paramName
	 * @param paramValue
	 */
	private void parseMultiParameter(HttpServletRequest request, String paramName, String paramValue) {
		String[] params = paramName.split(",");
		String[] values = paramValue.split("/");
		// 取参数和值最少的来匹配
		int length = Math.min(params.length, values.length);
		for (int i = 0; i < length; i++) {
			request.setAttribute(params[i], i <= values.length ? values[i] : "");
		}
	}
	
	/**
	 * 返回去掉参数后的URI，包含namespace和action
	 * 
	 * @param uri
	 * @return String
	 */
	private String getURIWithoutParameter(String uri){
		String newUri = uri;
		String namespace = "";
		String[] actions = uri.split("/");
		boolean configuredAction = false;
		// 如果找到已经配置过的action，则返回namespace和action组合
		for(String action: actions){
			if(dynamicActionMap.containsKey(action)) {
				configuredAction = true;
				int pos = uri.indexOf(action);
				if(pos > 0) {
					pos = pos - 1;
				}
				namespace = uri.substring(0, pos);
				if(namespace.length() > 0) {
					newUri = namespace + "/" + action;
				}
				else {
					newUri = action;
				}
				break;
			}
		}
		// 该action没有配置，则判断其namespace是否配置过
		if(!configuredAction){
			// 对于没有配置的action，且没有namespace，直接返回。
			if(actions.length == 1){
				return null;
			}
			else {
				String tempNS = uri.substring(0, uri.lastIndexOf("/"));
				// 只要配置过namespace，那么其下一级(不是多级)的子目录也应该视作以无后缀访问，如：已经配置namespace为mobile，那么mobile/user也应该可以被访问
				// 注意：此处的namespace的前面是没有“/”
				if(tempNS.indexOf("/") > 0) {
					tempNS = tempNS.substring(0, tempNS.indexOf("/"));
				}
				if(dynamicNamespaceList.contains("/" + tempNS)){
					newUri = uri;
				}
				else {
					return null;
				}
			}
		}
		
		return newUri;
	}
	
	/**
	 * 判断给定的Action是否有后缀。
	 * 
	 * @param name
	 * @param mapping
	 * @return boolean
	 */
	private boolean hasExtension(String name) {
		if (extensions == null) {
			return false;
		}
		for (String ext : extensions) {
			if (!"".equals(ext)) {
				String extension = "." + ext;
				if (name.endsWith(extension)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void handleMethod(HttpServletRequest request, ActionMapping mapping){
		String method = request.getParameter(method_name);
		if (method == null) {
			method = (String) request.getAttribute(method_name);
		}
		if (method != null && method.length() > 0 && mapping != null) {
			mapping.setMethod(method);
		}
		// 处理get/post中的process参数
//		Map<String,String[]> parmMap = (Map<String,String[]>)request.getParameterMap();
//		if(mapping != null && parmMap.containsKey(method_name)) {
//			method = parmMap.get(method_name)[0];
//			if(!StringUtils.isBlank(method)) {
//				mapping.setMethod(method);
//			}
//		}
	}

}