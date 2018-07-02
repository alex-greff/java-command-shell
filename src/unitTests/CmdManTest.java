// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: ursualex
// UT Student #: 1004357199
// Author: Alexander Ursu
//
// Student2:
// UTORID user_name: greffal1
// UT Student #: 1004254497
// Author: Alexander Greff
//
// Student3:
// UTORID user_name: sankarch
// UT Student #: 1004174895
// Author: Chedy Sankar
//
// Student4:
// UTORID user_name: kamins42
// UT Student #: 1004431992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import commands.CmdMan;
import containers.CommandArgs;
import org.junit.BeforeClass;
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
    CommandArgs args = new CommandArgs("man", new String[]{"man"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertEquals(0, tc_err.getAllWritesAsString().length());
  }

  @Test
  public void testExecuteGetEchoDoc() {
    CommandArgs args = new CommandArgs("man", new String[]{"echo"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertEquals(0, tc_err.getAllWritesAsString().length());
  }

  @Test
  public void testExecuteGetNonExistentCommandDoc() {
    CommandArgs args = new CommandArgs("man", new String[]{"nonExistentCmd"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdMan();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals(0, tc.getAllWritesAsString().length());
    assertEquals(
        "Error: No description found for command \"nonExistentCmd\"\n",
        tc_err.getAllWritesAsString());
  }
}
