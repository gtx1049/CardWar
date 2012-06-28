package com.codeblood.global;

import com.codeblood.activity.Mutistatus;
import com.codeblood.activity.Selectleader;
import com.codeblood.activity.Singlepre;
import com.codeblood.activity.Summarize;
import com.codeblood.activity.Teamscroll;
import com.codeblood.activity.Welcome;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 
 * @Author: Star
 * 
 *          Description: UI集中的跳转
 * 
 */
public class DispatchUI
{
	/**
	 * FunName: dispatchButton Description: 分发按钮的activity连接
	 * 
	 * @param: enter传进来的按钮 preactivity传进来的当前activity target目标位置，常量里有声名
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-1
	 */
	public static void dispatchButton(ImageButton enter,
			final Activity preactivity, final int target)
	{
		enter.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = null;
				boolean finishit = true;
				switch (target)
				{
				case Const.WELCOME:
					intent = new Intent(preactivity, Welcome.class);
					finishit = false;
					break;
				case Const.SINGLEPRE:
					intent = new Intent(preactivity, Singlepre.class);
					break;
				case Const.SUMMARIZE:
					intent = new Intent(preactivity, Summarize.class);
					break;
				case Const.SELECTLEADR:
					intent = new Intent(preactivity, Selectleader.class);
					break;
				case Const.TEAMSCROLL:
					intent = new Intent(preactivity, Teamscroll.class);
					break;
				case Const.MUTISTATUS:
					intent = new Intent(preactivity, Mutistatus.class);
					break;
				}
				preactivity.startActivity(intent);
				preactivity.overridePendingTransition(R.anim.fade_in,
						R.anim.fade_out);
				if(finishit)
				{
					preactivity.finish();
				}
			}

		});
	}

	/**
	 * FunName: dispatchBack Description: 分发back键的activity连接
	 * 
	 * @param: preactivity原来到activity target目标位置，常量里有声名
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-16
	 */
	public static void dispatchBack(Activity preactivity, int target)
	{
		Intent intent = null;
		switch (target)
		{
		case Const.WELCOME:
			intent = new Intent(preactivity, Welcome.class);
			break;
		case Const.SINGLEPRE:
			intent = new Intent(preactivity, Singlepre.class);
			break;
		case Const.SUMMARIZE:
			intent = new Intent(preactivity, Summarize.class);
			break;
		case Const.SELECTLEADR:
			intent = new Intent(preactivity, Selectleader.class);
			break;
		case Const.TEAMSCROLL:
			intent = new Intent(preactivity, Teamscroll.class);
			break;
		case Const.MUTISTATUS:
			intent = new Intent(preactivity, Mutistatus.class);
			break;
		}
		preactivity.startActivity(intent);
		preactivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
