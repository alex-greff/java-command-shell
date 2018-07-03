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
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCdTest {

  // Create Testing Consoles, an instance of the command, and get an instance
  // of the file system
  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdCd();
  private FileSystem FS = FileSystem.getInstance();

  @Before
  public void resetSingleton()
      throws SecurityException, NoSuchFieldException,
             IllegalArgumentException, IllegalAccessException {
    Field instance = FileSystem.class.getDeclaredField("ourInstance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

  @Test
  public void testChildDir() throws FileAlreadyExistsException {
    // Create a directory and add it to the root directory
    FS.getRoot().createAndAddNewDir("testDir");

    // Attempt to change into the child directory created
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the child directory which we created
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir");
  }

  @Test
  public void testCurrentDir() {
    // Attempt to change into the current directory
    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is still the root directory
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

  @Test
  public void testParentDir() throws FileAlreadyExistsException {
    // Create a directory and add it to the root directory
    FS.getRoot().createAndAddNewDir("testDir");

    // Change into the child directory we created, so we can try to change back
    // to the parent directory
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testErrOut);

    // Attempt to change into the parent directory
    String argParam2[] = {".."};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the root directory again
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

  @Test
  public void testAbsolutePathToDir() throws FileAlreadyExistsException {
    // Create a directory and add it to the root directory
    FS.getRoot().createAndAddNewDir("testDir");

    // Change into the child directory we created, so we can make another in it
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testErrOut);

    // Create a directory and add it to the directory we are currently in
    FS.getWorkingDir().createAndAddNewDir("testDirAgain");

    // Attempt to change into the grandchild directory, giving in a path
    // starting from the root
    String argParam2[] = {"/testDir/testDirAgain"};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the grandchild directory which we created
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir/testDirAgain");
  }

  @Test
  public void testRelativePathToDir() throws FileAlreadyExistsException {
    // Create two directories and add them to the root directory
    FS.getRoot().createAndAddNewDir("testDir");
    FS.getRoot().createAndAddNewDir("testDirAgain");

    // Change into the child directory we created, so we can try to access its
    // sibling from it
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);
    cmd.execute(args1, testOut, testErrOut);

    // Attempt to change into the sibling directory, giving in a path that goes
    // up to the parent first, and then to the sibling
    String argParam2[] = {"../testDirAgain"};
    CommandArgs args2 = new CommandArgs("cd", argParam2);
    ExitCode exitVal = cmd.execute(args2, testOut, testErrOut);

    // Assert that the command successfully executed, and that the working
    // directory is now the second sibling directory which we created
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDirAgain");
  }

}
