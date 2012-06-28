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
 * Description: ��ͼƬ���д���
 * 
 */
public class Imagedeal
{
	/**
	 * FunName: getScreenwidthandheight
	 * Description: �õ���ǰ��Ļ�ĳ��Ϳ�
	 * 
	 * @param: activity��ǰ��Ļ��activity
	 * @return int[]������Ļ�Ŀ�Ⱥͳ���
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
	 * Description: ��ͼƬ��������
	 * 
	 * @param: bitmap��Ҫ�����ͼƬ
	 *         widthĿ����
	 *         heightĿ��߶�
	 * @return Bitmap���ش�����ͼƬ
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
	 * Description: ��assets��Դ����ȡͼƬ
	 * 
	 * @param: string�ļ���
	 *         activity
	 * @return Bitmap������Դ�õ���ͼƬ
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
	 * Description: ��assets��Դ����ȡͼƬ
	 * 
	 * @param: string�ļ���
	 *         context
	 * @return Bitmap������Դ�õ���ͼƬ
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
	 * Description: ��ͨѶ¼���ݿ��ȡͷ��
	 * 
	 * @param: photoidͷ��id
	 *         contactid��ϵ��id
	 *         resolver���ݿ��������
	 *         activity��ȡ��ǰ��Ϣ��
	 * @return Bitmap���ش����ݿ�õ���ͷ��
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
	 * Description: ��ͷ�����Բ��
	 * 
	 * @param: photoͷ��ͼƬ
	 *         context������
	 * @return Bitmap���ش�����ͷ��
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
	 * Description: װ��õ�ʿ����������
	 * 
	 * @param: solderʿ��
	 *         mode��Ļģʽ
	 * @return: Bitmapͼ��
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
	 * Description: ����ͼ�и��Сͼ
	 * 
	 * @param: bitmapԭͼ
	 *         xˮƽ����
	 *         y��ֱ����
	 * @return: Bitmap[]ͼ��
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
