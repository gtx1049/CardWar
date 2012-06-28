package com.codeblood.customview;

import com.codeblood.actors.Solder;
import com.codeblood.global.Const;

import android.graphics.*;

/**
 * 
 * @author GodBlessedMay
 * 
 * Description: 绘制能力六边形，由Star修改
 * 
 */
public class Capable
{
	private Bitmap shape;

	// p1 - p6 按顺时针从12点方向开始

	private Point[] maxValuePoints = new Point[6];
	private Point[] valuePoints = new Point[6];
	private Point[] textPoints = new Point[6];

	private String[] textToDraw = new String[]
	{ "攻击", "速度", "防御", "智力", "运气", "命中" };

	private float scale = 2.0f;

	public Capable(int width, int height, float scale)
	{
		shape = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		setScale(scale);
	}

	/**
	 * FunName: setSolderability
	 * Description: 设置士兵能力
	 * 
	 * @param: solder士兵
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-8
	 */
	public void setSolerability(Solder solder)
	{
		setValue(1, solder.getAttact());
		setValue(2, solder.getSpeed());
		setValue(3, solder.getGuard());
		setValue(4, solder.getIntelligence());
		setValue(5, solder.getLuck());
		setValue(6, solder.getHitrate());
	}
	
	// index 从1开始，范围为 1 - 6

	private void setValue(int index, int value)
	{
		valuePoints[index - 1] = calculatePointValue(index, value);
	}

	private void setScale(float scale)
	{
		this.scale = scale;
		init();
	}

	private void init()
	{
		for (int i = 0; i != 6; i++)
		{
			maxValuePoints[i] = calculatePointValue(i + 1, 100);
			valuePoints[i] = calculatePointValue(i + 1, 50);
			textPoints[i] = calculatePointValue(i + 1, 125);
		}
	}

	private Point calculatePointValue(int index, int value)
	{
		Point point = new Point();

		switch (index)
		{
		case 1:
		case 4:
		{
			point.x = 0;

			if (index == 1)
				point.y = (int) (value * scale);
			else
				point.y = -(int) (value * scale);

			break;
		}

		case 2:
		case 5:
		{
			point.y = (int) (value * scale / 2);
			point.x = (int) (Math.sqrt(3) * value * scale / 2);

			if (index == 5)
			{
				point.x *= -1;
				point.y *= -1;
			}

			break;
		}

		case 3:
		case 6:
		{
			point.x = (int) (Math.sqrt(3) * value * scale / 2);
			point.y = -(int) (value * scale / 2);

			if (index == 6)
			{
				point.x *= -1;
				point.y *= -1;
			}

			break;
		}

		default:
			break;
		}

		return point;
	}
	
	private void drawShape(int mode)
	{
		Point middlePoint = new Point();
		Canvas canvas = new Canvas(shape);

        middlePoint.x = canvas.getWidth() / 2;
        middlePoint.y = canvas.getHeight() / 2;

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


        Paint paint = new Paint();
        
        if(mode == Const.MODE_WHITE)
        {
        	paint.setColor(Color.BLACK);
        }
        else if(mode == Const.MODE_BLACK)
        {
        	paint.setColor(Color.WHITE);
        }
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        Path maxValuePath = new Path();
        Path realValuePath = new Path();

        maxValuePath.moveTo(maxValuePoints[0].x + middlePoint.x, maxValuePoints[0].y + middlePoint.y);
        realValuePath.moveTo(valuePoints[0].x + middlePoint.x, valuePoints[0].y + middlePoint.y);

        for (int i = 1; i != 6; i++)
        {
            maxValuePath.lineTo(maxValuePoints[i].x + middlePoint.x, maxValuePoints[i].y + middlePoint.y);
            realValuePath.lineTo(valuePoints[i].x + middlePoint.x, valuePoints[i].y + middlePoint.y);
        }

        maxValuePath.close();
        realValuePath.close();

        canvas.drawPath(maxValuePath, paint);

        for (int i = 0; i != 6; i++)
            canvas.drawText(textToDraw[i], textPoints[i].x + middlePoint.x, textPoints[i].y + middlePoint.y, paint);

        if(mode == Const.MODE_WHITE)
        {
        	paint.setColor(Color.WHITE);
        }
        else if(mode == Const.MODE_BLACK)
        {
        	paint.setColor(Color.BLACK);
        }

        canvas.drawPath(realValuePath, paint);
	}
	
	/**
	 * FunName: getShape
	 * Description: 得到图像
	 * 
	 * @param: mode屏幕模式
	 * @return: 得到六边形
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-8
	 */
	public Bitmap getShape(int mode)
	{
		drawShape(mode);
		return shape;
	}
}
