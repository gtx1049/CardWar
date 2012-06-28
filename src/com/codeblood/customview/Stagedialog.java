package com.codeblood.customview;

import com.codeblood.R;
import com.codeblood.actors.Solder;
import com.codeblood.global.Const;
import com.codeblood.stage.Mutistage;
import com.codeblood.stage.Singlestage;
import com.codeblood.stage.Stage;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Stagedialog extends Dialog
{
	private ImageView stagelive;
	private Handler refreshhandler;
	
	private Singlestage singlestage = null;
	private Mutistage mutistage = null;
	private Stage stage = null;
	
	//private Bitmap stagecanvas;
	
	public Stagedialog(Context context, int theme)
	{
		super(context, theme);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.stagelayout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = Const.DIALOG_WIDTH;
		params.height = Const.DIALOG_HEIGHT;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);

		stagelive = (ImageView)findViewById(R.id.stage_living);
		
		refreshhandler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if(msg.what == Const.REFRESH)
				{
					stagelive.setImageBitmap(stage.getCanvas());
				}
				else if(msg.what == Const.END)
				{
					Stagedialog.this.dismiss();
				}
			}
		};
		
	}
	
	/**
	 * FunName: beginFight 
	 * Description: ¾ö¶¨±øÖÖ
	 * 
	 * @param: solderone±ø1
	 * 		   soldertwo±ø2
	 * 
	 * @Author: Star
	 * @CreateDate: 2012-9-1
	 */
	public void beginFight(Solder solderone, Solder soldertwo, int mode)
	{
		if(mode == Const.SINGLESTAGE)
		{
			singlestage = new Singlestage(solderone, soldertwo, refreshhandler);
			stage = singlestage;
			singlestage.setStyle(getContext());
			singlestage.fightDisplay();
		}
		else if(mode == Const.MULTISTAGE)
		{
			mutistage = new Mutistage(solderone, soldertwo, refreshhandler);
			stage = mutistage;
		}
	}
	

}
