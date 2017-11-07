package logic;

public enum eGameState {
	GAME_PREPARING,//游戏准备阶段
	GAME_READY, //准备好 即将开始游戏
	GAME_PLAYING,//玩儿；          、//////
	GAME_OVER;//结束;
/*	GAME_PAUSE,//暂停;
	GAME_WAIT, // 解散房间的阶段;
	GAME_EMPTY_ROOM;*/
	public int ID(){
		switch(this){
		case GAME_PREPARING:return 1;
		case GAME_READY:return 2;
		case GAME_PLAYING:return 3;
		case GAME_OVER:return 4;
/*		case GAME_PAUSE:return 5;
		case GAME_WAIT:return 6;
		case GAME_EMPTY_ROOM:return 7;*/
		}
		return 0;
	}
}
