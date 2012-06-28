package com.codeblood.global;

import com.codeblood.R;

import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * 
 * @Author: Star
 * 
 * Description: 所有的常量均在此处
 * 
 */
public class Const
{
	//activity code
	public static final int WELCOME     = 0;
	public static final int SINGLEPRE   = 1;
	public static final int SELECTLEADR = 2;
	public static final int SUMMARIZE   = 3;
	public static final int ITEMSCROLL  = 4;
	public static final int TEAMSCROLL  = 5;
	public static final int MUTISTATUS  = 6;
	
	
	//animate code
	public static final int ANIMATE1 = 0;
	public static final int ANIMATE2 = 1;
	public static final int REFRESH = 1;
	public static final int ANIMATE_PIECE = 4;
	public static final int ANIM_STAND = 0;
	public static final int ANIM_WALK = 1;
	public static final int ANIM_ATTACT = 2;
	public static final int ANIM_DEAD = 3;
	public static final int ANIM_SIZE = 75;
	public static final int FOOTMAN = 0;
	public static final int MAGE = 1;
	public static final int ARCHER = 2;
	public static final int FIREKNIGHT = 3;
	public static final int SOLDERONE = 1;
	public static final int SOLDERTWO = 2;
	public static final int ATTACT_POSITON = 105;
	public static final int TEXT_COUNT = 30;
	public static final int SPRITE_POSITIONY = 22;
	public static final int END = 2;
	
	//account log
	public static final String[] PHONES_PROJECTION = new String[]
													{Phone.DISPLAY_NAME, Phone.PHOTO_ID, Phone.CONTACT_ID};
	public static final int PHONE_DIPLAY_NAME = 0;
	public static final int PHONE_PHOTO_ID = 1;
	public static final int PHONE_CONTACT_ID = 2;
	
	public static final String[] PHONES_PROJECTION2 = new String[]
			                                         {Phone.PHOTO_ID, Phone.CONTACT_ID};
	public static final int PHONE_PHOTO_ID2 = 0;
	public static final int PHONE_CONTACT_ID2 = 1;
	
	public static final String[] PHONES_PROJECTION3 = new String[]
													 {Phone.DISPLAY_NAME};
	
	//data tag
	public static final int CONTACT_NAME = 0;
	public static final int CONTACT_PHOTO = 1;
	public static final int CONTACT_RECORD = 2;
	
	//index of width and height
	public static final int WIDTH = 0;
	public static final int HEIGHT = 1;
	
	//number max
	public static final int TEAM_SOLDER_NUM = 5;
	public static final int TEAM_NUM = 3;
	public static final int SKILL_NUM = 4;
	
	//database all
	public static final String DB_URL = "/data/data/com.codeblood/files/";
	public static final String DB_FILE_NAME = "cardwar.db";
	
	public static final String TABLE_LEADER = "leader";
	public static final String LEADER_ID = "id";
	public static final String LEADER_NAME = "name";
	public static final String LEADER_ATTACT = "attact";
	public static final String LEADER_GUARD = "guard";
	public static final String LEADER_SPEED= "speed";
	public static final String LEADER_INTELLIGENCE = "intelligence";
	public static final String LEADER_HITRATE = "hitrate";
	public static final String LEADER_LUCKY = "lucky";
	public static final String LEADER_AMOUNT = "amount";
	public static final String LEADER_WIN = "win";
	public static final int LEADER_ATTACT_INDEX = 0;
	public static final int LEADER_GUARD_INDEX = 1;
	public static final int LEADER_SPEED_INDEX = 2;
	public static final int LEADER_INTELLIGENCE_INDEX = 3;
	public static final int LEADER_HITRATE_INDEX = 4;
	public static final int LEADER_LUCKY_INDEX = 5;
	public static final int LEADER_ATTRI_GET = 6;
	
	public static final String TABLE_LEADER_SKILL = "leader_skill";
	public static final String LEADER_SKILL_LEADERID = "leader_id";
	public static final String LEADER_SKILL_SKILLID = "skill_id";
	public static final int LEADER_SKILL_LEADERID_INDEX = 0;
	public static final int LEADER_SKILL_SKILLID_INDEX = 1;
	
	public static final String TABLE_KILL = "skill";
	public static final String SKILL_ID = "id";
	public static final String SKILL_NAME = "name";
	public static final String SKILL_INTRODUCTION = "introduction";
	public static final int SKILL_ID_INDEX = 0;
	public static final int SKILL_NAME_INDEX = 1;
	public static final int SKILL_INTRODUCTION_INDEX = 2;
	
	public static final String TABLE_SOLDER = "solder";
	public static final String SOLDER_NAME = "name";
	public static final String SOLDER_AMOUNT = "amount";
	public static final String SOLDER_WIN = "win";
	public static final String SOLDER_ITEM = "item_id";
	public static final int SOLDER_AMOUT_INDEX = 0;
	public static final int SOLDER_WIN_INDEX = 1;
	public static final int SOLDER_ITEM_INDEX = 2;
	public static final int SOLDER_DATA_COUNT = 3;
	
	public static final String TABLE_ITEM = "item";
	public static final String ITEM_ID = "id";
	public static final String ITEM_NAME = "name";
	public static final String ITEM_INTRODUCTION = "introduction";
	public static final String ITEM_AMOUNT = "amount";
	public static final int ITEM_ID_INDEX = 0;
	public static final int ITEM_NAME_INDEX = 1;
	public static final int ITEM_INTRODUCTION_INDEX = 2;
	public static final int ITEM_AMOUNT_INDEX = 1;
	public static final int ITEM_NAME_INDEX2 = 0;
	public static final int ITEM_INTRODUCTION_INDEX2 = 1;
	public static final int ITEM_AMOUNT_INDEX2 = 2;
	
	//shared preference
	public static final String SP_ISFIRST = "isfirst";
	public static final String SP_LEADER_ID = "leaderid";
	public static final String SP_LEADER_WIN = "leaderwin";
	public static final String SP_LEADER_COUNT = "leadercount";
	public static final String SP_TEAM1 = "team1";
	public static final String SP_TEAM2 = "team2";
	public static final String SP_TEAM3 = "team3";
	
	//status code
	public static final int ST_HEATHY = 0;
	public static final int ST_LESSPOWER = 1;
	public static final int ST_POISION = 2;
	public static final int ST_DIZZY = 3;
	public static final int ST_CHAOS = 4;
	
	//item code
	public static final int ITEM_SUM = 40;
	
	//color mode
	public static final int MODE_WHITE = 1;
	public static final int MODE_BLACK = 2;
	
	//select leader
	public static final int IAMGE_BEGIN = R.drawable.intro_kingnight;
	public static final int IAMGE_COUNT = 3;
	
	//leader face begin
	public static final int LEADERFACE_BEGIN = R.drawable.face_kingnight;
	
	//dialog size
	public static final int DIALOG_HEIGHT = 240;
	public static final int DIALOG_WIDTH = 400;
	public static final int DIALOG_GET = 1;
	public static final int DIALOG_ITEM_GET = 2;
	
	//ablity code
	public static final int ATTACT = 0;
	public static final int GUARD = 1;
	public static final int SPEED = 2;
	public static final int INTELLIGENCE = 3;
	public static final int HITRATE = 4;
	public static final int LUCK = 5;
	
	//stage mode code
	public static final int SINGLESTAGE = 1;
	public static final int MULTISTAGE = 2;
	
}
