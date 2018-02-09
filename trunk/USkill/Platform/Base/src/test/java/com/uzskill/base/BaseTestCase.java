package com.uzskill.base;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:config/applicationConfig-light.xml",
    "classpath:config/applicationConfig-base.xml"
})
@Transactional
public class BaseTestCase {
	
}
