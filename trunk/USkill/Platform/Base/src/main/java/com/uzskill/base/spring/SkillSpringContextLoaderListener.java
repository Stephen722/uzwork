package com.uzskill.base.spring;

import javax.servlet.ServletContextEvent;

import com.light.app.ApplicationContext;
import com.light.spring.SpringContextLoaderListener;
import com.uzskill.base.manager.BaseManager;

/**
 * 该类继承自ContextLoaderListener，当一个Web应用上下文初始化成功后，系统将取得该上下文并将其设置到ApplicationContext，
 * 方便以后在Action，servlet之外的类中可以使用该上下文获取Bean。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class SkillSpringContextLoaderListener extends SpringContextLoaderListener {
	
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		BaseManager baseManager = (BaseManager) ApplicationContext.getInstance().getBean("BaseManager");
		baseManager.destoryScheduledExecutor();
	}
}
