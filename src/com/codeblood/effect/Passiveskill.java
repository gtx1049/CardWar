package com.codeblood.effect;

import com.codeblood.actors.Solder;

/**
 * 
 * Description: 被动技能 
 * 
 * @Author: Star
 * @CreateDate: 2012-9-2
 *
 */
public class Passiveskill implements Skill
{

	@Override
	public void buffOne(Solder solder)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * FunName: buffAttact
	 * Description: 增强攻击
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffAttact(Solder solder)
	{
		solder.setAttact(buffNumber(solder.getAttact()));
	}
	
	/**
	 * FunName: buffIntelligence
	 * Description: 增强智力
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffIntelligence(Solder solder)
	{
		solder.setIntelligence(buffNumber(solder.getIntelligence()));
	}
	
	/**
	 * FunName: buffGuard
	 * Description: 增强防御
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffGuard(Solder solder)
	{
		solder.setGuard(buffNumber(solder.getGuard()));
	}
	
	/**
	 * FunName: buffHitrate
	 * Description: 增强命中
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffHitrate(Solder solder)
	{
		solder.setHitrate(solder.getHitrate());
	}
	
	/**
	 * FunName: buffSpeed
	 * Description: 增强速度
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffSpeed(Solder solder)
	{
		solder.setSpeed(solder.getSpeed());
	}
	
	/**
	 * FunName: buffLuck
	 * Description: 增强运气
	 * 
	 * @param: solder需要增强的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	public void buffLuck(Solder solder)
	{
		solder.setLuck(solder.getLuck());
	}
	
	/**
	 * FunName: buffNumber
	 * Description: 返回数值
	 * 
	 * @param: number计算数
	 * @return: int返回数
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	private int buffNumber(int number)
	{
		return (int)((float)number * 0.15f + (float)number);
	}
}
