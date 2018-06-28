package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdCd;
import containers.CommandArgs;
import filesystem.DirectoryAlreadyExistsException;
import filesystem.FileSystem;
import org.junit.Test;
import utilities.Command;

public class CmdCdTest {

  @Test
  public void testDirInWorkingDir() throws DirectoryAlreadyExistsException {
    String argParam[] = {"testDir"};
    CommandArgs args = new CommandArgs("cd", argParam);

    Command cmd = new CmdCd();

    FileSystem FS = FileSystem.getInstance();
    FS.getRoot().createAndAddNewDir("testDir");

    cmd.execute(args);

    assertEquals(FS.getWorkingDirPath(), "/testDir/");
  }
}
