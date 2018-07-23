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
package driver;

import containers.CommandArgs;
import filesystem.InMemoryFileSystem;
import io.Console;
import io.ErrorConsole;
import io.QueryConsole;
import java.util.ArrayList;
import utilities.CommandManager;
import utilities.Parser;
import io.Readable;

/**
 * the JShell terminal.
 *
 * @author chedy
 */
public class JShell {

  // the exit condition can be toggled by a toggle function
  /**
   * the condition which keeps the JShell running
   */
  private static boolean running = true;

  // this filesystem (singleton) is used by the JShell
  /**
   * the filesystem that the JShell operates on
   */
  private static InMemoryFileSystem fs = new InMemoryFileSystem();

  /**
   * The console that the JShell reads and writes from
   */
  private static Console<String> console = new Console<>();

  /**
   * The console used for querying the user mid-command execution.
   */
  private static Console<String> queryConsole = new QueryConsole<>();
  
  /**
   * The error console that JShell uses.
   */
  private static Console<String> errorConsole = new ErrorConsole<>();

  /**
   * The command manager instance that JShell uses.
   */
  private static CommandManager cmdManager = CommandManager
      .constructCommandManager(console, queryConsole, errorConsole, fs);

  /**
   * A record of all of the user input.
   */
  private static ArrayList<String> history = new ArrayList<>();


  /**
   * The main function which makes the appropriate calls for JShell to operate
   * and that loops continually until exited
   *
   * @param args the arguments that are passed in after running the JShell
   */
  public static void main(String[] args) {
    String rawInput;
    // Initialize the commands in the command manager
    cmdManager.initializeCommands();

    // create while loop which only exits once the exit command is called
    // send user input to parser, then validate, then execute
    while (running) {
      // get working directory string, to be printed along with prompt
      String workingDirPath = fs.getWorkingDirPath();
      // write the default prompt symbol
      String prompt = workingDirPath + "# ";
      console.write(prompt);

      // read user input form the console
      rawInput = console.read();

      // add input to history
      history.add(rawInput);

      // Parse and execute the user input
      parseAndExecute(rawInput);
    }
  }

  /**
   * This function works as a toggle for JShells exit condition
   */
  public static void exit() {
    JShell.clearHistory();
    JShell.running = false;
  }

  /**
   * Gets the user input history.
   *
   * @return the user input history
   */
  public static ArrayList<String> getHistory() {
    return history;
  }

  /**
   * Clears all of the entries in history. Used in testing and termination
   */
  public static void clearHistory() { history.clear(); }

  /**
   * given a command input, parses and executes the user command
   *
   * @param input the raw string that is to be parsed and executed
   */
  public static void parseAndExecute(String input) {
    // parse the input
    CommandArgs parsedInput = Parser.parseUserInput(input);
    // Execute the command
    cmdManager.executeCommand(parsedInput);
  }

  public static boolean getRunning(){ return running;}

}
