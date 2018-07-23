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
import io.Readable;
import containers.CommandDescription;
import filesystem.Directory;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import static utilities.JShellConstants.APPEND_OPERATOR;
import static utilities.JShellConstants.OVERWRITE_OPERATOR;
import static utilities.JShellConstants.RECURSIVE_FLAG;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * the ls command
 *
 * @author chedy
 */
public class CmdLs extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdLs(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * name of the command
   */
  private static final String NAME = "ls";

  /**
   * the documentation of the command
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Lists all of the files and directories in "
          + "the current working directory.",
          "ls [-R]")
              .description(
                  "Can take multiple filenames/directory names as arguments.")
              .usage("ls [-R] [Directory]...").usage("ls [-R] [File]...")
              .usage("ls [-R] [Directory or File]...")
              .additionalComment(
                  "If given a filename, ls will simply print back that "
                      + "name")
              .additionalComment(
                  "ls separates multiple argument's content with an "
                      + "extra newline")
              .build();

  /**
   * @param args The command Arguments.
   * @param out The writable for any normal output of the command.
   * @param in The standard input.
   * @param errOut The writable for any error output of the command.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Writable<String> out, Readable in,
      Writable<String> errOut) {
    StringBuilder result = new StringBuilder();
    Directory curr = fileSystem.getWorkingDir();
    Path path;
    boolean rec = false;

    if (args.getNumberOfCommandFieldParameters() > 0)
      rec = args.getCommandFlags()[0].equals("R");
    // check parameters

    String[] params = args.getCommandParameters();
    if (params.length > 0) {
      for (String name : params) {
        try {
          curr = fileSystem.getDirByPath(new Path(name));
          result.append(curr.getName()).append(":\n")
              .append(addOn(curr, rec, 0));
        } catch (MalformedPathException | FSElementNotFoundException m) {
          // if name was not detected as directory, try searching for the file
          try {
            File file = fileSystem.getFileByPath(new Path(name));
            result.append(addFileName(file));
          } catch (FSElementNotFoundException | MalformedPathException e) {
            // only error out if the name was not found as either file or dir.
            errOut.writeln("Error: File \"" + name + "\" was not found");
          } // end catch for FileNotFound
        } // end catch for bad/no existing path
      } // end for loop for all params
    } // endif

    // if no parameters are given, perform command on current working dir
    else {
      result = new StringBuilder(addOn(curr, rec, 0));
    }

    // Initialize the result string
    String resultStr = "";

    if (result.length() > 1) {
      // trim all final newlines
      trimNewlinesLeaveOne(result);
      resultStr = result.toString();
    }

    // Write all the contents read to the Console and return SUCCESS always
    out.write(resultStr);
    return ExitCode.SUCCESS;
  }

  /**
   * @param dir The directory whose contents will be represented by a string
   * @param tabNum the amount of tabs needed for spacing in recursive calls
   * @return The string representation of the directories contents
   */
  private String addOn(Directory dir, boolean recursive, int tabNum) {
    StringBuilder result = new StringBuilder();
    // get lists of files and dirs of the current working path
    ArrayList<String> dirs = dir.listDirNames();
    ArrayList<String> files = dir.listFileNames();
    // initialize tabspaces
    StringBuilder tabs = new StringBuilder();
    for (int i = 0; i < tabNum; i++) {
      tabs.append("\t");
    }
    // now append the each string from the arraylists with a newline to result
    for (String name : dirs) {
      if (recursive) {
        Directory d = dir.getChildDirectoryByName(name);
        if (d == null)
          return "";
        result.append(tabs).append(name).append(":\n");
        result.append(addOn(d, true, tabNum + 1));
      } else {
        result.append(name);
        result.append("\n");
      }
    }
    for (String name : files) {
      if (recursive)
        result.append(tabs);
      result.append(name).append("\n");
    }
    return (result.toString() + "\n");
  }

  /**
   *
   * @param file that will get printed by ls
   * @return the string of the filename followed by newlines
   */
  private String addFileName(File file) {
    String res;
    String name = file.getName();
    res = name + "\n\n";
    return res;
  }

  /**
   * @param in the stringbuilder input
   */
  private void trimNewlinesLeaveOne(StringBuilder in) {
    in.reverse();
    while (in.substring(0, 1).equals("\n")) {
      in = in.delete(0, 1);
    }
    // unreverse it
    in.reverse().append("\n");
    // return in;
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() >= 0
        && ((args.getNumberOfCommandFieldParameters() == 1
            && args.getCommandFlags()[0].equals(RECURSIVE_FLAG))
            || args.getNumberOfCommandFieldParameters() == 0)
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
