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
import static utilities.JShellConstants.RECURSIVE_FLAG;
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
          "grep REGEX PATH").usage("grep -R REGEX PATH")
              .additionalComment("The given PATH can be relative or absolute.")
              .additionalComment(
                  "Regular Usage: PATH is a file, any lines in the file that"
                      + " contain the regex are printed.")
              .additionalComment(
                  "-R: PATH is a directory, any lines in any file in the directory,"
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
  protected ExitCode run(CommandArgs args, Writable<String> out,
      Writable<String> errOut) {
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
      errOut.writeln("Error: Invalid file path");
      return ExitCode.FAILURE; // Stop the function here
    }

    if (cmdFlags.length == 0) {
      // If there is no recursive flag
      try { // Try to obtain a file with the given path
        File src = fileSystem.getFileByPath(srcPath);
        // matches is the result of the helper function on the file
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        // Error message if file not found at the given path
        errOut.writeln("Error: File does not exist");
        return ExitCode.FAILURE; // Stop the function here
      }

    } else if (cmdFlags.length == 1 && cmdFlags[0].equals(RECURSIVE_FLAG)) {
      // If there is a recursive flag
      try { // Try to obtain a directory with the given path
        Directory src = fileSystem.getDirByPath(srcPath);
        // matches is the result of the helper function on the directory
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        // Error message if directory not found at the given path
        errOut.writeln("Error: Directory does not exist");
        return ExitCode.FAILURE; // Stop the function here
      }
    }

    // Print Strings from the matches ArrayList to standard output
    for (String match : matches) {
      out.writeln(match);
    }

    // If this line is reached, nothing went wrong
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
    // Create an empty ArrayList of Strings to hold matching lines
    ArrayList<String> matches = new ArrayList<>();
    // Split the contents of the given file by every newline character into a
    // String array
    String[] fileLines = src.read().split("\n");

    // For every String (line) in our String array (fileLines), if it matches
    // the given regex, add the line to the matches String ArrayList
    for (String line : fileLines) {
      if (line.matches(regex)) {
        matches.add(line);
      }
    }

    // Return the String ArrayList of matching lines
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
    // Create an empty ArrayList of Strings to hold matching lines
    ArrayList<String> matches = new ArrayList<>();
    // Get the String ArrayLists for the names of all files and directories
    // contained in the given directory
    ArrayList<String> containedFiles = src.listFileNames();
    ArrayList<String> containedDirs = src.listDirNames();

    // Iterate through the names of all contained files
    for (String fileName : containedFiles) {
      // Obtain an instance of a file by its name
      File fileSrc = src.getChildFileByName(fileName);
      // Obtain all matching lines from the file
      ArrayList<String> fileMatches = executeHelper(fileSrc, regex);

      // Prefix each matching line from the file with its path before adding
      // it to our String ArrayList of all matches
      for (String match : fileMatches) {
        matches
            .add(fileSystem.getAbsolutePathOfFSElement(fileSrc) + ": " + match);
      }
    }

    // Iterate through the names of all contained directories
    for (String dirName : containedDirs) {
      // Obtain an instance of a directory by its name
      Directory dirSrc = src.getChildDirectoryByName(dirName);
      // Obtain all matching lines from the directory
      ArrayList<String> inDirMatches = executeHelper(dirSrc, regex);
      // Add all the matching lines from the directory to our String ArrayList
      // of all matches
      matches.addAll(inDirMatches);
    }

    // Return the String ArrayList of matching lines
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
