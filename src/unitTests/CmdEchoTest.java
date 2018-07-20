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
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdEchoTest {

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
    cmd = new CmdEcho(fs, cm);

    // Setup base file system
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = root.createAndAddNewFile("file1");
    file1.write("file1's contents\n");
    dir2.createAndAddNewDir("dir3");
    File file2 = dir2.createAndAddNewFile("file2");
    file2.write("file2's contents\n");
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = dir4.createAndAddNewFile("file3");
    file3.write("file3's contents\n");
    File file4 = dir4.createAndAddNewFile("file4");
    file4.write("file4's contents\n");
  }

  @Test
  public void testExecuteEchoToConsole() {
    CommandArgs args = Parser.parseUserInput("echo \"nice sentence you got there\"");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("nice sentence you got there", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteWriteToExistingFile()
      throws MalformedPathException, FSElementNotFoundException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" > /dir1/dir4/file4");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    File file = fs.getFileByPath(new Path("/dir1/dir4/file4"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("some string", file.read());
  }


  @Test
  public void testExecuteAppendToFile()
      throws MalformedPathException, FSElementNotFoundException {
    CommandArgs args = Parser.parseUserInput("echo \"some string\" >> /file1");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    File file = fs.getFileByPath(new Path("/file1"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contents\nsome string", file.read());
  }

  @Test
  public void testExecuteCreateFile()
      throws MalformedPathException, FSElementNotFoundException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /fileBlahBlahBlah");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    File file = fs.getFileByPath(new Path("/fileBlahBlahBlah"));

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("some string", file.read());
  }

  @Test
  public void testExecuteMisingDirectory() {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /wrongDir/f1.txt");

    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.FAILURE);
  }
}
