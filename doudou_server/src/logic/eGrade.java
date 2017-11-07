package logic;

public enum eGrade {
	
/*	*	青铜：2星 0-2
	白银：3星 3-6
	黄金1：3星  7-9
	黄金2：3星  10-13
	白金1：4星   14-18
	白金2：4星19-23
	钻石1：4星24-28
	钻石2：4星29-33
	钻石3：4星34-38
	大师1：4星39-43
	大师2：4星44-48
	大师3：4星49-53
	王者1：4星54-58
	王者2：5星59-64
	王者3：5星65-70*/
	QINGTONG,
	BAIYIN,
	HUANGJIN1,
	HUANGJIN2,
	BOJIN1,
	BOJIN2,
	ZUANSHI,
	ZUANSHI2,
	ZUANSHI3,
	DASHI,
	DASHI2,
	DASHI3,
	WANGZHE,
	WANGZHE2,
	WANGZHE3,
	CHAOSHEN;
	
	

	public int ID(){
		switch(this){
		case QINGTONG:return 1;
		case BAIYIN:return 2;
		case HUANGJIN1:return 3;
		case HUANGJIN2:return 4;
		case BOJIN1:return 5;
		case BOJIN2:return 6;
		case ZUANSHI:return 7;
		case ZUANSHI2:return 8;
		case ZUANSHI3:return 9;
		case DASHI: return 10;
		case DASHI2:return 11;
		case DASHI3:return 12;
		case WANGZHE:return 13;
		case WANGZHE2:return 14;
		case WANGZHE3:return 15;
		case CHAOSHEN:return 16;
	
		}
		return 0;
	}
}
