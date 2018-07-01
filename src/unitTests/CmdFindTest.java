package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import commands.CmdFind;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FileAlreadyExistsException;
import filesystem.File;
import filesystem.FileSystem;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;
import utilities.Parser;

public class CmdFindTest {

  @BeforeClass
  public static void Setup()
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
    File file1_2 = new File("file1", "file1's contents");
    dir4.addFile(file4);
    dir4.addFile(file1_2);
    dir4.createAndAddNewDir("dir1");
  }

  @Test
  public void test_execute_find_one_file() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type f -name \"file4\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file4\n\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_execute_find_multiple_files() {
    CommandArgs args =
        Parser.parseUserInput("find / -type f -name \"file1\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/file1\n/file1\n\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_execute_find_no_files() {
    CommandArgs args =
        Parser.parseUserInput("find / -type f -name \"nonExistentFile\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_execute_find_one_directory() {
    CommandArgs args =
        Parser.parseUserInput("find /dir1 -type d -name \"dir4\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/\n\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_execute_find_multiple_directories() {
    CommandArgs args =
        Parser.parseUserInput("find / -type d -name \"dir1\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
        
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("/dir1/dir4/dir1/\n/dir1/\n\n", tc.getAllWritesAsString());
  }
  
  @Test
  public void test_execute_find_no_directories() {
    CommandArgs args =
        Parser.parseUserInput("find / -type d -name \"nonExistentDir\"");

    TestingConsole tc = new TestingConsole();
    TestingConsole tc_err = new TestingConsole();

    Command cmd = new CmdFind();
    ExitCode exitVal = cmd.execute(args, tc, tc_err);
    
    assertSame(exitVal, ExitCode.SUCCESS);
    assertEquals("\n", tc.getAllWritesAsString());
  }
}
