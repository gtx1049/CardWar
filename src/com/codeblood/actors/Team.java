package com.codeblood.actors;

import com.codeblood.effect.Skill;
import com.codeblood.global.Analyze;
import com.codeblood.global.Const;

/**
 * 
 * @author Star
 * 
 * Description: 队伍，每位玩家有3支队伍
 *              每个队伍有5位士兵
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
	 * Description: 设置队伍中的队员
	 * 
	 * @param: name士兵
	 *         index士兵的序号
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
	 * Description: 设置队伍中的队员
	 * 
	 * @param: solder士兵
	 *         index士兵的序号
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
	 * Description: 设置队伍中的队员
	 * 
	 * @param: index需要得到的士兵的标记
	 * @return: Solder返回士兵
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
	 * Description: 检查是否重名
	 * 
	 * @param: solder待检查士兵
	 * @return: boolean返回true说明未重名
	 * 			返回false说明已经存在
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
	 * Description: 甚至辅助技能
	 * 
	 * @param: theskill技能
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
