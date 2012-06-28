package com.codeblood.global;

import com.codeblood.effect.Item;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author Star
 * 
 * Description: 用户类，提供一些基础操作
 *
 */
public class User
{
	/**
	 * FunName: displayToast
	 * Description: 显示提示
	 * 
	 * @param: context上下文
	 * 		   tip显示信息
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-12
	 */
	public static void displayToast(Context context, String tip)
	{
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * FunName: itemFactory
	 * Description: 生成道具
	 * 
	 * @param: id道具的id
	 * @return: item道具接口
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-6
	 */
	public static Item itemFactory(int id)
	{
		return null;
	}
}
