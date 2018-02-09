package com.uzskill.base.topic.bean;

import com.uzskill.base.common.bean.Praise;

/**
 * Topic comment java bean
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class TopicPraise extends Praise {
	
	private long topicId;
	
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
}