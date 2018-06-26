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

import filesystem.*;
import containers.CommandArgs;
import utilities.Command;
import java.util.ArrayList;


public class CmdTree extends Command {
  private final String NAME = "tree";
  private static FileSystem filesys = FileSystem.getInstance();
  private static Directory root = filesys.getRoot();


  @Override
  public String execute(CommandArgs args){
    //currently, tree takes in no parameters
    if (args.getCommandParameters().length >0){
      return null;
    }

    String result = (root.getName()+"\n");
    result+=(addon(root,0));
    return result;
  }

  private String addon(Directory curr, int tabs){
    // get proper amount of tabs
    String spacing = "";
    for (int i=0; i<tabs; i++){
      spacing+="\t";
    }
    // the name of the curr dir gets inserted in the parent recursive call.
    String result="";
    // get the names of all the files in the directory
    ArrayList<String> files = curr.listFiles();
    if (files.size() > 0) {
      for (String name:files) {
        result += spacing+name +"\n";
      }
    }
    // now finally get all of the subdirectories
    //HashMap<String, Directory> childs = curr.getChildDirs();
    ArrayList<String> childs = curr.listDirNames();
    for (String key : childs){
      result+=spacing+key+"\n";
      //result+=addon(childs.get(key), tabs+1);
      result+=addon(curr.getDirByName(key), tabs+1);
    }
    return result;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    String manDoc = "This command prints a tree representation of the entire"
        + "filesystem, starting from the root. Takes in no parameters."
        + "Files and subdirectories within a directory appear on tab ahead,"
        + "listed below the directory name.\n";
    return manDoc;
  }

}
