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
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.NonPersistentFileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
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
        Directory currDir = fileSystem.getDirByPath(dirStrPath);

        // Initialize the set of paths of the occurrences of <expression>
        Set<String> outputPaths = new HashSet<>();

        // If we're looking for file occurrences
        if (type.equals(TYPE_FILE))
        // Search recursively for the file
        {
          outputPaths = findFileInDirectoryStructure(currDir, expr);
        }
        // If we're looking for directory occurrences
        else if (type.equals(TYPE_DIR))
        // Search recursively for the directory
        {
          outputPaths = findDirectoryInDirectoryStructure(currDir, expr);
        }

        // Print out the set as a string with each entry on a new line
        for (String outputPath : outputPaths) {
          output.append(outputPath).append("\n");
        }

      } catch (MalformedPathException e1) {
        errOut.writeln("Error: invalid path");
      } catch (FileNotFoundException e2) {
        errOut.writeln("Error: file/directory not found");
      }
    }
    // Print the output
    out.write(output.toString());

    return ExitCode.SUCCESS;
  }


  /**
   * Gets a set of all absolute paths to instances of files with the name "name"
   *
   * @param dir The current directory
   * @param name The wanted file name
   * @return Returns the set
   */
  private Set<String> findFileInDirectoryStructure(Directory dir, String name)
      throws FileNotFoundException {
    // Initialize return set
    Set<String> ret_set = new HashSet<>();

    // If the current directory contains the wanted file then add the absolute
    // path of the file to the return set
    if (dir.containsFile(name)) {
      // Get the absolute path of the directory
      String dirAbsPath = fileSystem.getAbsolutePathOfDir(dir);

      // If the directory is the root directory
      if (dirAbsPath.equals("/"))
      // Remove the extra "/" character
      {
        dirAbsPath = "";
      }

      // Add the file's path to the return set
      ret_set.add(dirAbsPath + "/" + name);
    }

    // Iterate through each child directory
    ArrayList<String> childDirNames = dir.listDirNames();
    for (String childDirName : childDirNames) {
      // Get the child directory object
      Directory childDir = dir.getDirByName(childDirName);

      // Call the function recursively again on the child directory and add any
      // instances of the file to the current return set
      ret_set.addAll(findFileInDirectoryStructure(childDir, name));
    }

    // Return the set
    return ret_set;
  }

  /**
   * Gets a set of all absolute paths to instances of directories with the name
   * "name"
   *
   * @param dir The current directory
   * @param name The wanted directory name
   * @return Returns the set
   */
  private Set<String> findDirectoryInDirectoryStructure(Directory dir,
      String name) throws FileNotFoundException {
    // Initialize return set
    Set<String> ret_set = new HashSet<>();

    // If the current directory contains the wanted directory then add the
    // absolute path of the file to the return set
    if (dir.containsDir(name)) {
      // Get the absolute path of the directory
      String dirAbsPath = fileSystem.getAbsolutePathOfDir(dir);

      // If the directory is the root directory
      if (dirAbsPath.equals("/"))
      // Remove the extra "/" character
      {
        dirAbsPath = "";
      }

      // Add the file's path to the return set
      ret_set.add(dirAbsPath + "/" + name + "/");
    }

    // Iterate through each child directory
    ArrayList<String> childDirNames = dir.listDirNames();
    for (String childDirName : childDirNames) {
      // Get the child directory object
      Directory childDir = dir.getDirByName(childDirName);

      // Call the function recursively again on the child directory and add any
      // instances of the directory to the current return set
      ret_set.addAll(findDirectoryInDirectoryStructure(childDir, name));
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
        && args.getCommandParameters().length > 0
        && args.getNumberOfNamedCommandParameters() == 2
        && args.getNamedCommandParameter(TYPE_IDENTIFIER) != null
        && args.getNamedCommandParameter(NAME_IDENTIFIER) != null
        && (args.getNamedCommandParameter(TYPE_IDENTIFIER).equals(TYPE_FILE)
            || args.getNamedCommandParameter(TYPE_IDENTIFIER).equals(TYPE_DIR))
        && args.getRedirectOperator().length() == 0
        && args.getTargetDestination().length() == 0;
  }
}
