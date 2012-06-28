package com.codeblood.actors;

import com.codeblood.effect.Skill;
import com.codeblood.global.Analyze;
import com.codeblood.global.Const;

/**
 * 
 * @author Star
 * 
 * Description: ���飬ÿλ�����3֧����
 *              ÿ��������5λʿ��
 *
 */
public class Team
{
	private Solder solders[];
	
	public Team()
	{
		solders = new Solder[Const.TEAM_SOLDER_NUM];
	}
	
	/**
	 * FunName: setSolder
	 * Description: ���ö����еĶ�Ա
	 * 
	 * @param: nameʿ��
	 *         indexʿ�������
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public void setSolder(String name, int index)
	{
		solders[index] = Analyze.getInstance().produceSolder(name);
	}
	
	/**
	 * FunName: setSolder
	 * Description: ���ö����еĶ�Ա
	 * 
	 * @param: solderʿ��
	 *         indexʿ�������
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-11
	 */
	public void setSolder(Solder solder, int index)
	{
		solders[index] = solder;
	}
	
	/**
	 * FunName: getSolder
	 * Description: ���ö����еĶ�Ա
	 * 
	 * @param: index��Ҫ�õ���ʿ���ı��
	 * @return: Solder����ʿ��
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public Solder getSolder(int index)
	{
		return solders[index];
	}
	
	/**
	 * FunName: checkUnique
	 * Description: ����Ƿ�����
	 * 
	 * @param: solder�����ʿ��
	 * @return: boolean����true˵��δ����
	 * 			����false˵���Ѿ�����
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public boolean checkUnique(Solder solder)
	{
		for(int i = 0; i < Const.TEAM_SOLDER_NUM; i++)
		{
			if(solder.getName() == solders[i].getName())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * FunName: setbuffKill
	 * Description: ������������
	 * 
	 * @param: theskill����
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public void setbuffKill(Skill theskill)
	{
		for(int i = 0; i < Const.TEAM_SOLDER_NUM; i++)
		{
			solders[i].setbuffSkill(theskill);
		}
	}
}
