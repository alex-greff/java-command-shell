package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdPwd;
import containers.CommandArgs;
import io.Console;
import io.ErrorConsole;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdPwdTest {

  private Console out = Console.getInstance();
  private ErrorConsole errOut = ErrorConsole.getInstance();
  private Command cmd = new CmdPwd();

  @Test
  public void testRootDir() {
    CommandArgs args = new CommandArgs("pwd");

    ExitCode exitVal = cmd.execute(args, out, errOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

}
