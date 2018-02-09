package com.uzskill.base.skill;

import java.util.List;

import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.skill.bean.Skill;
import com.uzskill.base.skill.bean.SkillComment;
import com.uzskill.base.skill.bean.SkillPraise;
import com.uzskill.base.topic.bean.Topic;

/**
 * Skill manager
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-27
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface SkillManager {
	
	/**
	 * Get skill by ID
	 * 
	 * @param skillId
	 * @return skill
	 */
	public Skill getSkill(int skillId);
	
	/**
	 * Get the top 10 skills
	 * 
	 * @return
	 */
	public List<Skill> getTopSkills();
	
	/**
	 * Get the next 10 skills from start
	 * 
	 * @param start
	 * @return
	 */
	public List<Skill> getNextSkills(int start);
	
	/**
	 * Insert a new skill
	 * 
	 * @param skill
	 * @return affected rows
	 */
	public int insertSkill(Skill skill);
	
	/**
	 * Delete a skill
	 * 
	 * @param skillId
	 * @return affected rows
	 */
	public int deleteSkill(int skillId);
	
	/**
	 * Insert a new skill comment
	 * 
	 * @param skill comment
	 * @return affected rows
	 */
	public int insertComment(SkillComment comment);
	
	/**
	 * Delete a skill comment
	 * 
	 * @param skillId
	 * @param commentId
	 * @return affected rows
	 */
	public int deleteComment(int skillId, int commentId);

	/**
	 * Insert a new skill praise
	 * 
	 * @param skill praise
	 * @return affected rows
	 */
	public int insertPraise(SkillPraise praise);
	
	/**
	 * Delete a skill praise
	 * 
	 * @param skillId
	 * @param praiseId
	 * @return affected rows
	 */
	public int deletePraise(int skillId, int praiseId);
	
}
