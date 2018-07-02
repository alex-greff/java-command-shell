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
import static org.junit.Assert.assertTrue;

import commands.CmdLs;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdLsTest {

  @BeforeClass
  public static void setup() {
    FileSystem fs = FileSystem.getInstance();
    Directory root = fs.getRoot();
    try {
      Directory dir1 = root.createAndAddNewDir("dir1");
      Directory dir2 = root.createAndAddNewDir("dir2");
      File file1 = new File("file1", "file1's contents\n");
      root.addFile(file1);
      File file2 = new File("file2", "file2's contents\n");
      root.addFile(file2);
      File file3 = new File("file3", "file3's contents, dir2\n");
      dir2.addFile(file3);
    } catch (FileAlreadyExistsException f) {

    }
  }

  @Test
  // test the root dir, which contains both files and directories
  public void testDirsAndFiles() {
    CommandArgs args = new CommandArgs("ls");
    Command cmd = new CmdLs();
    ExitCode exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testEmptyDir() {
    String[] params = new String[1];
    params[0] = "dir1";
    CommandArgs args = new CommandArgs("ls", params);
    Command cmd = new CmdLs();
    ExitCode exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

  @Test
  public void testDirFilesOnly() {
    String[] params = new String[1];
    params[0] = "/dir2";
    CommandArgs args = new CommandArgs("ls", params);
    Command cmd = new CmdLs();
    ExitCode exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, ExitCode.SUCCESS);
  }

}
