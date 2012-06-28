package com.codeblood.effect;

import com.codeblood.actors.Solder;

/**
 * 
 * Description: �������� 
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
	 * Description: ��ǿ����
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ��ǿ����
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ��ǿ����
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ��ǿ����
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ��ǿ�ٶ�
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ��ǿ����
	 * 
	 * @param: solder��Ҫ��ǿ��ʿ��
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
	 * Description: ������ֵ
	 * 
	 * @param: number������
	 * @return: int������
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-2
	 */
	private int buffNumber(int number)
	{
		return (int)((float)number * 0.15f + (float)number);
	}
}
