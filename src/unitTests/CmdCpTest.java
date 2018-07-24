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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import commands.CmdCp;
import containers.CommandArgs;
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.BufferedConsole;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdCpTest {

  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_qry;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cpCmd;
  private OutputStream os;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<>();
    tc_qry = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_qry, tc_err, fs);
    cpCmd = new CmdCp(fs, cm);

    os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
  }

  @Test
  public void testCopyingDirectoryToAnExistingDirectory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    root.createAndAddNewDir("inside_test");
    // cp inside_test test
    CommandArgs args =
        new CommandArgs("cp", new String[]{"inside_test", "test"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, cpExit);
    // inside_test should still be a child of root
    assertTrue(root.containsChildElement("inside_test"));
    // inside_test should now be inside test
    assertTrue(root.getChildDirectoryByName("test")
                   .containsChildElement("inside_test"));
  }

  @Test
  public void testCopyingDirectoryToANonExistentDirectoryInSameTree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("inside_test");
    // cp inside_test test
    CommandArgs args =
        new CommandArgs("cp", new String[]{"inside_test", "test"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, cpExit);
    // should be inside_test as a child of root
    assertTrue(root.containsChildElement("inside_test"));
    // there should now be test inside of root
    assertTrue(root.containsChildElement("test"));
  }

  @Test
  public void testCopyingDirectoryToItself()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("test");
    // cp inside_test test
    CommandArgs args = new CommandArgs("cp", new String[]{"test", "test"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should fail
    assertEquals(ExitCode.FAILURE, cpExit);
    assertEquals("Cannot copy element to itself",
                 tc_err.getLastWrite());
    // nothing should happen to the directory
    assertTrue(root.containsChildElement("test"));
  }

  @Test
  public void testCopyingDirectoryToNonExistingDirectoryInDifferentTree()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewDir("getinme");
    root.createAndAddNewDir("seeyalater");
    CommandArgs args =
        new CommandArgs("cp", new String[]{"seeyalater", "getinme/heythere"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, cpExit);
    // there should still be seeyalater in root
    assertTrue(root.containsChildElement("seeyalater"));
    // there should now be heythere inside getinme
    assertTrue(root.getChildDirectoryByName("getinme")
                   .containsChildElement("heythere"));
  }

  @Test
  public void testCopyingNonEmptyDirectory()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    // cp daddy mommy
    CommandArgs args = new CommandArgs("cp", new String[]{"daddy", "mommy"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should run successfully
    assertEquals(ExitCode.SUCCESS, cpExit);
    // there should still be daddy as a child of root
    assertTrue(root.containsChildElement("daddy"));
    // there should now be mommy inside of root
    assertTrue(root.containsChildElement("mommy"));
    // make sure the kids don't get aborted
    assertTrue(
        root.getChildDirectoryByName("mommy").containsChildElement("kid1"));
    assertTrue(
        root.getChildDirectoryByName("mommy").containsChildElement("kid2"));
  }

  @Test
  public void testCopyingNonRootChild() throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory daddy = root.createAndAddNewDir("daddy");
    daddy.createAndAddNewDir("kid1");
    daddy.createAndAddNewDir("kid2");
    CommandArgs args =
        new CommandArgs("cp", new String[]{"daddy/kid1", "daddy/kid2"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should succeed
    assertEquals(ExitCode.SUCCESS, cpExit);
    // daddy kid 1 should still exist
    assertTrue(daddy.containsChildElement("kid1"));
    // kid2 should exist and contain kid 1
    assertTrue(
        daddy.getChildDirectoryByName("kid2").containsChildElement("kid1"));
  }

  @Test
  public void testCopyingDirectoryIntoItsChild()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    Directory hello = root.createAndAddNewDir("hello");
    hello.createAndAddNewDir("hi");
    CommandArgs args =
        new CommandArgs("cp", new String[]{"hello", "hello/hi"});
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // this should pass
    assertEquals(ExitCode.SUCCESS, cpExit);
    // hello should still exist
    assertTrue(root.containsChildElement("hello"));
    // hello/hi should still exist
    assertTrue(hello.containsChildElement("hi"));
    // hello/hi/hello should exist
    assertTrue(hello.getChildDirectoryByName("hi")
                   .containsChildElement("hello"));
    // hello/hi/hello/hi should also exist
    assertTrue(hello.getChildDirectoryByName("hi")
                   .getChildDirectoryByName("hello")
                   .containsChildElement("hi"));
  }

  @Test
  public void testCopyingDirectoryThatIsCurrWorkingDir()
      throws FSElementAlreadyExistsException, MalformedPathException,
             FSElementNotFoundException {

    Directory root = fs.getRoot();
    root.createAndAddNewDir("d1_t");
    // Change working directory to the file
    fs.changeWorkingDir(new Path("/d1_t"));
    // mv d1_t d1
    CommandArgs args = Parser.parseUserInput("cp /d1_t /d1");
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);
    // You can't move the current working directory
    assertEquals(ExitCode.FAILURE, cpExit);
  }

  @Test
  public void testCopyingFileWithUserPromptYes()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewFile("f1", "f1's contents");
    root.createAndAddNewFile("f2", "f2's contents");

    // buffer user input
    InputStream in = new ByteArrayInputStream("y".getBytes());
    System.setIn(in);

    // mv f1 f2
    CommandArgs args = Parser.parseUserInput("cp f1 f2");
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.SUCCESS, cpExit);
    assertTrue(root.containsChildElement("f1"));
    assertEquals("f1's contents", ((File) root.getChildByName("f1")).read());
    assertTrue(!root.containsChildElement("f2"));
  }

  @Test
  public void testCopyingFileWithUserPromptNo()
      throws FSElementAlreadyExistsException {
    Directory root = fs.getRoot();
    root.createAndAddNewFile("f1", "f1's contents");
    root.createAndAddNewFile("f2", "f2's contents");

    // buffer user input
    InputStream in = new ByteArrayInputStream("n".getBytes());
    System.setIn(in);

    // mv f1 f2
    CommandArgs args = Parser.parseUserInput("cp f1 f2");
    ExitCode cpExit = cpCmd.execute(args, tc, tc_qry, tc_err);

    assertEquals(ExitCode.FAILURE, cpExit);
    assertEquals("f1", root.getChildByName("f1").getName());
    assertEquals("f1's contents", ((File) root.getChildByName("f1")).read());
    assertEquals("f2", root.getChildByName("f2").getName());
    assertEquals("f2's contents", ((File) root.getChildByName("f2")).read());
  }
}
