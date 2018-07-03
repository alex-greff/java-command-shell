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
import filesystem.FileAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

/**
 * The echo command.
 *
 * @author greff
 */
public class CmdEcho extends Command {

  /**
   * The name of the command.
   */
  private static final String NAME = "echo";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Appends or writes a string to a file.",
          "echo STRING")
          .usage("echo STRING [> OUTFILE]")
          .usage("echo STRING [>> OUTFILE]")
          .additionalComment(
              "The \">\" character signals to overwrite the file "
                  + "conents.")
          .additionalComment(
              "The \">>\" character signals to append to the file conents.")
          .build();
  /**
   * The overwrite operator character.
   */
  private final String OVERWRITE_OPERATOR = ">";
  /**
   * The append operator character.
   */
  private final String APPEND_OPERATOR = ">>";

  /**
   * Constructs a new command instance
   */
  public CmdEcho() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the echo command.
   *
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the exit status of the command.
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Initialize default command output
    String output = "";
    ExitCode exitValue = ExitCode.SUCCESS;

    // If there is a redirect operator provided
    if (args.getRedirectOperator().length() > 0) {
      // Write to the file
      try {
        // Attempt to write to the file
        exitValue = writeToFile(args, out, errOut);
      } catch (FileAlreadyExistsException e) {
        // Set the failure exit code and print that the file already exists
        exitValue = ExitCode.FAILURE;
        errOut.writeln("File already exists");
      }
    }
    // If not
    else {
      // Set the string parameter to the output
      output = args.getCommandParameters()[0];
    }

    // If there is any output for the standard out then write to it
    if (!output.equals("")) {
      out.writeln(output);
    }

    // Return the output
    return exitValue;
  }

  /**
   * A helper function that writes the given command args to a file
   *
   * @param args The command args
   * @param out The standard output to use.
   * @param errOut The error output to use.
   * @return Returns if the write succeeded or not
   */
  private ExitCode writeToFile(CommandArgs args, Writable out, Writable errOut)
      throws FileAlreadyExistsException {
    // Setup references
    String redirOper = args.getRedirectOperator();
    String strContents = args.getCommandParameters()[0];
    String filePathStr = args.getTargetDestination();

    try {
      // Get file system reference
      FileSystem fs = FileSystem.getInstance();

      // Get the path of the file
      Path filePath = new Path(filePathStr);

      // Get the File
      File file;
      try {
        file = fs.getFileByPath(filePath);
        // If the file does not exist
      } catch (FileNotFoundException e) {
        // Attempt to make the file
        try {
          file = makeFile(filePathStr);
        } catch (FileNotFoundException e1) {
          errOut.writeln("Error: No directory found");
          return ExitCode.FAILURE;
        }
      } catch (MalformedPathException e1) {
        errOut.writeln("Error: Not a valid file path");
        return ExitCode.FAILURE;
      }

      // Wipe the file contents if the overwrite operator is given in the args
      if (redirOper.equals(OVERWRITE_OPERATOR))
        file.clear();

      // Add the string contents to the file
      file.write(strContents);

    } catch (MalformedPathException e) {
      errOut.write("Error: Invalid path \"" + filePathStr + "\"");
      return ExitCode.FAILURE;
    }

    // Reaching this point means that the write to file executed successfully
    return ExitCode.SUCCESS;
  }


  /**
   * Attempts to make a file from the given file path string.
   *
   * @param filePathStr The file path string.
   * @return Returns the created file.
   * @throws MalformedPathException
   * @throws FileNotFoundException
   * @throws FileAlreadyExistsException
   */
  private File makeFile(String filePathStr) throws MalformedPathException,
      FileNotFoundException, FileAlreadyExistsException {
    // Get a reference to the file system singleton
    FileSystem fs = FileSystem.getInstance();

    // Make the new file
    String[] fileSplit = filePathStr.split("/");
    String fileName = fileSplit[fileSplit.length - 1];
    File file = new File(fileName);
    // Get the index of the last "/"
    int lastSlash = filePathStr.lastIndexOf('/');

    // Get the directory that the file is in
    String dirPathStr =
        (lastSlash > -1) ? filePathStr.substring(0, lastSlash) : "";

    // If the file is in the root
    if (dirPathStr.equals(""))
      dirPathStr = "/";

    Path dirPath = new Path(dirPathStr);
    Directory dirOfFile = fs.getDirByPath(dirPath);

    // Add the file to the directory
    dirOfFile.addFile(file);

    // Return the file
    return file;
  }

  /**
   * Checks if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR))
        && args.getNumberOfNamedCommandParameters() == 0;
  }
}
