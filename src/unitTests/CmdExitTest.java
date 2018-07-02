package unitTests;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import commands.CmdExit;
import containers.CommandArgs;
import utilities.Command;
import utilities.ExitCode;
import utilities.Parser;

public class CmdExitTest {
  @Test
  public void testExecuteExit() {
    CommandArgs args =
        Parser.parseUserInput("exit");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdExit();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() == 0);
    assertTrue(tc_err.getAllWritesAsString().length() == 0);
  }
}
