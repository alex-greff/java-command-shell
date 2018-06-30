package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdCd;
import containers.CommandArgs;
import filesystem.FileAlreadyExistsException;
import filesystem.FileSystem;
import io.Console;
import io.ErrorConsole;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdCdTest {

  private Console out = Console.getInstance();
  private ErrorConsole errOut = ErrorConsole.getInstance();
  private Command cmd = new CmdCd();

  @Test
  public void testChildDir() throws FileAlreadyExistsException {
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);

    FileSystem FS = FileSystem.getInstance();
    FS.getRoot().createAndAddNewDir("testDir");

    ExitCode exitVal = cmd.execute(args, out, errOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir/");
  }

  @Test
  public void testCurrentDir() {
    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);

    FileSystem FS = FileSystem.getInstance();

    cmd.execute(args, out, errOut);
    ExitCode exitVal = cmd.execute(args, out, errOut);

    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir/");
  }

  @Test
  public void testParentDir() throws FileAlreadyExistsException {
    String argParam1[] = {"testDir"};
    CommandArgs args1 = new CommandArgs("cd", argParam1);

    String argParam2[] = {".."};
    CommandArgs args2 = new CommandArgs("cd", argParam2);

    FileSystem FS = FileSystem.getInstance();
    FS.getRoot().createAndAddNewDir("testDir");

    cmd.execute(args1, out, errOut);
    ExitCode exitVal = cmd.execute(args2, out, errOut);

    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

}
