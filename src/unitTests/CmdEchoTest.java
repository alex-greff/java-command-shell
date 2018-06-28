package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import commands.CmdEcho;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.DirectoryAlreadyExistsException;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.Parser;

public class CmdEchoTest {

  @Before
  public void Setup()
      throws FileAlreadyExistsException, DirectoryAlreadyExistsException {
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
  public void testExecute1() {
    CommandArgs args =
        new CommandArgs("echo",
            new String[]{"nice sentence you got there"});

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);
    assertTrue(out_actual.length() > 0);
  }

  @Test
  public void testExecute2()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args =
        Parser.parseUserInput(
            "echo \"some string\" > /dir1/dir4/file4");

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/dir1/dir4/file4");
    File file = fs.getFileByPath(filePath);

    assertEquals("some string", file.read());
  }

  @Test
  public void testExecute3()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args = Parser
        .parseUserInput("echo \"some string\" >> /file1");

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    FileSystem fs = FileSystem.getInstance();

    Path filePath = new Path("/file1");
    File file = fs.getFileByPath(filePath);

    assertEquals("file1's contents\nsome string", file.read());
  }

  @Test
  public void testExecute4()
      throws MalformedPathException, FileNotFoundException {
    CommandArgs args =
        Parser.parseUserInput(
            "echo \"some string\" >> /fileBlahBlahBlah");

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    FileSystem fs = FileSystem.getInstance();

    File file = fs.getFileByPath(new Path("/fileBlahBlahBlah"));

    assertEquals("some string", file.read());
  }

  @Test
  public void testExecute5() {
    CommandArgs args =
        Parser.parseUserInput(
            "notEcho \"some string\" >> /fileBlahBlahBlah");

    Command cmd = new CmdEcho();
    String out_actual = cmd.execute(args);

    assertNull(out_actual);
  }
}
