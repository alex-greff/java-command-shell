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
import java.util.HashMap;

public class CommandManager {

  private HashMap<String, Command> cmdList = new HashMap<>();

  public CommandManager() {
    cmdList.put("Cat", new CmdCat());
    cmdList.put("Cd", new CmdCd());
    cmdList.put("Echo", new CmdEcho());
    cmdList.put("Exit", new CmdExit());
    cmdList.put("History", new CmdHistory());
    cmdList.put("Ls", new CmdLs());
    cmdList.put("Man", new CmdMan());
    cmdList.put("Mkdir", new CmdMkdir());
    cmdList.put("Popd", new CmdPopd());
    cmdList.put("Pushd", new CmdPushd());
    cmdList.put("Pwd", new CmdPwd());
  }

  public void executeCommand(CommandArgs cArgs) {

  }
}
