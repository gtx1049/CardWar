package com.codeblood.global;

import com.codeblood.effect.Item;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author Star
 * 
 * Description: �û��࣬�ṩһЩ��������
 *
 */
public class User
{
	/**
	 * FunName: displayToast
	 * Description: ��ʾ��ʾ
	 * 
	 * @param: context������
	 * 		   tip��ʾ��Ϣ
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
	 * Description: ���ɵ���
	 * 
	 * @param: id���ߵ�id
	 * @return: item���߽ӿ�
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-6
	 */
	public static Item itemFactory(int id)
	{
		return null;
	}
}
