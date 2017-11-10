package logic.userdata;

import logic.MyUser;
import manager.UserManager;
import core.remote.PL;
import core.remote.PU;
import core.remote.RFC;


public class CenterDateImpl implements CenterDateInterface {

	@Override
	@RFC(ID = 1)
	public void getClass(@PU MyUser p_user) {

		UserManager.getInstance().getClassmates(p_user);
	  /*  loader.getClassmates(p_user);*/
	
	}

	@Override
	@RFC(ID = 2)
	public void addFriend(@PU MyUser p_user, @PL long friendID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RFC(ID = 3)
	public void agreeAdd(@PU MyUser p_user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givePresent(MyUser p_user, long friendID, int giftID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyShopping(MyUser p_user, int giftID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userHome(MyUser p_user, int giftID) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	
	
}
