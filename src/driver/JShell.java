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
// UT Student #: 1004331992
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
import filesystem.FileSystem;
import io.Console;
import utilities.Parser;

public class JShell {

  // the exit condition can be toggled by a toggle function
  private static boolean running = true;
  // this filesystem (singleton) is used by the JShell
  private static FileSystem fs = FileSystem.getInstance();

  public static void main(String[] args) {
    // create means of attaining User Input (scanner may be replaced)
    String rawInput;
    Console console = new Console();
    // create while loop which only exits once the exit command is called
    // send user input to parser, then validate, then execute
    while (running) {
      // get working directory string, to be printed along with prompt
      // default prompt symbol
      String workingDirPath = fs.getWorkingDirPath();
      String prompt = workingDirPath + "#";
      console.write(prompt);
      rawInput = console.read();
      CommandArgs parsedInput = Parser.parseUserInput(rawInput);
      if (parsedInput != null) {
        // execute that shit
      }
      // find means of exiting shell
    }

    // use means of outputting to output data to the right destination
    // which can be out to system or file

  }

  public static void exit() {
    JShell.running = false;
  }
}
