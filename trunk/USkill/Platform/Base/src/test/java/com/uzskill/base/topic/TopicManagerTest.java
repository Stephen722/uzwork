package com.uzskill.base.topic;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.light.utils.DateUtils;
import com.uzskill.base.BaseTestCase;
import com.uzskill.base.topic.bean.Topic;

public class TopicManagerTest extends BaseTestCase {

	@Autowired
	public TopicManager topicManager;
	@Test
	public void testGetTopic() {
		long start = System.currentTimeMillis();
	    	for(int i = 0; i < 10000; i++) {
				topicManager.getTopic(i);
			}
	    	System.out.println("Process time: " + (System.currentTimeMillis() - start));
		
	}
//
//	@Test
//	public void testGetTopTopics() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetNextTopics() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testInsertTopic() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteTopic() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testInsertComment() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteComment() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testInsertPraise() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeletePraise() {
//		fail("Not yet implemented");
//	}

}
