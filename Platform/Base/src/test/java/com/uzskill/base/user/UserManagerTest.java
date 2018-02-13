package com.uzskill.base.user;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.light.utils.DateUtils;
import com.light.utils.RequestUtils;
import com.uzskill.base.BaseTestCase;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.user.bean.User;

public class UserManagerTest extends BaseTestCase {
	@Autowired
	private UserManager userManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

//	@Test
//	public void testGetUserById() {
//		long start = System.currentTimeMillis();
//		User user = userManager.getUserById(1033056);
//		System.out.println("Get user by ID:" + (System.currentTimeMillis() - start));
//		assertTrue(user.getUserId() > 0);
//	}

//	@Test
//	public void testGetUserByPhone() {
//		User user = userManager.getUserByPhone("15000000001");
//		assertEquals("15000000001", user.getPhone());
//	}
//
//	@Test
//	public void testGetAllFriends() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testLogin() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testInsertUser() {
//		User user = new User();
//		user.setPhone("15800953600");
//		user.setLoginIp("127.0.0.2");
//		int newUserId = userManager.insertUser(user);
//		assertTrue(newUserId > 0);
		List<User> list = new ArrayList<User>();
//		long start = System.currentTimeMillis();
		for(int i = 1; i <= 10000; i++) {
			User user = new User();
			long phone = 15000000000l + i;
			user.setUserId(130000+i);
			user.setPhone(String.valueOf(phone));
			user.setNickname(String.valueOf(phone));
			user.setCreatedIp("127.0.0.2");
			user.setLoginIp("127.0.0.2");
			long currentTime = System.currentTimeMillis(); 
			String createdTime = DateUtils.getDate(currentTime);
			user.setCreatedTime(createdTime);
			user.setLoginTime(createdTime);
			list.add(user);
		}
		
//		long startDB1 = System.currentTimeMillis();
//		for(User u1 : list) {
//			userManager.insertUser(u1);
//		}
//		System.out.println("Insert Users:" + (System.currentTimeMillis() - startDB1));
		long startDB2 = System.currentTimeMillis();
		userManager.insertUserList(list);
		System.out.println("Insert Users in Batch:" + (System.currentTimeMillis() - startDB2));
		
	}
//
//	@Test
//	public void testUpdateUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testActivateUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUserTotal() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMakeFriends() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAcceptFriends() {
////		fail("Not yet implemented");
//		assertEquals(1, 2);
//	}

}
