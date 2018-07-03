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
import filesystem.File;
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The cat command class that inherits from command
 * 
 * @author ursu
 */
public class CmdCat extends Command {

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
   * Constructs a new command instance
   */
  public CmdCat() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the cat command with the given arguments. Cat prints the contents
   * of files. Error messages if the file path is invalid, or the file does not
   * exist
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
        // Initiate a Path to the FILE with the path given as a String
        Path filePath = new Path(filePathStr);
        // Get the File with the given Path
        File file = fileSystem.getFileByPath(filePath);
        // Finally, append the contents of the File to the StringBuilder,
        // with 1 new line to separate multiple files, and 2 more for spacing
        result.append(file.read()).append("\n\n\n");

      } catch (MalformedPathException e) {
        // Argument given is an improper Path
        errOut.writeln("Invalid file path");

      } catch (FileNotFoundException e) {
        // No File at the Path of the argument given
        errOut.writeln("File does not exist");
      }

    }

    // Write all the contents read to the Console and return SUCCESS always
    out.writeln(result.toString().trim());
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
    // Make sure the NAME matches, and at least 1 argument, nothing else
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length > 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
