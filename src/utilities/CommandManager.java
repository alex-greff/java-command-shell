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
import commands.CmdCp;
import commands.CmdCurl;
import commands.CmdEcho;
import commands.CmdExit;
import commands.CmdFind;
import commands.CmdGrep;
import commands.CmdHistory;
import commands.CmdLs;
import commands.CmdMan;
import commands.CmdMkdir;
import commands.CmdMv;
import commands.CmdPopd;
import commands.CmdPushd;
import commands.CmdPwd;
import commands.CmdRecall;
import commands.CmdTree;
import containers.CommandArgs;
import containers.CommandDescription;
import filesystem.FileSystem;
import io.Console;
import java.util.HashMap;

/**
 * The CommandManager class that stores all known commands, and is in charge of
 * command execution and obtaining command descriptions.
 *
 * @author ursu
 */
public class CommandManager {

  /**
   * HashMap container for command names mapped to command instances, initially
   * empty.
   */
  private HashMap<String, Command> cmdMap = new HashMap<>();
  /**
   * The standard console.
   */
  private Console<String> console;
  /**
   * The query console.
   */
  private Console<String> queryConsole;
  /**
   * The error console.
   */
  private Console<String> errorConsole;
  /**
   * The file system used.
   */
  private FileSystem fileSystem;
  /**
   * The exit code of the last command run.
   */
  private ExitCode lastExitCode = ExitCode.SUCCESS;

  /**
   * Private constructor.
   *
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @param fileSystem The file system to be used.
   */
  private CommandManager(Console<String> console, Console<String> queryConsole,
      Console<String> errorConsole, FileSystem fileSystem) {
    this.console = console;
    this.queryConsole = queryConsole;
    this.errorConsole = errorConsole;
    this.fileSystem = fileSystem;

    initializeCommands();
  }

  /**
   * Factory method constructing a new instance of a command manager.
   *
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @param fileSystem The file system to be used.
   * @return Returns a new instance of a command manager.
   */
  public static CommandManager constructCommandManager(Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole,
      FileSystem fileSystem) {
    return new CommandManager(console, queryConsole, errorConsole, fileSystem);
  }

  /**
   * Populates cmdMap with all known commands as they are expected to be typed
   * in String format, mapped to an instance of the respective command.
   */
  public void initializeCommands() {
    cmdMap.put("cat", new CmdCat(fileSystem, this));
    cmdMap.put("cd", new CmdCd(fileSystem, this));
    cmdMap.put("echo", new CmdEcho(fileSystem, this));
    cmdMap.put("exit", new CmdExit(fileSystem, this));
    cmdMap.put("find", new CmdFind(fileSystem, this));
    cmdMap.put("grep", new CmdGrep(fileSystem, this));
    cmdMap.put("history", new CmdHistory(fileSystem, this));
    cmdMap.put("ls", new CmdLs(fileSystem, this));
    cmdMap.put("man", new CmdMan(fileSystem, this));
    cmdMap.put("mkdir", new CmdMkdir(fileSystem, this));
    cmdMap.put("popd", new CmdPopd(fileSystem, this));
    cmdMap.put("pushd", new CmdPushd(fileSystem, this));
    cmdMap.put("pwd", new CmdPwd(fileSystem, this));
    cmdMap.put("tree", new CmdTree(fileSystem, this));
    cmdMap.put("curl", new CmdCurl(fileSystem, this));
    cmdMap.put("mv", new CmdMv(fileSystem, this));
    cmdMap.put("cp", new CmdCp(fileSystem, this));
    cmdMap.put("recall", new CmdRecall(fileSystem, this));
  }

  /**
   * Attempt to execute a command, corresponding to the command name found in
   * the CommandArgs container cArgs. cArgs is passed to the command being
   * executed for information about any arguments found in cArgs. Error messages
   * if the command name in cArgs does not exist, or the command is given
   * invalid arguments.
   *
   * @param cArgs The command arguments container.
   */
  public void executeCommand(CommandArgs cArgs) {
    if (cArgs != null) { // Make sure the command args parsed properly

      // Get the name of the command the user inputted
      String cmdName = cArgs.getCommandName();

      if (cmdMap.containsKey(cmdName)) { // If the command exists in the HashMap

        // Then get the instance of the command from the HashMap
        Command cmd = cmdMap.get(cmdName);

        // Execute the command with the given args, remembering the exit value
        lastExitCode = cmd.execute(cArgs, console, queryConsole, errorConsole);
        // Does nothing with the exit value (perhaps a future update)

        return; // End here since we've done all we need to do
      }
    }

    // If this line is reached the command failed to execute, so print the
    // corresponding error message
    errorConsole.writeln("Error: Invalid command, please try again");
  }

  /**
   * Attempt to obtain the CommandDescription of a command, given its name.
   *
   * @param commandName The command name, as it expected to be seen
   * @return Returns the CommandDescription container for the command, if it
   *         exists, or null.
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
   * Shows if the cmdMap is empty.
   *
   * @return Returns true if empty, false otherwise.
   */
  public boolean isCmdMapEmpty() {
    return cmdMap.isEmpty();
  }

  /**
   * Getter for the exit code of the last command run.
   *
   * @return The exit code of the last run command.
   */
  public ExitCode getLastExitCode() {
    return lastExitCode;
  }
}
