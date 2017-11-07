package logic.userdata;
import core.detail.impl.socket.SendMsgBuffer;
import core.db.*;
/**
*@author niuhao
*@version 0.0.1
*@create by zb_mysql_to_class.py
*@time:Oct-25-17 12:17:36
**/
public class zz_usertype extends RoleDataBase
{
	public DBInt ID;//

	public DBString title;//

	public void packData(SendMsgBuffer buffer){
		buffer.Add(ID.Get());
		buffer.Add(title.Get());
	}
}
