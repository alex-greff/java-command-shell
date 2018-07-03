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
import filesystem.Directory;
import filesystem.File;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import java.util.ArrayList;
import utilities.Command;
import utilities.ExitCode;

/**
 * the ls command
 *
 * @author chedy
 */
public class CmdLs extends Command {

  /**
   * name of the command
   */
  private static final String NAME = "ls";
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Lists all of the files and directories in the current\n"
              + "working directory. Can take multiple "
              + "filenames/directory \n names as arguments",
          "ls")
          .usage("ls [Directory]")
          .usage("ls [File]")
          .usage("ls [Directory] [File]")
          .additionalComment(
              "If given a filename, ls will simply print back that "
                  + "name")
          .additionalComment(
              "ls separates multiple argument's content with an "
                  + "extra newline")
          .build();

  /**
   * Constructs a new command instance
   */
  public CmdLs() {
    super(NAME, DESCRIPTION);
  }


  /**
   * @param args The command Arguments.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return an Exitcode: 0 -> success, 1 -> failure
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
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
          result.append(addOn(curr));
        } catch (MalformedPathException | FileNotFoundException m) {
          // if name was not detected as directory, try searching for the file
          try {
            File file = curr.getFileByName(name);
            result.append(addFileName(file));
          } catch (FileNotFoundException e) {
            // only error out if the name was not found as either file or dir.
            errOut
                .writeln(
                    "Error: File \"" + name + "\" was not found");
          }//end catch for FileNotFound
        } // end catch for bad/no existing path
      }//end for loop for all params
    }//endif

    // if no parameters are given, perform command on current working dir
    else {
      result = new StringBuilder(addOn(curr));
    }

    if (result.length() > 0) {
      // trim final newline
      result.reverse().delete(0, 1).reverse();
      out.write(result.toString());
    }

    return ExitCode.SUCCESS;
  }

  /**
   * @param dir The directory whose contents will be represented by a string
   * @return The string representation of the directories contents
   */
  private String addOn(Directory dir) {
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
    return (result.toString() + "\n");
  }

  private String addFileName(File file) {
    String res = "";
    String name = file.getName();
    res = name + "\n\n";
    return res;
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
