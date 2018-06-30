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
  private FileSystem FS = FileSystem.getInstance();

  @Test
  public void testChildDir() throws FileAlreadyExistsException {
    FS.getRoot().createAndAddNewDir("testDir1");

    String argParam[] = {"testDir1"};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, out, errOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testCurrentDir() {
    String argParam[] = {"."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, out, errOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/testDir1");
  }

  @Test
  public void testParentDir() {
    String argParam[] = {".."};
    CommandArgs args = new CommandArgs("cd", argParam);

    ExitCode exitVal = cmd.execute(args, out, errOut);
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(FS.getWorkingDirPath(), "/");
  }

}
