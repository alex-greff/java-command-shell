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
import containers.CommandDescription;
import filesystem.FileSystem;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

public class CmdPwd extends Command {

  // Name and Description Constants
  private final String NAME = "pwd";
  private CommandDescription DESCRIPTION = new CommandDescription(
      "Print working directory.", new String[]{"pwd"},
      new String[]{"None."});

  /**
   * Executes the pwd command with the given arguments pwd prints the working
   * directory
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, always SUCCESS
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Obtain the instance of the FileSystem
    FileSystem FS = FileSystem.getInstance();
    // Write the path of the working directory in the FileSystem to the Console
    out.writeln(FS.getWorkingDirPath());
    // return SUCCESS always
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this command
   * pwd expects no arguments
   *
   * @param args The command arguments container
   * @return Returns true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Make sure the NAME matches, nothing else
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  /**
   * Gets the name of this command
   *
   * @return Returns the name of the command
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   * Gets the description for this command
   *
   * @return Returns the command description
   */
  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }

}
