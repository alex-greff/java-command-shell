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
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.ErrorConsole;
import utilities.Command;

public class CmdCd extends Command {

  private final String NAME = "cd";
  private final String DESCRIPTION =
      "" + "Cd Command Documentation\n"
          + "Description:\n" + "    - cd: change directory\n"
          + "    \n" + "Usage:\r\n" + "    - cd DIRECTORY\n" + "    \n"
          + "Additional Comments:\n" + "    - Path of DIRECTORY can be"
          + "relative or absolute\n";

  @Override
  public String execute(CommandArgs args) {
    String location = args.getCommandParameters()[0];
    ErrorConsole errorOut = ErrorConsole.getInstance();

    try {
      Path new_dir = new Path(location);
      fileSystem.changeWorkingDir(new_dir);
    } catch (MalformedPathException e) {
      errorOut.writeln("Error: Invalid file path");
      return null;
    } catch (FileNotFoundException e){
      errorOut.writeln("Error: File does not exist");
      return null;
    }

    return "";
  }

  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

}
