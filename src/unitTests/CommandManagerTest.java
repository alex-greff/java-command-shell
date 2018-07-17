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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import containers.CommandDescription;
import filesystem.FileSystem;
import filesystem.InMemoryFileSystem;
import io.BufferedConsole;
import org.junit.Test;
import utilities.CommandManager;

public class CommandManagerTest {

  BufferedConsole testOut = new BufferedConsole();
  BufferedConsole testErrOut = new BufferedConsole();
  FileSystem fs = new InMemoryFileSystem();
  CommandManager cm =
      CommandManager.constructCommandManager(testOut, testErrOut, fs);

  @Test
  public void testInitialization() {
    // Assert that the cmdMap has in fact been populated by initializeCommands
    cm.initializeCommands();
    assertFalse(cm.isCmdMapEmpty());
  }

  @Test
  public void testGetCommandDescription() {
    // Assert that a CommandDescription is returned for a known command
    CommandDescription desc = cm.getCommandDescription("man");
    assertNotNull(desc);
  }

}
