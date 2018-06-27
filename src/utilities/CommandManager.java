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
import io.Console;
import io.ErrorConsole;
import java.util.HashMap;

public class CommandManager {

  private static CommandManager ourInstance = new CommandManager();
  private HashMap<String, Command> cmdMap = new HashMap<>();

  private Console out = Console.getInstance();
  private ErrorConsole errorOut = ErrorConsole.getInstance();

  private CommandManager() {
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
    return ourInstance;
  }

  public void executeCommand(CommandArgs cArgs) {
    // make sure the command args parsed properly
    if (cArgs != null) {
      // get the name of the command the user inputted
      String cmdName = cArgs.getCommandName();
      // if the command exists
      if (cmdMap.containsKey(cmdName)) {
        // then get the instance of the command
        Command cmd = cmdMap.get(cmdName);
        // make sure the args are valid for the command
        if (cmd.isValidArgs(cArgs)) {
          String result = cmd.execute(cArgs);
          out.write(result);
        }
      }
    } else {
      // TODO: better error handling
      errorOut.write("Error: Invalid command, please try again");
    }
  }

  public String getCommandDescription(String commandName) {
    Command cmd = cmdMap.get(commandName);
    String desc = null;
    if (cmd != null) {
      desc = cmd.getDescription();
    }
    return desc;
  }
}
