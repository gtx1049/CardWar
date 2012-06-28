package com.codeblood.customview;

import java.util.List;

import com.codeblood.R;
import com.codeblood.actors.Leader;
import com.codeblood.global.Const;
import com.codeblood.global.Datamanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Star
 * 
 *Description: ÐÕÃû¶Ô»°¿ò
 * 
 */
public class Itemdialog extends Dialog
{
	private ListView itemlist;
	private String selecteditem;
	private int selectid = -1;
	private Handler refreshhandler;
	
	private List<String> itemstringlist;
	
	public Itemdialog(Context context, int theme, Handler handler)
	{
		super(context, theme);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.namelistdialog);
		
		refreshhandler = handler;
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = Const.DIALOG_WIDTH;
		params.height = Const.DIALOG_HEIGHT;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		itemstringlist = Datamanager.getInstance().getItemtable(getContext());
		
		itemlist = (ListView)findViewById(R.id.dialog_namelist);
		itemlist.setAdapter(new Dialogadapter());
		itemlist.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				// TODO Auto-generated method stub
				position /= 3;
				selecteditem = itemstringlist.get(position);
				selectid= Datamanager.getInstance().getItemid(selecteditem);
				Leader.getLeader().getItemtable()[selectid]--;
				refreshhandler.sendMessage(refreshhandler.obtainMessage(Const.DIALOG_ITEM_GET));
				Itemdialog.this.dismiss();
			}
		});
	}

	public class Dialogadapter extends BaseAdapter
	{
		View[] itemviews;
		
		public Dialogadapter()
		{
			super();
			
			if(itemstringlist.size() == 0)
			{
				return;
			}
			
			itemviews = new View[itemstringlist.size()];
			for(int i = 0; i < itemviews.length; i++)
			{
				itemviews[i] = makeItemview(i);
			}
		}
		
		private View makeItemview(int i)
		{
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater)Itemdialog.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemview = inflater.inflate(R.layout.dialognameitem, null);
			
			TextView item = (TextView)itemview.findViewById(R.id.dialog_name);
			
			item.setTextColor(Color.WHITE);
			
			TextView ability = (TextView)itemview.findViewById(R.id.dialog_ability);
			ability.setText("");
			ability.setTextColor(Color.BLACK);
			
			
			return itemview;
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
		
	}
	
	//getter and setter
	public String getSelected()
	{
		return selecteditem;
	}
	
	public int getSelectid()
	{
		return selectid;
	}
}
