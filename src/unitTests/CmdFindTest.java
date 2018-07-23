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
import commands.CmdFind;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdFindTest {

  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() throws FSElementAlreadyExistsException {
    tc = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc, tc_err, fs);
    cmd = new CmdFind(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = root.createAndAddNewFile("file1");
    file1.write("file1's contents");
    dir2.createAndAddNewDir("dir3");
    File file2 = dir2.createAndAddNewFile("file2");
    file2.write("file2's contents");
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = dir4.createAndAddNewFile("file3");
    file3.write("file3's contents");
    File file4 = dir4.createAndAddNewFile("file4");
    file4.write("file4's contents");
    File file1_2 = dir4.createAndAddNewFile("file1");
    file1_2.write("file1's contents");
    dir4.createAndAddNewDir("dir1");
  }


  @Test
  public void testExecuteFindOneFile() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type f -name \"file4\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file4\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindMultipleFiles() {
    CommandArgs args = Parser.parseUserInput("find / -type f -name \"file1\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file1\n/file1\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindNoFiles() {
    CommandArgs args =
        Parser.parseUserInput("find / -type f -name \"nonExistentFile\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindOneDirectory() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type d -name \"dir4\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindMultipleDirectories() {
    CommandArgs args = Parser.parseUserInput("find / -type d -name \"dir1\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1\n/dir1/dir4/dir1\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteFindNoDirectories() {
    CommandArgs args =
        Parser.parseUserInput("find / -type d -name \"nonExistentDir\"");

    ExitCode exitVal = cmd.execute(args, tc, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("", tc.getAllWritesAsString());
  }
}
