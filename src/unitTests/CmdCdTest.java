package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdCd;
import containers.CommandArgs;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCdTest {

  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdCd();
  private FileSystem FS = FileSystem.getInstance();

  @Test
  public void testChildDir() throws FileAlreadyExistsException {
    FS.getRoot().createAndAddNewDir("testDir1");
    FS.getRoot().createAndAddNewDir("testDir2");

    String argParam[] = {"testDir1"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testCurrentDir() throws FileAlreadyExistsException {
    FS.getWorkingDir().createAndAddNewDir("testDir3");

    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testParentDir() {
    String argParam[] = {".."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

  @Test
  public void testAbsolutePathToDir() {
    String argParam[] = {"/testDir1/testDir3"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1/testDir3");
  }

  @Test
  public void testRelativePathToDir() {
    String argParam[] = {"../../testDir2"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir2");
  }

}
