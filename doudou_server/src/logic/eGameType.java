package logic;

public enum eGameType {
TEAM,
SOLO;
	public int ID(){
		switch(this){
		case TEAM:return 1;
		case SOLO:return 0;
		}
		return 0;
	}
}
