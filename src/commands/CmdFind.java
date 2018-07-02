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
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import utilities.Command;
import utilities.ExitCode;

/**
 * The find command.
 * 
 * @author ajg
 *
 */
public class CmdFind extends Command {
  // Setup command information
  private final String NAME = "find";
  private CommandDescription DESCRIPTION = new CommandDescription(
      "Finds and lists all found files/directories of a given expression.",
      new String[] {"find PATH... -type [f|d] -name EXPRESSION"},
      new String[] {"Nothing is printed if no files are found"});

  // Setup flag constants
  private final String TYPE_IDENTIFIER = "type";
  private final String NAME_IDENTIFIER = "name";
  private final String TYPE_FILE = "f";
  private final String TYPE_DIR = "d";

  /**
   * Executes the find command which finds all files/directories in the given
   * locations.
   * 
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the exit status of the command.
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Store the values of the named parameters
    String type = args.getNamedCommandParameter(TYPE_IDENTIFIER);
    String expression = args.getNamedCommandParameter(NAME_IDENTIFIER);

    // Initialize a new string builder
    StringBuilder output = new StringBuilder();

    // Get all the directory paths to be explored
    String[] dirStrPaths = args.getCommandParameters();

    // Iterate through each of the directory paths
    for (String dirStrPath : dirStrPaths) {
      try {
        // Get the current directory
        Path dirPath = new Path(dirStrPath);
        Directory currDir = fileSystem.getDirByPath(dirPath);

        // Initialize the set of paths of the occurrences of <expression>
        Set<String> outputPaths = new HashSet<>();

        // If we're looking for file occurrences
        if (type.equals(TYPE_FILE))
          // Search recursively for the file
          outputPaths = findFileInDirectoryStructure(currDir, expression);
        // If we're looking for directory occurrences
        else if (type.equals(TYPE_DIR))
          // Search recursively for the directory
          outputPaths = findDirectoryInDirectoryStructure(currDir, expression);

        // Print out the set as a string with each entry on a new line
        for (String outputPath : outputPaths)
          output.append(outputPath).append("\n");

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
    // Initialize references
    FileSystem fs = FileSystem.getInstance();
    Set<String> ret_set = new HashSet<>();

    // If the current directory contains the wanted file then add the absolute
    // path of the file to the return set
    if (dir.containsFile(name)) {
      // Get the absolute path of the directory
      String dirAbsPath = fs.getAbsolutePathOfDir(dir);

      // If the directory is the root directory
      if (dirAbsPath.equals("/"))
        // Remove the extra "/" character
        dirAbsPath = "";

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
    // Initialize references
    FileSystem fs = FileSystem.getInstance();
    Set<String> ret_set = new HashSet<>();

    // If the current directory contains the wanted directory then add the
    // absolute path of the file to the return set
    if (dir.containsDir(name)) {
      // Get the absolute path of the directory
      String dirAbsPath = fs.getAbsolutePathOfDir(dir);

      // If the directory is the root directory
      if (dirAbsPath.equals("/"))
        // Remove the extra "/" character
        dirAbsPath = "";

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

  /**
   * Gets the name of the command
   *
   * @return Returns the name of the command
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   * Gets the documentation for this command
   *
   * @return The command description
   */
  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }
}
