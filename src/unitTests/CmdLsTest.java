package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import commands.CmdLs;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.DirectoryAlreadyExistsException;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;

public class CmdLsTest {

  @Before
  public void setup()
      throws FileAlreadyExistsException, DirectoryAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    Directory root = fs.getRoot();
    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = new File("file1", "file1's contents\n");
    root.addFile(file1);
    File file2 = new File("file2", "file2's contents\n");
    root.addFile(file2);
    File file3 = new File("file3", "file3's contents, dir2\n");
    dir2.addFile(file3);

  }

  @Test
  // test the root dir, which contains both files and directories
  public void testDirsAndFiles() {
    CommandArgs args = new CommandArgs("ls");
    Command cmd = new CmdLs();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, 0);
  }

  @Test
  public void testEmptyDir() {
    String[] params = new String[1];
    params[0] = "/dir1";
    CommandArgs args = new CommandArgs("ls", params);
    Command cmd = new CmdLs();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, 0);
  }

  @Test
  public void testDirFilesOnly() {
    String[] params = new String[1];
    params[0] = "/dir2";
    CommandArgs args = new CommandArgs("ls", params);
    Command cmd = new CmdLs();
    int exitVal =
        cmd.execute(args, Console.getInstance(), ErrorConsole.getInstance());
    assertEquals(exitVal, 0);
  }

}
