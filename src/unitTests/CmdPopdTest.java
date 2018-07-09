package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import commands.CmdPopd;
import filesystem.FileSystem;
import filesystem.NonPersistentFileSystem;
import utilities.Command;
import utilities.CommandManager;

public class CmdPopdTest {
  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private TestingConsole tc;
  private TestingConsole tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new TestingConsole();
    tc_err = new TestingConsole();
    fs = new NonPersistentFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    cmd = new CmdPopd(fs, cm);
  }
}
