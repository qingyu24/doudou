package logic.config;

import utility.ExcelData;

@ExcelData( File = "SpeedConfig.xls", Table = "Config" )
public class SpeedConfig implements IConfig{
public int	Score;
public int Speed;
public int Scale;
public int CameraSize;
public int spriteorder;

}
