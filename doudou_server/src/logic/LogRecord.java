package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.detail.impl.log.Log;
import logic.module.log.eLogicDebugLogType;

public class LogRecord {

	public static void Log(MyUser user, String record) {
		if (user != null) {
			Log.out.Log(eLogicDebugLogType.LOGIC_SQL_RECORD, user.GetRoleGID(), record);
		} else {

			Log.out.Log(eLogicDebugLogType.LOGIC_SQL_RECORD, 11, record);

		}
	}

	public static void Log(String record) {

		Log.out.Log(eLogicDebugLogType.LOGIC_SQL_RECORD, 0, record);

	}

	public static void writePing(String record, long ping) {
		if (ping > 50) {
			try {
				File file = new File("testPing.txt");
				// 创建文件
				/* file.createNewFile(); */
				// creates a FileWriter Object
				FileWriter writer = new FileWriter(file, true);
				BufferedWriter writer2 = new BufferedWriter(writer);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				/* System.out.println(); */
				writer2.write(df.format(new Date()) + ":" + record + ping);
				writer2.newLine();
				writer2.flush();
				writer2.close();
				writer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
