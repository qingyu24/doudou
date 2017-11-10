package logic.userdata;

import logic.MyUser;
import logic.Reg;
import core.remote.PI;
import core.remote.PL;
import core.remote.PU;
import core.remote.RCC;
import core.remote.RFC;

@RCC(ID = Reg.CENTERDATA)
public interface CenterDateInterface {

	static final int MID_CLASS = 1;//獲取班級列表
	static final int MID_ADDFRIEND = 2;//添加好友
	static final int MID_AGRREADD = 3;//同意添加好友
	static final int MID_SHOP_PRESENT = 4;//增送礼物
	static final int MID_SHOP_BUY = 5;//购买礼物
	static final int MID_USER_HOME = 6;//个人主页

	/*	@RFC(ID = MID_ENTER)
	void getClass(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID,
			@PL long time);
	 */

	@RFC(ID = MID_CLASS)
	void getClass(@PU(Index = Reg.ROOM) MyUser p_user);

	@RFC(ID = MID_ADDFRIEND)
	void addFriend(@PU(Index = Reg.ROOM) MyUser p_user,@PL  long friendID);

	@RFC(ID = MID_AGRREADD)
	void agreeAdd(@PU(Index = Reg.ROOM) MyUser p_user);

	@RFC(ID = MID_SHOP_PRESENT)
	void givePresent(@PU(Index = Reg.ROOM) MyUser p_user,@PL  long friendID ,@PI int giftID);

	@RFC(ID = MID_SHOP_BUY)
	void buyShopping(@PU(Index = Reg.ROOM) MyUser p_user,@PI int giftID);
	
	@RFC(ID = MID_USER_HOME)
	void userHome(@PU(Index = Reg.ROOM) MyUser p_user,@PI int giftID);


}
