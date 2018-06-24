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
import filesystem.File;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import utilities.Command;

public class CmdEcho extends Command {
  private final String NAME = "echo";
  private final String DESCRIPTION = "" + "Description:\r\n"
      + "    - echo: appends or writes a string to a file.\n"
      + "            If no file is given then the string is written to console.\n"
      + "\n" + "Usage:\n" + "    - echo STRING\n"
      + "    - echo STRING [> OUTFILE]\n" + "    - echo STRING [>> OUTFILE]\n"
      + "    \n" + "Additional Comments:\n"
      + "    - The \">\" characer signals to overwrite the file contents.\n"
      + "    - The \">>\" characer signals to append to the file contents.\n";

  private final String OVERWRITE_OPERATOR = ">";
  private final String APPEND_OPERATOR = ">>";

  @Override
  public String execute(CommandArgs args) {
    // Check validity of args
    if (isValidArgs(args) == false) {
      return null; // TODO: figure out what to return here
    }

    // Initialize default command output
    String output = "";

    // If there is a redirect operator provided
    if (args.getRedirectOperator().length() > 0) {
      // Write to the file
      writeToFile(args);
    }
    // If not
    else {
      // Set the string parameter to the ouput
      output = args.getCommandParameters()[0];
    }
    
    // Return the output
    return output;
  }
  
  /**
   * A helper function that writes the given command args to a file
   * 
   * @param args The command args
   */
  private void writeToFile(CommandArgs args) {
    // Setup references
    FileSystem fs = FileSystem.getInstance();
    String redirOper = args.getRedirectOperator();
    String strContents = args.getCommandParameters()[0];
    
    try {
      // Get the path of the file
      Path path = new Path(args.getTargetDestination());
      
      // Get the File
      File file = fs.getFileByPath(path);
      
      // Check if file exists
      if (file == null)
        return;
      
      // Wipe the file contents if the overwrite operator is given in the args 
      if (redirOper.equals(OVERWRITE_OPERATOR)) {
        file.clear();
      }
      
      // Add the string contents to the file
      file.write(strContents);
      
    } catch (MalformedPathException e) {
      // TODO: do something
    }
  }


  /**
   * A helper checking if args is a valid CommandArgs instance for this command
   * 
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  private boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR))
        && args.getNumberOfNamedCommandParameters() == 0;
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
  public String getDescription() {
    return DESCRIPTION;
  }

}
