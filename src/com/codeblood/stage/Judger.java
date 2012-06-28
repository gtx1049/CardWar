package com.codeblood.stage;

import com.codeblood.global.Const;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * 
 * @author Star
 * 
 * Description: 裁判，判断当前情况
 * PS: 此处动画设计严重失败
 *
 */
public class Judger
{
	private Sprite solderone;
	private Sprite soldertwo;
	
	private TextPaint paint;
	private Paint lifepaint;
	
	public Judger()
	{
		solderone = new Sprite(Const.SOLDERONE);
		soldertwo = new Sprite(Const.SOLDERTWO);
		
		paint = new TextPaint();
		paint.setTextSize(20);
		paint.setColor(Color.BLUE);
		
		lifepaint = new Paint();
		lifepaint.setColor(Color.GREEN);
	}
	
	/**
	 * FunName: drawSolderone 
	 * Description: 绘制兵1
	 * 
	 * @param: bitmap绘制目标
	 * @param: name名字
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void drawSolderone(Canvas canvas, String name)
	{	
		solderone.drawSprite(canvas, name);
	}
	
	/**
	 * FunName: drawSoldertwo
	 * Description: 绘制兵2
	 * 
	 * @param: bitmap绘制目标
	 * @param: name名字
	 * 	 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void drawSoldertwo(Canvas canvas, String name)
	{	
		soldertwo.drawSprite(canvas, name);
	}
	
	/**
	 * FunName: drawText
	 * Description: 绘制文字
	 * 
	 * @param: bitmap绘制目标
	 *         String文字
	 * 	 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void drawText(Canvas canvas, String text)
	{	
		int lines = text.length() / Const.TEXT_COUNT + 1;
		
		String[] texts = new String[lines];
		for(int i = 0; i < lines; i++)
		{
			if(i != lines -1)
			{
				texts[i] = text.substring(i * Const.TEXT_COUNT, (i + 1) * Const.TEXT_COUNT);
			}
			if(i == lines - 1)
			{
				texts[i] = text.substring(i * Const.TEXT_COUNT);
			}
			
		}
		
		for(int i = 0; i < lines; i++)
		{
			canvas.drawText(texts[i], 20, 190 + i * 20, paint);
		}
		
	}
	
	/**
	 * FunName: drawLife
	 * Description: 绘制生命条
	 * 
	 * @param: canvas绘制目标
	 *         lifeone生命1
	 *         lifetwo生命2
	 * 	 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void drawLife(Canvas canvas, int lifeone, int lifetwo)
	{
		canvas.drawRect(40, 10, (float)lifeone / 320 * 160 + 40, 20, lifepaint);
		canvas.drawText(lifeone + "", 0, 20, paint);
		canvas.drawRect(360 - (float)lifetwo / 320 * 160, 10, 360, 20, lifepaint);
		canvas.drawText(lifetwo + "", 360, 20, paint);
	}
	
	public class Sprite
	{
		private int number;
		private Bitmap[] solderanim;
		private int soldertype;
		private int solderstatus;
		private int solderoffset;
		private int offset;
		
		private Matrix matrix;
		private int frameindex;
		private int deathcount;
		private boolean isdead;
		private boolean attack;
		
		Sprite(int number)
		{
			this.number = number;
			frameindex = -1;
			solderstatus = Const.ANIM_STAND;
			attack = false;
			matrix = new Matrix();
			solderoffset = 0;
			deathcount = 0;
			isdead = false;
			if(number == Const.SOLDERONE)
			{
				matrix.setScale(2, 2);
				matrix.postTranslate(30, Const.SPRITE_POSITIONY);
				offset = 15;
			}
			else if(number == Const.SOLDERTWO)
			{
				matrix.setScale(-2, 2);
				matrix.postTranslate(Const.DIALOG_WIDTH - 30, Const.SPRITE_POSITIONY);
				offset = -15;
			}
		}
		
		/**
		 * FunName: drawSprite
		 * Description: 绘制动画
		 * 
		 * @param: canvas画布
		 *         matrix调整矩阵
		 * 
		 * @Author: Star
		 * @CreateDate: 2012-9-2
		 */
		public void drawSprite(Canvas canvas, String name)
		{
			attack = true;
			
			if(solderstatus == Const.ANIM_STAND)
			{
				frameindex++;
				if(frameindex == 4)
				{
					frameindex = 0;
				}
				
				if(number == Const.SOLDERONE)
				{
					if(soldertwo.getOffset() < -Const.ATTACT_POSITON && soldertwo.getFrameindex() == 3)
					{
						solderstatus = Const.ANIM_DEAD;
						frameindex = 0;
					}
				}
				else if(number == Const.SOLDERTWO)
				{
					if(solderone.getOffset() > Const.ATTACT_POSITON && solderone.getFrameindex() == 3)
					{
						solderstatus = Const.ANIM_DEAD;
						frameindex = 0;
					}
				}
			}
			
			if(solderstatus == Const.ANIM_WALK)
			{
				solderoffset += offset;
				
				matrix.postTranslate(offset, 0);
				
				frameindex++;
				if(frameindex == 4)
				{
					frameindex = 0;
				}
				
				if(Math.abs(solderoffset) > Const.ATTACT_POSITON)
				{
					solderstatus = Const.ANIM_ATTACT;
					frameindex = 0;
				}
				
				if(number == Const.SOLDERONE)
				{
					if(solderoffset < 0)
					{
						solderstatus = Const.ANIM_STAND;
						solderoffset = 0;
						matrix.setScale(2, 2);
						matrix.postTranslate(30, Const.SPRITE_POSITIONY);
						offset = -offset;
						attack = false;
					}
				}
				else if(number == Const.SOLDERTWO)
				{
					if(solderoffset > 0)
					{
						solderstatus = Const.ANIM_STAND;
						solderoffset = 0;
						matrix.setScale(-2, 2);
						matrix.postTranslate(370, Const.SPRITE_POSITIONY);
						offset = -offset;
						attack = false;
					}
				}
			}
			
			if(solderstatus == Const.ANIM_ATTACT)
			{
				frameindex++;
				if(frameindex == 4)
				{
					solderstatus = Const.ANIM_WALK;
					frameindex = 0;
					if(number == Const.SOLDERONE)
					{
						matrix.setScale(-2, 2);
						matrix.postTranslate(300, Const.SPRITE_POSITIONY);
					}
					else if(number == Const.SOLDERTWO)
					{
						matrix.setScale(2, 2);
						matrix.postTranslate(120, Const.SPRITE_POSITIONY);
					}
					offset = -offset;
				}
			}
			
			if(solderstatus == Const.ANIM_DEAD)
			{
				frameindex++;
				if(frameindex == 4)
				{
					solderstatus = Const.ANIM_STAND;
					frameindex = 0;
				}
			}
			
			if(isdead)
			{
				solderstatus = Const.ANIM_DEAD;
				frameindex = 3;
				deathcount++;
				if(deathcount == 10)
				{
					attack = false;
				}
			}
			
			canvas.drawBitmap(solderanim[solderstatus * Const.ANIMATE_PIECE + frameindex], matrix, null);
			if(number == Const.SOLDERONE)
			{
				canvas.drawText(name, 40 + solderoffset, 45, paint);
			}
			else if(number == Const.SOLDERTWO)
			{
				canvas.drawText(name, 280 + solderoffset, 45, paint);
			}
		}
		
		//get and set
		public void setImage(Bitmap[] bitmap)
		{
			this.solderanim = bitmap;
		}
		
		public void setType(int code)
		{
			this.soldertype = code;
		}
		
		public void setAttack(boolean is)
		{
			if(is)
			{
				this.solderstatus = Const.ANIM_WALK;
			}
			else
			{
				this.solderstatus = Const.ANIM_STAND;
			}
		}
		
		public void setDead()
		{
			this.isdead = true;
		}
		
		public boolean getAttack()
		{
			return attack;
		}
		
		public int getOffset()
		{
			return this.solderoffset;
		}
		
		public int getFrameindex()
		{
			return this.frameindex;
		}
	}
	
	//set and get
	public void setImagepieceone(Bitmap[] bitmap)
	{
		this.solderone.setImage(bitmap);
	}
	
	public void setImagepiecetwo(Bitmap[] bitmap)
	{
		this.soldertwo.setImage(bitmap);
	}
	
	public void setSolderonetype(int code)
	{
		this.solderone.setType(code);
	}
	
	public void setSoldertwotype(int code)
	{
		this.soldertwo.setType(code);
	}
	
	public void setIsoneattack(boolean is)
	{
		this.solderone.setAttack(is);
	}
	
	public void setIstwoattack(boolean is)
	{
		this.soldertwo.setAttack(is);
	}
	
	public boolean isEnd()
	{
		return !solderone.getAttack() || !soldertwo.getAttack();
	}
	
	public void setSolderonedead()
	{
		this.solderone.setDead();
	}
	
	public void setSoldertwodead()
	{
		this.soldertwo.setDead();
	}
}
