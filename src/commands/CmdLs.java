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
import java.util.ArrayList;
import utilities.Command;
import filesystem.Directory;

public class CmdLs extends Command {

  @Override
  public String execute(CommandArgs args) {
    // get lists of files and dirs of the current working path
    Directory dir = FileSystem.getInstance().getWorkingDir();
    ArrayList<String> dirs = dir.listDirNames();
    ArrayList<String> files= dir.listFiles();
    String result="";
    // now append the each string from the arraylists with a newline to result
    for (String name : dirs){
      result += name+"\n";
    }
    for (String name : files){
      result += name+"\n";
    }
    // trim the final newline
    result = result.substring(0, result.length()-2);
    return result;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    String name = "ls";
    return name;
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

}
