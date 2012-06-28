package com.codeblood.actors;

import com.codeblood.effect.Item;
import com.codeblood.effect.Skill;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;
import com.codeblood.global.User;
import com.codeblood.record.Solderrecord;

/**
 * 
 * @author Star
 * 
 * Description: 士兵，对战的主体，根据姓名得到
 *
 */
public class Solder extends Role
{
	private int life;
	private String name;
	
	private int status;
	private int unheathyacount;
	private int maxlife;
	private boolean magicaused;
	
	private Item item = null;
	private Solderrecord srecord= null;
	
	public Solder()
	{
		this.name = "";
		status = Const.ST_HEATHY;
		unheathyacount = 0;
	}
	
	/**
	 * FunName: Solder
	 * Description: 构造函数，把已经构造好的数值装载
	 * 
	 * @param: codestring为能力值序列
	 *         life生命
	 *         name姓名 
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public Solder(int[] codestring, int life, String name)
	{
		this.attact       = codestring[Const.ATTACT];
		this.guard        = codestring[Const.GUARD];
		this.intelligence = codestring[Const.INTELLIGENCE];
		this.speed        = codestring[Const.SPEED];
		this.hitrate      = codestring[Const.HITRATE];
		this.luck         = codestring[Const.LUCK];
		
		this.life         = life;
		this.name         = name;
		
		maxlife = life;
		magicaused = false;
		status = Const.ST_HEATHY;
		unheathyacount = 0;
		
		int[] record = Datamanager.getInstance().getSolderrecord(name);
		
		srecord = new Solderrecord();
		srecord.setCount(record[Const.SOLDER_AMOUT_INDEX]);
		srecord.setWin(record[Const.SOLDER_WIN_INDEX]);
		srecord.setName(this.name);
		
		if(record[Const.SOLDER_ITEM_INDEX] == -1)
		{
			return;
		}
		else
		{
			item = User.itemFactory(record[Const.SOLDER_ITEM_INDEX]);
		}
	}

	/**
	 * FunName: lessPower
	 * Description: 脱力
	 * 
	 * @param: offset削弱比率
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	public void lessPower(float offset)
	{
		this.attact -= (int)((float)this.attact * offset);
		this.guard -= (int)((float)this.guard * offset);
		this.speed -= (int)((float)this.speed * offset);
		this.hitrate -= (int)((float)this.hitrate * offset);
		this.intelligence -= (int)((float)this.intelligence * offset);
		this.luck -= (int)((float)this.luck * offset);
	}
	/**
	 * FunName: countStatus
	 * Description: 状态说明与计数
	 * 
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	public String countStatus()
	{
		String message = new String();
		int damage;
		switch(status)
		{
		case Const.ST_POISION:
			damage = (int)((float)life * 0.1f);
			if(damage == 0)
			{
				damage = 1; 
			}
			message = name + "因为毒损失" + damage + "生命！";
			life -= damage;
			unheathyacount--;
			break;
		case Const.ST_LESSPOWER:
			unheathyacount--;
			message = name + "脱力中！";
			break;
		case Const.ST_DIZZY:
			message = name + "眩晕无法行动！";
			unheathyacount--;
			break;
		case Const.ST_CHAOS:
			message = name +"混乱中无法行动！";
			if(Math.random() * 100 < 50)
			{
				//伤害计算
				damage = 32 + (getAttact() - getGuard()) / 2;
				
				if(damage < 10)
				{
					damage = ((int)(Math.random() * 100) % 13) + 1;
				}
				message += (name + "混乱中攻击了自己！");
				message += (name + "收到了" + damage + "点伤害!");
				life -= damage;
				if(life < 0)
				{
					life = 0;
				}
			}
		}
		if(unheathyacount == 1)
		{
			message += name + "康复了！";
			unheathyacount--;
			if(status == Const.ST_LESSPOWER || status == Const.ST_POISION)
			{
				status = Const.ST_HEATHY;
			}	
		}
		return message;
	}
	
	/**
	 * FunName: useItem
	 * Description: 使用道具
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-6
	 */
	public void useItem()
	{
		item.itemEffect(this);
	}
	
	/**
	 * FunName: throwItem
	 * Description: 投掷道具
	 * 
	 * @param: solder敌对士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-6
	 */
	public void throwItem(Solder solder)
	{
		item.itemAttact(solder);
	}
	
	/**
	 * FunName: displayAttribute
	 * Description: 显示属性函数，测试用
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public void displayAttribute()
	{
		System.out.println("姓名： " + name);
		System.out.println("生命： " + life);
		super.displayAttribute();		
	}
	
	/**
	 * FunName: setbuffSkill
	 * Description: 给士兵加辅助技能
	 * 
	 * @param: setbuffSkill为能力值序列
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	public void setbuffSkill(Skill theskill)
	{
		
	}
	
	//Setters and Getters
	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setItem(int id)
	{
		this.item = User.itemFactory(id);
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public String getRecord()
	{
		return "胜" + srecord.getWin() + "负" + (srecord.getCount() - srecord.getWin());
	}
	
	public int getUnheathyacount()
	{
		return unheathyacount;
	}

	public void setUnheathyacount(int unheathyacount)
	{
		this.unheathyacount = unheathyacount;
	}

	public int getMaxlife()
	{
		return maxlife;
	}

	public void setMaxlife(int maxlife)
	{
		this.maxlife = maxlife;
	}

	public boolean isMagicaused()
	{
		return magicaused;
	}

	public void setMagicaused(boolean magicaused)
	{
		this.magicaused = magicaused;
	}
	
}
