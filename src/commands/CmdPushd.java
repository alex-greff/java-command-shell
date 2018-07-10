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
import filesystem.FileSystem;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The pushd command
 *
 * @author anton
 */
public class CmdPushd extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdPushd(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }


  /**
   * Command info constants
   */
  private static final String NAME = "pushd";
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Pushes the current directory to the top of the directory"
              + " stack and changes the current working directory to the"
              + " given directory",
          "pushd DIRECTORY").build();

  private DirectoryStack dirStack = DirectoryStack.getInstance();


  /**
   * Executes the pushd command with the given arguments
   *
   * @param args The arguments for the command call.
   * @param out The standard output console.
   * @param errorOut The error console
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errorOut) {
    String curPath = fileSystem.getWorkingDirPath();
    dirStack.push(curPath);
    // make command args to call the cd command with
    String[] fileNameArg = args.getCommandParameters();
    CommandArgs cdArgs = new CommandArgs("cd", fileNameArg);
    // execute the cd command to go to the given directory
    commandManager.executeCommand(cdArgs);
    // this command does not print anything
    return ExitCode.SUCCESS;
  }

  /**
   * Validates the args with respect to the pushd command
   *
   * @param args The command arguments.
   * @return True iff the args are valid with respect to the pushd command
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().isEmpty()
        && args.getTargetDestination().isEmpty();
  }
}
