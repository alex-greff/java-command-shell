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
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The cat command class that inherits from command
 *
 * @author ursu
 */
public class CmdCat extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdCat(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "cat";

  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder("Print contents of file(s).",
          "cat FILES")
              .additionalComment("Path of FILE can be relative or absolute.")
              .additionalComment("Can take more than one FILE as arguments.")
              .build();

  /**
   * Executes the cat command with the given arguments. Cat prints the contents
   * of files. Error messages if a file path is invalid, or a file does not
   * exist.
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, always SUCCESS
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Obtain the FILES arguments passed and initiate a StringBuilder
    String[] files = args.getCommandParameters();
    StringBuilder result = new StringBuilder();

    for (String filePathStr : files) { // Iterate through FILES arguments
      try {
        // Get the File with the given Path
        File file = fileSystem.getFileByPath(new Path(filePathStr));
        // Finally, append the contents of the File to the StringBuilder,
        // with 1 new line to separate multiple files, and 2 more for spacing
        result.append(file.read()).append("\n\n\n");

      } catch (MalformedPathException e) {
        // Argument given is an improper Path
        errOut.writeln("Invalid file path");

      } catch (FSElementNotFoundException e) {
        // No File at the Path of the argument given
        errOut.writeln("File does not exist");
      }
    }

    // Get the result string
    String resultStr = result.toString().trim();
    if (!resultStr.isEmpty())
      resultStr += "\n";

    // If a redirect is given then attempt to write to file and return exit code
    if (!args.getRedirectOperator().isEmpty())
      return writeToFile(resultStr, args.getRedirectOperator(),
          args.getTargetDestination(), errOut);

    // If no redirect operator then...
    // Write all the contents read to the Console and return SUCCESS always
    out.write(resultStr);
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Cat expects at least 1 argument
   *
   * @param args The command arguments container
   * @return Returns true iff the arguments are valid, false otherwise
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
