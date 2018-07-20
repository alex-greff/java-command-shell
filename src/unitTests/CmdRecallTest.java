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
import static org.junit.Assert.assertTrue;

import commands.*;
import containers.CommandArgs;
import driver.JShell;
import filesystem.Directory;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import filesystem.MalformedPathException;
import io.BufferedConsole;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import filesystem.Path;

public class CmdRecallTest {
  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<String>();
    tc_err = new BufferedConsole<String>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_err, fs);
    cmd = new CmdRecall(fs, cm);
  }

  @Test
  public void testRecallFirstEntryOnLs(){
    // make some ls test
    String paramsLs[] = new String[0];
    CommandArgs lsargs = new CommandArgs("ls", paramsLs);
    CmdLs LsCall = new CmdLs(fs, cm);
    // end that

    String params[] = new String[1];
    params[0]="1";
    CommandArgs args = new CommandArgs("recall", params);

    BufferedConsole<String> tc = new BufferedConsole<String>();
    BufferedConsole<String> tc_err = new BufferedConsole<String>();
    // manually populate history with commands
    ArrayList<String> hist = JShell.getHistory();
    hist.add("ls"); hist.add("mkdir one");
    // manually add the directory
    Directory dir1 = new Directory("one", fs.getRoot());
    Directory dir2 = new Directory("two", fs.getRoot());
    hist.add("bad command");

    // test to see if the recall command executed ls command
    ExitCode excLs = LsCall.execute(lsargs, tc, tc_err);
    ExitCode exc = cmd.execute(args, tc, tc_err);
    assertTrue(tc_err.getAllWritesAsString().length() == 0);
    assertTrue(tc.getAllWrites().size() > 0);
    assertTrue(tc.getAllWrites().get(0).equals("one"));


  }

  @Test
  public void TestRecallOnNumberTooBig(){
    String params[] = new String[1];
    params[0] = "4";
    CommandArgs args = new CommandArgs("recall",params);
    cmd = new CmdRecall(fs, cm);

    ExitCode exc = cmd.execute(args, tc, tc_err);
    // in the case that i want to change the error message wording,
    // only check for the general "Error:" statement.
    assertTrue(tc_err.getAllWritesAsString().contains("Error:"));
  }

}
