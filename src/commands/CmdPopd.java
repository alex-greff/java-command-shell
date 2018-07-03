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
import filesystem.DirectoryStack;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The popd command
 *
 * @author anton
 */
public class CmdPopd extends Command {

  /**
   * Command info constants
   */
  private static final String NAME = "popd";
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Removes the directory at the top of the "
              + "directory stack and changes the current working "
              + "directory to the removed directory.",
          "popd")
          .build();

  /**
   * Constructs a new command instance
   */
  public CmdPopd() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the popd command with the given arguments
   *
   * @param args The arguments for the command call.
   * @param out The standard output console.
   * @param errorOut The error console
   * @return The exit code of the command
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out,
      Writable errorOut) {
    DirectoryStack dirStack = DirectoryStack.getInstance();
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
      return ExitCode.SUCCESS;
    } else {
      // can't popd if there's nothing to pop
      errorOut.writeln("Error: The directory stack is empty.");
      return ExitCode.FAILURE;
    }
  }

  /**
   * Validates the args with respect to the popd command
   *
   * @param args The command arguments.
   * @return True iff the command arguments are valid false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // this command does not take any arguments
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
