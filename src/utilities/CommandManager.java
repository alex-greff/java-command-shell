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

import containers.*;
import commands.*;
import io.Console;
import io.ErrorConsole;
import java.util.HashMap;

public class CommandManager {

  private static CommandManager ourInstance = new CommandManager();
  private HashMap<String, Command> cmdList = new HashMap<>();

  private CommandManager() {
    cmdList.put("cat", new CmdCat());
    cmdList.put("cd", new CmdCd());
    cmdList.put("echo", new CmdEcho());
    cmdList.put("exit", new CmdExit());
    cmdList.put("history", new CmdHistory());
    cmdList.put("ls", new CmdLs());
    cmdList.put("man", new CmdMan());
    cmdList.put("mkdir", new CmdMkdir());
    cmdList.put("popd", new CmdPopd());
    cmdList.put("pushd", new CmdPushd());
    cmdList.put("pwd", new CmdPwd());
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
    if(cArgs != null) {
      Command cmd = cmdList.get(cArgs.getCommandName());
      if (cmd != null) {
        String result = cmd.execute(cArgs);
      }
    }
  }

  public String getCommandDescription(String commandName) {
    Command cmd = cmdList.get(commandName);
    String desc = null;
    if (cmd != null) {
      desc = cmd.getDescription();
    }
    return desc;
  }
}
