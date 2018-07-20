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

import commands.CmdGrep;
import containers.CommandArgs;
import filesystem.FSElementAlreadyExistsException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdGrepTest {

  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> testOut;
  private BufferedConsole<String> testErrOut;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    testOut = new BufferedConsole<String>();
    testErrOut = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(testOut, testErrOut, fs);
    cmd = new CmdGrep(fs, cm);
  }

  @Test
  public void testInvalidArgsNumberOfParameters() {
    String argParam[] = {};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid arguments", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testInvalidArgsWrongFlag() {
  }

  @Test
  public void testInvalidPath() {
  }

  @Test
  public void testFileNotFound() {
  }

  @Test
  public void testDirNotFound() {
  }

  @Test
  public void testFileNoMatches() {
  }

  @Test
  public void testFileOneMatch() {
  }

  @Test
  public void testFileMultipleMatches() {
  }

  @Test
  public void testDirNoMatches() {
  }

  @Test
  public void testDirOneMatch() {
  }

  @Test
  public void testDirMultipleMatches() {
  }

}
