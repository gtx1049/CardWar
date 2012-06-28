package com.codeblood.activity;

import com.codeblood.R;
import com.codeblood.actors.Leader;
import com.codeblood.actors.Solder;
import com.codeblood.customview.Itemdialog;
import com.codeblood.customview.Namedialog;
import com.codeblood.customview.Singlesolder;
import com.codeblood.global.Analyze;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;
import com.codeblood.global.DispatchUI;
import com.codeblood.global.Imagedeal;
import com.codeblood.global.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

public class Teamscroll extends Activity
{
	private ListView teamlist;
	private Singlesolder solderwatch;
	private ImageButton btnteam1;
	private ImageButton btnteam2;
	private ImageButton btnteam3;
	
	private ImageButton btnchange;
	private ImageButton btnequip;
	
	private TextView solderitem;
	
	private Handler refreshhandler;
	private Namedialog namedialog;
	private Itemdialog itemdialog;
	private int currentteam = 0;
	
	private int changeposition = -1;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teamscroll);
		
		teamlist    = (ListView)findViewById(R.id.team_namelist);
		solderwatch = (Singlesolder)findViewById(R.id.team_solderwatch);
		solderwatch.setModeblack();
		
		btnteam1    = (ImageButton)findViewById(R.id.scroll_team1);
		btnteam2    = (ImageButton)findViewById(R.id.scroll_team2);
		btnteam3    = (ImageButton)findViewById(R.id.scroll_team3);
		
		solderitem  = (TextView)findViewById(R.id.scroll_item);
		
		btnchange   = (ImageButton)findViewById(R.id.team_member_change);
		btnequip    = (ImageButton)findViewById(R.id.team_member_equip);
		
		BtnListener bl = new BtnListener();
		btnchange.setOnClickListener(bl);
		btnequip.setOnClickListener(bl);
		
		Teamlistener tl = new Teamlistener();
		btnteam1.setOnClickListener(tl);
		btnteam2.setOnClickListener(tl);
		btnteam3.setOnClickListener(tl);
		
		teamlist.setAdapter(new Teamlistview());
		teamlist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				// TODO Auto-generated method stub
				Solder theone = Leader.getLeader().getTeammember(currentteam, position);
				if(theone.getName() != "")
				{
					solderwatch.setSolder(theone, 
									  Imagedeal.circlePhoto(Datamanager.getInstance().getNamephoto(theone.getName(), Teamscroll.this),
									  Teamscroll.this, Const.MODE_BLACK));
				}
				else
				{
					changeposition = position;
					namedialog.show();
				}
			}
		});
		
		//当接收到从对话框返回的换人时，进行换人操作
		refreshhandler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if(msg.what == Const.DIALOG_GET)
				{
					String name = namedialog.getSelected();
					Solder theone = Analyze.getInstance().produceSolder(name);
					
					if(!Leader.getLeader().setSolder(theone,
												 currentteam, 
												 changeposition))
					{
						User.displayToast(Teamscroll.this, "队伍中已有此人");
						return;
					}
					
					System.out.println(Leader.getLeader().getTeammember(currentteam, changeposition).getName() + "papapa");
					
					View tempview = (View)teamlist.getAdapter().getItem(changeposition);
					TextView nametext = (TextView)tempview.findViewById(R.id.card_name);
					TextView recordtext = (TextView)tempview.findViewById(R.id.card_record);
					ImageView image = (ImageView)tempview.findViewById(R.id.card_image);
					nametext.setText(theone.getName());
					recordtext.setText(theone.getRecord());		
					image.setImageBitmap(Imagedeal.circlePhoto(Datamanager.getInstance().getNamephoto(theone.getName(), Teamscroll.this),
										 Teamscroll.this, Const.MODE_WHITE));
					
					solderwatch.setSolder(theone, Imagedeal.circlePhoto(Datamanager.getInstance().getNamephoto(theone.getName(), Teamscroll.this),
										          Teamscroll.this, Const.MODE_BLACK));
					
				}
				else if(msg.what == Const.DIALOG_ITEM_GET)
				{
					String name = itemdialog.getSelected();
					int id = itemdialog.getSelectid();
					Leader.getLeader().getTeammember(currentteam, changeposition).setItem(id);
					solderitem.setText(name);
				}
			}
		};
		namedialog = new Namedialog(this, R.style.NameListDialog, refreshhandler);
		
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		DispatchUI.dispatchBack(this, Const.SUMMARIZE);
		this.finish();
	}

	/**
	 * 
	 * @author Star
	 * 内部类，继承自BaseAdapter
	 * 完成对列表的装载工作，使用Leader信息装载
	 * 
	 */
	public class Teamlistview extends BaseAdapter
	{
		View[] itemviews;
		
		public Teamlistview()
		{
			super();
			
			itemviews = new View[Const.TEAM_SOLDER_NUM];
			for(int i = 0; i < Const.TEAM_SOLDER_NUM; i++)
			{
				itemviews[i] = makeItemview(i);
			}
		}
		
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return Const.TEAM_SOLDER_NUM;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return itemviews[position];
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			return itemviews[position];
		}
	
		private View makeItemview(int i)
		{
			LayoutInflater inflater = (LayoutInflater)Teamscroll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.carditem, null);
			
			Solder theone = Leader.getLeader().getTeammember(currentteam, i);
			
			TextView nametext = (TextView)itemView.findViewById(R.id.card_name);
			TextView recordtext = (TextView)itemView.findViewById(R.id.card_record);
			ImageView image = (ImageView)itemView.findViewById(R.id.card_image);
			
			if(theone.getName().length() == 0)
			{
				image.setImageResource(R.drawable.xmdefault);
				nametext.setText("未知");
				nametext.setTextColor(Color.BLACK);
				recordtext.setText("未知");
				recordtext.setTextColor(Color.BLACK);
				return itemView;
			}
			
			nametext.setText(theone.getName());
			nametext.setTextColor(Color.BLACK);
			
			recordtext.setText(theone.getRecord());
			recordtext.setTextColor(Color.BLACK);
			
			image.setImageBitmap(Imagedeal.circlePhoto(Datamanager.getInstance().getNamephoto(theone.getName(), Teamscroll.this),
								 Teamscroll.this, Const.MODE_WHITE));
			return itemView;
		}
	}
	
	public class BtnListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			ImageButton ib = (ImageButton)v;
			if(ib.equals(btnchange))
			{
				if(changeposition != -1)
				{
					namedialog.show();
				}
			}
			else if(ib.equals(btnequip))
			{
				
			}
		}
		
	}
	
	public class Teamlistener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			ImageButton btn = (ImageButton)v;
			int id = btn.getId();
			switch(id)
			{
				case R.id.scroll_team1:
					btn.setImageResource(R.drawable.btn_selected_team1);
					btnteam2.setImageResource(R.drawable.btn_scroll_team2);
					btnteam3.setImageResource(R.drawable.btn_scroll_team3);
					currentteam = 0;
					break;
				case R.id.scroll_team2:
					btn.setImageResource(R.drawable.btn_selected_team2);
					btnteam1.setImageResource(R.drawable.btn_scroll_team1);
					btnteam3.setImageResource(R.drawable.btn_scroll_team3);
					currentteam = 1;
					break;
				case R.id.scroll_team3:
					btn.setImageResource(R.drawable.btn_selected_team3);
					btnteam1.setImageResource(R.drawable.btn_scroll_team1);
					btnteam2.setImageResource(R.drawable.btn_scroll_team2);
					currentteam = 2;
					break;
			}
			solderwatch.clearView();
			changeposition = -1;
			teamlist.setAdapter(new Teamlistview());
		}
		
	}
}
