package test.robot.module.chat;

import core.remote.*;
import logic.Reg;
import test.robot.Robot;

@RCC(ID = Reg.CHAT)
public interface ChatRtnInterface {
    final int MID_ChatRES = 0;

    @RFC(ID = MID_ChatRES)
    public void ChatRes(@PU Robot r, @PST short channel, @PS String res);

}
