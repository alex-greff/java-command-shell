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
import filesystem.File;
import filesystem.Directory;
import filesystem.FileNotFoundException;
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import java.util.ArrayList;
import utilities.Command;
import utilities.ExitCode;

public class CmdLs extends Command {

  private final String NAME = "ls";
  private CommandDescription DESCRIPTION = null; // TODO: initialize


  /**
   *
   * @param args The command Arguments.
   * @param out Gets the commands standard output written to
   * @param errOut Gets the commands standard error output written to
   * @return an Exitcode: 0 -> success, 1 -> failure
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out,
      Writable errOut) {
    StringBuilder result = new StringBuilder();
    Directory curr = FileSystem.getInstance().getWorkingDir();
    Path path;
    // check parameters

    String[] params = args.getCommandParameters();
    if (params.length > 0) {
      for (String name : params) {
        try {
          path = new Path(name);
          curr = fileSystem.getDirByPath(path);
          result.append(addon(curr));
        } catch (MalformedPathException | FileNotFoundException m) {
          // if name was not detected as directory, try searching for the file
          try{
            File file = curr.getFileByName(name);
            result.append(addFileName(file));
          } catch(FileNotFoundException e){
            // only error out if the name was not found as either file or dir.
            errOut
                .writeln("Error: File \"" + name + "\" was not found");
          }//end catch for filenotFound
        } // end catch for bad/no existing path
      }//end forloop for all params
    }//endif

    // if no parameters are given, perform command on current working dir
    else {
      result = new StringBuilder(addon(curr));
    }

    if (result.length() > 0) {
      // trim final newline
      result.reverse().delete(0,1).reverse();
      out.write(result.toString());
    }

    return ExitCode.SUCCESS;
  }

  /**
   *
   * @param dir The directory whose contents will be represented by a string
   * @return The string representation of the directories contents
   */
  private String addon(Directory dir) {
    StringBuilder result = new StringBuilder();
    // get lists of files and dirs of the current working path
    ArrayList<String> dirs = dir.listDirNames();
    ArrayList<String> files = dir.listFiles();
    // now append the each string from the arraylists with a newline to result
    for (String name : dirs) {
      result.append(name).append("\n");
    }
    for (String name : files) {
      result.append(name).append("\n");
    }
    return (result.toString()+"\n");
  }

  private String addFileName(File file){
    String res="";
    String name = file.getName();
    res=name+"\n\n";
    return res;
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for
   * this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  /**
   *
   * @return the name of the command
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   *
   * @return the documentation of the command
   */
  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }

}
