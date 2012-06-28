package com.codeblood.activity;

import java.util.List;

import com.codeblood.R;
import com.codeblood.customview.Singlesolder;
import com.codeblood.customview.Stagedialog;
import com.codeblood.global.Analyze;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;
import com.codeblood.global.Imagedeal;
import com.codeblood.global.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * 
 * @author Star
 * 
 * Description: 单人模式游戏开始前的准备界面
 *              在通讯录中选择对战双方
 *
 */
public class Singlepre extends Activity
{
	//分别存储玩家名和玩家头像
	private ListView cardlist;
	private ImageButton fight;
	private List<String> namelist = null;
	private List<Bitmap> photolist = null;
	
	private Singlesolder viewsolderone = null;
	private Singlesolder viewsoldertwo = null;
	
	private Stagedialog stagedialog = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singlepre);
		
		stagedialog = new Stagedialog(this, R.style.NameListDialog);
		
		cardlist = (ListView)findViewById(R.id.namelist);
		fight = (ImageButton)findViewById(R.id.view_single_fight);
		viewsolderone = (Singlesolder)findViewById(R.id.view_solderone);
		viewsoldertwo = (Singlesolder)findViewById(R.id.view_soldertwo); 		
		
		//在数据库中得到Object
		Datamanager ins = Datamanager.getInstance();
		Object[] ret = ins.contactInfo(this);
		namelist = (List<String>)ret[Const.CONTACT_NAME];
		photolist = (List<Bitmap>)ret[Const.CONTACT_PHOTO];
		
		cardlist.setAdapter(new Cardlistview());
		
		cardlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			
			//当选中列表中人物时，装载到右侧
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					                int position, long arg3)
			{
				// TODO Auto-generated method stub
				Analyze ins = Analyze.getInstance();
				if(!viewsolderone.isUsed())
				{
					if(viewsoldertwo.getSolder() != null)
					{
						if(namelist.get(position) == viewsoldertwo.getSolder().getName())
						{
							User.displayToast(Singlepre.this, "同名不可战斗。");
							return;
						}
					}
					viewsolderone.setSolder(ins.produceSolder(namelist.get(position)),
							                Imagedeal.circlePhoto(photolist.get(position), 
							                Singlepre.this, Const.MODE_WHITE));
				}
				else
				{
					if(viewsolderone.getSolder() != null)
					{
						if(namelist.get(position) == viewsolderone.getSolder().getName())
						{
							User.displayToast(Singlepre.this, "同名不可战斗。");
							return;
						}
					}
					if(!viewsoldertwo.isUsed())
					{
						viewsoldertwo.setSolder(ins.produceSolder(namelist.get(position)),
									 			Imagedeal.circlePhoto(photolist.get(position), 
									 			Singlepre.this, Const.MODE_WHITE));
					}
				}
			}
			
		});
		
		fight.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				if(viewsolderone.getSolder() == null || viewsoldertwo.getSolder() == null)
				{
					User.displayToast(Singlepre.this, "请选择对战双方");
					return;
				}
				
				stagedialog.show();
				stagedialog.beginFight(viewsolderone.getSolder(), viewsoldertwo.getSolder(), Const.SINGLESTAGE);
			}
			
		});
	}
	
	
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}



	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		for(int i = 0; i < photolist.size(); i++)
		{
			photolist.get(i).recycle();
		}
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		this.finish();
	}

	/**
	 * 
	 * @author Star
	 * 内部类，继承自BaseAdapter
	 * 完成对列表的装载工作，使用已经获取在内存中的
	 * 名称与图片
	 * 
	 */
	public class Cardlistview extends BaseAdapter
	{
		View[] itemviews;
		
		public Cardlistview()
		{
			super();
			
			if(Singlepre.this.namelist.size() == 0)
			{
				return;
			}
			
			itemviews = new View[namelist.size()];
			for(int i = 0; i < itemviews.length; i++)
			{
				itemviews[i] = makeItemview(i);
			}
		}
		
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return itemviews.length;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return position;
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
			LayoutInflater inflater = (LayoutInflater)Singlepre.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.carditem, null);
			
			TextView nametext = (TextView)itemView.findViewById(R.id.card_name);
			nametext.setText(Singlepre.this.namelist.get(i));
			nametext.setTextColor(Color.WHITE);
			
			TextView recordtext = (TextView)itemView.findViewById(R.id.card_record);
			recordtext.setText(Datamanager.getInstance().getRecordone(Singlepre.this.namelist.get(i)));
			recordtext.setTextColor(Color.WHITE);
			
			ImageView image = (ImageView)itemView.findViewById(R.id.card_image);
			image.setImageBitmap(Imagedeal.circlePhoto(Singlepre.this.photolist.get(i), Singlepre.this, Const.MODE_BLACK));
			return itemView;
		}
	}
}
