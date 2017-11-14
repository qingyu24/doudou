package logic.userdata;

import core.User;
import core.detail.impl.socket.SendMsgBuffer;
import core.remote.*;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.module.center.CenterInterface;
import manager.UserManager;


public class CenterDateImpl implements CenterDateInterface {

	@Override
	@RFC(ID = 1)
	public void getClass(@PU MyUser p_user) {

		UserManager.getInstance().getClassmates(p_user);

	
	}

	@Override
	@RFC(ID = 2)
	public void addFriend(@PU MyUser p_user, @PL long friendID) {
		// TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(friendID);
        if(user!=null&&user.hasFriend(p_user)==0){
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
                    .AddID(Reg.CENTERDATA, CenterDateInterface.MID_ADDFRIEND);
            buffer.Add(p_user.GetRoleGID());
            buffer.Add(p_user.getTickName());
            buffer.Send(user);
        }
        else{
            //如果玩家不在线或者已经是好友返回错误信息
        }
	}

	@Override
	@RFC(ID = 3)
	public void agreeAdd(@PU MyUser p_user, @PL long f_ID) {
		// TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(f_ID);
        if(user!=null){
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
                    .AddID(Reg.CENTERDATA, CenterDateInterface.MID_AGRREADD);
            buffer.Add(p_user.GetRoleGID());
            buffer.Add(p_user.getTickName());
            buffer.Send(user);
        }
        UserManager.getInstance().addFriends(p_user.GetRoleGID(),f_ID);

    }

	@Override
	public void givePresent(@PU MyUser p_user,@PL long friendID, @PI int giftID) {
		// TODO Auto-generated method stub
/*        UserManager.getInstance().*/
		
	}

	@Override
	public void buyShopping(@PU MyUser p_user, @PI int giftID,@PI int price) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userHome(@PU MyUser p_user,@PI int giftID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(@PU MyUser p_user, @PL long targetID, @PS String message) {

	}

	@Override
	public void deleteFriends(@PU MyUser p_user, @PL long targetID) {


	}

	@Override
	public void rankingList(@PU MyUser p_user, @PI int list_type, @PI int number, @PI int size) {


	}


}
