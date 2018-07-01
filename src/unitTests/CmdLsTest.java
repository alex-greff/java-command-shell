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
