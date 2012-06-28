package com.codeblood.actors;

/**
 * 
 * @author Star
 * 
 * Description: 角色，游戏中有属性的单位
 *
 */

public class Role
{
	protected int attact;
	protected int guard;
	protected int intelligence;
	protected int speed;
	protected int hitrate;
	protected int luck;
	
	/**
	 * FunName: displayAttribute
	 * Description: 显示属性，测试用，此函数已经被取代
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	
	public void displayAttribute()
	{
		System.out.println("攻击： " + attact);
		System.out.println("防御： " + guard);
		System.out.println("智力： " + intelligence);
		System.out.println("速度： " + speed);
		System.out.println("命中： " + hitrate);
		System.out.println("运气： " + luck);
	}
	
	//Getters and Setters
	public int getAttact()
	{
		return attact;
	}

	public void setAttact(int attact)
	{
		this.attact = attact;
	}

	public int getGuard()
	{
		return guard;
	}

	public void setGuard(int guard)
	{
		this.guard = guard;
	}

	public int getIntelligence()
	{
		return intelligence;
	}

	public void setIntelligence(int intelligence)
	{
		this.intelligence = intelligence;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getHitrate()
	{
		return hitrate;
	}

	public void setHitrate(int hitrate)
	{
		this.hitrate = hitrate;
	}

	public int getLuck()
	{
		return luck;
	}

	public void setLuck(int luck)
	{
		this.luck = luck;
	}
}
