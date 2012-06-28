package com.codeblood.actors;

import android.content.Context;
import android.content.SharedPreferences;

import com.codeblood.effect.Skill;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;
import com.codeblood.record.Allrecord;

/**
 * 
 * @author Star
 * 
 * Description: �ӳ���ӵ��3�������4������
 *
 */
public class Leader extends Role
{
	private Team teams[];
	private Skill skills[];
	private int[] itemtable;
	
	private Allrecord record = null;
	
	private static Leader theleader = null;
	
	/**
	 * FunName: Leader
	 * Description: ���캯������������ͼ���
	 * 
	 * @param: context�������Ա�õ���¼�Ķӳ���Ϣ
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	private Leader(Context context)
	{
		teams = new Team[Const.TEAM_NUM];
		skills = new Skill[Const.SKILL_NUM];
		
		for(int i = 0; i < Const.TEAM_NUM; i++)
		{
			teams[i] = new Team();
		}
		
		SharedPreferences leaderinfo = context.getSharedPreferences("com.codeblood", 0);
		int id = leaderinfo.getInt(Const.SP_LEADER_ID, -1);
		
		String[] teammembers = new String[Const.TEAM_NUM];
		teammembers[0] = leaderinfo.getString(Const.SP_TEAM1, "null");
		teammembers[1] = leaderinfo.getString(Const.SP_TEAM2, "null");
		teammembers[2] = leaderinfo.getString(Const.SP_TEAM3, "null");
		
		for(int i =0; i < Const.TEAM_NUM; i++)
		{
			String[] name = null;
			if(teammembers[i] != "null")
			{
				name = teammembers[i].split(",");
			}
			for(int j = 0; j < Const.TEAM_SOLDER_NUM; j++)
			{
				if(name == null)
				{
					teams[i].setSolder(new Solder(), j);
					continue;
				}
				if(j < name.length)
				{
					teams[i].setSolder(name[i], j);
				}
				else
				{
					teams[i].setSolder(new Solder(), j);
				}
			}
		}
		
		int[] attributes = Datamanager.getInstance().getLeaderattribute(id);
		this.setAttrbutes(attributes);
		
		itemtable = Datamanager.getInstance().getItemtable();
		
		record = new Allrecord();
		record.setCount(leaderinfo.getInt(Const.SP_LEADER_COUNT, 0));
		record.setWin(leaderinfo.getInt(Const.SP_LEADER_WIN, 0));
		
	}
	
	/**
	 * FunName: getLeader
	 * Description: �õ��ӳ����ӳ�ֻ��һ�ˣ�Ϊ����ģʽ
	 * 
	 * @param: context������
	 * @return: �õ��ӳ�
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public static Leader getLeader(Context context)
	{
		if(theleader == null)
		{
			theleader = new Leader(context);
		}
		return theleader;
	}
	
	/**
	 * FunName: getLeader
	 * Description: �õ��ӳ������أ�����Ҫ����
	 * 
	 * @return: �õ��ӳ�
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	public static Leader getLeader()
	{
		return theleader;
	}
	
	/**
	 * FunName: setAttrbutes
	 * Description: ���ӳ��������ֵ
	 * 
	 * @param: attribute��ֵ����
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	private void setAttrbutes(int[] attributes)
	{
		this.attact       = attributes[Const.LEADER_ATTACT_INDEX];
		this.guard        = attributes[Const.LEADER_GUARD_INDEX];
		this.speed        = attributes[Const.LEADER_SPEED_INDEX];
		this.intelligence = attributes[Const.LEADER_INTELLIGENCE_INDEX];
		this.hitrate      = attributes[Const.LEADER_HITRATE_INDEX];
		this.luck         = attributes[Const.LEADER_LUCKY_INDEX];
	}
	/**
	 * FunName: setSolder
	 * Description: ��ĳһ�����ĳ��λ�ò���ʿ��
	 * 
	 * @param: nameҪ���õ�ʿ��
	 *         teamindex������
	 *         solderindexʿ���ڶ����е�λ��
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public void setSolder(String name, int teamindex, int solderindex)
	{
		teams[teamindex].setSolder(name, solderindex);
	}
	/**
	 * FunName: setSolder
	 * Description: ��ĳһ�����ĳ��λ�ò���ʿ��
	 * 
	 * @param: nameҪ���õ�ʿ��
	 *         teamindex������
	 *         solderindexʿ���ڶ����е�λ��
	 * @return: boolean�Ƿ�ɹ�
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public boolean setSolder(Solder solder, int teamindex, int solderindex)
	{
		if(teams[teamindex].checkUnique(solder))
		{
			teams[teamindex].setSolder(solder, solderindex);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * FunName: usebuffSkill
	 * Description: ��ĳһ���鲼�ø�������
	 * 
	 * @param: skillcodeҪ���õļ��ܱ��
	 *         teamindex������
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public void usebuffSkill(int skillcode, int teamindex)
	{
		teams[teamindex].setbuffKill(skills[skillcode]);
	}
	
	//get and set
	public int[] getItemtable()
	{
		return itemtable;
	}
	
	public void setItemtablenum(int id, int amount)
	{
		itemtable[id] = amount;
	}
	
	public int getItemtablenum(int id)
	{
		return itemtable[id];
	}
	
	public Solder getTeammember(int teamindex, int member)
	{
		return teams[teamindex].getSolder(member);
	}
}
