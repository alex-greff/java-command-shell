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

public class CmdCdTest {

  @Test
  public void testDirInWorkingDir()
      throws FileAlreadyExistsException {
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);

    Command cmd = new CmdCd();

    FileSystem FS = FileSystem.getInstance();
    FS.getRoot().createAndAddNewDir("testDir");

    cmd.execute(args, Console.getInstance(),
        ErrorConsole.getInstance());

    assertEquals(FS.getWorkingDirPath(), "/testDir/");
  }
}
