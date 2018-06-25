package unitTests;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import commands.CmdEcho;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.ErrorConsole;
import utilities.Command;
import utilities.Parser;

public class CmdEchoTest {
  @Before
  public void Setup() {
    FileSystem fs = FileSystem.getInstance();

    // try {
    // See my notebook for a diagram of this file system
    // Path rootPath = new Path("/"); // This crashes
    // Directory root = fs.getDirByPath(rootPath);
    Directory root = fs.getRoot();

    Directory dir1 = new Directory("dir1", root);
    root.addDir(dir1);
    Directory dir2 = new Directory("dir2", root);
    root.addDir(dir2);
    File file1 = new File("file1", "file1's contents");
    root.addFile(file1);
    Directory dir3 = new Directory("dir3", root);
    dir2.addDir(dir3);
    File file2 = new File("file2", "file2's contents");
    dir2.addFile(file2);
    Directory dir4 = new Directory("dir4", dir1);
    dir1.addDir(dir4);
    File file3 = new File("file3", "file3's contents");
    dir4.addFile(file3);
    File file4 = new File("file4", "file4's contents");
    dir4.addFile(file4);

    // } catch (MalformedPathException e) {
    // ErrorConsole.getInstance().write("Failed to initialize file system");
    // }

  }


  @Test
  public void testExecute1() {
    CommandArgs args =
        new CommandArgs("echo", new String[] {"nice sentence you got there"});

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    assertEquals(true, out_actual.length() > 0);
  }

  @Test
  public void testExecute2() throws MalformedPathException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" > /dir1/dir4/file4");

    System.out.println(args.toString());

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/dir1/dir4/file4");
    File file = fs.getFileByPath(filePath);

    assertEquals("some string", file.read());
  }
  
  @Test
  public void testExecute3() throws MalformedPathException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /file1");

    System.out.println(args.toString());

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/file1");
    File file = fs.getFileByPath(filePath);

    assertEquals("file1's contents\nsome string", file.read());
  }
  
  @Test
  public void testExecute4() throws MalformedPathException {
    CommandArgs args =
        Parser.parseUserInput("echo \"some string\" >> /fileBlahBlahBlah");

    System.out.println(args.toString());

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    assertEquals("", out_actual);
  }
}
