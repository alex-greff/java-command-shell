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

import commands.CmdCat;
import containers.CommandArgs;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCatTest {

  // Create Testing Consoles, an instance of the command, and get an instance
  // of the file system
  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdCat();
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
  public void testFileWithOneLine()
      throws FileAlreadyExistsException {
    // Create a file with one line of content, and add it to the root directory
    File file1 = new File("testFile1", "hello");
    FS.getRoot().addFile(file1);

    String argParam[] = {"testFile1"};
    CommandArgs args = new CommandArgs("cat", argParam);

    // Assert that the command successfully executed, and just the one line of
    // content was printed
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n");
  }

  @Test
  public void testMultipleFilesWithOneLine()
      throws FileAlreadyExistsException {
    // Create another file with one line of content, and add it to the root
    // directory, the file from before still exists in the file system
    File file2 = new File("testFile2", "world");
    FS.getRoot().addFile(file2);

    String argParam[] = {"testFile1", "testFile2"};
    CommandArgs args = new CommandArgs("cat", argParam);

    // Assert that the command successfully executed, and that both lines of
    // content were printed, with 2 blank lines in between
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(),
        "hello\n\n\nworld\n");
  }

  @Test
  public void testFileWithMultipleLines()
      throws FileAlreadyExistsException {
    // Create a file with multiple lines of content, and add it to the root
    // directory
    File file3 = new File("testFile3", "hello\nworld\nthis\n"
        + "is\na\ntest");
    FS.getRoot().addFile(file3);

    String argParam[] = {"testFile3"};
    CommandArgs args = new CommandArgs("cat", argParam);

    // Assert that the command successfully executed, and that every line of
    // content was printed
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(),
        "hello\nworld\nthis\n"
            + "is\na\ntest\n");
  }

}
