package logic.userdata;

import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

public class shopping extends RoleDataBase
{
	public DBLong RoleID;//用户id

	public DBInt shopID;//
	
	public DBString time;//网名
	
	public DBInt  price;//段位
	
	public void packData(SendMsgBuffer buffer){
		buffer.Add(RoleID.Get());
		buffer.Add(shopID.Get());
		buffer.Add(time.Get());
		buffer.Add(price.Get());
		
		
	}
}