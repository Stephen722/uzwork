package com.uzskill.base.skill.bean;

import java.io.Serializable;
/**
 * Skill java bean
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class Skill implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int skillId;
	private int userId;
	private String content;
	private String createdTime;
	private int categoryId;
	private int imageB;
	
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getImageB() {
		return imageB;
	}
	public void setImageB(int imageB) {
		this.imageB = imageB;
	}
}
