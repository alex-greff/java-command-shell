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

import static org.junit.Assert.assertTrue;
import commands.CmdMkdir;
import containers.CommandArgs;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;

public class CmdMkdirTest {

  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_qry;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command mkdirCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<>();
    tc_qry = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_qry, tc_err, fs);
    mkdirCmd = new CmdMkdir(fs, cm);
  }

  @Test
  public void testWithOnePath() {
    // only creating the test directory
    CommandArgs cargs = new CommandArgs("mkdir", new String[] {"test"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_qry, tc_err);
    // make sure the directory exists
    assertTrue(fs.getWorkingDir().containsChildElement("test"));
  }

  @Test
  public void testWithMultiplePaths() {
    // creating multiple directories
    CommandArgs cargs =
        new CommandArgs("mkdir", new String[] {"test", "test2"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_qry, tc_err);
    // make sure the directories exist
    assertTrue(fs.getWorkingDir().containsChildElement("test"));
    assertTrue(fs.getWorkingDir().containsChildElement("test2"));
  }

  @Test
  public void testMultipleNotInWorkingDir() throws FSElementNotFoundException {
    // creating parent
    CommandArgs cargs =
        new CommandArgs("mkdir", new String[] {"test1", "test1/test2"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_qry, tc_err);
    // make sure the directories exist
    assertTrue(fs.getWorkingDir().containsChildElement("test1"));
    assertTrue(fs.getWorkingDir().getChildDirectoryByName("test1")
        .containsChildElement("test2"));
  }
}
