package com.codeblood.stage;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;

import com.codeblood.R;
import com.codeblood.actors.Solder;
import com.codeblood.global.Analyze;
import com.codeblood.global.Const;
import com.codeblood.global.Imagedeal;

/**
 * 
 * @author Star
 * 
 * Description: 舞台
 *
 */
public class Stage
{

	private Judger judger;
	private Solder solderone;
	private Solder soldertwo;
	private Bitmap stagecanvas;
	private Handler displayhandler;
	
	public Stage(Solder solderone, Solder soldertwo, Handler handler)
	{
		//将外部创建的空白bitmap放进，并在其上进行绘制
		this.solderone = solderone;
		this.soldertwo = soldertwo;
		this.displayhandler = handler;
		this.stagecanvas = Bitmap.createBitmap(Const.DIALOG_WIDTH, Const.DIALOG_HEIGHT, Bitmap.Config.ARGB_8888);;
		this.judger = new Judger();
	}

	/**
	 * FunName: setStyle 
	 * Description: 决定兵种
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void setStyle(Context context)
	{
		if (solderone.getAttact() >= solderone.getIntelligence()
				&& solderone.getAttact() >= solderone.getHitrate())
		{
			judger.setImagepieceone(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
					                R.drawable.footman), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSolderonetype(Const.FOOTMAN);
		}
		else if (solderone.getIntelligence() >= solderone.getAttact()
				&& solderone.getIntelligence() >= solderone.getHitrate())
		{
			judger.setImagepieceone(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
	                R.drawable.mages), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSolderonetype(Const.MAGE);
		} 
		else if (solderone.getHitrate() >= solderone.getAttact()
				&& solderone.getHitrate() >= solderone.getIntelligence())
		{
			judger.setImagepieceone(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
	                R.drawable.archers), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSolderonetype(Const.ARCHER);
		}
		if (soldertwo.getAttact() >= soldertwo.getIntelligence()
				&& soldertwo.getAttact() >= soldertwo.getHitrate())
		{
			judger.setImagepiecetwo(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
	                R.drawable.footman), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSoldertwotype(Const.FOOTMAN);
		} 
		else if (soldertwo.getIntelligence() >= soldertwo.getAttact()
				&& soldertwo.getIntelligence() >= soldertwo.getHitrate())
		{
			judger.setImagepiecetwo(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
	                R.drawable.mages), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSoldertwotype(Const.MAGE);
		} 
		else if (soldertwo.getHitrate() >= soldertwo.getAttact()
				&& soldertwo.getHitrate() >= soldertwo.getIntelligence())
		{
			judger.setImagepiecetwo(Imagedeal.getImagepiece(BitmapFactory.decodeResource(context.getResources(), 
	                R.drawable.archers), Const.ANIMATE_PIECE, Const.ANIMATE_PIECE));
			judger.setSoldertwotype(Const.ARCHER);
		}

	}

	public void fightDisplay()
	{
		Fightprocess fp = new Fightprocess();
		fp.start();

	}
	
	public Bitmap getCanvas()
	{
		return stagecanvas;
	}
	
	public class Fightprocess extends Thread
	{
		List<String> fightlist = null;

		public Fightprocess()
		{
			Analyze ins = Analyze.getInstance();
			fightlist = ins.fightAnalyze(solderone, soldertwo);
		}

		public void run()
		{
			Canvas canvas = new Canvas(stagecanvas);
			Iterator<String> it = fightlist.iterator();
			
			boolean turn = false;
			
			int lifeone;
			int lifetwo;
			String life;
			String[] splits;
			int namelengths = solderone.getName().length() + soldertwo.getName().length();
			
			life = it.next();
			splits = life.substring(namelengths + 2).split(" ");
			if(life.substring(0, solderone.getName().length()).equals(solderone.getName()))
			{
				turn = false;
				lifeone = Integer.parseInt(splits[0]);
				lifetwo = Integer.parseInt(splits[1]);
			}
			else 
			{
				turn = true;
				lifeone = Integer.parseInt(splits[1]);
				lifetwo = Integer.parseInt(splits[0]);
			}

			
			while (it.hasNext())
			{
				//通知显示文字
				turn = !turn;
				String condition = it.next();
				
				if(condition.length() > namelengths + 1)
				{
					String check = condition.substring(0, namelengths + 1);
					if(check.equals(solderone.getName() +  "#" + soldertwo.getName()) ||
					   check.equals(soldertwo.getName() + "#" + solderone.getName()))
					{
						condition = condition.substring(namelengths + 2);
						splits = condition.split(" ");
						if(check.substring(0, solderone.getName().length()).equals(solderone.getName()))
						{
							lifeone = Integer.parseInt(splits[0]);
							lifetwo = Integer.parseInt(splits[1]);
						}
						else 
						{
							lifeone = Integer.parseInt(splits[1]);
							lifetwo = Integer.parseInt(splits[0]);
						}
						turn = !turn;
						continue;
					}
				}
				judger.setIsoneattack(turn);
				judger.setIstwoattack(!turn);
				
				if(lifeone == 0 || lifetwo == 0)
				{
					if(solderone.getLife() == 0)
					{
						judger.setSolderonedead();
						judger.setIsoneattack(false);
					}
					else
					{
						judger.setSoldertwodead();
						judger.setIstwoattack(false);
					}
				}
				
				while(true)
				{
					long begin = System.currentTimeMillis();
					canvas.drawColor(Color.YELLOW);
					judger.drawText(canvas, condition);
					judger.drawSolderone(canvas, solderone.getName());
					judger.drawSoldertwo(canvas, soldertwo.getName());	
					judger.drawLife(canvas, lifeone, lifetwo);
					
					long end = System.currentTimeMillis();
					
					try
					{
						if((end - begin) < 200)
						{
							Thread.sleep(200 - (end - begin));
						}
					} 
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					displayhandler.sendMessage(displayhandler.obtainMessage(Const.REFRESH));
					
					if(judger.isEnd())
					{
						break;
					}
				}
			}
			
			displayhandler.sendMessage(displayhandler.obtainMessage(Const.END));
		}
	}
}
