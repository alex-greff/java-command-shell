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

import commands.CmdCd;
import commands.CmdHistory;
import commands.CmdLs;
import commands.CmdMkdir;
import commands.CmdRecall;
import containers.CommandArgs;
import driver.JShell;
import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.Parser;

public class CmdRecallTest {

  // Create Testing Consoles, a command manager instance, an instance of the
  // mock file system and an instance of the command
  private BufferedConsole<String> tc;
  private BufferedConsole<String> tc_qry;
  private BufferedConsole<String> tc_err;
  private FileSystem fs;
  private CommandManager cm;
  private Command cmd;

  @Before
  // Resets the file system for each test case
  public void reset() {
    tc = new BufferedConsole<>();
    tc_qry = new BufferedConsole<>();
    tc_err = new BufferedConsole<>();
    fs = new InMemoryFileSystem();
    cm = CommandManager.constructCommandManager(tc, tc_qry, tc_err, fs);
    cmd = new CmdRecall(fs, cm);
  }

  @Test
  public void testRecallFirstEntryOnLs() {
    // make some ls test
    CommandArgs lsargs = Parser.parseUserInput("ls");
    CmdLs LsCall = new CmdLs(fs, cm);
    // end that
    CommandArgs args = Parser.parseUserInput("!1");

    // manually populate history with commands
    ArrayList<String> hist = JShell.getHistory();
    hist.add("ls");
    hist.add("mkdir one");
    // manually add the directory
    Directory dir1 = new Directory("dir1", fs.getRoot());
    fs.getRoot().addChild(dir1);
    Directory dir2 = new Directory("dir2", fs.getRoot());
    fs.getRoot().addChild(dir2);
    hist.add("bad command");

    // test to see if the recall command executed ls command
    ExitCode excLs = LsCall.execute(lsargs, tc, tc_qry, tc_err);
    ExitCode exc = cmd.execute(args, tc, tc_qry, tc_err);
    assertEquals(0, tc_err.getAllWritesAsString().length());
    assertTrue(tc.getAllWrites().size() > 0);
    assertEquals("dir2\ndir1\n", tc.getAllWritesAsString());


  }

  @Test
  public void TestRecallOnNumberTooBig() {
    String params[] = new String[1];
    params[0] = "4";
    CommandArgs args = new CommandArgs("recall", params);
    cmd = new CmdRecall(fs, cm);

    ExitCode exc = cmd.execute(args, tc, tc, tc_err);
    // in the case that i want to change the error message wording,
    // only check for the general "Error:" statement.
    assertTrue(tc_err.getAllWritesAsString().contains("error"));
    assertEquals(0, tc.getAllWritesAsString().length());
  }

  @Test
  public void testRecallDoubleDigitEntryHistoryWithParams() {
    // make some ls test
    CommandArgs histargs = Parser.parseUserInput("history 5");
    CmdHistory histCall = new CmdHistory(fs, cm);
    // end that
    CommandArgs args = Parser.parseUserInput("!11");

    // manually populate history with commands
    ArrayList<String> hist = JShell.getHistory();
    for (int i = 1; i < 10; i++) {
      String adder = "cmd" + String.valueOf(i);
      hist.add(adder);
    }
    hist.add("history 5");

    // test to see if the recall command executed history command
    ExitCode excHist = histCall.execute(histargs, tc, tc, tc_err);
    ExitCode exc = cmd.execute(args, tc, tc, tc_err);
    assertTrue(tc.getAllWrites().size() > 0);
    // the history carries over from previous tests
    assertEquals("7. cmd6\n8. cmd7\n9. cmd8\n10. cmd9\n11. "
                     + "history 5\n", tc.getAllWritesAsString());
  }

  @Test
  public void testRecallMkdirInDifferentDirectory() {
    // make some ls test
    CommandArgs mkdirArgs = Parser.parseUserInput("mkdir testingDir");
    CmdMkdir mkdirCall = new CmdMkdir(fs, cm);
    // end that
    CommandArgs args = Parser.parseUserInput("!12");

    ArrayList<String> hist = JShell.getHistory();
    // execute the mkdir
    ExitCode excMkdir = mkdirCall.execute(mkdirArgs, tc, tc, tc_err);
    Directory d = new Directory("testingDir", fs.getRoot());
    fs.getRoot().addChild(d);

    // now change directory
    CommandArgs cdArgs = Parser.parseUserInput("cd testingDir");
    CmdCd change = new CmdCd(fs, cm);
    ExitCode excCd = change.execute(cdArgs, tc, tc, tc_err);

    // create another directory now using recall
    ExitCode exc = cmd.execute(args, tc, tc, tc_err);

    CommandArgs lsRArgs = Parser.parseUserInput("ls -R /");
    CmdLs lscommand = new CmdLs(fs, cm);
    ExitCode eh = lscommand.execute(lsRArgs, tc, tc, tc_err);
    assertEquals("/:\ntestingDir:\n", tc.getAllWritesAsString());

  }

}
