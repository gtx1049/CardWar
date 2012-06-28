package com.codeblood.customview;

import com.codeblood.R;
import com.codeblood.connector.Connector;
import com.codeblood.global.Const;
import com.codeblood.global.User;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;

public class Connectdialog extends Dialog
{
	private ImageButton host;
	private ImageButton wait;
	private ListView connectlist;
	
	public Connectdialog(Context context, int theme)
	{
		super(context, theme);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.connectdialog);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = Const.DIALOG_WIDTH;
		params.height = Const.DIALOG_HEIGHT;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		
		host = (ImageButton)findViewById(R.id.connect_host);
		wait = (ImageButton)findViewById(R.id.connect_wait);
		connectlist = (ListView)findViewById(R.id.connectnamelist);
		
		connectlist.setVisibility(View.GONE);
		host.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				host.setVisibility(View.GONE);
				Connector connector = Connector.getInstance(getContext());
				if(!connector.fillList(connectlist, getContext()))
				{
					User.displayToast(getContext(), "您的手机不支持蓝牙。");
					return;
				}
				connectlist.setVisibility(View.VISIBLE);
			}	
		});
		wait.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

}
