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
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The grep command class that inherits from command
 *
 * @author ursu
 */
public class CmdGrep extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdGrep(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Constant instance variable for the command name
   */
  private static final String NAME = "grep";

  /**
   * Container built for the command's description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Prints lines in a file containing the given regex.",
          "grep REGEX PATH").usage("grep -r REGEX PATH")
          .additionalComment("The given PATH can be relative or absolute.")
          .additionalComment(
              "Regular Usage: PATH is a file, any lines in the file that"
                  + " contain the regex are printed.")
          .additionalComment(
              "-r: PATH is a directory, any lines in any file in the directory,"
                  + " and any subdirectories, that contain the regex are"
                  + " printed.")
          .build();

  /**
   * Executes the grep command with the given arguments. Grep prints all lines
   * of a file that match a given regex. Grep also has the option to recursively
   * traverse through a directory and all its subdirectories to find matches
   * within multiple files. The path to every file will be printed alongside
   * each matching line found. Error messages if the file or directory path is
   * invalid, or the file or directory does not exist.
   *
   * @param args The command arguments container
   * @param out Writable for Standard Output
   * @param errOut Writable for Error Output
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Obtain the String arrays for the Command Flags and Parameters
    String[] cmdFlags = args.getCommandFlags();
    String[] cmdParams = args.getCommandParameters();
    // Create a null path object for the source
    Path srcPath;
    // Create an empty ArrayList of Strings to hold matching lines
    ArrayList<String> matches = new ArrayList<>();

    try { // Try to create a path object from the second parameter
      srcPath = new Path(cmdParams[1]);
    } catch (MalformedPathException e) { // Error if the path parses incorrectly
      errOut.write("Invalid path given");
      return ExitCode.FAILURE;
    }

    if (cmdFlags.length == 0) {
      try {
        File src = fileSystem.getFileByPath(srcPath);
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        errOut.writeln("File not found");
      }

    } else if (cmdFlags.length == 1 && cmdFlags[0].equals("R")) {
      try {
        Directory src = fileSystem.getDirByPath(srcPath);
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        errOut.writeln("Directory not found");
      }

    } else {
      errOut.writeln("Invalid command syntax");
    }

    if (!matches.isEmpty()) {
      for (String match : matches) {
        out.writeln(match);
      }
    }

    return ExitCode.SUCCESS;
  }

  /**
   * Helper function for grep's execute. Given a file and a regex, executeHelper
   * finds all lines in a file that match the regex.
   *
   * @param src The source file
   * @param regex The regex to compare against
   * @return String ArrayList of all matching lines
   */
  private ArrayList<String> executeHelper(File src, String regex) {
    ArrayList<String> matches = new ArrayList<>();
    String[] fileLines = src.read().split("\n");

    for (String line : fileLines) {
      if (line.matches(regex)) {
        matches.add(line);
      }
    }

    return matches;
  }

  /**
   * Helper function for grep's execute. Given a directory and a regex,
   * executeHelper calls itself on every file found in the directory, as well as
   * every subdirectory. Matches found in files have their path added before the
   * matching line.
   *
   * @param src The source directory
   * @param regex The regex to compare against
   * @return String ArrayList of paths to each file and their matching line(s)
   */
  private ArrayList<String> executeHelper(Directory src, String regex) {
    ArrayList<String> matches = new ArrayList<>();
    ArrayList<String> containedFiles = src.listFileNames();
    ArrayList<String> containedDirs = src.listDirNames();

    for (String fileName : containedFiles) {

      try {
        File fileSrc = src.getChildFileByName(fileName);
        ArrayList<String> fileMatches = executeHelper(fileSrc, regex);

        for (String match : fileMatches) {
          matches.add(
              fileSystem.getAbsolutePathOfFSElement(fileSrc) + ": " + match);
        }

      } catch (FSElementNotFoundException e) {
      }

    }

    for (String dirName : containedDirs) {

      try {
        Directory dirSrc = src.getChildDirectoryByName(dirName);
        ArrayList<String> inDirMatches = executeHelper(dirSrc, regex);
        matches.addAll(inDirMatches);

      } catch (FSElementNotFoundException e) {
      }

    }

    return matches;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Grep expects exactly 2 arguments, and at most 1 optional
   * parameter.
   *
   * @param args The command arguments container
   * @return Returns true iff the arguments are valid, false otherwise
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 2
        && args.getNumberOfCommandFieldParameters() <= 1
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR));

    int i = 1;
    // Check that the parameters are not strings
    boolean stringParamsMatches = true;
    for (String p : args.getCommandParameters()) {
      if (i == 1) {
        stringParamsMatches = stringParamsMatches && isStringParam(p);
      } else {
        stringParamsMatches = stringParamsMatches && !isStringParam(p);
      }
      i++;
    }

    // Return the result
    return paramsMatches && stringParamsMatches;
  }
}
