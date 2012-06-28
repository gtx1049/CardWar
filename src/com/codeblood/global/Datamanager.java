package com.codeblood.global;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.codeblood.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * 
 * @Author: Star
 * 
 * Description: 对数据库进行管理，单例模式
 * 
 */
public class Datamanager
{
	private static Datamanager instance = null;
	
	private SQLiteDatabase db = null;
	
	private Datamanager()
	{
		
	}
	
	/**
	 * FunName: dbReady
	 * Description: 准备数据库,进入游戏就要运行此函数
	 * 
	 * @param: context上下文
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	public void dbReady(Context context)
	{
		if(copyDB(context))
		{
			File file = new File(Const.DB_URL, Const.DB_FILE_NAME);
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
		}
	}
	
	/**
	 * FunName: copyDB
	 * Description: 查看数据库是否存在，若不存在，则拷贝进
	 * 
	 * @param: context传进上下文
	 * @return: boolean返回是否成功
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	private boolean copyDB(Context context)
	{
		try
		{
			if(!(new File(Const.DB_URL, Const.DB_FILE_NAME).exists()))
			{
				InputStream is = context.getResources().openRawResource(R.raw.cardwar);
				FileOutputStream fos = context.openFileOutput(Const.DB_FILE_NAME, Context.MODE_WORLD_READABLE);
				byte[] buffer = new byte[1024 * 32];
				int count = 0;
				
				while((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				
				fos.close();
				is.close();
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * FunName: getInstance
	 * Description: 得到Datamanager实例
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-2
	 */
	public static Datamanager getInstance()
	{
		if(instance == null)
		{
			instance = new Datamanager();
		}
		return instance;
	}
	
	/**
	 * FunName: contactInfo
	 * Description: 获取通讯录信息，姓名和头像
	 * 
	 * @param: activity传进来的activity，为了得到当前Context
	 * @return: Object[]数组，装载着两个List
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public Object[] contactInfo(Activity activity)
	{
		Context context = activity;
		Object[] ret = new Object[2];

		List<String> namelist = new ArrayList<String>();
		List<Bitmap> photolist = new ArrayList<Bitmap>();
		
		ContentResolver resolver = context.getContentResolver();
		
		Cursor phonecursor = resolver.query(Phone.CONTENT_URI, 
				                           Const.PHONES_PROJECTION, 
				                           null, null, null);
		//游标遍历
		if(phonecursor != null)
		{
			String contactname;
			long photoid;
			long contactid = 0;
			while(phonecursor.moveToNext())
			{

				contactname = phonecursor.getString(Const.PHONE_DIPLAY_NAME);
				try
				{	
					photoid = phonecursor.getLong(Const.PHONE_PHOTO_ID);
					contactid = phonecursor.getLong(Const.PHONE_CONTACT_ID);
				}
				catch(IllegalStateException e)
				{
					photoid = -1;
				}
				
				//使用Imagedeal的函数得到头像
				Bitmap photo = Imagedeal.getContatcphoto(photoid, contactid, resolver, activity);
				
				namelist.add(contactname);
				photolist.add(photo);
			}
			phonecursor.close();
		}
		
		ret[Const.CONTACT_NAME] = (Object)namelist;
		ret[Const.CONTACT_PHOTO] = (Object)photolist;
		
		return ret;
	}
	/**
	 * FunName: contactnameInfo
	 * Description: 获取通讯录信息，姓名
	 * 
	 * @param: activity传进来的activity，为了得到当前Context
	 * @return: List数组
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-11
	 */
	public List<String> contactnameInfo(Context activity)
	{
		Context context = activity;

		List<String> namelist = new ArrayList<String>();
		
		ContentResolver resolver = context.getContentResolver();
		
		Cursor phonecursor = resolver.query(Phone.CONTENT_URI, 
				                           Const.PHONES_PROJECTION3, 
				                           null, null, null);
		//游标遍历
		if(phonecursor != null)
		{
			String contactname;
			while(phonecursor.moveToNext())
			{

				contactname = phonecursor.getString(Const.PHONE_DIPLAY_NAME);
				
				namelist.add(contactname);
			}
			phonecursor.close();
		}
		
		return namelist;
	}
	
	/**
	 * FunName: getItemtable
	 * Description: 为对话框装载list
	 * 
	 * @param: 得到当前Context
	 * @return: List数组
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-12
	 */
	public List<String> getItemtable(Context context)
	{
		List<String> table = new ArrayList<String>();
		String[] columns = {Const.ITEM_NAME, Const.ITEM_INTRODUCTION, Const.ITEM_AMOUNT};
		
		Cursor cursor = db.query(Const.TABLE_ITEM, columns, null, null, null, null, null);
		
		if(cursor != null)
		{
			while(cursor.moveToNext())
			{
				int amount = cursor.getInt(Const.ITEM_AMOUNT_INDEX2);
				if(amount != 0)
				{
					table.add(cursor.getString(Const.ITEM_NAME_INDEX2));
					table.add(cursor.getString(Const.ITEM_INTRODUCTION_INDEX2));
					table.add("数量:" + amount);
				}
			}
			cursor.close();
		}
		
		return table;
	}
	/**
	 * FunName: getItemid
	 * Description: 从道具名得到道具id
	 * 
	 * @param: 得到当前Context
	 * @return: int为ID
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-12
	 */
	public int getItemid(String itemname)
	{
		int id = -1;
		String[] columns = {Const.ITEM_ID, Const.ITEM_NAME};
		Cursor cursor = db.query(Const.TABLE_ITEM, columns, "name = ?", new String[]{itemname}, null, null, null);
		
		if(cursor != null && cursor.moveToFirst())
		{
			id = cursor.getInt(Const.ITEM_ID_INDEX);
			cursor.close();
		}
		
		return id;
	}
	
	/**
	 * FunName: getLeaderattribute
	 * Description: 从数据库中得到队长的数据
	 * 
	 * @param: id队长的id
	 * @return: int[]装载数据的数组
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	public int[] getLeaderattribute(int id)
	{
		if(db == null)
		{
			return null;
		}
		String columns[] = {Const.LEADER_ATTACT, Const.LEADER_GUARD, Const.LEADER_SPEED, 
                			Const.LEADER_INTELLIGENCE, Const.LEADER_HITRATE, Const.LEADER_LUCKY};
		String idstring = "" + id;
		
		Cursor cursor = db.query(Const.TABLE_LEADER, columns, "id = ?", 
				                 new String[]{idstring}, null, null, null);
		
		int [] attributes = new int[Const.LEADER_ATTRI_GET];
		if(cursor != null && cursor.moveToFirst())
		{
			for(int i = 0; i < Const.LEADER_ATTRI_GET; i++)
			{
				attributes[i] = cursor.getInt(i);
			}
			cursor.close();
		}
		
		return attributes;
	}
	
	/**
	 * FunName: getSolderrecord
	 * Description: 从数据库中得到士兵的数据
	 * 
	 * @param: name士兵的姓名
	 * @return: int[]装载数据的数组
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	public int[] getSolderrecord(String name)
	{
		if(db == null)
		{
			return null;
		}
		String columns[] = {Const.SOLDER_AMOUNT, Const.SOLDER_WIN, Const.SOLDER_ITEM};
		Cursor cursor = db.query(Const.TABLE_SOLDER, columns, "name = ?", 
								 new String[]{name}, null, null, null);
		int[] ret = new int[Const.SOLDER_DATA_COUNT];
		
		if(cursor != null && cursor.moveToFirst())
		{
			ret[Const.SOLDER_AMOUT_INDEX] = cursor.getInt(Const.SOLDER_AMOUT_INDEX);
			ret[Const.SOLDER_WIN_INDEX]   = cursor.getInt(Const.SOLDER_WIN_INDEX);
			ret[Const.SOLDER_ITEM_INDEX]  = cursor.getInt(Const.SOLDER_ITEM_INDEX);
			cursor.close();
		}
		else
		{
			String pre = "insert into " + Const.TABLE_SOLDER + " values";
			pre += "(\"" + name + "\", 0, 0, -1);";
			
			db.execSQL(pre);
			ret[Const.SOLDER_AMOUT_INDEX] = 0;
			ret[Const.SOLDER_WIN_INDEX]   = 0;
			ret[Const.SOLDER_ITEM_INDEX]    = 0;
		}
		
		return ret;
	}
	
	/**
	 * FunName: getRecordone
	 * Description: 从姓名得到士兵记录
	 * 
	 * @param: name名字
	 * @return String返回记录数据
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-8
	 */
	public String getRecordone(String name)
	{
		if(db == null)
		{
			return null;
		}
		String columns[] = {Const.SOLDER_AMOUNT, Const.SOLDER_WIN};
		Cursor cursor = db.query(Const.TABLE_SOLDER, columns, "name = ?", 
								 new String[]{name}, null, null, null);
		int[] ret = new int[Const.SOLDER_DATA_COUNT];
		
		if(cursor != null && cursor.moveToFirst())
		{
			ret[Const.SOLDER_AMOUT_INDEX] = cursor.getInt(Const.SOLDER_AMOUT_INDEX);
			ret[Const.SOLDER_WIN_INDEX]   = cursor.getInt(Const.SOLDER_WIN_INDEX);
			cursor.close();
		}
		else
		{
			ret[Const.SOLDER_AMOUT_INDEX] = 0;
			ret[Const.SOLDER_WIN_INDEX] = 0;
		}

		return "胜" + ret[Const.SOLDER_WIN_INDEX] + "负" + 
			   (ret[Const.SOLDER_AMOUT_INDEX] - ret[Const.SOLDER_WIN_INDEX]);
	}
	
	/**
	 * FunName: getNamephoto
	 * Description: 通过名字获取头像
	 * 
	 * @param: name名字
	 *         activity获取当前信息用
	 * @return Bitmap返回从数据库得到的头像
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-9
	 */
	public Bitmap getNamephoto(String name, Activity activity)
	{
		Context context = activity;
		
		ContentResolver resolver = context.getContentResolver();
		
		Cursor phonecursor = resolver.query(Phone.CONTENT_URI, 
				                           Const.PHONES_PROJECTION2, 
				                           Const.PHONES_PROJECTION[0] + " = ?",
				                           new String[]{name}, null);
		Bitmap ret = null;
		if(phonecursor != null && phonecursor.moveToFirst())
		{
			ret = Imagedeal.getContatcphoto(phonecursor.getLong(Const.PHONE_PHOTO_ID2), 
					                  phonecursor.getLong(Const.PHONE_CONTACT_ID2),
					                  resolver, activity);
			phonecursor.close();
		}
		else 
		{
			ret = Imagedeal.getImageassets("xm.png", activity);
		}
		return ret;
	}
	
	/**
	 * FunName: getItemtable
	 * Description: 从数据库中得到队长的道具表
	 * 
	 * @return: int[]道具表数组
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-6
	 */
	public int[] getItemtable()
	{
		if(db == null)
		{
			return null;
		}
		int[] itemtable = new int[Const.ITEM_SUM];
		
		String columns[] = {Const.ITEM_ID, Const.ITEM_AMOUNT};
		Cursor cursor = db.query(Const.TABLE_ITEM, columns, null, null, null, null, null);
		
		if(cursor != null)
		{
			while(cursor.moveToNext())
			{
				itemtable[cursor.getInt(Const.ITEM_ID_INDEX)] = cursor.getInt(Const.ITEM_AMOUNT_INDEX);
			}
			cursor.close();
		}
		
		
		return itemtable;
	}
	
	/**
	 * FunName: clearMemory
	 * Description: 释放内存
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-5
	 */
	public void clearMemory()
	{
		if(db != null)
		{
			db.close();
		}
		instance = null;
		db = null;
	}
}
