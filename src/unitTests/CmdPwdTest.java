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

import commands.CmdPwd;
import containers.CommandArgs;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdPwdTest {

  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> testOut;
  private BufferedConsole<String> testQueryOut;
  private BufferedConsole<String> testErrOut;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    testOut = new BufferedConsole<>();
    testQueryOut = new BufferedConsole<>();
    testErrOut = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(testOut, testOut, testErrOut,
                                                fs);
    cmd = new CmdPwd(fs, cm);
  }

  @Test
  public void testInvalidArgsNumberOfParameters() {
    String argParam[] = {"unwantedParam"};
    CommandArgs args = new CommandArgs("pwd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid arguments", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testRootDir() {
    // Attempt to display the current working directory
    CommandArgs args = new CommandArgs("pwd");
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the path of the
    // root directory was printed
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/\n", testOut.getAllWritesAsString());
  }

  @Test
  public void testChildDir() throws FSElementAlreadyExistsException,
                                    MalformedPathException, FSElementNotFoundException {
    // Create a directory, add it to the root directory, and make it the working
    // directory
    fs.getRoot().createAndAddNewDir("testDir");
    Path dirPath = new Path("/testDir");
    fs.changeWorkingDir(dirPath);

    // Attempt to display the current working directory
    CommandArgs args = new CommandArgs("pwd");
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the path of the
    // directory created was printed
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/testDir\n", testOut.getAllWritesAsString());
  }

}
