package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import commands.CmdMv;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdMvTest {

  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command mvCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc, tc_err, fs);
    mvCmd = new CmdMv(fs, cm);
  }

  @Test
  public void testMovingDirectoryToAnExistingDirectory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    root.createAndAddNewDir("inside_test");
    // mv inside_test test
    CommandArgs args =
        new CommandArgs("mv", new String[] {"inside_test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be inside_test as a child of root
    assertFalse(root.containsChildElement("inside_test"));
    // inside_test should now be inside test
    assertTrue(root.getChildDirectoryByName("test")
        .containsChildElement("inside_test"));
  }

  @Test
  public void testMovingDirectoryToANonExistentDirectoryInSameTree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("inside_test");
    // mv inside_test test
    CommandArgs args =
        new CommandArgs("mv", new String[] {"inside_test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be inside_test as a child of root
    assertFalse(root.containsChildElement("inside_test"));
    // there should now only be test inside of root
    assertTrue(root.containsChildElement("test"));
  }

  @Test
  public void testMovingDirectoryToItself()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    // mv inside_test test
    CommandArgs args = new CommandArgs("mv", new String[] {"test", "test"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should fail
    assertEquals(ExitCode.FAILURE, mvExit);
    assertEquals("Cannot move element to itself or to its child",
        tc_err.getLastWrite());
    // nothing should happen to the directory
    assertTrue(root.containsChildElement("test"));
  }

  @Test
  public void testMovingDirectoryToNonExistingDirectoryInDifferentTree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("getinme");
    root.createAndAddNewDir("seeyalater");
    // mv inside_test test
    CommandArgs args =
        new CommandArgs("mv", new String[] {"seeyalater", "getinme/heythere"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be seeyalater as a child of root
    assertFalse(root.containsChildElement("seeyalater"));
    // there should now be heythere inside getinme
    assertTrue(root.getChildDirectoryByName("getinme")
        .containsChildElement("heythere"));
  }

  @Test
  public void testRenamingNonEmptyDirectory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    // mv daddy mommy
    CommandArgs args = new CommandArgs("mv", new String[] {"daddy", "mommy"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, mvExit);
    // there should no longer be daddy as a child of root
    assertFalse(root.containsChildElement("daddy"));
    // there should now only be mommy inside of root
    assertTrue(root.containsChildElement("mommy"));
    // make sure the kids don't get aborted
    assertTrue(
        root.getChildDirectoryByName("mommy").containsChildElement("kid1"));
    assertTrue(
        root.getChildDirectoryByName("mommy").containsChildElement("kid2"));
  }

  @Test
  public void testMovingNonRootChild() throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    // mv kid1 kid2
    CommandArgs args =
        new CommandArgs("mv", new String[] {"daddy/kid1", "daddy/kid2"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should succeed
    assertEquals(ExitCode.SUCCESS, mvExit);
    // daddy kid 1 should no longer exist
    assertFalse(daddy.containsChildElement("kid1"));
    // kid2 should exist and contain kid 1
    assertTrue(
        daddy.getChildDirectoryByName("kid2").containsChildElement("kid1"));
  }

  @Test
  public void testMovingDirectoryIntoItsChild()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory hello = root.createAndAddNewDir("hello");
    hello.createAndAddNewDir("hi");
    // mv hello hello/hi
    CommandArgs args =
        new CommandArgs("mv", new String[] {"hello", "hello/hi"});
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // this should fail
    assertEquals(ExitCode.FAILURE, mvExit);
    // hello should still exist
    assertTrue(root.containsChildElement("hello"));
    // hello/hi should still exist
    assertTrue(hello.containsChildElement("hi"));
    assertEquals("Cannot move element to itself or to its child",
        tc_err.getLastWrite());
  }

  @Test
  public void testMovingDirectoryThatIsCurrWorkingDir()
      throws FSElementAlreadyExistsException, MalformedPathException,
      FSElementNotFoundException {

    Directory root = fs.getRoot();
    root.createAndAddNewDir("d1_t");
    // Change working directory to the file
    fs.changeWorkingDir(new Path("/d1_t"));
    // mv d1_t d1
    CommandArgs args = Parser.parseUserInput("mv /d1_t /d1");
    ExitCode mvExit = mvCmd.execute(args, tc, tc, tc_err);
    // You can't move the current working directory
    assertEquals(ExitCode.FAILURE, mvExit);
  }
}
