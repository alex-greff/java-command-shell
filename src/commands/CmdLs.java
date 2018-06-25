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
import filesystem.FileSystem;
import filesystem.MalformedPathException;
import java.util.ArrayList;
import utilities.Command;
import filesystem.Directory;
import filesystem.Path;

public class CmdLs extends Command {
  private final String NAME="ls";
  private static final FileSystem filesys=FileSystem.getInstance();
  private static Directory rootDir = filesys.getRoot();

  @Override
  public String execute(CommandArgs args) {
    String result ="";
    Directory curr=FileSystem.getInstance().getWorkingDir();
    Path path;
    // check parameters
    String[] params= args.getCommandParameters();
    if (params.length > 0){
      for (String name : params){
        try {
          path = new Path(name);
          curr = filesys.getDirByPath(path);
        }catch(MalformedPathException m){
          result += "Error: Path "+name+ " was not found.\n\n";
        }//end try-catch
        result+=addon(curr);
      }
    }
    // if no parameters are given, perform command on current working dir
    else{
      result=addon(curr);
    }
    // trim the final newline
    result = result.substring(0, result.length()-2);
    return result;
  }

  private String addon(Directory dir){
    String result="";
    // get lists of files and dirs of the current working path
    ArrayList<String> dirs = dir.listDirNames();
    ArrayList<String> files= dir.listFiles();
    // now append the each string from the arraylists with a newline to result
    for (String name : dirs){
      result += name+"\n";
    }
    for (String name : files){
      result += name+"\n";
    }
    // add extra newline to separate text block from other parameters
    return result+"\n";
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    String result="This command lists all of the directories and files in"
        + "the current working directory. No arguments are needed for this "
        + "command.";
    return result;
  }

}
