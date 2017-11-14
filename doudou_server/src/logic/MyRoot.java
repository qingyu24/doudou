/**
 * MyRoot.java 2012-6-11下午9:48:17
 */
package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import utility.TimeMethod;
import utility.RFC.RFCGather.RFCClass;
import utility.RFC.RFCGather.RFCMethod;
import utility.dyjar.DynamicLoadJarFile;
import utils.RandomId;
import logic.config.*;
import logic.module.room.Room;
import manager.ConfigManager;
import manager.LoaderManager;
import manager.RoomManager;
import manager.UserManager;
import core.*;

/**
 * @author ddoq
 * @version 1.0.0
 *
 * 正常服务器的启动模块
 *
 */
public class MyRoot extends Root implements Tick
{
	public MyRoot()
	{
		super();
		m_Factory = new MyFactory();
		
//		AddOnceTimer(this, 3);
//		System.out.println("AddTimer:" + System.currentTimeMillis());
		
//		AddDefineTimer(this, "16:50:00", 10);
//		AddSolidDefineTimer(this, "16:30:00", 10);
	}
	
	public void RegAll()
	{
		super.RegAll();
	}
	
	public void StartAllThread()
	{
		super.StartAllThread();
	}
	
	public void StartShow()
	{
		super.StartShow();
	}
	
	public void RFCBuild()
	{
		super.RFCBuild();
	}
	
	/**
	 * 入口
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
//		System.setOut(EmptyPrintStream.m_Instance);
		
		System.err.println("Server go~~~");
		
		RootConfig c = RootConfig.GetInstance();
		c.Init();
		
		TimeMethod.Init();
		
		if ( c.OpenSecurityService )
		{
			//自动开安全协议服务器
			DynamicLoadJarFile.ThreadRunJarClassMain("SecurityPolicyServer.jar", "sec.SecurityPolicyServer");
		}
				
		//sql连接
		DBMgr.Init();
		
				
		//逻辑层的注册配置
		ConfigManager.getInstance().initAll();
				
		MyRoot r = new MyRoot();
		r.RegAll();
		
		if ( c.Debug )
		{
			r.RFCBuild();
		}
		
		if ( c.Show )
		{
			r.StartShow();
		}
		LoaderManager.getInstance().loadAll();
	
		RoomManager.initRoomId();
		DBMgr.LoadAll();
	/*	LoaderManager.getInstance().setRoleID();*/
		//把机器人插入到库里;
		r.StartAllThread();

		
		String time = readFileByLines("Server.ini");
		if(null == time){
			FileOutputStream output = new FileOutputStream("Server.ini");
			OutputStreamWriter osw = null;
			try{
				osw = new OutputStreamWriter(output, "UTF-8");
				long now = System.currentTimeMillis();
				osw.write(String.valueOf(now));
				osw.flush();
				osw.close();
					
			}catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (osw != null) {
	                try {
	                	osw.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
			
		}else{
			System.out.println("成功记录了服务器的启动时间:" + time);
			ConfigManager.serverStartRunTime = Long.parseLong(time);
		}
		//记录一下游戏的开服时间;
		
		
/*UserManager.getInstance().testFriend();*/
//
		System.err.println("Server Start Finish!!!");

		
		synchronized (Root.GetInstance())
		{
			try
			{
				Root.GetInstance().wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	
	}
	
	public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
        	return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
                //line++;
            	return tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }


	/* (non-Javadoc)
	 * @see core.Tick#OnTick(long)
	 */
	@Override
	public void OnTick(long p_lTimerID) throws Exception
	{
		System.out.println("OnTick : " + p_lTimerID + " time:" + System.currentTimeMillis());
	}
}
