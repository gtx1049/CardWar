package com.codeblood.effect;

import com.codeblood.actors.Solder;

/**
 * 
 * @author Star
 * 
 * Description: ���ߣ���ʿ������
 * 				�ӿڣ������ж��Լ�Ӱ�죬�Ե�Ӱ��
 *
 */
public interface Item 
{
	public void itemEffect(Solder solder);
	public void itemAttact(Solder solder);
}
