package logic.module.character;

import java.util.List;
import java.util.Map;

import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.userdata.handler.PlayerCenterData;
import utility.Debug;
import core.Root;
import core.db.DBInt;
import core.detail.impl.socket.SendMsgBuffer;
import core.remote.PF;
import core.remote.PI;
import core.remote.PS;
import core.remote.PU;
import core.remote.RFC;

public class CharacterImpl implements CharacterInterface
{

    @Override
    public void RequestBaseInfo(MyUser p_user)
    {
        p_user.client_DataReady = true;
        Send_BaseData(p_user);
    }

    public void ServerDataReady(MyUser p_user)
    {
        p_user.server_DataReady = true;
        Send_BaseData(p_user);
    }

    public void Send_BaseData(MyUser p_user)
    {
    	
        if (false == p_user.client_DataReady || false == p_user.server_DataReady)
            return;
        // 仅发送一次
        p_user.client_DataReady = false;
        System.out.println("发送玩家的基础数据到客户端:");
        this._SToC_PlayerUserData(p_user);
        
    }


    private void _SToC_PlayerUserData(MyUser p_user)
    {
    	System.out.println("Send Msg _SToC_PlayerUserData");
		SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CHARACTER, 0);
		boolean b = p_user.packBaseData(p);
		if (b) 
		{
			p.Send(p_user);
		}
		Root.GetInstance().AttachUser(p_user);
    }

    private static CharacterImpl s_instance = new CharacterImpl();

    public static CharacterImpl GetInstance()
    {
        return s_instance;
    }
}
