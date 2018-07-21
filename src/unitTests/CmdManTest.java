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
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdManTest {

  //Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<String>();
    tc_err = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    cmd = new CmdMan(fs, cm);

    cm.initializeCommands();
  }

  @Test
  public void testExecuteGetManDoc() {
    CommandArgs args = new CommandArgs("man", new String[]{"man"});

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertEquals(0, tc_err.getAllWritesAsString().length());
  }

  @Test
  public void testExecuteGetEchoDoc() {
    CommandArgs args = new CommandArgs("man", new String[]{"echo"});

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertTrue(tc.getAllWritesAsString().length() > 0);
    assertEquals(0, tc_err.getAllWritesAsString().length());
  }

  @Test
  public void testExecuteGetNonExistentCommandDoc() {
    CommandArgs args = new CommandArgs("man", new String[]{"nonExistentCmd"});

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
    assertEquals(0, tc.getAllWritesAsString().length());
    assertEquals(
        "Error: No description found for command \"nonExistentCmd\"",
        tc_err.getAllWritesAsString());
  }
}
