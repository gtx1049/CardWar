package com.codeblood.activity;

import com.codeblood.R;
import com.codeblood.global.Const;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.app.Activity;
import android.content.Context;

/**
 * 
 * @author Star
 * 
 * Description: 使用viewflipper选择队长
 *
 */
public class Selectleader extends Activity
{
	private ViewFlipper flipperleader;
	private GestureDetector detector;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectleader);
		
		flipperleader = (ViewFlipper)findViewById(R.id.flipper_select_leader);
		detector = new GestureDetector(new Gesturelistener());
		
		for(int i = 0; i < Const.IAMGE_COUNT; i++)
		{
			flipperleader.addView(addView(Const.IAMGE_BEGIN + i));
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event);
	}

	/**
	 * FunName: addView
	 * Description: 向flipper里装载view
	 * 
	 * @param: imagenumber图片编号
	 * @return: View装载进flipper里的View
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-4
	 */
	private View addView(int imagenumber)
	{
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View theview = inflater.inflate(R.layout.viewselectleader, null);
		
		//装载背景
		ImageView leaderimage = (ImageView)theview.findViewById(R.id.background_select_leader);
		leaderimage.setImageResource(imagenumber);
		
		return theview;
	}
	
	/**
	 * 
	 * @author Star
	 * 
	 * Description: 实现手势的监听器
	 *
	 */
	public class Gesturelistener implements OnGestureListener
	{
		
		@Override
		public boolean onDown(MotionEvent e)
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			// TODO Auto-generated method stub
			if(e1.getX() - e2.getX() > 120)
			{
				flipperleader.setInAnimation(AnimationUtils.loadAnimation(Selectleader.this, 
																		  R.anim.left_in));
				flipperleader.setOutAnimation(AnimationUtils.loadAnimation(Selectleader.this, 
																		  R.anim.left_out));
				flipperleader.showNext();
				return true;
			}
			else if(e1.getX() - e2.getX() < -120)
			{
				flipperleader.setInAnimation(AnimationUtils.loadAnimation(Selectleader.this, 
						  R.anim.right_in));
				flipperleader.setOutAnimation(AnimationUtils.loadAnimation(Selectleader.this, 
						  R.anim.right_out));
				flipperleader.showPrevious();
				return true;
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY)
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e)
		{
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
