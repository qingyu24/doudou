/**
 * ChatReceive200.java 2012-8-9下午3:09:00
 */
package test.robot.module.login;

import org.junit.BeforeClass;
import org.junit.Test;

import test.robot.*;
import test.robot.step.*;

/**
 * @author ddoq
 * @version 1.0.0
 *
 */
public class ChatReceive200
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		new RobotConfig();
	}
	
	@Test
	public void Normal50bytes()
	{
		TimeBatchRun r = new TimeBatchRun(10*60*1000, 1000, 10*60*1000);
		r.SetStep(new ChatReceiveStep(200));
		r.run();
	}
}
