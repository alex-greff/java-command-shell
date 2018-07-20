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
import filesystem.File;
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
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() throws FSElementAlreadyExistsException {
    tc = new BufferedConsole<String>();
    tc_err = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    cmd = new CmdLs(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    root.createAndAddNewFile("file1", "file1's contents\n");
    root.createAndAddNewFile("file2", "file2's contents\n");
    root.createAndAddNewFile("file3", "file3's contents, dir2\n");
  }

  @Test
  // test the root dir, which contains both files and directories
  public void testDirsAndFiles() {
    CommandArgs args = new CommandArgs("ls");

    BufferedConsole<String> tc = new BufferedConsole<String>();
    BufferedConsole<String> tc_err = new BufferedConsole<String>();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertEquals("dir2\ndir1\nfile2\nfile1\n\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testEmptyDir() {
    String[] params = new String[1];
    params[0] = "dir1";
    CommandArgs args = new CommandArgs("ls", params);

    BufferedConsole<String> tc = new BufferedConsole<String>();
    BufferedConsole<String> tc_err = new BufferedConsole<String>();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertEquals("\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testDirFilesOnly() {
    String[] params = new String[1];
    params[0] = "/dir2";
    CommandArgs args = new CommandArgs("ls", params);

    BufferedConsole<String> tc = new BufferedConsole<String>();
    BufferedConsole<String> tc_err = new BufferedConsole<String>();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertEquals("file3\n\n", tc.getAllWritesAsString());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  
  @Test
  public void testWithFileAsParam() throws FSElementAlreadyExistsException {
    CommandArgs args = Parser.parseUserInput("ls file1");
    
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertEquals(ExitCode.SUCCESS, exitVal);
    assertEquals("file1\n", tc.getAllWritesAsString());
  }
}
