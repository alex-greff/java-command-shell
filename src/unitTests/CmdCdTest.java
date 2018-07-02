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
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCdTest {

  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdCd();
  private FileSystem FS = FileSystem.getInstance();

  @Test
  public void testChildDir() throws FileAlreadyExistsException {
    FS.getRoot().createAndAddNewDir("testDir1");
    FS.getRoot().createAndAddNewDir("testDir2");

    String argParam[] = {"testDir1"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testCurrentDir() throws FileAlreadyExistsException {
    FS.getWorkingDir().createAndAddNewDir("testDir3");

    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testParentDir() {
    String argParam[] = {".."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

  @Test
  public void testAbsolutePathToDir() {
    String argParam[] = {"/testDir1/testDir3"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1/testDir3");
  }

  @Test
  public void testRelativePathToDir() {
    String argParam[] = {"../../testDir2"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir2");
  }

}
