package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdPwd;
import containers.CommandArgs;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import org.junit.Test;
import utilities.Command;

public class CmdPwdTest {

  @Test
  public void testRootDir() {
    CommandArgs args = new CommandArgs("pwd");

    Command cmd = new CmdPwd();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());

    assertEquals(exitVal, 0);
  }
}
