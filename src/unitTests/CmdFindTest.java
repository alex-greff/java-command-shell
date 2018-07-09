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
import commands.CmdEcho;
import commands.CmdFind;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.NonPersistentFileSystem;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdFindTest {
  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private TestingConsole tc;
  private TestingConsole tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() throws FileAlreadyExistsException {
    tc = new TestingConsole();
    tc_err = new TestingConsole();
    fs = new NonPersistentFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    cmd = new CmdFind(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = new File("file1", "file1's contents");
    root.addFile(file1);
    dir2.createAndAddNewDir("dir3");
    File file2 = new File("file2", "file2's contents");
    dir2.addFile(file2);
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = new File("file3", "file3's contents");
    dir4.addFile(file3);
    File file4 = new File("file4", "file4's contents");
    File file1_2 = new File("file1", "file1's contents");
    dir4.addFile(file4);
    dir4.addFile(file1_2);
    dir4.createAndAddNewDir("dir1");
  }



  @Test
  public void testExecuteFindOneFile() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type f -name \"file4\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file4\n\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindMultipleFiles() {
    CommandArgs args = Parser.parseUserInput("find / -type f -name \"file1\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file1\n/file1\n\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindNoFiles() {
    CommandArgs args =
        Parser.parseUserInput("find / -type f -name \"nonExistentFile\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindOneDirectory() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type d -name \"dir4\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/\n\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindMultipleDirectories() {
    CommandArgs args = Parser.parseUserInput("find / -type d -name \"dir1\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/dir1/\n/dir1/\n\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindNoDirectories() {
    CommandArgs args =
        Parser.parseUserInput("find / -type d -name \"nonExistentDir\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("\n", tc.getAllWritesAsString());
  }
}
