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
import filesystem.DirectoryAlreadyExistsException;
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;

public class CmdMkdir extends Command {

  private final String NAME = "mkdir";
  private CommandDescription DESCRIPTION = null; // TODO: initialize

  @Override
  public int execute(CommandArgs args, Writable out, Writable errOut) {
    for (String pathString : args.getCommandParameters()) {
      try {
        Path path = new Path(pathString);
        String newDirName = path.removeLast();
        Directory parent = fileSystem.getDirByPath(path);
        parent.createAndAddNewDir(newDirName);
      } catch (MalformedPathException e) {
        errOut.writeln("Error: Invalid path" + pathString);
        return 1;
      } catch (FileNotFoundException e) {
        errOut.writeln("Error: Parent directory not found");
        return 1;
      } catch (DirectoryAlreadyExistsException e) {
        errOut.writeln("Error: Directory already exists");
        return 1;
      }
    }
    return 0;
  }

  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length > 0
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }

}
