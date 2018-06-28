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
import filesystem.Directory;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import java.util.ArrayList;
import utilities.Command;

public class CmdLs extends Command {

  private final String NAME = "ls";

  @Override
  public String execute(CommandArgs args) {
    StringBuilder result = new StringBuilder();
    Directory curr = FileSystem.getInstance().getWorkingDir();
    Path path;
    // check parameters

    String[] params = args.getCommandParameters();
    if (params.length > 0) {
      for (String name : params) {
        try {
          path = new Path(name);
          curr = fileSystem.getDirByPath(path);
        } catch (MalformedPathException | FileNotFoundException m) {
          result.append("Error: Path ").append(name)
              .append(" was not found.\n");
        } // end try-catch for absolute pathing errors
        result.append(addon(curr));
      }
    }
    // if no parameters are given, perform command on current working dir
    else {
      result = new StringBuilder(addon(curr));
    }
    return result.toString();
  }

  private String addon(Directory dir) {
    StringBuilder result = new StringBuilder();
    // get lists of files and dirs of the current working path
    ArrayList<String> dirs = dir.listDirNames();
    ArrayList<String> files = dir.listFiles();
    // now append the each string from the arraylists with a newline to result
    for (String name : dirs) {
      result.append(name).append("\n");
    }
    for (String name : files) {
      result.append(name).append("\n");
    }
    return (result.toString());
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getNumberOfNamedCommandParameters() == 0 && args
        .getRedirectOperator().equals("") && args.getTargetDestination()
        .equals("");
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "This command lists all of the directories and files in"
        + "the current working directory. No arguments are needed for this "
        + "command.\n";
  }

}
