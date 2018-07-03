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

import commands.CmdPwd;
import containers.CommandArgs;
import filesystem.FileSystem;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import utilities.Command;
import utilities.ExitCode;

public class CmdPwdTest {

  // Create Testing Consoles, and an instance of the command
  private TestingConsole testOut = new TestingConsole();
  private TestingConsole testErrOut = new TestingConsole();
  private Command cmd = new CmdPwd();

  @Before
  public void resetSingleton()
      throws SecurityException, NoSuchFieldException,
             IllegalArgumentException, IllegalAccessException {
    Field instance = FileSystem.class.getDeclaredField("ourInstance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

  @Test
  public void testRootDir() {
    // Attempt to display the current working directory
    CommandArgs args = new CommandArgs("pwd");
    ExitCode exitVal = cmd.execute(args, testOut, testErrOut);

    // Assert that the command successfully executed, and that the path of the
    // root directory was printed
    assertEquals(exitVal, ExitCode.SUCCESS);
    assertEquals(testOut.getLastWrite(), "/");
  }

}
