using Networks;
public static class _ConnectionManager
{
	static public void Login_Enter(this ConnectionManager mgr, string p_username, string p_password, int p_nServerID, string p_deviceIdentifier, string p_deviceModel)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(0,0).Add(p_username).Add(p_password).Add(p_nServerID).Add(p_deviceIdentifier).Add(p_deviceModel).Send(mgr);
	}

	static public void Login_ResetEnter(this ConnectionManager mgr, string p_username, string p_password, int userType, int p_nServerID, string p_deviceIdentifier, string p_deviceModel, int code)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(0,3).Add(p_username).Add(p_password).Add(userType).Add(p_nServerID).Add(p_deviceIdentifier).Add(p_deviceModel).Add(code).Send(mgr);
	}

	static public void Login_Register(this ConnectionManager mgr, string p_username, string p_password, int p_nServerID, string p_deviceIdentifier, string p_deviceModel)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(0,2).Add(p_username).Add(p_password).Add(p_nServerID).Add(p_deviceIdentifier).Add(p_deviceModel).Send(mgr);
	}

	static public void CharacterImpl_RequestBaseInfo(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(5,0).Send(mgr);
	}

	static public void RoomImpl_ComposeBody(this ConnectionManager mgr, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,7).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_gameStart(this ConnectionManager mgr, int roomID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,29).Add(roomID).Send(mgr);
	}

	static public void RoomImpl_ThornMove(this ConnectionManager mgr, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,5).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_EatFood(this ConnectionManager mgr, int bodyID, int score, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,13).Add(bodyID).Add(score).Add(time).Send(mgr);
	}

	static public void RoomImpl_EnterRoom(this ConnectionManager mgr, int buffer, long p)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,1).Add(buffer).Add(p).Send(mgr);
	}

	static public void RoomImpl_getFriendList(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,34).Send(mgr);
	}

	static public void RoomImpl_leaveRoom(this ConnectionManager mgr, int roomID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,36).Add(roomID).Send(mgr);
	}

	static public void RoomImpl_MoveBody(this ConnectionManager mgr, int p_user, int[] playerID, long list)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,3).Add(p_user).Add(playerID).Add(list).Send(mgr);
	}

	static public void RoomImpl_chooseTeam(this ConnectionManager mgr, int this, int p_user)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,33).Add(this).Add(p_user).Send(mgr);
	}

	static public void RoomImpl_dissolution(this ConnectionManager mgr, int roomID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,35).Add(roomID).Send(mgr);
	}

	static public void RoomImpl_CreateRoom(this ConnectionManager mgr, int p_user, long arg)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,2).Add(p_user).Add(arg).Send(mgr);
	}

	static public void RoomImpl_EatBody(this ConnectionManager mgr, int p_user, int eatType, int playerId, int bodyId, int xpos, int ypos, int TargetPlayerID, int targetbodyID, int targetxpos, long targetypos)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,4).Add(p_user).Add(eatType).Add(playerId).Add(bodyId).Add(xpos).Add(ypos).Add(TargetPlayerID).Add(targetbodyID).Add(targetxpos).Add(targetypos).Send(mgr);
	}

	static public void RoomImpl_SplitBody(this ConnectionManager mgr, int playerID, int m_xpos, int m_ypos, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,6).Add(playerID).Add(m_xpos).Add(m_ypos).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_SplitQiu(this ConnectionManager mgr, int playerID, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,17).Add(playerID).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_SplitQiuPlace(this ConnectionManager mgr, int qiuId, int playerId, int xpos, int ypos, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,19).Add(qiuId).Add(playerId).Add(xpos).Add(ypos).Add(time).Send(mgr);
	}

	static public void RoomImpl_rebirth(this ConnectionManager mgr, int p_user, long playerId)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,22).Add(p_user).Add(playerId).Send(mgr);
	}

	static public void RoomImpl_LeftMatch(this ConnectionManager mgr, long playerId, int teamID, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,31).Add(playerId).Add(teamID).Add(time).Send(mgr);
	}

	static public void CenterImpl_CreatTeam(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,1).Send(mgr);
	}

	static public void CenterImpl_JoinTeam(this ConnectionManager mgr, int p_teamID, long m_friendID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,4).Add(p_teamID).Add(m_friendID).Send(mgr);
	}

	static public void CenterImpl_TeamGameMatch(this ConnectionManager mgr, int this)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,10).Add(this).Send(mgr);
	}

	static public void CenterImpl_RetreatTeam(this ConnectionManager mgr, long roleID, int p_teamID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,12).Add(roleID).Add(p_teamID).Send(mgr);
	}

	static public void CenterImpl_creatRoomRule(this ConnectionManager mgr, int p_user, int isTeam, int gameTime, int teNumber)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,13).Add(p_user).Add(isTeam).Add(gameTime).Add(teNumber).Send(mgr);
	}

	static public void CenterImpl_InvitationFriend(this ConnectionManager mgr, long p_user, int p_ID, long p_teamID, int m_friendID, int roomID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,3).Add(p_user).Add(p_ID).Add(p_teamID).Add(m_friendID).Add(roomID).Send(mgr);
	}

	static public void CenterImpl_EnterFreeRoomCenter(this ConnectionManager mgr, int isEnter, int isTeam)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,14).Add(isEnter).Add(isTeam).Send(mgr);
	}

	static public void CenterDateImpl_getClass(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,1).Send(mgr);
	}

	static public void CenterDateImpl_givePresent(this ConnectionManager mgr, long friendID, int giftID, int price)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,5).Add(friendID).Add(giftID).Add(price).Send(mgr);
	}

	static public void CenterDateImpl_rankingList(this ConnectionManager mgr, int list_type, int number, int size)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,9).Add(list_type).Add(number).Add(size).Send(mgr);
	}

	static public void CenterDateImpl_userHome(this ConnectionManager mgr, int giftID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,7).Add(giftID).Send(mgr);
	}

	static public void CenterDateImpl_deleteFriends(this ConnectionManager mgr, long targetID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,4).Add(targetID).Send(mgr);
	}

	static public void CenterDateImpl_agreeAdd(this ConnectionManager mgr, long p_user)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,3).Add(p_user).Send(mgr);
	}

	static public void CenterDateImpl_addFriend(this ConnectionManager mgr, long p_user)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,2).Add(p_user).Send(mgr);
	}

	static public void CenterDateImpl_buyShopping(this ConnectionManager mgr, int giftID, int price)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,6).Add(giftID).Add(price).Send(mgr);
	}

	static public void CenterDateImpl_sendMessage(this ConnectionManager mgr, long targetID, string message)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(7,8).Add(targetID).Add(message).Send(mgr);
	}

}
