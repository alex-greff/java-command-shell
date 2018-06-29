package unitTests;

import static org.junit.Assert.assertSame;

import commands.CmdFind;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FileAlreadyExistsException;
import filesystem.File;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;
import utilities.Parser;

public class CmdFindTest {

  @Before
  public void Setup()
      throws FileAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    // See my notebook for a diagram of this file system
    Directory root = fs.getRoot();

    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = new File("file1", "file1's contents");
    root.addFile(file1);
    Directory dir3 = dir2.createAndAddNewDir("dir3");
    File file2 = new File("file2", "file2's contents");
    dir2.addFile(file2);
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = new File("file3", "file3's contents");
    dir4.addFile(file3);
    File file4 = new File("file4", "file4's contents");
    dir4.addFile(file4);
  }

  @Test
  public void testExecute1() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type f -name \"file4\"");

    System.out.println(args.toString());

    Command cmd = new CmdFind();
    ExitCode exitVal =
        cmd.execute(args, Console.getInstance(),
            ErrorConsole.getInstance());

    assertSame(exitVal, ExitCode.SUCCESS);
  }

  // TODO: add more tests
}
