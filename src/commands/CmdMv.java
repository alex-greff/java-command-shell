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
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Console;
import io.Readable;
import io.Writable;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.InvalidBooleanInputException;
import utilities.Parser;
import utilities.UserDecision;

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
          "Move elements of the filesystem.",
          "mv OLDPATH NEWPATH")
          .additionalComment(
              "Paths of OLDPATH and NEWPATH can be relative or absolute.")
          .additionalComment("Element at OLDPATH must exist")
          .additionalComment("If moving any element to an existing dir deletes"
                                 + " the source element from its parent and moves"
                                 + " it to the dir.")
          .additionalComment(
              "If moving an element to a path that does not exist"
                  + " takes the last segment off the path and deletes "
                  + "and moves the element to the modified path if it exists "
                  + "and is a dir, and renames the"
                  + " element to the last segment of the original path")
          .additionalComment("If moving a dir to a file errors")
          .additionalComment("If at any point an element with the same name as"
                                 + " the element being moved exists in the"
                                 + " destination the user will be prompted if"
                                 + " it should be overwritten")
          .build();
  /**
   * Storage of the standard console.
   */
  private Console<String> console;
  /**
   * Storage of the query console.
   */
  private Console<String> queryConsole;
  /**
   * Storage of the error console.
   */
  private Console<String> errorConsole;

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
   * Executes the mv command with the given arguments.
   *
   * @param args The command arguments container
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
    // save the consoles
    this.console = console;
    this.queryConsole = queryConsole;
    this.errorConsole = errorConsole;
    Path fromPath, toPath;
    FSElement from, to;
    // rename flag for mv if the string is not empty a rename is required
    String newName;
    try {
      // get the paths given in the arguments
      fromPath = new Path(args.getCommandParameters()[0]);
      toPath = new Path(args.getCommandParameters()[1]);
    } catch (MalformedPathException e) {
      errorConsole.writeln("Invalid path(s) given");
      return ExitCode.FAILURE;
    }
    // try to get the from element
    if ((from = tryGetFromEl(fromPath)) == null) {
      // if from is null then the helper errored and printed a message
      // time to return failure exit code
      return ExitCode.FAILURE;
    }
    // we now have the from directory and so far are good to go
    // we will now try to get the destination element
    ArrayList<Object> toPair = tryGetToEl(toPath);
    if ((to = (FSElement) toPair.get(0)) == null) {
      // if the fselement is null then there was an error getting it and the
      // error has already been printed we just exit with failure
      return ExitCode.FAILURE;
    }
    newName = (String) toPair.get(1);
    // set the rename flag depending on the value of the string
    boolean renaming = !newName.isEmpty();
    // we have now taken care of the to path and the from path
    // we now need to do some validity checks
    if (!isValidMove(from, to, renaming)) {
      // if any of them fail then we need to exit with failure
      return ExitCode.FAILURE;
    }
    // exit code for mv determined by the helpers
    ExitCode mvExit;
    // now we have done all the preprocessing to make sure that the move is valid
    // we are ready to actually move the elements
    // if we are renaming we will call the appropriate helper
    if (renaming) {
      mvExit = moveElWithRename(from, (Directory) to, newName);
    } else if (to instanceof File) {
      mvExit = maybeOverwriteElement(from, to);
    } else {
      // check if the thing we are moving to already exist in the place we
      // are moving it to for example mv file somedir but somedir already contains
      // something named file
      Directory dest = (Directory) to;
      if (dest.containsChildElement(from.getName())) {
        // then we will prompt the user for an overwrite
        FSElement overwrite = dest.getChildByName(from.getName());
        mvExit = maybeOverwriteElement(from, overwrite);
      }
      // otherwise we are moving an fselement to an existing directory where no
      // element with the same name as from exists
      else {
        mvExit = moveElToDir(from, dest);
      }
    }
    return mvExit;
  }

  /**
   * Prompts the user if they wish to overwrite the file `to` with the file
   * `from`
   *
   * @param from File to overwrite with
   * @param to File to overwrite
   * @return Exit code that mv should return success or failure
   */
  private ExitCode maybeOverwriteElement(FSElement from, FSElement to) {
    // prompt the user if the element should be overwritten
    queryConsole.writeln("Overwrite element at path "
                           + fileSystem.getAbsolutePathOfFSElement(to)
                           + " [y/n]?");
    String answer = queryConsole.read().trim();
    UserDecision overwrite;
    // get the users decision
    try {
      overwrite = Parser.parseBooleanDecisionInput(answer, false);
    } catch (InvalidBooleanInputException e) {
      errorConsole.writeln("Operation cancelled");
      return ExitCode.FAILURE;
    }
    if (overwrite == UserDecision.YES) {
      // remove the from from its parent
      from.getParent().removeChildByName(from.getName());
      // remove the to from its parent
      to.getParent().removeChildByName(to.getName());
      // add the new child in
      to.getParent().addChild(from);
      // all done
      return ExitCode.SUCCESS;
    } else {
      // otherwise cancel
      errorConsole.writeln("Operation cancelled");
      return ExitCode.FAILURE;
    }
  }

  /**
   * Moves the given fselement to the given dir
   *
   * @param from The fselement to move
   * @param to The dir to move it to
   * @return Exit code that mv should return success or failure
   */
  private ExitCode moveElToDir(FSElement from, Directory to) {
    // remove the element from its parent
    from.getParent().removeChildByName(from.getName());
    // add it to the destination
    to.addChild(from);
    return ExitCode.SUCCESS;
  }

  /**
   * Moves the given fselement to the given dir and renames the source element
   * to  the new name given
   *
   * @param from The fselement to move
   * @param to The dir to move it to
   * @param newName The name the moved file should have
   * @return Exit code that mv should return success or failure
   */
  private ExitCode moveElWithRename(FSElement from, Directory to,
      String newName) {
    // rename the source file
    from.rename(newName);
    // if we are moving the file to the same dir its in already we are
    // done i.e. mv file newnameforfile
    if (from.getParent() == to) {
      return ExitCode.SUCCESS;
    }
    // otherwise we need to remove from from its parent and move
    // to the to directory
    // tell froms parent to get rid of the dir with the new name (we renamed)
    from.getParent().removeChildByName(newName);
    // add this element to the new location
    // no overwrite prompt since this element cannot exist in to otherwise
    // we would find it by path
    to.addChild(from);
    return ExitCode.SUCCESS;
  }

  /**
   * Validates the move from given FSElement to given FSElement
   *
   * @param from The fselement to be moved
   * @param to The destination fselement
   * @param renaming Flag set true if renaming will occur false otherwise
   * @return True iff not moving dir to file not moving parent to child not
   * moving element to its parent (unless renaming) false otherwise
   */
  private boolean isValidMove(FSElement from, FSElement to, boolean renaming) {
    // make sure we aren't moving directory to file
    if (from instanceof Directory && to instanceof File) {
      errorConsole.writeln("Cannot move directory to file");
      return false;
    }
    // second make sure we dont move an element to itself or its child
    String absFrom = fileSystem.getAbsolutePathOfFSElement(from);
    String absTo = fileSystem.getAbsolutePathOfFSElement(to);
    if (absTo.startsWith(absFrom)) {
      errorConsole.writeln("Cannot move element to itself or to its child");
      return false;
    }
    // third make sure we aren't moving an element to its parent
    // the only time this is permitted is if we are renaming
    if (!renaming && to == from.getParent()) {
      errorConsole.writeln("The element being moved is already in the destination");
      return false;
    }
    return true;
  }

  /**
   * Helper to get the from FSElement returns null on error
   *
   * @param fromPath The path of the from FSElement we are trying to get
   * @return The FSElement at this path if it exists or null if it does not
   * exist, is root or is the current working directory
   */
  private FSElement tryGetFromEl(Path fromPath) {
    FSElement from;
    try {
      // make sure that the element we are moving from exists
      from = fileSystem.getFSElementByPath(fromPath);
      // make sure we aren't moving root
      if (from == fileSystem.getRoot()) {
        errorConsole.writeln("Cannot move root directory");
        return null;
      }
      // make sure we aren't moving current working dir
      if (from == fileSystem.getWorkingDir()) {
        errorConsole.writeln("Cannot move current working directory");
        return null;
      }
    } catch (MalformedPathException e) {
      errorConsole.writeln("Invalid path(s) given");
      return null;
    } catch (FSElementNotFoundException e) {
      errorConsole.writeln("You can only move an existing element");
      return null;
    }
    return from;
  }

  /**
   * Tries to get the element we want to move to if it fails the first time
   * remove the last segment of the path and try again
   *
   * @param toPath The path of the element moving to
   * @return A pair fselement to String where element is the place we will be
   * moving to and the string is the new name of the element if a rename is
   * wanted by the user
   */
  private ArrayList<Object> tryGetToEl(Path toPath) {
    FSElement to;
    String newName = "";
    try {
      to = fileSystem.getFSElementByPath(toPath);
    } catch (MalformedPathException e) {
      errorConsole.writeln("Invalid destination path given");
      to = null;
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
      } catch (MalformedPathException | FSElementNotFoundException e1) {
        errorConsole.writeln("Destination path does not exist");
        to = null;
      }
    }
    ArrayList<Object> pair = new ArrayList<>();
    pair.add(to);
    pair.add(newName);
    return pair;
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
