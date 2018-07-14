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
          "Move contents of a file to another.", "mv OLDPATH NEWPATH")
          .additionalComment(
              "Paths of OLDPATH and NEWPATH can be relative or absolute.")
          .additionalComment(
              "File(s) at OLDPATH gets removed, and replaces the content at"
                  + " NEWPATH.")
          .additionalComment(
              "If both the old and new paths are files, the content of the old"
                  + " file is moved to the new file. The old file must exist,"
                  + " but the new file is created if it does not yet exist")
          .additionalComment(
              "If the old path is a file, and the new path is a directory, the"
                  + " file is moved into the directory. The file and directory"
                  + " must exist")
          .additionalComment(
              "If both the old and new paths are directories, all files in the"
                  + " old directory are moved into the new directory. The"
                  + " directories must exist")
          .additionalComment(
              "No functionality if the old path is a directory and the new path"
                  + " is a file at the same time")
          .build();

  /**
   * Executes the mv command with the given arguments. Mv moves the contents of
   * one file to another, one file to a directory, or all contents of a
   * directory to another. Error messages if the path of the old file/directory
   * or new directory is invalid, or does not exist.
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    Path oldPath, newPath;

    try {
      oldPath = new Path(args.getCommandParameters()[0]);
      newPath = new Path(args.getCommandParameters()[1]);

    } catch (MalformedPathException e) {
      errOut.write("Invalid path(s) given");
    }

    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Mv expects exactly 2 arguments
   *
   * @param args The command arguments container
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
