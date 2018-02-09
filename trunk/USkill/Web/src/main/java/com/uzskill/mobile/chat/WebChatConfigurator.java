package com.uzskill.mobile.chat;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.apache.log4j.Logger;
/**
 * Web chat configuration
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-10-06
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebChatConfigurator extends Configurator {
	private static Logger logger = Logger.getLogger(WebChatConfigurator.class);
	@Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        logger.debug("Web chat socket hand shaking");
    }
}
