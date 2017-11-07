package logic.module.character;

import logic.MyUser;
import logic.Reg;
import core.remote.PF;
import core.remote.PI;
import core.remote.PL;
import core.remote.PS;
import core.remote.PU;
import core.remote.PUGID;
import core.remote.RCC;
import core.remote.RFC;

@RCC (ID = Reg.CHARACTER)
public interface CharacterInterface
{
    static final int MID_REQUESTBASEINFO = 0;	///< 获取属性
    
    @RFC (ID = MID_REQUESTBASEINFO)
    void RequestBaseInfo(@PU (Index = Reg.CHARACTER) MyUser p_user);
    
    
}
