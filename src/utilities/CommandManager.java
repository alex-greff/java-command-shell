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
package utilities;

import commands.CmdCat;
import commands.CmdCd;
import commands.CmdEcho;
import commands.CmdExit;
import commands.CmdFind;
import commands.CmdHistory;
import commands.CmdLs;
import commands.CmdMan;
import commands.CmdMkdir;
import commands.CmdPopd;
import commands.CmdPushd;
import commands.CmdPwd;
import commands.CmdTree;
import containers.CommandArgs;
import containers.CommandDescription;
import io.Console;
import io.ErrorConsole;
import java.util.HashMap;

public class CommandManager {

  private static CommandManager ourInstance = null;

  // HashMap to contain known commands
  private HashMap<String, Command> cmdMap = new HashMap<>();

  // Instances of the Console and ErrorConsole to pass in command execution
  private Console out = Console.getInstance();
  private ErrorConsole errorOut = ErrorConsole.getInstance();

  /**
   * Private constructor for singleton
   */
  private CommandManager() {
  }

  /**
   * Populates cmdMap with all known commands as they are expected to be typed
   * in String format, mapped to an instance of the respective command
   */
  public void initializeCommands() {
    cmdMap.put("cat", new CmdCat());
    cmdMap.put("cd", new CmdCd());
    cmdMap.put("echo", new CmdEcho());
    cmdMap.put("exit", new CmdExit());
    cmdMap.put("history", new CmdHistory());
    cmdMap.put("ls", new CmdLs());
    cmdMap.put("man", new CmdMan());
    cmdMap.put("mkdir", new CmdMkdir());
    cmdMap.put("popd", new CmdPopd());
    cmdMap.put("pushd", new CmdPushd());
    cmdMap.put("pwd", new CmdPwd());
    cmdMap.put("tree", new CmdTree());
    cmdMap.put("find", new CmdFind());
  }

  /**
   * Get the singleton instance of the CommandManager
   *
   * @return Returns this CommandManager instance
   */
  public static CommandManager getInstance() {
    if (ourInstance == null) {
      ourInstance = new CommandManager();
    }
    return ourInstance;
  }

  /**
   * Attempt to execute a command, corresponding to the command name found in
   * the CommandArgs container cArgs. cArgs is passed to the command being
   * executed for information about any arguments found in cArgs. Error messages
   * if the command name in cArgs does not exist, or the command is given
   * invalid arguments.
   *
   * @param cArgs The command arguments container
   */
  public void executeCommand(CommandArgs cArgs) {
    // Default error message
    String errMsg = "Error: Invalid command, please try again";

    if (cArgs != null) { // Make sure the command args parsed properly

      // Get the name of the command the user inputted
      String cmdName = cArgs.getCommandName();

      if (cmdMap.containsKey(cmdName)) { // If the command exists in the HashMap

        // Then get the instance of the command from the HashMap
        Command cmd = cmdMap.get(cmdName);

        if (cmd.isValidArgs(cArgs)) { // If the args are valid for the command

          // Execute the command with the given args, remembering the exit value
          ExitCode exitValue = cmd.execute(cArgs, out, errorOut);
          // Does nothing with the exit value (perhaps a future update)

          return; // End here since we've done all we need to do

        } else {
          errMsg = "Error: Invalid arguments"; // Change the error message
        }
      }
    }

    // If this line is reached the command failed to execute, so print the
    // corresponding error message
    errorOut.writeln(errMsg);
  }

  /**
   * Attempt to obtain the CommandDescription of a command, given its name.
   *
   * @param commandName The command name, as it expected to be seen
   * @return Returns the command's description, if the command exists, or null
   */
  public CommandDescription getCommandDescription(String commandName) {
    // Get the command from the HashMap, given the command name as a String
    Command cmd = cmdMap.get(commandName);
    // Set the description found to null for now
    CommandDescription desc = null;

    // If the command name is in the HashMap, then we'll have an instance of
    // the command to get a description from, otherwise the description will
    // remain null
    if (cmd != null) {
      desc = cmd.getDescription();
    }

    // return the CommandDescription that was obtained
    // will be null if the command doesn't exist
    return desc;
  }

  /**
   * Shows if the cmdMap is empty
   *
   * @return Returns true if empty, false otherwise
   */
  public boolean isCmdMapEmpty() {
    return cmdMap.isEmpty();
  }

}
