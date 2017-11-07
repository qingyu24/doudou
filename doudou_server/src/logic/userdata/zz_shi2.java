package logic.userdata;
import core.detail.impl.socket.SendMsgBuffer;
import core.db.*;
/**
*@author niuhao
*@version 0.0.1
*@create by zb_mysql_to_class.py
*@time:Oct-25-17 12:17:36
**/
public class zz_shi2 extends RoleDataBase
{
	public DBInt id;//

	public DBString c_id;//

	public DBString c_nm;//

	public DBString p_id;//

	public void packData(SendMsgBuffer buffer){
		buffer.Add(id.Get());
		buffer.Add(c_id.Get());
		buffer.Add(c_nm.Get());
		buffer.Add(p_id.Get());
	}
}
