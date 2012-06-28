package com.codeblood.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Star
 * 
 * Description: 蓝牙连接类
 *
 */
public class Connector
{
	private static Connector instance = null;
	
	private BluetoothAdapter bluetoothadapter = null;
	private List<String> namelist = null;
	private ArrayList<Map<String, String>> array = null;
	private HashMap<String, String> map = null;
	
	private Connector(Context context)
	{
		onCreate(context);
	}
	
	public static Connector getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new Connector(context);
		}
		return instance;
	}
	
	/**
	 * FunName: onCreate
	 * Description: 初始化连接
	 *
	 *@param: context上下文
	 *
	 * @Author: Star
	 * @CreateDate: 2012-8-18
	 */
	public void onCreate(Context context)
	{
		bluetoothadapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothadapter == null)
		{
			return;
		}
		if(!bluetoothadapter.isEnabled())
		{
			bluetoothadapter.enable();
		}
		
		namelist = new ArrayList<String>();
		array = new ArrayList<Map<String, String>>();
		
		IntentFilter intentfilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		Bluetoothreceiver recevier = new Bluetoothreceiver();
		context.registerReceiver(recevier, intentfilter);
	}
	
	/**
	 * FunName: waitConnect
	 * Description: 等待连接，打开可见性
	 *
	 *@param: context上下文
	 *
	 * @Author: Star
	 * @CreateDate: 2012-8-30
	 */
	public void waitConnect(Context context)
	{
		Intent discoveryble = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoveryble.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		context.startActivity(discoveryble);
	}
	
	/**
	 * FunName: fillList
	 * Description: 填充list
	 *
	 *@param: list需要填充的list
	 *		  context上下文
	 *@return: 是否成功
	 *
	 * @Author: Star
	 * @CreateDate: 2012-8-30
	 */
	public boolean fillList(ListView list, Context context)
	{
		if(namelist == null)
		{
			return false;
		}
		
		list.setAdapter(new ArrayAdapter<String>(context, 
												 android.R.layout.simple_list_item_1,
												 namelist));
		
		list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		return true;
	}
	
	class Bluetoothreceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				map = new HashMap<String, String>();
				map.put("address", device.getAddress());
				array.add(map);
				
				namelist.add(device.getName());
			}
		}
		
	}
}
