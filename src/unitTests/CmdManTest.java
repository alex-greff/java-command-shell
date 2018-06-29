package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import commands.CmdMan;
import containers.CommandArgs;
import io.Console;
import io.ErrorConsole;
import org.junit.Test;
import utilities.Command;

public class CmdManTest {

  @Test
  public void testExecute1() {
    CommandArgs args = new CommandArgs("man", new String[]{"man"});

    Command cmd = new CmdMan();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());

    assertEquals(exitVal, 0);
  }

  @Test
  public void testExecute2() {
    CommandArgs args = new CommandArgs("man", new String[]{"echo"});

    Command cmd = new CmdMan();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());

    assertEquals(exitVal, 0);
  }
}
