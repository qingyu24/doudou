package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import core.detail.impl.log.Log;
import core.detail.impl.socket.SendMsgBuffer;
import logic.LogRecord;
import logic.MyUser;
import logic.module.log.eLogicDebugLogType;
import logic.module.room.Room;
import logic.module.room.RoomRule;

public class RoomManager {

	private static RoomManager _instance;
	private static Map<Long, Room> x_list = new HashMap<Long,Room>(); //根据玩家ＩＤ取
	private static Map<Integer, Room> m_list = new HashMap<Integer, Room>();
	/*private static Map<Integer, Room> m_teamlist = new HashMap<Integer, Room>();*/
	private static int roomId = 1;
	private static ArrayList<Integer> ms_roomids = new ArrayList<Integer>();
	private static ArrayList<Room> m_roomlist = new ArrayList<Room>();
	private static ArrayList<Room> m_teamroomlist = new ArrayList<Room>(); 
	private static ArrayList<Room> m_freeroomlist = new ArrayList<Room>();
	public static RoomManager getInstance(){
		if(_instance != null){
			return _instance;
		}
		return _instance = new RoomManager();
	}

	public Room getFreeRoom(){

		if(!m_roomlist.isEmpty()){
			Iterator<Room> it = m_roomlist.iterator();
			while(it.hasNext()){
				Room r = it.next();
				if(!r.isFull()&&r.canJoin()){
					m_list.put(r.getID(), r);
					return r;
				}
			}
		}else if(m_roomlist.isEmpty()){
			int id = this.generalRoomId();
			Room r = new Room(id);
			m_roomlist.add(r);
			m_list.put(id, r);
			return r;
		}
		int id = this.generalRoomId();
		Room r = new Room(id);
		m_roomlist.add(r);
		m_list.put(id, r);
		return r;
	}

	public Room getTeamRoom(int teamsize){

		if(!m_teamroomlist.isEmpty()){
			Iterator<Room> it = m_teamroomlist.iterator();
			while(it.hasNext()){
				Room r = it.next();
				if(!r.isFull()&&r.canTeamJoin(teamsize)){
					r.setTeamRoom();
					m_list.put(r.getID(), r);
					/*	x_list.add()*/

					return r;
				}
			}
		}else if(m_teamroomlist.isEmpty()){
			int id = this.generalRoomId();
			Room r = new Room(id);
			r.setTeamRoom();
			m_teamroomlist.add(r);
			m_list.put(id, r);
			LogRecord.Log("新建了房间");
			return r;
		}
		int id = this.generalRoomId();
		Room r = new Room(id);
		m_teamroomlist.add(r);
		m_list.put(id, r);
		return r;
	}


	public Room createRoom(MyUser user){
		//todo这里需要检查是否在房间内。
		int id = this.generalRoomId();
		Room r = new Room(id);
		String str = String.format("%d 创建房间:%d", user.GetRoleGID(),id);
		LogRecord(user, str);
		m_list.put(id, r);
		return r;
	}
	public Room createFreeRoom(MyUser user,RoomRule rr){
		//todo这里需要检查是否在房间内。
		int id = this.generalRoomId();
		Room r = new Room(id,rr);
		String str = String.format("%d 创建房间:%d", user.GetRoleGID(),id);
		LogRecord(user, str);
		m_list.put(id, r);
		m_freeroomlist.add(r);
		return r;
	}




	public Room getRoom(long roleId){

		return x_list.get(roleId);
	}


	public boolean removeRoom(int roomId){
		Room r = this.getRoom(roomId);
		r.clearUser();
		r.destroy();
		m_list.remove(roomId);
		m_freeroomlist.remove(r);
		Iterator<Entry<Long, Room>> it = x_list.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<java.lang.Long, logic.module.room.Room> entry = (Map.Entry<java.lang.Long, logic.module.room.Room>) it
					.next();
			if(	entry.getValue().getID()==roomId){
		/*		x_list.remove(entry.getKey());*/
				it.remove();
			}		
		}
/*x_*/
		String str = String.format("房间被删除:%d\n", roomId);
		LogRecord(null, str);
		return true;
	}

	public void joinRoom(MyUser user, int roomId){
		Room room2 = this.getRoom(user.GetRoleGID());
		if(room2!=null){
			room2.RemovePlayer(user, System.currentTimeMillis());
		}

		Room room = this.getRoom(roomId);
		if(null != room){
			LogRecord(user, user.GetUserName()+"加入房间");
			x_list.put(user.GetRoleGID(), room);
		}
	}

	public Room getRoom(int roomId){
		return m_list.get(roomId);
	}


	/*	public Room getTeamRoom(int roomId){
		return m_teamroomlist.get(roomId);
	}*/

	public void removeRoomUser(long roleId){
		x_list.remove(roleId);
		System.err.printf("删除房间内的用户:%d\n", roleId);

	}

	private int generalRoomId(){
		if(this.roomId >= ms_roomids.size()){
			this.roomId = 0;
		}
		return ms_roomids.get(this.roomId++);

	}

	public static void initRoomId(){
		int index = 0;
		int r = (int) (Math.random() * 900000) + 100000;
		while(index++ < 1000){
			while(hasRoomId(r)){
				r = (int) (Math.random() * 900000) + 100000;
			}
			//System.out.printf("roomId:%d\n", r);
			ms_roomids.add(r);
		}
	}

	private static boolean hasRoomId(int id){
		for(int i = 0; i < ms_roomids.size(); ++i){
			if(ms_roomids.get(i) == id){
				return true;
			}
		}
		return false;
	}

	private void LogRecord(MyUser user, String record)
	{
		if(null != user){
			Log.out.Log(eLogicDebugLogType.LOGIC_SQL_RECORD, user.GetRoleGID(), record);
		}else{
			Log.out.Log(eLogicDebugLogType.LOGIC_SQL_RECORD, 0l, record);
		}

	}

	public Room getWaitTeamRoom(int m_roomID) {
		// TODO Auto-generated method stub
		return this.m_list.get(m_roomID);
	}

	public void packFreeRoom(SendMsgBuffer p,int isTeam) {
		// TODO Auto-generated method stub
		int i=0;
		Iterator<Room> its = m_freeroomlist.iterator();
		while (its.hasNext()) {
			Room room = (Room) its.next();
			if(room.getRr().getM_type().ID()==isTeam){
				i++;;
			}
		}

		Iterator<Room> it = m_freeroomlist.iterator();
/*		p.Add(isTeam);*/
		p.Add((short)i);
		System.err.println(i);
		while (it.hasNext()) {
			Room room = (Room) it.next();
			if(room.getRr().getM_type().ID()==isTeam){
				room.packSize(p);
			}
		}

	}

	/*	public Room getFreeRoom(RoomRule rule) {
		// TODO Auto-generated method stub

	}*/

}
