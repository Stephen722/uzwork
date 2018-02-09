package com.light.struts.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.utils.StringUtils;
/**
 * 负责处理Token相关操作。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-09-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class TokenProcessor {
	
	private static final Logger logger = LogManager.getLogger(TokenProcessor.class);
	private static final String BEAN_NAME = "TokenProcessor";
	private static final String TOKEN = "UZWorkToken";
    private static TokenProcessor instance;

    private TokenProcessor () {
    	
    }
    
    /**
	 * 返回一个TokenProcessor实例。
	 */
    public static final TokenProcessor getInstance() {
    	if (instance == null) {
    		synchronized (TokenProcessor.class) {
    			if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (TokenProcessor) ApplicationContext.getInstance().getBean(BEAN_NAME);
					}
					else {
						instance = new TokenProcessor();
					}
    			}
			}
			logger.info("Initialized an instance of TokenProcessor");
		}
        return instance;
    }

    /**
     * 如果当前session中存在的token与作为参数提交的token匹配的话，返回true。
     * 以下情况都返回false：
     * <ul>
     * <li>当前请求没有相关Session。</li>
     * <li>当前Session中没有token。</li>
     * <li>请求参数中没有token。</li>
     * <li>两个token不匹配。</li>
     * </ul>
     *
     * @param request
     * @return boolean
     */
    public boolean isTokenValid(HttpServletRequest request) {
        return this.isTokenValid(request, false);
    }

    /**
     * 如果当前session中存在的token与作为参数提交的token匹配的话，返回true。并且根据参数是否删除token。
     * 以下情况都返回false：
     * <ul>
     * <li>当前请求没有相关Session。</li>
     * <li>当前Session中没有token。</li>
     * <li>请求参数中没有token。</li>
     * <li>两个token不匹配。</li>
     * </ul>
     *
     * @param request
     * @param remove
     * @return boolean
     */
    public boolean isTokenValid(HttpServletRequest request, boolean remove) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String saved = (String) session.getAttribute(TOKEN);
        if (StringUtils.isBlank(saved)) {
            return false;
        }
        if (remove) {
            this.removeToken(session);
        }
        String token = request.getParameter(TOKEN);
        if (StringUtils.isBlank(token)) {
            return false;
        }

        return saved.equals(token);
    }

    /**
     * 删除token。
     *
     * @param request
     */
    public void removeToken(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute(TOKEN);
    }
    
    /**
     * 存储token到当前的session。
     * 
     * @param request
     */
    public void initializeToken(HttpSession session) {
        String token = generateToken(session);
        if (token != null) {
            session.setAttribute(TOKEN, token);
        }
    }

    /**
     * 生成一个新的token。
     * 当前时间加上一个随机数。
     * 
     * @param session
     * @return String
     */
    public String generateToken(HttpSession session) {
        try {
        	Random random = new Random();
            byte id[] = session.getId().getBytes();
            long current = System.currentTimeMillis() + random.nextInt(10000);
            byte now[] = new Long(current).toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(id);
            md.update(now);
            return toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
        	logger.error("Failed to generate token");
            return null;
        }
    }

    private String toHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }
}
