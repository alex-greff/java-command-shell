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

  private CommandManager() {
  }

  public void initializeCommands() {
    // All known commands as they are expected to be typed in String format,
    // along with an instance of the respective command
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
   * Get the singleton instance of the command manager
   *
   * @return This command manager instance
   */
  public static CommandManager getInstance() {
    if (ourInstance == null) {
      ourInstance = new CommandManager();
    }
    return ourInstance;
  }

  public void executeCommand(CommandArgs cArgs) {

    // Make sure the command args parsed properly
    if (cArgs != null) {

      // Get the name of the command the user inputted
      String cmdName = cArgs.getCommandName();
      // If the command exists in the HashMap
      if (cmdMap.containsKey(cmdName)) {

        // Then get the instance of the command from the HashMap
        Command cmd = cmdMap.get(cmdName);
        // If the args given are valid for the command
        if (cmd.isValidArgs(cArgs)) {

          ExitCode exitValue = cmd.execute(cArgs, out, errorOut);
          // Does nothing with the exit value (perhaps a future update)

        } else {
          errorOut.writeln("Error: Invalid command arguments");
        }

      } else {
        errorOut.writeln("Error: Invalid command, please try again");
      }

    }

  }

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
}
