package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import commands.CmdEcho;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;
import utilities.Parser;

public class CmdEchoTest {

  @BeforeClass
  public static void setup() throws FileAlreadyExistsException {
    FileSystem fs = FileSystem.getInstance();
    // See my notebook for a diagram of this file system
    Directory root = fs.getRoot();

    Directory dir1 = root.createAndAddNewDir("dir1");
    Directory dir2 = root.createAndAddNewDir("dir2");
    File file1 = new File("file1", "file1's contents\n");
    root.addFile(file1);
    dir2.createAndAddNewDir("dir3");
    File file2 = new File("file2", "file2's contents\n");
    dir2.addFile(file2);
    Directory dir4 = dir1.createAndAddNewDir("dir4");
    File file3 = new File("file3", "file3's contents\n");
    dir4.addFile(file3);
    File file4 = new File("file4", "file4's contents\n");
    dir4.addFile(file4);
  }


  @Test
  public void testExecuteEchoToConsole() {
    CommandArgs args =
        new CommandArgs("echo", new String[] {"nice sentence you got there"});

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdEcho();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("nice sentence you got there\n", tc.getAllWritesAsString());
  }

  @Test
  public void testExecuteWriteToFile()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" > /dir1/dir4/file4");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdEcho();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/dir1/dir4/file4");
    File file = fs.getFileByPath(filePath);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("some string", file.read());
  }


  @Test
  public void testExecuteAppendToFile()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args = Parser.parseUserInput("echo \"some string\" >> /file1");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdEcho();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/file1");
    File file = fs.getFileByPath(filePath);

    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("file1's contents\nsome string", file.read());
  }

  @Test
  public void testExecuteCreateFile()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /fileBlahBlahBlah");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdEcho();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);

    FileSystem fs = FileSystem.getInstance();

    File file = fs.getFileByPath(new Path("/fileBlahBlahBlah"));
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("some string", file.read());
  }

  @Test
  public void testExecuteMisingDirectory()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /wrongDir/f1.txt");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdEcho();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.FAILURE);
  }
}
