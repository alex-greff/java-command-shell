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

import commands.CmdLs;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdLsTest {

  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_qry;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() throws FSElementAlreadyExistsException {
    tc = new BufferedConsole<>();
    tc_qry = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_qry, tc_err, fs);
    cmd = new CmdLs(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    root.createAndAddNewFile("file1", "file1's contents\n");
    root.createAndAddNewFile("file2", "file2's contents\n");
    dir2.createAndAddNewFile("file3", "file3's contents, dir2\n");
  }

  @Test
  // test the root dir, which contains both files and directories
  public void testDirsAndFiles() {
    CommandArgs args = new CommandArgs("ls");

    BufferedConsole<String> tc = new BufferedConsole<>();
    BufferedConsole<String> tc_err = new BufferedConsole<>();
    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals("dir2\ndir1\nfile2\nfile1\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testEmptyDir() {
    String[] params = new String[1];
    params[0] = "dir1";
    CommandArgs args = new CommandArgs("ls", params);

    BufferedConsole<String> tc = new BufferedConsole<>();
    BufferedConsole<String> tc_err = new BufferedConsole<>();
    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals("dir1:\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testDirFilesOnly() {
    String[] params = new String[1];
    params[0] = "/dir2";
    CommandArgs args = new CommandArgs("ls", params);

    BufferedConsole<String> tc = new BufferedConsole<>();
    BufferedConsole<String> tc_err = new BufferedConsole<>();
    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals("dir2:\nfile3\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }


  @Test
  public void testWithFileAsParam() {
    CommandArgs args = Parser.parseUserInput("ls file1");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("file1\n", tc.getAllWritesAsString());
  }

  @Test
  public void testWithMultipleDirectoryParams() {
    CommandArgs args = Parser.parseUserInput("ls dir1 dir2");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("dir1:\n\ndir2:\nfile3\n", tc.getAllWritesAsString());
  }

  @Test
  public void testRecursiveFromRoot() {
    CommandArgs args = Parser.parseUserInput("ls -R");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("dir2:\n\tfile3\n\ndir1:\n\nfile2\nfile1\n",
                 tc.getAllWritesAsString());
  }

  @Test
  public void testRecursiveFromSubdir() {
    CommandArgs args = Parser.parseUserInput("ls -R dir2");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("dir2:\nfile3\n", tc.getAllWritesAsString());
  }

  @Test
  public void testRecursiveMultipleDirs() {
    CommandArgs args = Parser.parseUserInput("ls -R dir2 dir1");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("dir2:\nfile3\n\ndir1:\n", tc.getAllWritesAsString());
  }

  @Test
  public void testRecursiveMultipleDirsAndFiles() {
    CommandArgs args = Parser.parseUserInput("ls -R dir2 file2 dir1 file1");

    ExitCode exitVal = cmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("dir2:\nfile3\n\nfile2\n\ndir1:\n\nfile1\n",
                 tc.getAllWritesAsString());
  }

}
