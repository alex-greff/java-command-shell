package unitTests;

import static org.junit.Assert.assertEquals;

import commands.CmdPopd;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.DirectoryStack;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;

public class CmdPopdTest {

  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command popdCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<String>();
    tc_err = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    popdCmd = new CmdPopd(fs, cm);
    cm.initializeCommands();
  }

  @Test
  public void testPopdWithDirectory() throws FSElementAlreadyExistsException {
    DirectoryStack ds = DirectoryStack.getInstance();
    // add a new directory for testing
    Directory newDir = fs.getWorkingDir().createAndAddNewDir("test");
    // add the new directory to the stack
    ds.push("/test");
    // execute popd with no args
    CommandArgs cargs = new CommandArgs("popd");
    popdCmd.execute(cargs, tc, tc_err);
    // make sure the working dir has changed to the value in the stack
    assertEquals(fs.getWorkingDirPath(), "/test");
  }
}