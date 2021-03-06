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

import static utilities.JShellConstants.APPEND_OPERATOR;
import static utilities.JShellConstants.OVERWRITE_OPERATOR;
import containers.CommandArgs;
import containers.CommandDescription;
import driver.JShell;
import filesystem.FileSystem;
import io.Console;
import java.util.ArrayList;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * the history command.
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
   * The name of the command.
   */
  private static final String NAME = "history";

  /**
   * Command description.
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
                  + "(i.e. history 1 prints: \"1. history 1\")")
              .build();

  /**
   * Executes the history command.
   * 
   * @param args The arguments for the command call.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE.
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
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
      // check if the parameter is a valid positive int
      if (!checkNumber(params[0])) {
        return ExitCode.FAILURE;
      }
      int paramInt = Integer.parseInt(params[0]);
      // index will be the int to get the last "paramInt" history entries
      int index = history.size() - paramInt;
      // in the case that the parameter int is bigger than the size of history
      // resort to printing the whole history rather than throwing error
      ArrayList<String> newList =
          new ArrayList<>(history.subList(Math.max(index, 0), history.size()));
      i = Math.max(index, 0);
      for (String item : newList) {
        i++;
        result.append(Integer.toString(i)).append(". ").append(item)
            .append("\n");

      }
      // if the parameter was 0, an empty string is returned
    }

    // Get the result string
    String resultStr = result.toString().trim();
    if (!resultStr.isEmpty()) {
      resultStr += "\n";
    }

    // Write all the contents read to the Console and return SUCCESS always
    console.write(resultStr);
    return ExitCode.SUCCESS;
  }

  /**
   * Checks if the string is a number.
   * 
   * @param arg The string of the supposed number.
   * @return Returns true if the string represents a positive integer, false
   *         otherwise.
   */
  private boolean checkNumber(String arg) {
    // check if arg is a valid string for an int
    if (arg.matches("\\d+")) {
      int num = Integer.parseInt(arg);
      return (num >= 0);
    } else {
      return false;
    }
  }


  /**
   * Checks if the arguments are valid for this command.
   * 
   * @param args The command arguments.
   * @return whether or not the arguments are valid for the command.
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() <= 1
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR));

    // Check that the parameters are not strings
    boolean stringParamsMatches = true;
    for (String p : args.getCommandParameters()) {
      stringParamsMatches = stringParamsMatches && !isStringParam(p);
    }

    // Return the result
    return paramsMatches && stringParamsMatches;
  }
}
