/**
 * ServerCommonConfig.java 2013-2-28下午2:37:37
 */
package logic.config;

import utility.ExcelData;

import java.util.Date;

/**
 * @author ddoq
 * @version 1.0.0
 */
@ExcelData(File = "ServerCommonConfig.xls", Table = "Sheet1")
public class ServerCommonConfig implements IConfig {
    public int LoginMaxWaitNum;
    public int LoginQueueMaxNum;
    public boolean ForRobot;
    public Date ServerStartUpTime;

}

