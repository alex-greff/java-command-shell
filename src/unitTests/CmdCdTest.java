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
import commands.CmdCd;
import containers.CommandArgs;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdCdTest {

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
    cm = CommandManager.constructCommandManager(testOut, testQueryOut,
        testErrOut, fs);
    cmd = new CmdCd(fs, cm);
  }

  @Test
  public void testInvalidArgsNumberOfParametersLess() {
    String argParam[] = {};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid arguments", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testInvalidArgsNumberOfParametersMore() {
    String argParam[] = {"wantedParam", "unwantedParam"};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid arguments", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testInvalidPath() {
    String argParam[] = {"invalid//path"};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid directory path",
        testErrOut.getAllWritesAsString());
  }

  @Test
  public void testDirNotFound() {
    String argParam[] = {"dir/does/not/exist"};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Directory does not exist",
        testErrOut.getAllWritesAsString());
  }

  @Test
  public void testChildDir() throws FSElementAlreadyExistsException {
    // Create a directory and add it to the root directory
    fs.getRoot().createAndAddNewDir("testDir");

    // Attempt to change into the child directory created
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the child directory which we created
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/testDir", fs.getWorkingDirPath());
  }

  @Test
  public void testCurrentDir() {
    // Attempt to change into the current directory
    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is still the root directory
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/", fs.getWorkingDirPath());
  }

  @Test
  public void testParentDir() throws FSElementAlreadyExistsException {
    // Create a directory and add it to the root directory
    fs.getRoot().createAndAddNewDir("testDir");

    // Change into the child directory we created, so we can try to change back
    // to the parent directory
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testQueryOut, testErrOut);

    // Attempt to change into the parent directory
    String argParam2[] = {".."};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the root directory again
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/", fs.getWorkingDirPath());
  }

  @Test
  public void testAbsolutePathToDir() throws FSElementAlreadyExistsException {
    // Create a directory and add it to the root directory
    fs.getRoot().createAndAddNewDir("testDir");

    // Change into the child directory we created, so we can make another in it
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testQueryOut, testErrOut);

    // Create a directory and add it to the directory we are currently in
    fs.getWorkingDir().createAndAddNewDir("testDirAgain");

    // Attempt to change into the grandchild directory, giving in a path
    // starting from the root
    String argParam2[] = {"/testDir/testDirAgain"};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the grandchild directory which we created
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/testDir/testDirAgain", fs.getWorkingDirPath());
  }

  @Test
  public void testRelativePathToDir() throws FSElementAlreadyExistsException {
    // Create two directories and add them to the root directory
    fs.getRoot().createAndAddNewDir("testDir");
    fs.getRoot().createAndAddNewDir("testDirAgain");

    // Change into the child directory we created, so we can try to access its
    // sibling from it
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testQueryOut, testErrOut);

    // Attempt to change into the sibling directory, giving in a path that goes
    // up to the parent first, and then to the sibling
    String argParam2[] = {"../testDirAgain"};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testQueryOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the second sibling directory which we created
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("/testDirAgain", fs.getWorkingDirPath());
  }

}
