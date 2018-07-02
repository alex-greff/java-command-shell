package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdPwd;
import containers.CommandArgs;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdPwdTest {

  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdPwd();

  @Test
  public void testRootDir() {
    CommandArgs args = new CommandArgs("pwd");

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getLastWrite(), "/");
  }

}
