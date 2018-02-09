package com.uzskill.base.skill.bean;

import com.uzskill.base.common.bean.Praise;

/**
 * Skill praise java bean
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class SkillPraise extends Praise {
	
	private long skillId;
	
	public long getSkillId() {
		return skillId;
	}
	public void setSkillId(long skillId) {
		this.skillId = skillId;
	}
}