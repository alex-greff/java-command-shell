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
package commands;

import containers.CommandArgs;
import filesystem.DirectoryStack;
import utilities.Command;

public class CmdPopd extends Command {

  private DirectoryStack dirStack = DirectoryStack.getInstance();

  @Override
  public String execute(CommandArgs args) {
    // get the most recently added directory off the stack
    if (!dirStack.empty()) {
      // get the path of the most recent directory
      String mostRecent = dirStack.pop();
      String[] arg = {mostRecent};
      // make command args to call the cd command with
      CommandArgs cdArgs = new CommandArgs("cd", arg);
      // execute the cd command to go to the directory popped off
      // the stack
      commandManager.executeCommand(cdArgs);
      // the command does not need to print anything
      return "";
    } else {
      return "The directory stack is empty.";
    }
  }

  @Override
  public boolean isValidArgs(CommandArgs args) {
    // this command does not take any arguments
    return args.getCommandParameters().length == 0;
  }

  @Override
  public String getName() {
    return "popd";
  }

  @Override
  public String getDescription() {
    return "popd Command Documentation\n"
        + "Description:\n"
        + "    - popd: removes the directory at the top of the "
        + "directory stack and changes the current working "
        + "directory to the removed directory.\n"
        + "\n Usage: popd";
  }

}
