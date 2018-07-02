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
  public void testFileWithOneLine() throws FileAlreadyExistsException {
    File file1 = new File("testFile1", "hello");
    FS.getRoot().addFile(file1);

    String argParam[] = {"testFile1"};
    CommandArgs args = new CommandArgs("cat", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n");
  }

  @Test
  public void testMultipleFilesWithOneLine() throws FileAlreadyExistsException {
    File file2 = new File("testFile2", "world");
    FS.getRoot().addFile(file2);

    String argParam[] = {"testFile1", "testFile2"};
    CommandArgs args = new CommandArgs("cat", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\n\n\nworld\n");
  }

  @Test
  public void testFileWithMultipleLines() throws FileAlreadyExistsException {
    File file3 = new File("testFile3", "hello\nworld\nthis\n"
        + "is\na\ntest");
    FS.getRoot().addFile(file3);

    String argParam[] = {"testFile3"};
    CommandArgs args = new CommandArgs("cat", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getAllWritesAsString(), "hello\nworld\nthis\n"
        + "is\na\ntest\n");
  }

}
