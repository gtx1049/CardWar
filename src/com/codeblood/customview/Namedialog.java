package com.codeblood.customview;

import java.util.List;

import com.codeblood.R;
import com.codeblood.global.Analyze;
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
public class Namedialog extends Dialog
{
	private ListView namelist;
	private String selectedname;
	private Handler refreshhandler;
	
	private List<String> namestringlist;
	
	public Namedialog(Context context, int theme, Handler handler)
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
		namestringlist = Datamanager.getInstance().contactnameInfo(getContext());
		
		namelist = (ListView)findViewById(R.id.dialog_namelist);
		namelist.setAdapter(new Dialogadapter());
		namelist.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				// TODO Auto-generated method stub
				selectedname = namestringlist.get(position);
				refreshhandler.sendMessage(refreshhandler.obtainMessage(Const.DIALOG_GET));
				Namedialog.this.dismiss();
			}
		});
	}

	public class Dialogadapter extends BaseAdapter
	{
		View[] itemviews;
		
		public Dialogadapter()
		{
			super();
			
			if(namestringlist.size() == 0)
			{
				return;
			}
			
			itemviews = new View[namestringlist.size()];
			for(int i = 0; i < itemviews.length; i++)
			{
				itemviews[i] = makeItemview(i);
			}
		}
		
		private View makeItemview(int i)
		{
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater)Namedialog.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemview = inflater.inflate(R.layout.dialognameitem, null);
			
			TextView name = (TextView)itemview.findViewById(R.id.dialog_name);
			name.setText(namestringlist.get(i));
			name.setTextColor(Color.WHITE);
			
			TextView ability = (TextView)itemview.findViewById(R.id.dialog_ability);
			ability.setText(Analyze.getInstance().getAbilitystring(namestringlist.get(i)));
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
		return selectedname;
	}
}
