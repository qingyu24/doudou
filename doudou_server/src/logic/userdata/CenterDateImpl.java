package logic.userdata;

import core.remote.*;
import logic.MyUser;
import manager.UserManager;


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
	public void givePresent(@PU MyUser p_user,@PL long friendID, @PI int giftID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyShopping(@PU MyUser p_user,@PI int giftID) {
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
	public void deleteFriends(MyUser p_user, long targetID) {

	}

	@Override
	public void rankingList(MyUser p_user, int list_type, int number, int size) {

	}


}
