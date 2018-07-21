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
import io.Writable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The find command.
 *
 * @author greff
 */
public class CmdFind extends Command {

  /**
   * The name of the command.
   */
  private static final String NAME = "find";

  // Setup command information
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Finds and lists all found files/directories of a given expression.",
          "find PATH... -type [f|d] -name EXPRESSION")
              .additionalComment("Nothing is printed if no files are found")
              .additionalComment(
                  "If an invalid path is found, it will print an error and "
                  + "continue running.")
              .build();
  /**
   * The identifier for the type flag.
   */
  private final String TYPE_IDENTIFIER = "type";
  /**
   * The identifier for the name flag.
   */
  private final String NAME_IDENTIFIER = "name";

  // Setup flag constants
  /**
   * The file type character option.
   */
  private final String TYPE_FILE = "f";
  /**
   * The directory type character option.
   */
  private final String TYPE_DIR = "d";

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdFind(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Executes the find command which finds all files/directories in the given
   * locations.
   *
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Writable<String> out,
      Writable<String> errOut) {
    // Store the values of the named parameters
    String type = args.getNamedCommandParameter(TYPE_IDENTIFIER);
    String expr =
        args.getNamedCommandParameter(NAME_IDENTIFIER).replaceAll("\"", "");

    // Initialize a new string builder
    StringBuilder output = new StringBuilder();

    // Get all the directory paths to be explored
    String[] dirStrPaths = args.getCommandParameters();

    // Iterate through each of the directory paths
    for (String dirStrPath : dirStrPaths) { // ur mom gay
      try {
        // Get the current directory
        Directory currDir = fileSystem.getDirByPath(new Path(dirStrPath));

        dirStrPath = (dirStrPath.equals("/")) ? "" : dirStrPath;

        // Initialize the set of paths of the occurrences of <expression>
        TreeSet<String> outputPaths = findFSElementInDirectoryStructure(currDir,
            dirStrPath, expr, errOut, type);

        // Print out the set as a string with each entry on a new line
        for (String outputPath : outputPaths) {
          output.append(outputPath).append("\n");
        }

      } catch (MalformedPathException e1) {
        errOut.writeln("Error: invalid path");
      } catch (FSElementNotFoundException e2) {
        errOut.writeln("Error: file/directory not found");
      }
    }

    // Print the output
    out.write(output.toString());

    return ExitCode.SUCCESS;
  }

  /**
   * Gets a set of all absolute paths to instances of files or directories with
   * the name "name"
   *
   * @param dir The current directory
   * @param fseStrPath The path of the current fsElement
   * @param name The wanted file name
   * @param errOut The error console
   * @param TYPE the type of the search (either "d" for directory or "f" for
   *        file)
   * @return Returns the set
   */
  private TreeSet<String> findFSElementInDirectoryStructure(Directory dir,
      String fseStrPath, String name, Writable errOut, final String TYPE) {

    // Initialize return set
    TreeSet<String> ret_set = new TreeSet<>();

    FSElement fse = dir.getChildByName(name);

    if ((TYPE.equals(TYPE_FILE) && fse instanceof File)
        || (TYPE.equals(TYPE_DIR) && fse instanceof Directory)) {

      // Get the absolute path of the directory
      String fseAbsPath = fileSystem.getAbsolutePathOfFSElement(dir);

      // If the directory is the root directory
      // Then remove the extra "/" character
      if (fseAbsPath.equals("/"))
        fseAbsPath = "";

      // Add the file's path to the return set
      ret_set.add(fseAbsPath + "/" + name);
    }

    // Iterate through each child directory
    ArrayList<String> childFseNames = dir.listDirNames();
    for (String childFseName : childFseNames) {
      String childDirStrPath = fseStrPath + "/" + childFseName;

      FSElement child_fse = dir.getChildByName(childFseName);
      if (child_fse instanceof Directory) {
        // Call the function recursively again on the child directory and add
        // any instances of the file to the current return set
        ret_set.addAll(findFSElementInDirectoryStructure((Directory) child_fse,
            childDirStrPath, name, errOut, TYPE));
      }

      if (child_fse == null) {
        errOut.writeln("Error: child directory not found: " + childDirStrPath);
      }
    }

    // Return the set
    return ret_set;
  }

  /**
   * Checks if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() > 0
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 2
        && args.getNamedCommandParameter(TYPE_IDENTIFIER) != null
        && args.getNamedCommandParameter(NAME_IDENTIFIER) != null
        && (args.getNamedCommandParameter(TYPE_IDENTIFIER).equals(TYPE_FILE)
            || args.getNamedCommandParameter(TYPE_IDENTIFIER).equals(TYPE_DIR))
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR));

    // Check that the parameters are not strings
    boolean stringParamsMatches = true;
    for (String p : args.getCommandParameters()) {
      stringParamsMatches = stringParamsMatches && !isStringParam(p);
    }

    // Check that the named parameters match
    boolean namedStringParamsMatches = true;
    namedStringParamsMatches = namedStringParamsMatches
        && isStringParam(args.getNamedCommandParameter(NAME_IDENTIFIER));
    namedStringParamsMatches = namedStringParamsMatches
        && !isStringParam(args.getNamedCommandParameter(TYPE_IDENTIFIER));

    // Return the result
    return paramsMatches && stringParamsMatches && namedStringParamsMatches;
  }
}
