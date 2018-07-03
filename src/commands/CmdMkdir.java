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
import filesystem.Directory;
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The mkdir command
 *
 * @author anton
 */
public class CmdMkdir extends Command {

  /**
   * Command info constants
   */
  private static final String NAME = "mkdir";
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Make a new directory given one or more paths to an "
              + "existing parent",
          "mkdir PATH_LIST")
          .additionalComment(
              "The given path may be absolute or relative")
          .additionalComment("The path up to and not including the "
                                 + "last segment must point to an existing directory")
          .build();

  /**
   * Constructs a new command instance
   */
  public CmdMkdir() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the mkdir command
   *
   * @param args The arguments for the command call.
   * @param out The standard output console.
   * @param errorOut The error output console.
   * @return Exit code of the command
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out,
                          Writable errorOut) {
    // iterate over each given path
    for (String pathString : args.getCommandParameters()) {
      try {
        // attempt to create a new path object
        Path path = new Path(pathString);
        // remove the last segment of the path and store it
        // this is the name of the new directory that the user
        // wants to create
        String newDirName = path.removeLast();
        // get the parent directory of the new directory to be created
        Directory parent = fileSystem.getDirByPath(path);
        // add the directory to the parent
        parent.createAndAddNewDir(newDirName);
        // error handling
      } catch (MalformedPathException e) {
        errorOut.writeln("Error: Invalid path" + pathString);
        return ExitCode.SUCCESS;
      } catch (FileNotFoundException e) {
        errorOut.writeln("Error: Parent directory not found");
        return ExitCode.FAILURE;
      } catch (FileAlreadyExistsException e) {
        errorOut.writeln("Error: File already exists");
        return ExitCode.FAILURE;
      }
    }
    // if execution reaches here everything went wee and success is
    // returned as the exitcode
    return ExitCode.SUCCESS;
  }

  /**
   * Verifies the validity of the args with respect to the mkdir command
   *
   * @param args The command arguments.
   * @return true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length > 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
