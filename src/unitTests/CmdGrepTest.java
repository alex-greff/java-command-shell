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
  public void testInvalidArgsNumberOfParametersLess() {
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
  public void testInvalidArgsNumberOfParametersMore() {
    String argParam[] = {"regexArg", "pathArg", "unwantedArg"};
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
    String argParam[] = {"regexArg", "pathArg"};
    String argFlags[] = {"r"};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid arguments", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testInvalidPath() {
    String argParam[] = {"regexArg", "invalid//path"};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Invalid file path", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testFileNotFound() {
    String argParam[] = {"regexArg", "file/does/not/exist"};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: File does not exist",
        testErrOut.getAllWritesAsString());
  }

  @Test
  public void testDirNotFound() {
    String argParam[] = {"regexArg", "dir/does/not/exist"};
    String argFlags[] = {"R"};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(ExitCode.FAILURE, exitVal);
    assertEquals("Error: Directory does not exist",
        testErrOut.getAllWritesAsString());
  }

  @Test
  public void testFileNoMatches() throws FSElementAlreadyExistsException {
    // Create a file with some content and add it to the root directory
    fs.getRoot().createAndAddNewFile("testFile",
        "test line 1\ntest line 2\ntest line 3");

    // Attempt to match a regex to the file
    String argParam[] = {"test line 4", "testFile"};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and no lines of content
    // matched the regex
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("\n", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testFileOneMatch() throws FSElementAlreadyExistsException {
    // Create a file with some content and add it to the root directory
    fs.getRoot().createAndAddNewFile("testFile",
        "test line 1\ntest line 2\ntest line 3");

    // Attempt to match a regex to the file
    String argParam[] = {"test line 1", "testFile"};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and one line of content
    // matched the regex
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("test line 1\n", testErrOut.getAllWritesAsString());
  }

  @Test
  public void testFileMultipleMatches() throws FSElementAlreadyExistsException {
    // Create a file with some content and add it to the root directory
    fs.getRoot().createAndAddNewFile("testFile",
        "test line 1\ntest line 2\ntest line 3");

    // Attempt to match a regex to the file
    String argParam[] = {"test line(.*)", "testFile"};
    String argFlags[] = {};
    HashMap<String, String> argNamedParam = new HashMap<>();
    CommandArgs args = new CommandArgs("grep", argParam, argFlags,
        argNamedParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and multiple lines of
    // content matched the regex
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("test line 1\ntest line 2\ntest line 3\n",
        testErrOut.getAllWritesAsString());
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
