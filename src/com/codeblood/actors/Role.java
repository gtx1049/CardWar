package com.codeblood.actors;

/**
 * 
 * @author Star
 * 
 * Description: ��ɫ����Ϸ�������Եĵ�λ
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
	 * Description: ��ʾ���ԣ������ã��˺����Ѿ���ȡ��
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	
	public void displayAttribute()
	{
		System.out.println("������ " + attact);
		System.out.println("������ " + guard);
		System.out.println("������ " + intelligence);
		System.out.println("�ٶȣ� " + speed);
		System.out.println("���У� " + hitrate);
		System.out.println("������ " + luck);
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
