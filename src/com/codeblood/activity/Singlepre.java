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
 * Description: ����ģʽ��Ϸ��ʼǰ��׼������
 *              ��ͨѶ¼��ѡ���ս˫��
 *
 */
public class Singlepre extends Activity
{
	//�ֱ�洢����������ͷ��
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
		
		//�����ݿ��еõ�Object
		Datamanager ins = Datamanager.getInstance();
		Object[] ret = ins.contactInfo(this);
		namelist = (List<String>)ret[Const.CONTACT_NAME];
		photolist = (List<Bitmap>)ret[Const.CONTACT_PHOTO];
		
		cardlist.setAdapter(new Cardlistview());
		
		cardlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			
			//��ѡ���б�������ʱ��װ�ص��Ҳ�
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
							User.displayToast(Singlepre.this, "ͬ������ս����");
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
							User.displayToast(Singlepre.this, "ͬ������ս����");
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
					User.displayToast(Singlepre.this, "��ѡ���ս˫��");
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
	 * �ڲ��࣬�̳���BaseAdapter
	 * ��ɶ��б��װ�ع�����ʹ���Ѿ���ȡ���ڴ��е�
	 * ������ͼƬ
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
