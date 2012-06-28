package com.codeblood.activity;

import java.util.TimerTask;

import com.codeblood.R;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;
import com.codeblood.global.DispatchUI;
import com.codeblood.global.Imagedeal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 
 * @author Star
 * 
 * Description: 欢迎类，进入游戏的Activity,包括欢迎LOGO
 * 			           单机与联机模式的选择
 * 
 */
public class Welcome extends Activity
{	
	private ImageButton singlegamebutton;
	private ImageButton mutigamebutton;
	private ImageView logoimage;
	
	private Handler animatehandler;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//准备数据库
		Datamanager.getInstance().dbReady(this);
		
		SharedPreferences settings = getSharedPreferences("com.codeblood", 0);
		boolean isfirst = settings.getBoolean(Const.SP_ISFIRST, true);
		
		logoimage        = (ImageView)this.findViewById(R.id.logo_image);
		singlegamebutton = (ImageButton)this.findViewById(R.id.single_game_button);
		mutigamebutton   = (ImageButton)this.findViewById(R.id.muti_game_button);
		
		//UI分发，初次进入游戏进入英雄选择模式
		DispatchUI.dispatchButton(singlegamebutton, this, Const.SINGLEPRE);
		if(isfirst)
		{
			DispatchUI.dispatchButton(mutigamebutton, this, Const.SELECTLEADR);
		}
		else
		{
			DispatchUI.dispatchButton(mutigamebutton, this, Const.SUMMARIZE);
		}
		
		//获取手机分辨率
		int[] screen = Imagedeal.getScreenwidthandheight(this);
		
		Bitmap logo = Imagedeal.zoomBitmap(Imagedeal.getImageassets("logo.png", this), 
										   screen[Const.WIDTH], screen[Const.HEIGHT] / 3);
		
		logoimage.setImageBitmap(logo);
		singlegamebutton.setVisibility(View.GONE);
		mutigamebutton.setVisibility(View.GONE);
		
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		
		//动画，使用TimeTask达成等待效果
		TimerTask timer1 = new TimerTask()
		{
			public void run()
			{
				animatehandler.sendMessageDelayed(animatehandler.obtainMessage(Const.ANIMATE1), 
						                          1500);
			}
		};
		
		AlphaAnimation logoanimation = new AlphaAnimation(0, 1);
		logoanimation.setDuration(1500);
		logoimage.startAnimation(logoanimation);
			
		animatehandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if(msg.what == Const.ANIMATE1)
				{
					TimerTask timer2 = new TimerTask()
					{
						public void run()
						{
							animatehandler.sendMessageDelayed(animatehandler.obtainMessage(Const.ANIMATE2), 
									                          1500);
						}
					};
					AlphaAnimation logoanimation = new AlphaAnimation(1, 0);
					logoanimation.setDuration(1500);
					logoimage.startAnimation(logoanimation);
					timer2.run();
				}
				else if(msg.what == Const.ANIMATE2)
				{
					logoimage.setVisibility(View.GONE);
					singlegamebutton.setVisibility(View.VISIBLE);
					mutigamebutton.setVisibility(View.VISIBLE);
				}
			}
		};
		timer1.run();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		animatehandler = null;
		
		//离开时记录
		SharedPreferences settings = getSharedPreferences("com.codeblood", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Const.SP_ISFIRST, false);
		
		editor.putInt(Const.SP_LEADER_ID, 2);
		
		editor.commit();
		super.onDestroy();
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Datamanager.getInstance().clearMemory();
		this.finish();
	}
	
	
}