package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdCat;
import containers.CommandArgs;
import filesystem.File;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCatTest {

  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdCat();
  private FileSystem FS = FileSystem.getInstance();

  @Test
  public void testOneFile() throws FileAlreadyExistsException {
    File file1 = new File("testFile1", "hello");
    FS.getRoot().addFile(file1);

    String argParam[] = {"testFile1"};
    CommandArgs args = new CommandArgs("cat", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n");
  }

  @Test
  public void testMultipleFiles() throws FileAlreadyExistsException {
    File file2 = new File("testFile2", "world");
    FS.getRoot().addFile(file2);

    String argParam[] = {"testFile1", "testFile2"};
    CommandArgs args = new CommandArgs("cat", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n\n\nworld\n");
  }

}
