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
import filesystem.MalformedPathException;
import filesystem.File;
import filesystem.Path;
import io.ErrorConsole;
import utilities.Command;

public class CmdCat extends Command {

  private final String NAME = "cat";
  private final String DESCRIPTION = "";

  @Override
  public String execute(CommandArgs args) {
    String[] files = args.getCommandParameters();
    StringBuilder result = new StringBuilder();
    ErrorConsole errorOut = ErrorConsole.getInstance();

    for(String filePathStr: files) {

      try {
        Path filePath = new Path(filePathStr);
        File file = fileSystem.getFileByPath(filePath);
        result.append(file.read()).append("\n");
      } catch (MalformedPathException e) {
        errorOut.writeln("Error: File Does Not Exist");
      }

    }

    return result.toString();
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
  public String getName() { return NAME; }

  @Override
  public String getDescription() { return DESCRIPTION; }

}
