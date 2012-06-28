package com.codeblood.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.codeblood.actors.Solder;

/**
 * 
 * Description: �����࣬��MD5�㷨�õ������ֽ����ֵ 
 * 
 * @Author: Star
 * @CreateDate: 2012-8-1
 *
 */
public class Analyze
{
	//Analyze��Ψһʵ��
	private static Analyze instance = null;
	
	private Analyze()
	{
		
	}
	
	/**
	 * FunName: getInstance
	 * Description: �õ�ʵ��������ģʽ
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
	 * Description: ͨ����������ʿ��
	 * 
	 * @param: name����
	 * @return: �����ɵ�ʿ��
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
		
		//�õ�MD5�м��byte��
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
	 * Description: ֻ������������������ʿ��
	 * 
	 * @param: name����
	 * @return: String����
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
		
		//�õ�MD5�м��byte��
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
		
		String abilitystring ="����: " + life + "\n" +
							  "��:" + transvalue[Const.ATTACT] + " " + 
				              "��:" + transvalue[Const.GUARD] +  " " +
				              "��:" + transvalue[Const.SPEED] + "\n" +
				              "��:" + transvalue[Const.HITRATE] + " " +
				              "��:" + transvalue[Const.INTELLIGENCE] + " " +
				              "��:" + transvalue[Const.LUCK];
		
		return abilitystring;
	}
	
	/**
	 * FunName: fightAnalyze
	 * Description: ս�����̷���
	 * 
	 * @param: solderoneʿ��һ
	 *         soldertwoʿ����
	 * @return: �������ɺõ�ս������
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public List<String> fightAnalyze(Solder solderone, Solder soldertwo)
	{
		Solder soldertemp;
		List<String> list = new ArrayList<String>();
		
		//�ٶȿ�����ж�
		if(soldertwo.getSpeed() > solderone.getSpeed())
		{
			soldertemp = solderone;
			solderone  = soldertwo;
			soldertwo  = soldertemp;
		}
		//�ٶ���ȣ����ѡȡ�����ж�
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
		
		//ս��ѭ�������˶�����ս���ͻ����
		int count = 0;
		while(solderone.getLife() > 0 && soldertwo.getLife() > 0)
		{
			String message = new String();
			//״̬�ж�
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
					list.add(solderone.getName() + "�������ˣ�");
					list.add(soldertwo.getName() + "��ʤ��");
					continue;
				}
			}
			
			//ħ������������
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
			//��Ϊ�˴�������һ��
			list.add(message);
			
			//����Ѫ����Ϣ
			list.add(solderone.getName() + "#" + soldertwo.getName() + 
					 " " + solderone.getLife() + " " + soldertwo.getLife());
			
			soldertemp = solderone;
			solderone  = soldertwo;
			soldertwo  = soldertemp;
			
			if(count > 30)
			{
				message = "ʱ�䵽�ˣ�" ;
				list.add(message);
				if(soldertwo.getLife() < solderone.getLife())
				{
					soldertemp = solderone;
					solderone  = soldertwo;
					soldertwo  = soldertemp;
				}
				else if(soldertwo.getLife() == solderone.getLife())
				{
					message = "ʣ������ֵ��ͬ���������˰ɣ�";
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
		list.add(solderone.getName() + "�������ˣ�");
		list.add(soldertwo.getName() + "��ʤ��");
		return list;
	}
	/**
	 * FunName: rateAnalyze
	 * Description: �����ʼ���
	 * 
	 * @param: solderoneʿ��һ
	 *         soldertwoʿ����
	 * @return: ����������
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
	 * Description: �����ʼ���
	 * 
	 * @param: solderoneʿ��һ
	 *         soldertwoʿ����
	 * @return: ����������
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
	 * Description: ����ս��ϸ��
	 * 
	 * @param: solderoneʿ��һ
	 *         soldertwoʿ����
	 * @return: ����message
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-14
	 */
	private String phsicalProcess(Solder solderone, Solder soldertwo)
	{
		//�˺�����
		int damage = 32 + (solderone.getAttact() - soldertwo.getGuard()) / 2;
		
		if(damage < 10)
		{
			damage = ((int)(Math.random() * 100) % 13) + 1;
		}
		
		String message = new String();
		
		message = solderone.getName() + "�Ľ�����";
		
		//����һ���ʼ���
		int exrate = exrateAnalyze(solderone, soldertwo);
		int exseed = (int)(Math.random() * 100);
		if(exseed < exrate)
		{
			message += "����һ����";
			damage = (int)((float)damage * 1.5);
		}
		
		//�����ʼ���
		int rate = rateAnalyze(solderone, soldertwo);
		
		int seed  = (int)(Math.random() * 100);
		if(seed < rate)
		{
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
		}
		else
		{
			message += "û�д��У�" + (soldertwo.getName()) + "����˹���!";
			damage = 0;
		}
		
		soldertwo.setLife(soldertwo.getLife() - damage);
		if(soldertwo.getLife() < 0)
		{
			soldertwo.setLife(0);
		}
		message += (soldertwo.getName() + "��ʣ" + soldertwo.getLife() + "��������");
		
		return message;
	}
	
	/**
	 * FunName: magicalProcess
	 * Description: ħ��ϸ��
	 * 
	 * @param: solderoneʿ��һ
	 *         soldertwoʿ����
	 * @return: ����message
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
			message = solderone.getName() + "ʹ�������壡";
			damage = 20;
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
			break;
		case 1:
			message = solderone.getName() + "ʹ�������ƶ�ҩ��";
			damage = (int)((float)soldertwo.getLife() * 0.1);
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
			if((Math.random() * 100) > soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "�ж��ˣ�";
				soldertwo.setStatus(Const.ST_POISION);
				soldertwo.setUnheathyacount(5);
			}
			else
			{
				message += soldertwo.getName() + "û���ж���";
			}
			break;
		case 2:
			message = solderone.getName() + "����ǿ�ҵ����䣡";
			damage = (int)((float)soldertwo.getLife() * 0.08) + 5;
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
			if((Math.random() * 100) > soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "�����ˣ�������ֵ�½��ˣ�";
				soldertwo.setStatus(Const.ST_LESSPOWER);
				soldertwo.setUnheathyacount(5);
				soldertwo.lessPower(0.1f);
			}
			else
			{
				message += soldertwo.getName() + "�������Ӷ�������";
			}
			break;
		case 3:
			message = solderone.getName() + "תȦȦ��";
			damage = (int)((float)soldertwo.getLife() * 0.1) + 10;
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
			if((Math.random() * 100) < soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "��ת���ˣ�";
				soldertwo.setStatus(Const.ST_DIZZY);
				soldertwo.setUnheathyacount(3);
			}
			else
			{
				message += soldertwo.getName() + "û�б�ת�Σ�";
			}
			break;
		case 4:
			message = solderone.getName() + "ʹ�����洫��������";
			damage = (int)((float)soldertwo.getLife() * 0.09) + 10;
			message += (soldertwo.getName() + "��ʧ" + damage + "��������");
			if((Math.random() * 100) < soldertwo.getIntelligence())
			{
				message += soldertwo.getName() + "�����ˣ�";
				soldertwo.setStatus(Const.ST_CHAOS);
				soldertwo.setUnheathyacount(3);
			}
			else
			{
				message += soldertwo.getName() + "��־���ᶨ��";
			}
			break;
		}
	
		soldertwo.setLife(soldertwo.getLife() - damage);
		message += (soldertwo.getName() + "��ʣ" + soldertwo.getLife() + "��������");
		return message;
	}
}


