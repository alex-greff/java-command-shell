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
          .additionalComment(
              "No functionality if the old path and new path "
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

  @Override
  protected ExitCode run(CommandArgs args, Writable<String> out,
      Writable<String> errorOut) {
    Path fromPath, toPath;
    FSElement from, to;
    // rename flag for mv if the string is not empty a rename is required
    String newName = "";
    try {
      // get the paths given in the arguments
      fromPath = new Path(args.getCommandParameters()[0]);
      toPath = new Path(args.getCommandParameters()[1]);
      // make sure that the element we are moving from exists
      from = fileSystem.getFSElementByPath(fromPath);
      // make sure we aren't moving root
      if (from == fileSystem.getRoot()) {
        errorOut.writeln("Cannot move root directory");
        return ExitCode.FAILURE;
      }
      if (from == fileSystem.getWorkingDir()) {
        errorOut.writeln("Cannot move current working directory");
      }
    } catch (MalformedPathException e) {
      errorOut.writeln("Invalid path(s) given");
      return ExitCode.FAILURE;
    } catch (FSElementNotFoundException e) {
      errorOut.writeln("You can only move an existing element");
      return ExitCode.FAILURE;
    }
    // we now have the from directory and so far are good to go
    try {
      to = fileSystem.getFSElementByPath(toPath);
    } catch (MalformedPathException e) {
      errorOut.writeln("Invalid destination path given");
      return ExitCode.FAILURE;
    } catch (FSElementNotFoundException e) {
      // we assume that the user wishes to move and rename
      // we will attempt to fetch one level up of the given path
      // copy the path
      Path levelUp = new Path(toPath);
      // the non existing part of the path is the name the user
      // wishes this element to have after it is moved
      newName = levelUp.removeLast();
      // we now attempt to get the element one level up
      try {
        to = fileSystem.getFSElementByPath(levelUp);
      } catch (MalformedPathException e1) {
        errorOut.writeln("Invalid destination path given");
        return ExitCode.FAILURE;
      } catch (FSElementNotFoundException e1) {
        errorOut.writeln("Destination path does not exist.");
        return ExitCode.FAILURE;
      }
    }
    // set the rename flag depending on the value of the string
    boolean rename = !newName.isEmpty();
    // we have now taken care of the to path and the from path
    // we now need to do some validity checks
    // first we make sure we don't move directory to file
    if (from instanceof Directory && to instanceof File) {
      errorOut.writeln("Cannot move directory to file");
      return ExitCode.FAILURE;
    }
    // second make sure we dont move an element to itself or its child
    String absFrom = fileSystem.getAbsolutePathOfFSElement(from);
    String absTo = fileSystem.getAbsolutePathOfFSElement(to);
    if (absTo.startsWith(absFrom)) {
      errorOut.writeln("Cannot move element to itself or to its child");
      return ExitCode.FAILURE;
    }
    // third make sure we aren't moving an element to its parent
    // the only time this is permitted is if we are renaming
    if (!rename && to == from.getParent()) {
      errorOut.writeln("The element being moved is already in the destination");
      return ExitCode.FAILURE;
    }
    ExitCode mvExit;
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
  protected ExitCode oldrun(CommandArgs args, Writable<String> out,
      Writable<String> errOut) {
    // Get the elements by the given paths if they exist
    Path oldPath, newPath;
    FSElement from, to;
    try {
      // make sure the paths are good
      oldPath = new Path(args.getCommandParameters()[0]);
      newPath = new Path(args.getCommandParameters()[1]);
      // make sure that the element we are moving from exists
      from = fileSystem.getFSElementByPath(oldPath);
      // make sure that we aren't trying to move the root directory
      if (from == fileSystem.getRoot()) {
        errOut.writeln("Cannot move the root directory");
        return ExitCode.FAILURE;
      }
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
      // edge case if we are moving to same directory
      if (oldParent != newParent) {
        // try to move the element into its new parent
        newParent.moveInto(from);
      } else {
        newParent.addChild(from);
      }
    } catch (FSElementAlreadyExistsException e) {
      errOut.writeln("Element with given name already exists in NEWPATH");
      return ExitCode.FAILURE;
    }
    // delete element being moved from its old parent
    if (oldParent != newParent) {
      oldParent.removeChildByName(from.getName());
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
