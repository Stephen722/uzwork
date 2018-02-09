package com.uzskill.mobile.token;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.identifying.bean.IdentifyingCode;
import com.light.redis.RedisDao;
import com.light.struts.action.TokenProcessor;
import com.light.utils.DESUtils;
import com.light.utils.RequestUtils;
import com.light.utils.StringUtils;
/**
 * 负责处理Access Token相关操作。
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class AccessTokenProcessor {
	
	public static final String ACCESS_TOKEN = "Access-Token";
	public static final String ACCEPT_CHANNEL = "Accept-Channel";
	public static final String ACCESS_WEB = "browser";
	public static final String ACCESS_APP = "UZAPP_V1";
	
	private static final String REDIS_DAO = "RedisDao";
    private static final String BEAN_NAME = "AccessTokenProcessor";
    private static RedisDao redis;
    private static AccessTokenProcessor instance;
	
	
    private static Logger logger = Logger.getLogger(AccessTokenProcessor.class);
    
    private AccessTokenProcessor () {

    }
    
    /**
	 * 返回一个TokenProcessor实例。
	 */
    public static final AccessTokenProcessor getInstance() {
    	if (instance == null) {
    		synchronized (AccessTokenProcessor.class) {
    			if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (AccessTokenProcessor) ApplicationContext.getInstance().getBean(BEAN_NAME);
					}
					else {
						instance = new AccessTokenProcessor();
					}
					// init redis dao
					if (ApplicationContext.getInstance().hasBean(REDIS_DAO)) {
						redis = (RedisDao) ApplicationContext.getInstance().getBean(REDIS_DAO);
					}
					else {
						redis = new RedisDao();
					}
    			}
			}
			logger.info("Initialized an instance of AccessTokenProcessor.");
			
		}
        return instance;
    }
    
    /**
     * 生成Token
     * 
     * @param userId
     * @param request
     * @return String
     */
    public AccessToken initializeToken(int userId, HttpServletRequest request) {
    	AccessToken mToken = new AccessToken();
    	String idString = TokenProcessor.getInstance().generateToken(request.getSession());
    	String desUserId = DESUtils.encrypt(String.valueOf(userId));
    	String ipAddress = RequestUtils.getIpAddress(request);
    	String desIpAddress = DESUtils.encrypt(ipAddress);
    	
    	mToken.setUserId(userId);
    	mToken.setIpAddress(ipAddress);
    	mToken.setId(idString + desUserId + desIpAddress);
    	return mToken;
    }
    
    /**
     * 根据Token字符串取得token对象
     * 
     * @param tokenId
     * @return AccessToken
     */
    public AccessToken getToken(String tokenId) {
    	AccessToken accToken = null;
    	// tokenId(32) + userId(16) + ip(at least 16)
    	if(!StringUtils.isBlank(tokenId) && (tokenId.length() >= 64)) {
    		accToken = new AccessToken();
    		accToken.setId(tokenId);
    		String userIdStr = tokenId.substring(32, 48);
    		String ipStr = tokenId.substring(48);
    		int userId = DESUtils.getIntDecrypt(userIdStr);
    		String ipAddress = DESUtils.decrypt(ipStr);
    		accToken.setUserId(StringUtils.getInt(userId));
    		accToken.setIpAddress(ipAddress);
    	}
    	return accToken;
    }
    
    /**
     * 根据Token ID和验证码类型设置验证码
     * 
     * @param tokenId
     * @param smsType
     * @return
     */
    public IdentifyingCode getCode(String tokenId, String smsType) {
    	return redis.hGet(tokenId, smsType, IdentifyingCode.class);
    }
    
    /**
     * 设置验证码，通知设置该tokenID 24小时的过期时间。
     * 
     * @param tokenId
     * @param smsType
     * @param code
     */
    public void setCode(String tokenId, String smsType, IdentifyingCode code) {
    	redis.hSet(tokenId, smsType, code);
    	redis.expire(tokenId, 24 * 3600);
    }
	
}
