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
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The cd command class that inherits from command
 */
public class CmdCd extends Command {

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "cd";

  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Change directory.",
          "cd DIRECTORY")
          .additionalComment(
              "Path of DIRECTORY can be relative or absolute.")
          .build();

  /**
   * Constructs a new command instance
   */
  public CmdCd() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the cd command with the given arguments. Cd changes the
   * working directory. Error messages if the directory path is
   * invalid, or the directory does not exist
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out,
      Writable errOut) {
    // Obtain the DIRECTORY argument passed
    String location = args.getCommandParameters()[0];

    try {
      // Initiate a Path to the DIRECTORY with the path given as a String
      Path newDir = new Path(location);
      // Change the FileSystem's working directory to the created Path
      fileSystem.changeWorkingDir(newDir);

    } catch (MalformedPathException e) {
      // Argument given is an improper Path, return FAILURE
      errOut.writeln("Error: Invalid file path");
      return ExitCode.FAILURE;

    } catch (FileNotFoundException e) {
      // No Directory at the Path of the argument given, return FAILURE
      errOut.writeln("Error: File does not exist");
      return ExitCode.FAILURE;
    }

    // Nothing went wrong if this line is reached, return SUCCESS
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for
   * this command. Cd expects only 1 argument
   *
   * @param args The command arguments container
   * @return Returns true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Make sure the NAME matches, and just 1 argument, nothing else
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
