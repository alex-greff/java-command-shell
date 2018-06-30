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
  public void testDirInWorkingDir() throws FileAlreadyExistsException {
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);

    FileSystem FS = FileSystem.getInstance();
    FS.getRoot().createAndAddNewDir("testDir");

    ExitCode exitVal = cmd.execute(args, out, errOut);

    assertEquals(exitVal, ExitCode.SUCCESS);
  }
}
