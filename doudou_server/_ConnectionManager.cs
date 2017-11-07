using Networks;
public static class _ConnectionManager
{
	static public void Login_Enter(this ConnectionManager mgr, string p_username, string p_password, int p_nServerID, string p_deviceIdentifier, string p_deviceModel)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(0,0).Add(p_username).Add(p_password).Add(p_nServerID).Add(p_deviceIdentifier).Add(p_deviceModel).Send(mgr);
	}

	static public void Login_Register(this ConnectionManager mgr, string p_username, string p_password, int p_nServerID, string p_deviceIdentifier, string p_deviceModel)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(0,2).Add(p_username).Add(p_password).Add(p_nServerID).Add(p_deviceIdentifier).Add(p_deviceModel).Send(mgr);
	}

	static public void CharacterImpl_RequestBaseInfo(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(5,0).Send(mgr);
	}

	static public void RoomImpl_EatFood(this ConnectionManager mgr, int bodyID, int score, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,13).Add(bodyID).Add(score).Add(time).Send(mgr);
	}

	static public void RoomImpl_MoveBody(this ConnectionManager mgr, int playerID, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,3).Add(playerID).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_ComposeBody(this ConnectionManager mgr, int[] list, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,7).Add(list).Add(time).Send(mgr);
	}

	static public void RoomImpl_CreateRoom(this ConnectionManager mgr, int arg, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,2).Add(arg).Add(time).Send(mgr);
	}

	static public void RoomImpl_EnterRoom(this ConnectionManager mgr, int roomID, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,1).Add(roomID).Add(time).Send(mgr);
	}

	static public void RoomImpl_SplitBody(this ConnectionManager mgr, int playerID, int m_xpos, int m_ypos, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,6).Add(playerID).Add(m_xpos).Add(m_ypos).Add(time).Send(mgr);
	}

	static public void RoomImpl_InitBody(this ConnectionManager mgr, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,5).Add(time).Send(mgr);
	}

	static public void RoomImpl_EatBody(this ConnectionManager mgr, int eatType, int playerId, int bodyId, int xpos, int ypos, int TargetPlayerID, int targetbodyID, int targetxpos, int targetypos, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,4).Add(eatType).Add(playerId).Add(bodyId).Add(xpos).Add(ypos).Add(TargetPlayerID).Add(targetbodyID).Add(targetxpos).Add(targetypos).Add(time).Send(mgr);
	}

	static public void RoomImpl_rebirth(this ConnectionManager mgr, int playerId, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,22).Add(playerId).Add(time).Send(mgr);
	}

	static public void RoomImpl_LeftMatch(this ConnectionManager mgr, long playerId, int teamID, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,31).Add(playerId).Add(teamID).Add(time).Send(mgr);
	}

	static public void RoomImpl_SplitQiuPlace(this ConnectionManager mgr, int qiuId, int playerId, int xpos, int ypos, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,19).Add(qiuId).Add(playerId).Add(xpos).Add(ypos).Add(time).Send(mgr);
	}

	static public void RoomImpl_SplitQiu(this ConnectionManager mgr, int playerID, int xpos, int ypos, long time)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,17).Add(playerID).Add(xpos).Add(ypos).Add(time).Send(mgr);
	}

	static public void RoomImpl_gameStart(this ConnectionManager mgr, int roomID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,29).Add(roomID).Send(mgr);
	}

	static public void RoomImpl_chooseTeam(this ConnectionManager mgr, int teamID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(1,33).Add(teamID).Send(mgr);
	}

	static public void CenterImpl_EnterFreeRoomCenter(this ConnectionManager mgr, int isEnter, int isTeam)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,14).Add(isEnter).Add(isTeam).Send(mgr);
	}

	static public void CenterImpl_InvitationFriend(this ConnectionManager mgr, long p_ID, int p_teamID, long m_friendID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,3).Add(p_ID).Add(p_teamID).Add(m_friendID).Send(mgr);
	}

	static public void CenterImpl_CreatTeam(this ConnectionManager mgr)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,1).Send(mgr);
	}

	static public void CenterImpl_TeamGameMatch(this ConnectionManager mgr, int p_teamID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,10).Add(p_teamID).Send(mgr);
	}

	static public void CenterImpl_creatRoomRule(this ConnectionManager mgr, int isTeam, int gameTime, int teNumber, int eachSize)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,13).Add(isTeam).Add(gameTime).Add(teNumber).Add(eachSize).Send(mgr);
	}

	static public void CenterImpl_JoinTeam(this ConnectionManager mgr, int p_teamID, long m_friendID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,4).Add(p_teamID).Add(m_friendID).Send(mgr);
	}

	static public void CenterImpl_RetreatTeam(this ConnectionManager mgr, long roleID, int p_teamID)
	{
		PacketBuffer.GetInstance().Clear().SetObjMethod(3,12).Add(roleID).Add(p_teamID).Send(mgr);
	}

}
