package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import commands.CmdMan;
import containers.CommandArgs;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdManTest {

  @BeforeClass
  public static void setup() {
    CommandManager.getInstance().initializeCommands();
  }

  @Test
  public void testExecuteGetManDoc() {
    CommandArgs args = new CommandArgs("man", new String[] {"man"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertTrue(tc_err.getAllWritesAsString().length() == 0);
  }

  @Test
  public void testExecuteGetEchoDoc() {
    CommandArgs args = new CommandArgs("man", new String[] {"echo"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertTrue(tc_err.getAllWritesAsString().length() == 0);
  }

  @Test
  public void testExecuteGetNonExistentCommandDoc() {
    CommandArgs args = new CommandArgs("man", new String[] {"nonExistentCmd"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertTrue(tc.getAllWritesAsString().length() == 0);
    assertEquals("Error: No description found for command \"nonExistentCmd\"\n",
        tc_err.getAllWritesAsString());
  }
}
