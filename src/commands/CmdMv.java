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
import filesystem.FSElement;
import filesystem.FSElementAlreadyExistsException;
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
 * The mv command class that inherits from command
 *
 * @author anton, ursu
 */
public class CmdMv extends Command {

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
                  + " file overwrites the new file. The old file must exist,"
                  + " but the new file is created if it does not yet exist")
          .additionalComment(
              "If the old path is a file, and the new path is a directory, the"
                  + " file is moved into the directory. The file and directory"
                  + " must exist")
          .additionalComment(
              "If both the old and new paths are directories, the"
                  + " old directory is moved into the new directories parent "
                  + "overwriting the directory that was there before. The"
                  + " directories must exist")
          .additionalComment(
              "No functionality if the old path is a directory and the new path"
                  + " is a file at the same time. This is undefined behaviour.")
          .additionalComment("No functionality if the old path and new path "
                                 + "point to the same element")
          .build();

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
  protected ExitCode run(CommandArgs args, Writable out, Writable errOut) {
    // Get the elements by the given paths if they exist
    Path oldPath, newPath;
    FSElement from, to;
    try {
      // make sure the paths are good
      oldPath = new Path(args.getCommandParameters()[0]);
      newPath = new Path(args.getCommandParameters()[1]);
      // make sure that the element we are moving from exists
      from = fileSystem.getFSElementByPath(oldPath);
    } catch (MalformedPathException s) {
      errOut.writeln("Invalid path(s) given");
      return ExitCode.FAILURE;
    } catch (FSElementNotFoundException s) {
      errOut.writeln("You can only move an existing element");
      return ExitCode.FAILURE;
    }

    // check if the element we are moving to exists
    try {
      to = fileSystem.getFSElementByPath(newPath);
    } catch (MalformedPathException e) {
      errOut.writeln("Invalid destination path given");
      return ExitCode.FAILURE;
    } catch (FSElementNotFoundException e) {
      // if it does not exist we will try to make it exist by creating the
      // element with the name equal to the last segment of the path in the
      // directory given by the path not including the last segment
      try {
        String wantedName = newPath.removeLast();
        to = fileSystem.getDirByPath(newPath);
        // make sure that you are not moving to a child of itself
        if (fileSystem.getAbsolutePathOfFSElement(to)
            .startsWith(fileSystem.getAbsolutePathOfFSElement(from))) {
          errOut.writeln("Cannot move element to its child or itself");
          return ExitCode.FAILURE;
        }
        // rename the from item to the wanted name
        from.rename(wantedName);
      } catch (MalformedPathException e1) {
        errOut.writeln("Invalid destination path given");
        return ExitCode.FAILURE;
      } catch (FSElementNotFoundException e1) {
        errOut.writeln("Destination path does not exist.");
        return ExitCode.FAILURE;
      }
    }
    // make sure we aren't moving directory to a file
    if (from instanceof Directory && to instanceof File) {
      // can't do this write error and return failure
      errOut.writeln("Cannot move directory to file");
      return ExitCode.FAILURE;
    }
    // make sure we are not moving an element to itself or a child of itself
    if (fileSystem.getAbsolutePathOfFSElement(to)
        .startsWith(fileSystem.getAbsolutePathOfFSElement(from))) {
      errOut.writeln("Cannot move element to its child or itself");
      return ExitCode.FAILURE;
    }
    // initialize new and old parent
    Directory newParent, oldParent = from.getParent();
    if (to instanceof File) {
      // if we moving to a file
      // rename the file to the name of the file given as newpath
      from.rename(to.getName());
      // the new parent is the parent of the file given as newpath
      newParent = to.getParent();
      // delete the file given as newpath
      to.getParent().removeChildByName(to.getName());
    } else { // we are moving to a Directory so the new parent should be the
      // directory we are moving to and no renaming is necessary
      // new parent is the directory given as newpath
      newParent = (Directory) to;
    }
    try {
      // try to move the element into its new parent
      newParent.moveInto(from);
    } catch (FSElementAlreadyExistsException e) {
      errOut.writeln("Element with given name already exists in NEWPATH");
      return ExitCode.FAILURE;
    }
    // delete element being moved from its old parent
    oldParent.removeChildByName(from.getName());
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
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 2
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
