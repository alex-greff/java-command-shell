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
import filesystem.Directory;
import filesystem.FSElementAlreadyExistsException;
import filesystem.FSElementNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Console;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The mkdir command
 *
 * @author anton
 */
public class CmdMkdir extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdMkdir(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Command info constants
   */
  private static final String NAME = "mkdir";
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Make a new directory given one or more paths to an "
              + "existing parent",
          "mkdir PATH...")
          .additionalComment("The given path may be absolute or relative")
          .additionalComment("The path up to and not including the "
                                 + "last segment must point to an existing directory")
          .build();

  /**
   * Executes the mkdir command
   *
   * @param args The arguments for the command call.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
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
        errorConsole.writeln("Error: Invalid path " + pathString);
        return ExitCode.SUCCESS;
      } catch (FSElementNotFoundException e) {
        errorConsole.writeln("Error: Parent directory not found");
        return ExitCode.FAILURE;
      } catch (FSElementAlreadyExistsException e) {
        errorConsole.writeln("Error: File already exists");
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
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() > 0
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
