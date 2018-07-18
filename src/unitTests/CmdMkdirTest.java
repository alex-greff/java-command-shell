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

  private BufferedConsole tc;
  private BufferedConsole tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command mkdirCmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole();
    tc_err = new BufferedConsole();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    mkdirCmd = new CmdMkdir(fs, cm);
    cm.initializeCommands();
  }

  @Test
  public void testWithOnePath() {
    // only creating the test directory
    CommandArgs cargs = new CommandArgs("mkdir", new String[]{"test"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_err);
    // make sure the directory exists
    assertTrue(fs.getWorkingDir().containsDir("test"));
  }

  @Test
  public void testWithMultiplePaths() {
    // creating multiple directories
    CommandArgs cargs = new CommandArgs("mkdir", new String[]{"test", "test2"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_err);
    // make sure the directories exist
    assertTrue(fs.getWorkingDir().containsDir("test"));
    assertTrue(fs.getWorkingDir().containsDir("test2"));
  }

  @Test
  public void testMultipleNotInWorkingDir() throws FSElementNotFoundException {
    // creating parent
    CommandArgs cargs = new CommandArgs("mkdir", new String[]{"test1",
        "test1/test2"});
    // execute mkdir
    mkdirCmd.execute(cargs, tc, tc_err);
    // make sure the directories exist
    assertTrue(fs.getWorkingDir().containsDir("test1"));
    assertTrue(fs.getWorkingDir().getDirByName("test1").containsDir("test2"));
  }
}