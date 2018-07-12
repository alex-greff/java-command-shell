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
import filesystem.File;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The mv command class that inherits from command
 *
 * @author ursu
 */
public class CmdMv extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdMv(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "mv";

  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Move contents of a file to another.", "mv OLDFILE NEWFILE")
          .additionalComment(
              "Paths of OLDFILE and NEWFILE can be relative or absolute.")
          .additionalComment(
              "The OLDFILE gets removed, and replaces the content of NEWFILE.")
          .additionalComment(
              "If NEWFILE doesn't exist yet, it will be created.")
          .build();

  /**
   * Executes the mv command with the given arguments. Mv moves the contents of
   * one file to another. Error messages if the path of the source/old file is
   * invalid, or the file does not exist.
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Mv expects exactly 2 arguments
   *
   * @param args The command arguments container.
   * @return Returns true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Make sure the NAME matches, and there are exactly 2 arguments
    return args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 2
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR));
  }
}
