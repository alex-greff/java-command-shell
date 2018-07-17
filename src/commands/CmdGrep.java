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

  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    String[] cmdFlags = args.getCommandFlags();
    String[] cmdParams = args.getCommandParameters();
    Path srcPath;
    ArrayList<String> matches = new ArrayList<>();

    try {
      srcPath = new Path(cmdParams[1]);
    } catch (MalformedPathException e) {
      errOut.write("Invalid path given");
      return ExitCode.FAILURE;
    }

    if (cmdFlags.length == 0) {
      try {
        File src = fileSystem.getFileByPath(srcPath);
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        errOut.write("File not found");
      }

    } else if (cmdFlags.length == 1 && cmdFlags[0].equals("R")) {
      try {
        Directory src = fileSystem.getDirByPath(srcPath);
        matches = executeHelper(src, cmdParams[0]);
      } catch (MalformedPathException | FSElementNotFoundException e) {
        errOut.write("Directory not found");
      }
    }

    if (!matches.isEmpty()) {
      for (String match : matches) {
        out.writeln(match);
      }
    }

    return ExitCode.SUCCESS;
  }

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

  private ArrayList<String> executeHelper(Directory src, String regex) {
    ArrayList<String> matches = new ArrayList<>();
    ArrayList<String> containedFiles = src.listFileNames();
    ArrayList<String> containedDirs = src.listDirNames();

    for (String fileName : containedFiles) {

      try {
        File fileSrc = src.getChildFileByName(fileName);
        ArrayList<String> fileMatches = executeHelper(fileSrc, regex);

        for (String match : fileMatches) {
          matches.add(fileSystem.getAbsolutePathOfFSElement(fileSrc) + ": " + match);
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
    // Make sure the NAME matches, exactly 2 arguments, and at most 1 optional
    // parameter
    return args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 2
        && args.getNumberOfCommandFieldParameters() <= 1
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR));
  }
}
