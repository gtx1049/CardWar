package com.codeblood.activity;

import com.codeblood.R;
import com.codeblood.actors.Leader;
import com.codeblood.global.Const;
import com.codeblood.global.DispatchUI;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;

public class Summarize extends Activity
{

	private ImageView leaderface;
	private ImageButton skill1;
	private ImageButton skill2;
	private ImageButton skill3;
	private ImageButton skill4;
	private ImageButton item;
	private ImageButton team;
	private ImageButton fight;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		leaderface = (ImageView)findViewById(R.id.status_face);
		skill1     = (ImageButton)findViewById(R.id.status_skill1);
		skill2     = (ImageButton)findViewById(R.id.status_skill2);
		skill3     = (ImageButton)findViewById(R.id.status_skill3);
		skill4     = (ImageButton)findViewById(R.id.status_skill4);
		item       = (ImageButton)findViewById(R.id.status_item);
		team       = (ImageButton)findViewById(R.id.status_team);
		fight      = (ImageButton)findViewById(R.id.status_fight);
		
		DispatchUI.dispatchButton(item, this, Const.ITEMSCROLL);
		DispatchUI.dispatchButton(team, this, Const.TEAMSCROLL);
		DispatchUI.dispatchButton(fight, this, Const.MUTISTATUS);
		
		skill1.setOnClickListener(new Skilllistener());
		skill2.setOnClickListener(new Skilllistener());
		skill3.setOnClickListener(new Skilllistener());
		skill4.setOnClickListener(new Skilllistener());
		
		SharedPreferences settings = getSharedPreferences("com.codeblood", 0);
		int leaderid = settings.getInt(Const.SP_LEADER_ID, -1);
		
		if(leaderid == -1)
		{
			return;
		}
		
		Leader.getLeader(this);
		
		leaderface.setImageResource(Const.LEADERFACE_BEGIN + leaderid - 1);
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		this.finish();
	}

	class Skilllistener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
}
