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
import driver.JShell;
import filesystem.FileSystem;
import io.Writable;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * the history command
 *
 * @author chedy
 */
public class CmdHistory extends Command {
  /**
   * Constructs a new command instance.
   * 
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdHistory(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * the name of the command
   */
  private static final String NAME = "history";

  /**
   * command description
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "This command lists all of the past lines of user entry "
              + "by default, but if given a positive integer "
              + "argument x, the last x user entries will be listed.",
          "history")
              .usage("history [int]")
              .additionalComment("The history command itself will "
                  + "always take place as the latest entry in history "
                  + "\n(i.e. history 1 prints: \n 1. history 1)")
              .build();

  /**
   * @param args The arguments for the command call.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    String[] params = args.getCommandParameters();
    ArrayList<String> history = JShell.getHistory();
    // by default, get all of the history
    StringBuilder result = new StringBuilder();
    int i = 1;
    if (params.length == 0) {
      for (String item : history) {
        result.append(Integer.toString(i)).append(". ").append(item)
            .append("\n");
        i++;
      }
    }
    // the case where they want a specific amount of history
    else if (params.length == 1) {
      // check if the parameter is an int
      if (!params[0].matches("\\d+")) {
        return ExitCode.FAILURE;
      }
      int paramInt = Integer.parseInt(params[0]);
      if (paramInt < 0) {
        // error if a negative integer is passed as an argument
        return ExitCode.FAILURE;
      }
      int index = history.size() - paramInt;
      // in the case that the parameter int is bigger than the size of history
      // resort to printing the whole history rather than throwing error
      ArrayList<String> newList =
          new ArrayList<>(history.subList(Math.max(index, 0), history.size()));

      for (String item : newList) {
        result.append(Integer.toString(i)).append(". ").append(item)
            .append("\n");
        i++;
      }
      // if the parameter was 0, an empty string is returned
    }

    out.writeln(result.toString());
    return ExitCode.SUCCESS;
  }

  /**
   * @param args The command arguments.
   * @return whether or not the arguments are valid for the command
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length <= 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }
}
