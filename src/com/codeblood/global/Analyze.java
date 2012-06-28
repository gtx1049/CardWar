package com.codeblood.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.codeblood.actors.Solder;

/**
 * 
 * Description: 分析类，用MD5算法得到姓名分解的数值 
 * 
 * @Author: Star
 * @CreateDate: 2012-8-1
 *
 */
public class Analyze
{
	//Analyze的唯一实例
	private static Analyze instance = null;
	
	private Analyze()
	{
		
	}
	
	/**
	 * FunName: getInstance
	 * Description: 得到实例，单例模式
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public static Analyze getInstance()
	{
		if(instance == null)
		{
			instance = new Analyze();
		}
		return instance;
	}
	
	/**
	 * FunName: produceSolder
	 * Description: 通过姓名制造士兵
	 * 
	 * @param: name姓名
	 * @return: 新生成的士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public Solder produceSolder(String name)
	{
		byte[] midcode = null;
		int[] midvalue = new int[16];
		int[] transvalue = new int[6];
		int life;
		
		//得到MD5中间的byte码
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			midcode = md.digest(name.getBytes());
		}
		catch(NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		for(int i = 0; i < 16; i++)
		{
			midvalue[i] = (int)midcode[i];
			midvalue[i] = midvalue[i] < 0 ? -midvalue[i] : midvalue[i];
		}
		
		for(int i = 0, j = 0; i < 8; i++, j++)
		{
			transvalue[j] = (midvalue[i] + midvalue[i + 1]) % 90 + 10;
			i += 2;
		}
		for(int i = 8, j = 3; i < 16; i++, j++)
		{
			transvalue[j] = (midvalue[i] + midvalue[i + 1]) % 90 + 10;
			i += 2;
		}
		
		life = (midvalue[2] + midvalue[5] + midvalue[10] + midvalue[13]) % 320;
		
		if(life < 100)
		{
			life += 99;
		}
		
		return new Solder(transvalue, life, name);
	}
	/**
	 * FunName: getAbilitystring
	 * Description: 只获得属性描述，不获得士兵
	 * 
	 * @param: name姓名
	 * @return: String描述
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-11
	 */
	public String getAbilitystring(String name)
	{
		byte[] midcode = null;
		int[] midvalue = new int[16];
		int[] transvalue = new int[6];
		int life;
		
		//得到MD5中间的byte码
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			midcode = md.digest(name.getBytes());
		}
		catch(NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		for(int i = 0; i < 16; i++)
		{
			midvalue[i] = (int)midcode[i];
			midvalue[i] = midvalue[i] < 0 ? -midvalue[i] : midvalue[i];
		}
		
		for(int i = 0, j = 0; i < 8; i++, j++)
		{
			transvalue[j] = (midvalue[i] + midvalue[i + 1]) % 90 + 10;
			i += 2;
		}
		for(int i = 8, j = 3; i < 16; i++, j++)
		{
			transvalue[j] = (midvalue[i] + midvalue[i + 1]) % 90 + 10;
			i += 2;
		}
		
		life = (midvalue[2] + midvalue[5] + midvalue[10] + midvalue[13]) % 320;
		
		if(life < 100)
		{
			life += 99;
		}
		
		String abilitystring ="生命: " + life + "\n" +
							  "攻:" + transvalue[Const.ATTACT] + " " + 
				              "防:" + transvalue[Const.GUARD] +  " " +
				              "速:" + transvalue[Const.SPEED] + "\n" +
				              "中:" + transvalue[Const.HITRATE] + " " +
				              "智:" + transvalue[Const.INTELLIGENCE] + " " +
				              "运:" + transvalue[Const.LUCK];
		
		return abilitystring;
	}
	
	/**
	 * FunName: fightAnalyze
	 * Description: 战斗流程分析
	 * 
	 * @param: solderone士兵一
	 *         soldertwo士兵二
	 * @return: 返回生成好的战斗序列
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public List<String> fightAnalyze(Solder solderone, Solder soldertwo)
	{
		Solder soldertemp;
		List<String> list = new ArrayList<String>();
		
		//速度快的先行动
		if(soldertwo.getSpeed() > solderone.getSpeed())
		{
			soldertemp = solderone;
			solderone  = soldertwo;
			soldertwo  = soldertemp;
		}
		//速度相等，随机选取人物行动
		else if(soldertwo.getSpeed() == solderone.getSpeed())
		{
			int seed = (int)(Math.random() * 2);
			if(seed == 1)
			{
				soldertemp = solderone;
				solderone  = soldertwo;
				soldertwo  = soldertemp;
			}
		}
		list.add(solderone.getName() + "#" + soldertwo.getName() + 
				 " " + solderone.getLife() + " " + soldertwo.getLife());
		
		//战斗循环，两人都活着战斗就会继续
		int count = 0;
		while(solderone.getLife() > 0 && soldertwo.getLife() > 0)
		{
			String message = new String();
			//状态判断
			if(solderone.getUnheathyacount() != 0)
			{
				message = solderone.countStatus();
				if(((solderone.getStatus() == Const.ST_DIZZY || 
				   solderone.getStatus() == Const.ST_CHAOS) && 
				   solderone.getUnheathyacount() == 0)
				   )
				{
					solderone.setStatus(Const.ST_HEATHY);
					list.add(message);
					soldertemp = solderone;
					solderone  = soldertwo;
					soldertwo  = soldertemp;
					continue;
				}
				if(solderone.getStatus() == Const.ST_DIZZY || 
				   solderone.getStatus() == Const.ST_CHAOS)
				{
					list.add(message);
					soldertemp = solderone;
					solderone  = soldertwo;
					soldertwo  = soldertemp;
					continue;
				}
				list.add(message);
				
				if(solderone.getLife() == 0)
				{
					list.add(solderone.getName() + "被击倒了！");
					list.add(soldertwo.getName() + "获胜！");
					continue;
				}
			}
			
			//魔法还是物理攻击
			if(solderone.getLife() < (int)((float)solderone.getMaxlife() * 0.6f)
			   && !solderone.isMagicaused())
			{
				message = magicalProcess(solderone, soldertwo);
				solderone.setMagicaused(true);
			}
			else
			{
				message = phsicalProcess(solderone, soldertwo);
			}
			//因为此处交换了一次
			list.add(message);
			
			//放置血量信息
			list.add(solderone.getName() + "#" + soldertwo.getName() + 
					 " " + solderone.getLife() + " " + soldertwo.getLife());
			
			soldertemp = solderone;
			solderone  = soldertwo;
			soldertwo  = soldertemp;
			
			if(count > 30)
			{
				message = "时间到了！" ;
				list.add(message);
				if(soldertwo.getLife() < solderone.getLife())
				{
					soldertemp = solderone;
					solderone  = soldertwo;
					soldertwo  = soldertemp;
				}
				else if(soldertwo.getLife() == solderone.getLife())
				{
					message = "剩余生命值相同，接受命运吧！";
					list.add(message);
					int seed2 = (int)(Math.random() * 2);
					if(seed2 == 1)
					{
						soldertemp = solderone;
						solderone  = soldertwo;
						soldertwo  = soldertemp;
					}
				}
				break;		
			}
			
			count++;
		}
		list.add(solderone.getName() + "被击倒了！");
		list.add(soldertwo.getName() + "获胜！");
		return list;
	}
	/**
	 * FunName: rateAnalyze
	 * Description: 命中率计算
	 * 
	 * @param: solderone士兵一
	 *         soldertwo士兵二
	 * @return: 返回命中率
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	private int rateAnalyze(Solder solderone, Solder soldertwo)
	{
		int rate = 0;
		
		if(solderone.getHitrate() > soldertwo.getSpeed() * 2)
		{
			rate = 100;
		}
		else if(solderone.getHitrate() > soldertwo.getSpeed())
		{
			rate = (solderone.getHitrate() - soldertwo.getSpeed()) * 10 / soldertwo.getSpeed() + 90;
		}
		else if(solderone.getHitrate() > soldertwo.getSpeed() / 2)
		{
			rate = (solderone.getHitrate() - soldertwo.getSpeed()) * 30 / (soldertwo.getSpeed() / 2) + 60;
		}
		else
		{
			rate = (solderone.getHitrate() - soldertwo.getSpeed() / 3) * 30 / (soldertwo.getSpeed() / 3) + 30;
		}
		
		return rate;
	}
	
	/**
	 * FunName: exrateAnalyze
	 * Description: 命中率计算
	 * 
	 * @param: solderone士兵一
	 *         soldertwo士兵二
	 * @return: 返回命中率
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	private int exrateAnalyze(Solder solderone, Solder soldertwo)
	{
		int exrate = 0;
		float value = (float)solderone.getLuck() / (float)soldertwo.getLuck();
		if(value > 3)
		{
			exrate = 90;
		}
		else if(value >= 2 && value < 3)
		{
			exrate = (int)(20 + 80 * (value - 2));
		}
		else if(value >= 1 && value < 2)
		{
			exrate = (int)(2 + 18 * (value - 1));
		}
		else if(value < 1)
		{
			exrate = 2;
		}
		return exrate;
	}
	
	/**
	 * FunName: phsicalProcess
	 * Description: 物理战斗细程
	 * 
	 * @param: solderone士兵一
	 *         soldertwo士兵二
	 * @return: 返回message
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	private String phsicalProcess(Solder solderone, Solder soldertwo)
	{
		//伤害计算
		int damage = 32 + (solderone.getAttact() - soldertwo.getGuard()) / 2;
		
		if(damage < 10)
		{
			damage = ((int)(Math.random() * 100) % 13) + 1;
		}
		
		String message = new String();
		
		message = solderone.getName() + "的进攻！";
		
		//致命一击率计算
		int exrate = exrateAnalyze(solderone, soldertwo);
		int exseed = (int)(Math.random() * 100);
		if(exseed < exrate)
		{
			message += "会心一击！";
			damage = (int)((float)damage * 1.5);
		}
		
		//命中率计算
		int rate = rateAnalyze(solderone, soldertwo);
		
		int seed  = (int)(Math.random() * 100);
		if(seed < rate)
		{
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
		}
		else
		{
			message += "没有打中！" + (soldertwo.getName()) + "躲过了攻击!";
			damage = 0;
		}
		
		soldertwo.setLife(soldertwo.getLife() - damage);
		if(soldertwo.getLife() < 0)
		{
			soldertwo.setLife(0);
		}
		message += (soldertwo.getName() + "还剩" + soldertwo.getLife() + "点生命。");
		
		return message;
	}
	
	/**
	 * FunName: magicalProcess
	 * Description: 魔法细程
	 * 
	 * @param: solderone士兵一
	 *         soldertwo士兵二
	 * @return: 返回message
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	private String magicalProcess(Solder solderone, Solder soldertwo)
	{
		int damage = 0;
		String message = new String();
		int magicount = 0;
		if(solderone.getIntelligence() >= 90)
		{
			magicount = 5;
		}
		else if(solderone.getIntelligence() >= 80 && solderone.getIntelligence() < 90)
		{
			magicount = 4;
		}
		else if(solderone.getIntelligence() >= 70 && solderone.getIntelligence() < 80)
		{
			magicount = 3;
		}
		else if(solderone.getIntelligence() >= 60 && solderone.getIntelligence() < 70)
		{
			magicount = 2;
		}
		else 
		{
			magicount = 1;
		}
		
		int method = (int)(Math.random() * 100) % magicount;
		
		switch(method)
		{
		case 0:
			message = solderone.getName() + "使用了陷阱！";
			damage = 20;
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
			break;
		case 1:
			message = solderone.getName() + "使用了自制毒药！";
			damage = (int)((float)soldertwo.getLife() * 0.1);
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
			if((Math.random() * 100) > soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "中毒了！";
				soldertwo.setStatus(Const.ST_POISION);
				soldertwo.setUnheathyacount(5);
			}
			else
			{
				message += soldertwo.getName() + "没有中毒！";
			}
			break;
		case 2:
			message = solderone.getName() + "发出强烈的诅咒！";
			damage = (int)((float)soldertwo.getLife() * 0.08) + 5;
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
			if((Math.random() * 100) > soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "脱力了！各能力值下降了！";
				soldertwo.setStatus(Const.ST_LESSPOWER);
				soldertwo.setUnheathyacount(5);
				soldertwo.lessPower(0.1f);
			}
			else
			{
				message += soldertwo.getName() + "对诅咒视而不见！";
			}
			break;
		case 3:
			message = solderone.getName() + "转圈圈！";
			damage = (int)((float)soldertwo.getLife() * 0.1) + 10;
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
			if((Math.random() * 100) < soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "被转晕了！";
				soldertwo.setStatus(Const.ST_DIZZY);
				soldertwo.setUnheathyacount(3);
			}
			else
			{
				message += soldertwo.getName() + "没有被转晕！";
			}
			break;
		case 4:
			message = solderone.getName() + "使用了祖传催眠术！";
			damage = (int)((float)soldertwo.getLife() * 0.09) + 10;
			message += (soldertwo.getName() + "损失" + damage + "点生命！");
			if((Math.random() * 100) < soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "混乱了！";
				soldertwo.setStatus(Const.ST_CHAOS);
				soldertwo.setUnheathyacount(3);
			}
			else
			{
				message += soldertwo.getName() + "意志力坚定！";
			}
			break;
		}
	
		soldertwo.setLife(soldertwo.getLife() - damage);
		message += (soldertwo.getName() + "还剩" + soldertwo.getLife() + "点生命。");
		return message;
	}
}


