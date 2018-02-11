package com.uzskill.base.skill;

/**
 * Constants for Skill field\Redis key\DB query
 * 
 * <p>(C) 2018 www.uzwork.com (UZWork)</p>
 * Date:  2018-01-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class SkillQuery {
	
	/*	
	 * Skill has the following Redis objects:
	 * 
	 * skill:id - string which for auto increase id.
	 * skill:map - hash which contains the skills, key = skill id, value = skill jSon string.
	 * skill:set - sorted set which contains the skills' id, score = skill id. 
	 * skill:insert - list which contains all the newly added skills.
	 * skill:delete - list which contains all the deleted skills.
	 * 
	 * skill:comment:id - string which for auto increase id.
	 * skill:comment:insert - list which contains all the newly added skill comments.  
	 * skill:comment:delete - list which contains all the deleted skill comments.
	 * sc:{skillId} - sorted set (limited size) which contains all the comments of specified skill, score = skill comment id.
	 * 
	 * skill:praise:id - string which for auto increase id.
	 * skill:praise:insert - list which contains all the newly added skill praises. 
	 * skill:praise:delete - list which contains all the deleted skill praises.
	 * sp:{skillId} - sorted set (limited size) which contains all the praise of specified skill, score = skill praise id.
	 * 
	 * */
	public static final String REDIS_SKILL = "skill";
	public static final String REDIS_SKILL_COMMENT = "skill:comment";
	public static final String REDIS_SKILL_PRAISE = "skill:praise";
	public static final String REDIS_SKILL_ID = "skill:id";
	public static final String REDIS_SKILL_COMMENT_ID = "skill:comment:id";
	public static final String REDIS_SKILL_PRAISE_ID = "skill:praise:id";
	public static final String REDIS_SKILL_SET = "skill:set";
	public static final String REDIS_SKILL_MAP = "skill:map";
	
	// DB queries
	public static final String GET_SKILL_BY_ID = "SK_Skill.getSkillById";
	public static final String INSERT_SKILL = "SK_Skill.insertSkill";
	public static final String INSERT_SKILL_COMMENT = "SK_Skill.insertSkillComment";
	public static final String INSERT_SKILL_PRAISE = "SK_Skill.insertSkillPraise";
	public static final String DELETE_SKILL = "SK_Skill.deleteSkill";
	public static final String DELETE_SKILL_COMMENT = "SK_Skill.deleteSkillComment";
	public static final String DELETE_SKILL_PRAISE = "SK_Skill.deleteSkillPraise";

}
