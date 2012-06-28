package com.codeblood.activity;

import com.codeblood.R;
import com.codeblood.customview.Connectdialog;
import com.codeblood.customview.Singlesolder;
import com.codeblood.global.Const;
import com.codeblood.global.DispatchUI;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;

public class Mutistatus extends Activity
{	
	private ListView team;
	private Singlesolder mysolder;
	private Singlesolder hissolder;
	
	private Connectdialog cdialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summarize);	
		
		team      = (ListView)findViewById(R.id.muti_namelist);
		mysolder  = (Singlesolder)findViewById(R.id.muti_view_solderone);
		hissolder = (Singlesolder)findViewById(R.id.muti_view_soldertwo);
		
		cdialog = new Connectdialog(this, R.style.NameListDialog);
		
		cdialog.show();
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		DispatchUI.dispatchBack(this, Const.SUMMARIZE);
		this.finish();
	}

}
