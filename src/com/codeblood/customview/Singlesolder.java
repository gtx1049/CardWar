package com.codeblood.customview;

import com.codeblood.R;
import com.codeblood.actors.Solder;
import com.codeblood.global.Const;
import com.codeblood.global.Imagedeal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Singlesolder
 * 
 * Description: 各属性总览控件
 * 
 */
public class Singlesolder extends RelativeLayout
{
	private ImageView photo;
	private TextView name;
	private TextView ability;
	private ImageView life;
	private TextView lifenumber;
	private ImageView shape;
	private ImageButton btncancel;
	
	private Solder solder;
	private int mode;
	private boolean isused;
	
	public Singlesolder(Context context, AttributeSet attrs)
	{		
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.singlesolder, this);
		
		photo      = (ImageView)findViewById(R.id.view_singlesolder_photo);
		name       = (TextView)findViewById(R.id.view_singlesolder_name);
		ability    = (TextView)findViewById(R.id.view_singlesolder_ability);
		life       = (ImageView)findViewById(R.id.view_singlesolder_life);
		lifenumber = (TextView)findViewById(R.id.view_singlesolder_lifenumber);
		shape      = (ImageView)findViewById(R.id.view_singlesolder_shape);
		btncancel  = (ImageButton)findViewById(R.id.view_singlesolder_cancel);
		
		btncancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Singlesolder.this.clearView();
			}
			
		});
		
		mode = Const.MODE_WHITE;
		isused = false;
	}
	
	/**
	 * FunName: setModeblack
	 * Description: 设置颜色模式，默认为白
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-11
	 */
	public void setModeblack()
	{
		mode = Const.MODE_BLACK;
	}
	
	/**
	 * FunName: setSolder
	 * Description: 装载士兵详细信息
	 * 
	 * @param: solder士兵
	 *         solderphoto头像
	 *         mode模式
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-11
	 */
	public void setSolder(Solder solder, Bitmap solderphoto)
	{
		this.solder = solder;
		int lifeint = solder.getLife();
		
		photo.setImageBitmap(solderphoto);
		name.setText(solder.getName());
		lifenumber.setText("" + lifeint);
		
		String abiltystring = "攻击: " + solder.getAttact()       + "  " +
				              "防御: " + solder.getGuard()        + "\n" + 
				              "速度: " + solder.getSpeed()        + "  " +
				              "命中: " + solder.getHitrate()      + "\n" +
				              "智力: " + solder.getIntelligence() + "  " +
				              "运气: " + solder.getLuck();
		ability.setText(abiltystring);
		
		String filename = null;
		if(mode == Const.MODE_BLACK)
		{
			btncancel.setImageResource(R.drawable.close_b);
			name.setTextColor(Color.WHITE);
			lifenumber.setTextColor(Color.WHITE);
			ability.setTextColor(Color.WHITE);
			filename = "lifew.png";
		}
		else if(mode == Const.MODE_WHITE)
		{
			btncancel.setImageResource(R.drawable.close_w);
			name.setTextColor(Color.BLACK);
			lifenumber.setTextColor(Color.BLACK);
			ability.setTextColor(Color.BLACK);
			filename = "life.png";
		}
		
		Bitmap lifebitmap = Imagedeal.getImageassets(filename, this.getContext());
		int lifezoom = (int)(150 * ((float)(solder.getLife()) / 320));
		lifebitmap = Imagedeal.zoomBitmap(lifebitmap, lifezoom, 10);
		life.setImageBitmap(lifebitmap);
		
		
		shape.setImageBitmap(Imagedeal.getShapebitmap(solder, mode));
		isused = true;
	}
	
	/**
	 * FunName: clearView
	 * Description: 清除显示信息
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-8-12
	 */
	public void clearView()
	{
		photo.setImageBitmap(null);
		name.setText(null);
		ability.setText(null);
		life.setImageBitmap(null);
		lifenumber.setText(null);
		shape.setImageBitmap(null);
		btncancel.setImageBitmap(null);
		solder = null;
		isused = false;
	}
	
	public boolean isUsed()
	{
		return isused;
	}
	
	public Solder getSolder()
	{
		return solder;
	}
}
