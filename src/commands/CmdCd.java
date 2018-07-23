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

import static utilities.JShellConstants.APPEND_OPERATOR;
import static utilities.JShellConstants.OVERWRITE_OPERATOR;
import containers.CommandArgs;
import containers.CommandDescription;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import io.Readable;

/**
 * The cd command class that inherits from command
 *
 * @author ursu
 */
public class CmdCd extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdCd(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "cd";

  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder("Change directory.",
          "cd DIRECTORY")
              .additionalComment(
                  "Path of DIRECTORY can be relative or absolute.")
              .build();

  /**
   * Executes the cd command with the given arguments. Cd changes the working
   * directory. Error messages if the directory path is invalid, or the
   * directory does not exist.
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param in The standard input
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Writable<String> out, Readable in,
      Writable<String> errOut) {
    // Obtain the DIRECTORY argument passed
    String location = args.getCommandParameters()[0];

    try {
      // Change the FileSystem's working directory to the created Path
      fileSystem.changeWorkingDir(new Path(location));

    } catch (MalformedPathException e) {
      // Argument given is an improper Path, return FAILURE
      errOut.writeln("Error: Invalid directory path");
      return ExitCode.FAILURE;

    } catch (FSElementNotFoundException e) {
      // No Directory at the Path of the argument given, return FAILURE
      errOut.writeln("Error: Directory does not exist");
      return ExitCode.FAILURE;
    }

    // Nothing went wrong if this line is reached, return SUCCESS
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Cd expects only 1 argument
   *
   * @param args The command arguments container
   * @return Returns true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 1
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR));

    // Check that the parameters are not strings
    boolean stringParamsMatches = true;
    for (String p : args.getCommandParameters()) {
      stringParamsMatches = stringParamsMatches && !isStringParam(p);
    }

    // Return the result
    return paramsMatches && stringParamsMatches;
  }
}
