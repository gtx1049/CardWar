package com.codeblood.global;

import java.io.IOException;
import java.io.InputStream;

import com.codeblood.actors.Solder;
import com.codeblood.customview.Capable;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;

/**
 * 
 * @Author: Star
 * 
 * Description: 对图片进行处理
 * 
 */
public class Imagedeal
{
	/**
	 * FunName: getScreenwidthandheight
	 * Description: 得到当前屏幕的长和宽
	 * 
	 * @param: activity当前屏幕的activity
	 * @return int[]返回屏幕的宽度和长度
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-2
	 */
	public static int[] getScreenwidthandheight(Activity activity)
	{
		int[] ret = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		ret[Const.WIDTH] = (int)(dm.widthPixels * dm.density);
		ret[Const.HEIGHT] = (int)(dm.heightPixels * dm.density);
		return ret;
	}
	
	/**
	 * FunName: zoomBitmap
	 * Description: 对图片进行缩放
	 * 
	 * @param: bitmap需要处理的图片
	 *         width目标宽度
	 *         height目标高度
	 * @return Bitmap返回处理后的图片
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-2
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scalewidth = ((float)width / w);
		float scaleheight = ((float)height / h);
		matrix.postScale(scalewidth, scaleheight);
		Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return ret;
	}
	
	/**
	 * FunName: getImageassets
	 * Description: 从assets资源处获取图片
	 * 
	 * @param: string文件名
	 *         activity
	 * @return Bitmap返回资源得到的图片
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-2
	 */
	public static Bitmap getImageassets(String filename, final Activity activity)
	{
		Bitmap bitmap = null;
		AssetManager am = activity.getAssets();
		InputStream is;
		try
		{
			is = am.open(filename);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}
	/**
	 * FunName: getImageassets
	 * Description: 从assets资源处获取图片
	 * 
	 * @param: string文件名
	 *         context
	 * @return Bitmap返回资源得到的图片
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-8
	 */
	public static Bitmap getImageassets(String filename, final Context context)
	{
		Bitmap bitmap = null;
		AssetManager am = context.getAssets();
		InputStream is;
		try
		{
			is = am.open(filename);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}
	
	/**
	 * FunName: getContatcphoto
	 * Description: 从通讯录数据库获取头像
	 * 
	 * @param: photoid头像id
	 *         contactid联系人id
	 *         resolver数据库操作对象
	 *         activity获取当前信息用
	 * @return Bitmap返回从数据库得到的头像
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-2
	 */
	public static Bitmap getContatcphoto(long photoid, long contactid, ContentResolver resolver, Activity activity)
	{
		if(photoid > 0)
		{
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
			Bitmap face = zoomBitmap(BitmapFactory.decodeStream(input), 80, 80);
			return face;
		}
		else
		{
			return zoomBitmap(getImageassets("xm.png", activity), 80, 80);
		}
	}
	
	/**
	 * FunName: circlePhoto
	 * Description: 将头像处理成圆形
	 * 
	 * @param: photo头像图片
	 *         context上下文
	 * @return Bitmap返回处理后的头像
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-7
	 */
	public static Bitmap circlePhoto(Bitmap photo, Activity context, int color)
	{
		int[] screen = getScreenwidthandheight(context);
		int square = 80;
		
		if(screen[Const.HEIGHT] < 480)
		{
			square = 60;
			photo = zoomBitmap(photo, square, square);
		}
		Bitmap back = null;
		Bitmap ret = Bitmap.createBitmap(square, square, Bitmap.Config.ARGB_8888);
		if(color == Const.MODE_BLACK)
		{
			back = zoomBitmap(getImageassets("headbolder2.png", context), square, square);
			for(int i = 0; i < square; i++)
			{
				for(int j = 0; j < square; j++)
				{
					int backpix = back.getPixel(i, j);
					int facepix = photo.getPixel(i, j);
					ret.setPixel(i, j, backpix & facepix);
				}
			}
		}
		else if(color == Const.MODE_WHITE)
		{
			back = zoomBitmap(getImageassets("headbolder.png", context), square, square);
			for(int i = 0; i < square; i++)
			{
				for(int j = 0; j < square; j++)
				{
					int backpix = back.getPixel(i, j);
					int facepix = photo.getPixel(i, j);
					ret.setPixel(i, j, backpix | facepix);
				}
			}
		}
		
		back.recycle();
		return ret;
	}
	
	/**
	 * FunName: getShapebitmap
	 * Description: 装配得到士兵的六边形
	 * 
	 * @param: solder士兵
	 *         mode屏幕模式
	 * @return: Bitmap图像
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-8
	 * 
	 */
	public static Bitmap getShapebitmap(Solder solder, int mode)
	{
		Capable capable = new Capable(100, 100, 0.3f);
		capable.setSolerability(solder);
		return capable.getShape(mode);
	}
	
	/**
	 * FunName: getImagepiece
	 * Description: 将大图切割成小图
	 * 
	 * @param: bitmap原图
	 *         x水平方向
	 *         y垂直方向
	 * @return: Bitmap[]图像
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 * 
	 */
	public static Bitmap[] getImagepiece(Bitmap bitmap, int x, int y)
	{
		Bitmap[] pieces = new Bitmap[x * y];
		
		int piecewidth = bitmap.getWidth() / x;
		int pieceheight = bitmap.getHeight() / y;
		
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				pieces[i * x + j] = Bitmap.createBitmap(bitmap, j * piecewidth,
														i * pieceheight, piecewidth, pieceheight);			
			}
		}
		
		return pieces;
	}
}
