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
import utilities.Command;
import filesystem.FileSystem;

public class CmdPwd extends Command {

  private final String NAME = "pwd";
  private final String DESCRIPTION = "" + "Pwd Command Documentation\n"
      + "Description:\n" + "    - pwd: print working directory\n"
      + "   \n" + "Usage:\r\n" + "    - pwd\n" + "    \n"
      + "Additional Comments:\n" + "    - None\n";

  @Override
  public String execute(CommandArgs args) {
    if(!isValidArgs(args)) {
      return null;
    }

    FileSystem FS = FileSystem.getInstance(); // Obtain FileSystem
    return FS.getWorkingDirPath() + "\n"; // And return the working directory
  }

  private boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  @Override
  public String getName() { return NAME; }

  @Override
  public String getDescription() { return DESCRIPTION; }

}
