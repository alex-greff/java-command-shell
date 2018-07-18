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
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be inside_test as a child of root
    assertFalse(root.containsDir("inside_test"));
    // inside_test should now be inside test
    assertTrue(root.getDirByName("test").containsDir("inside_test"));
  }

  @Test
  public void test_moving_directory_to_a_non_existent_directory_in_same_tree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("inside_test");
    // mv inside_test test
    CommandArgs args = new CommandArgs("mv",
                                       new String[]{"inside_test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be inside_test as a child of root
    assertFalse(root.containsDir("inside_test"));
    // there should now only be test inside of root
    assertTrue(root.containsDir("test"));
  }

  @Test
  public void test_moving_directory_to_itself()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    // mv inside_test test
    CommandArgs args = new CommandArgs("mv",
                                       new String[]{"test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should fail
    assertEquals(ExitCode.FAILURE, mvExit);
    assertEquals("Cannot move element to its child or itself",
                 tc_err.getLastWrite());
    // nothing should happen to the directory
    assertTrue(root.containsDir("test"));
  }

  @Test
  public void test_moving_directory_to_non_existing_directory_in_diff_tree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("getinme");
    root.createAndAddNewDir("seeyalater");
    // mv inside_test test
    CommandArgs args =
        new CommandArgs("mv",
                        new String[]{"seeyalater", "getinme/heythere"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be seeyalater as a child of root
    assertFalse(root.containsDir("seeyalater"));
    // there should now be heythere inside getinme
    assertTrue(root.getDirByName("getinme").containsDir("heythere"));
  }

  @Test
  public void test_renaming_non_empty_directory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    // mv daddy mommy
    CommandArgs args = new CommandArgs("mv", new String[]{"daddy", "mommy"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be daddy as a child of root
    assertFalse(root.containsDir("daddy"));
    // there should now only be mommy inside of root
    assertTrue(root.containsDir("mommy"));
    // make sure the kids don't get aborted
    assertTrue(root.getDirByName("mommy").containsDir("kid1"));
    assertTrue(root.getDirByName("mommy").containsDir("kid2"));
  }

  @Test
  public void test_moving_non_root_child()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    // mv kid1 kid2
    CommandArgs args =
        new CommandArgs("mv", new String[]{"daddy/kid1", "daddy/kid2"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
    // this should succeed
    assertEquals(ExitCode.SUCCESS, mvExit);
    // daddy kid 1 should no longer exist
    assertFalse(daddy.containsDir("kid1"));
    // kid2 should exist and contain kid 1
    assertTrue(daddy.getDirByName("kid2").containsDir("kid1"));
  }

  @Test
  public void test_moving_directory_into_its_child()
      throws FSElementAlreadyExistsException {
      Directory root = fs.getRoot();
      Directory hello = root.createAndAddNewDir("hello");
      hello.createAndAddNewDir("hi");
      // mv hello hello/hi
      CommandArgs args =
          new CommandArgs("mv", new String[]{"hello", "hello/hi"});
      ExitCode mvExit = mvCmd.execute(args, tc, tc_err);
      // this should fail
      assertEquals(ExitCode.FAILURE, mvExit);
      // hello should still exist
      assertTrue(root.containsDir("hello"));
      // hello/hi should still exist
      assertTrue(hello.containsDir("hi"));
      assertEquals("Cannot move element to its child or itself", tc_err.getLastWrite());
  }
}