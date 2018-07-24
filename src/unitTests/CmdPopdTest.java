// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: ursualex
// UT Student #: 1004357199
// Author: Alexander Ursu
//
// Student2:
// UTORID user_name: greffal1
// UT Student #: 1004254497
// Author: Alexander Greff
//
// Student3:
// UTORID user_name: sankarch
// UT Student #: 1004174895
// Author: Chedy Sankar
//
// Student4:
// UTORID user_name: kamins42
// UT Student #: 1004431992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
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
  private BufferedConsole<String> tc_qry;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command popdCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<>();
    tc_qry = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_qry, tc_err, fs);
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
    popdCmd.execute(cargs, tc, tc_qry, tc_err);
    // make sure the working dir has changed to the value in the stack
    assertEquals(fs.getWorkingDirPath(), "/test");
  }
}
