package com.codeblood.effect;

import com.codeblood.actors.Solder;

/**
 * 
 * @author Star
 * 
 * Description: 道具，由士兵持有
 * 				接口，函数有对自己影响，对敌影响
 *
 */
public interface Item 
{
	public void itemEffect(Solder solder);
	public void itemAttact(Solder solder);
}
