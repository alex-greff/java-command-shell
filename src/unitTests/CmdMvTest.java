package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import commands.CmdMv;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdMvTest {

  private BufferedConsole tc;
  private BufferedConsole tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command mvCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole();
    tc_err = new BufferedConsole();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    mvCmd = new CmdMv(fs, cm);
  }

  @Test
  public void test_movingDirectory_to_an_existing_directory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    root.createAndAddNewDir("inside_test");
    // mv inside_test test
    CommandArgs args = new CommandArgs("mv",
                                       new String[]{"inside_test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should run successfully
    assertEquals(mvExit, ExitCode.SUCCESS);
    // there should no longer be inside_test as a child of root
    assertFalse(root.containsDir("inside_test"));
    // inside_test should now be inside test
    assertTrue(root.getDirByName("test").containsDir("inside_test"));
  }

  @Test
  public void test_moving_directory_to_a_non_existent_directory()
      throws FSElementAlreadyExistsException {
      Directory root = fs.getRoot();
      root.createAndAddNewDir("inside_test");
      // mv inside_test test
      CommandArgs args = new CommandArgs("mv",
                                         new String[]{"inside_test", "test"});
      ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
      // this should run successfully
      assertEquals(mvExit, ExitCode.SUCCESS);
      // there should no longer be inside_test as a child of root
      assertFalse(root.containsDir("inside_test"));
      // there should now only be test inside of root
      assertTrue(root.containsDir("test"));
  }
}