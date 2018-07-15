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
import filesystem.FSElement;
import filesystem.FSElementNotFoundException;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import static utilities.JShellConstants.APPEND_OPERATOR;
import static utilities.JShellConstants.OVERWRITE_OPERATOR;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdFind(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  // Setup command information
  /**
   * The name of the command.
   */
  private static final String NAME = "find";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Finds and lists all found files/directories of a given expression.",
          "find PATH... -type [f|d] -name EXPRESSION")
              .additionalComment("Nothing is printed if no files are found")
              .build();
  /**
   * The identifier for the type flag.
   */
  private final String TYPE_IDENTIFIER = "type";

  // Setup flag constants
  /**
   * The identifier for the name flag.
   */
  private final String NAME_IDENTIFIER = "name";
  /**
   * The file type character option.
   */
  private final String TYPE_FILE = "f";
  /**
   * The directory type character option.
   */
  private final String TYPE_DIR = "d";

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
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Store the values of the named parameters
    String type = args.getNamedCommandParameter(TYPE_IDENTIFIER);
    String expr = args.getNamedCommandParameter(NAME_IDENTIFIER);

    // Initialize a new string builder
    StringBuilder output = new StringBuilder();

    // Get all the directory paths to be explored
    String[] dirStrPaths = args.getCommandParameters();

    // Iterate through each of the directory paths
    for (String dirStrPath : dirStrPaths) {
      try {
        // Get the current directory
        Directory currDir = fileSystem.getDirByPath(new Path(dirStrPath));

        // Initialize the set of paths of the occurrences of <expression>
        Set<String> outputPaths = findFSElementInDirectoryStructure(currDir,
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

    // If a redirect is given then attempt to write to file and return exit code
    if (!args.getRedirectOperator().isEmpty())
      return writeToFile(output.toString(), args.getRedirectOperator(),
          args.getTargetDestination(), errOut);

    // If no redirect operator is given then...
    // Print the output
    out.write(output.toString());

    return ExitCode.SUCCESS;
  }

  /**
   * Gets a set of all absolute paths to instances of files or directories with
   * the name "name"
   *
   * @param dir The current directory
   * @param dirStrPath The path of the current directory
   * @param name The wanted file name
   * @errOut The error console
   * @TYPE the type of the search (either "d" for directory or "f" for file)
   * @return Returns the set
   */
  private Set<String> findFSElementInDirectoryStructure(Directory dir,
      String fseStrPath, String name, Writable errOut, final String TYPE) {

    // Initialize return set
    Set<String> ret_set = new HashSet<>();

    try {
      FSElement fse = dir.getChildByName(name);

      if ((TYPE.equals(TYPE_FILE) && fse instanceof File)
          || (TYPE.equals(TYPE_DIR) && fse instanceof Directory)) {

        // Get the absolute path of the directory
        String fseAbsPath = fileSystem.getAbsolutePathOfDir(dir);

        // If the directory is the root directory
        if (fseAbsPath.equals("/"))
          // Remove the extra "/" character
          fseAbsPath = "";

        // Add the file's path to the return set
        ret_set.add(fseAbsPath + "/" + name);
      }
      // If no File or Directory (depending on what was wanted) was found
      else {
        // errOut.writeln("Error: file not found: " + dirStrPath + "/" + name);
      }

      // No FS element was found
    } catch (FSElementNotFoundException e) {
      // errOut.writeln("Error: file not found: " + dirStrPath + "/" + name);
    }

    // Iterate through each child directory
    ArrayList<String> childFseNames = dir.listDirNames();
    for (String childFseName : childFseNames) {
      String childDirStrPath = fseStrPath + "/" + childFseName;

      try {
        FSElement child_fse = dir.getChildByName(childFseName);
        if (child_fse instanceof Directory) {
          // Call the function recursively again on the child directory and add
          // any instances of the file to the current return set
          ret_set.addAll(findFSElementInDirectoryStructure(
              (Directory) child_fse, childDirStrPath, name, errOut, TYPE));
        }
      } catch (FSElementNotFoundException e) {
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
    return args.getCommandName().equals(NAME)
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
  }
}
