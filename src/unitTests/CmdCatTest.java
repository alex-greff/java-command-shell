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
  public void testFileWithOneLine() throws FileAlreadyExistsException {
    // Create a file with one line of content, and add it to the root directory
    File file = new File("testFile", "hello");
    FS.getRoot().addFile(file);

    // Attempt to display the contents of the file
    String argParam[] = {"testFile"};
    CommandArgs args = new CommandArgs("cat", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and just the one line of
    // content was printed
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n");
  }

  @Test
  public void testMultipleFilesWithOneLine() throws FileAlreadyExistsException {
    // Create two files with one line of content each, and add them to the root
    // directory
    File file1 = new File("testFile1", "hello");
    File file2 = new File("testFile2", "world");
    FS.getRoot().addFile(file1);
    FS.getRoot().addFile(file2);

    // Attempt to display the contents of both files
    String argParam[] = {"testFile1", "testFile2"};
    CommandArgs args = new CommandArgs("cat", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and that both lines of
    // content were printed, with 2 blank lines in between
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n\n\nworld\n");
  }

  @Test
  public void testFileWithMultipleLines() throws FileAlreadyExistsException {
    // Create a file with multiple lines of content, and add it to the root
    // directory
    File file = new File("testFile",
        "hello\nworld\nthis\nis\na\ntest");
    FS.getRoot().addFile(file);

    // Attempt to display the contents of the file
    String argParam[] = {"testFile"};
    CommandArgs args = new CommandArgs("cat", argParam);
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and that every line of
    // content was printed
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(),
        "hello\nworld\nthis\nis\na\ntest\n");
  }

}
