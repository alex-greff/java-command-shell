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
import filesystem.FileNotFoundException;
import filesystem.MalformedPathException;
import filesystem.Path;
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

public class CmdCat extends Command {

  private final String NAME = "cat";
  private CommandDescription DESCRIPTION = new CommandDescription(
      "Print contents of file(s).", new String[]{"cat FILES"},
      new String[]{"Path of FILE can be relative or absolute.",
          "Can take more than one FILE as arguments."});

  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    String[] files = args.getCommandParameters();
    StringBuilder result = new StringBuilder();

    for (String filePathStr : files) {

      try {
        Path filePath = new Path(filePathStr);
        File file = fileSystem.getFileByPath(filePath);
        result.append(file.read()).append('\n');
      } catch (MalformedPathException e) {
        errOut.writeln("Invalid file path");
      } catch (FileNotFoundException e) {
        errOut.writeln("File does not exist");
      }

    }

    out.writeln(result.toString());
    return ExitCode.SUCCESS;
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
