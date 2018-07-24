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
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import utilities.InvalidBooleanInputException;
import utilities.Parser;
import utilities.UserDecision;

/**
 * The cp command class that inherits from command
 *
 * @author anton, ursu
 */
public class CmdCp extends Command {

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "cp";
  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Moves around files and directories keeping the original copies",
          "cp OLDPATH NEWPATH")
          .additionalComment(
              "Paths of OLDPATH and NEWPATH can be relative or absolute.")
          .additionalComment("Element at OLDPATH must exist")
          .additionalComment("If cping any element to an existing dir keeps"
                                 + " the source element in its parent and moves"
                                 + " a copy to the dir.")
          .additionalComment(
              "If copying an element to a path that does not exist"
                  + " takes the last segment off the path and copies the "
                  + "element to the modified path if it exists and is a dir,"
                  + " and renames the element to the last segment"
                  + " of the original path")
          .additionalComment("If copying a dir to a file errors")
          .additionalComment("If at any point an element with the same name as"
                                 + " the element being copied exists in the"
                                 + " destination the user will be prompted if it"
                                 + " should be overwritten")
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
   * Constructs a new command instance
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdCp(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Executes the cp command with the given arguments.
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
    // rename flag for cp if the string is not empty a rename is required
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
    if (!isValidCopy(from, to, renaming)) {
      // if any of them fail then we need to exit with failure
      return ExitCode.FAILURE;
    }
    // exit code for mv determined by the helpers
    ExitCode mvExit;
    // now we have done all the preprocessing to make sure that the copy is valid
    // we are ready to actually copy the elements
    // if we are renaming we will call the appropriate helper
    if (renaming) {
      mvExit = copyElWithRename(from, (Directory) to, newName);
    } else if (to instanceof File) {
      mvExit = maybeOverwriteElement(from, to);
    } else {
      // check if the thing we are copying to already exist in the place we
      // are copying it to for example cp file somedir but somedir already
      // contains something named file
      Directory dest = (Directory) to;
      if (dest.containsChildElement(from.getName())) {
        // then we will prompt the user for an overwrite
        FSElement overwrite = dest.getChildByName(from.getName());
        mvExit = maybeOverwriteElement(from, overwrite);
      }
      // otherwise we are moving an fselement to an existing directory where no
      // element with the same name as from exists
      else {
        mvExit = copyElToDir(from, dest);
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
   * @return Exit code that cp should return success or failure
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
      // remove the to from its parent
      to.getParent().removeChildByName(to.getName());
      // copy the element we are overwriting with
      FSElement newFrom = from.copy();
      // add the new child in
      to.getParent().addChild(newFrom);
      // all done
      return ExitCode.SUCCESS;
    } else {
      // otherwise cancel
      errorConsole.writeln("Operation cancelled");
      return ExitCode.FAILURE;
    }
  }

  /**
   * Copies the given fselement to the given dir
   *
   * @param from The fselement to copy
   * @param to The dir to copy it to
   * @return Exit code that cp should return success or failure
   */
  private ExitCode copyElToDir(FSElement from, Directory to) {
    // copy the element being copied
    FSElement newFrom = from.copy();
    // add it to the destination
    to.addChild(newFrom);
    return ExitCode.SUCCESS;
  }

  /**
   * Copies the given fselement to the given dir and renames the source element
   * to  the new name given
   *
   * @param from The fselement to copy
   * @param to The dir to copy it to
   * @param newName The name the copied file should have
   * @return Exit code that cp should return success or failure
   */
  private ExitCode copyElWithRename(FSElement from, Directory to,
      String newName) {
    // copy the source file
    FSElement newFrom = from.copy();
    newFrom.setName(newName);
    // add this element to the new location
    // no overwrite prompt since this element cannot exist in to otherwise
    // we would find it by path
    to.addChild(newFrom);
    return ExitCode.SUCCESS;
  }

  /**
   * Validates the copy from given FSElement to given FSElement
   *
   * @param from The fselement to be copied
   * @param to The destination fselement
   * @param renaming Flag set true if renaming will occur false otherwise
   * @return True iff not copying dir to file, not copying element to its parent
   * (unless renaming), not copying to itself false otherwise
   */
  private boolean isValidCopy(FSElement from, FSElement to, boolean renaming) {
    // make sure we aren't copying directory to file
    if (from instanceof Directory && to instanceof File) {
      errorConsole.writeln("Cannot copy directory to file");
      return false;
    }
    // second make sure we dont copy an element to itself
    if (from == to) {
      errorConsole.writeln("Cannot copy element to itself");
      return false;
    }
    // third make sure we aren't moving an element to its parent
    // the only time this is permitted is if we are renaming
    if (!renaming && to == from.getParent()) {
      errorConsole.writeln("The element copied is already in the destination");
      return false;
    }
    return true;
  }

  /**
   * Tries to get the element we want to copy to if it fails the first time
   * remove the last segment of the path and try again
   *
   * @param toPath The path of the element cping to
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
        errorConsole.writeln("Cannot copy root directory");
        return null;
      }
      // make sure we aren't moving current working dir
      if (from == fileSystem.getWorkingDir()) {
        errorConsole.writeln("Cannot copy current working directory");
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
   * Helper function to check if the arguments passed are valid for this
   * command. Cp expects exactly 2 arguments
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
