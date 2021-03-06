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
 * The !number command, otherwise known as Recall.
 *
 * @author chedy
 */
public class CmdRecall extends Command {

  /**
   * The name of this command.
   */
  private static final String NAME = "recall";
  /**
   * The command description.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "This command executes the n'th last command executed", "![num]")
              .additionalComment("The history command itself will "
                  + "always take place as the latest entry in history "
                  + "(i.e. history 1 prints: \"[int]. history 1\")")
              .build();

  /**
   * Constructs a new CmdRecall instance.
   *
   * @param fileSystem the filesystem that the command uses.
   * @param commandManager the commandManager that the command uses.
   */
  public CmdRecall(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * @param args The arguments for the command call.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return the exitcode indicating success or failure of execution.
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
    // Get JShell's command history
    ArrayList<String> history = JShell.getHistory();
    history.remove(history.size() - 1); // erases the !num cmd
    String[] params = args.getCommandParameters();
    String strNum = params[0];
    // check if the argument is an int;
    int num;
    if (isInt(strNum)) {
      num = Integer.parseInt(strNum);
    } else {
      errorConsole.writeln("error: argument must be integer");
      return ExitCode.FAILURE;
    }
    // get the command at the num's index. If the num is too big, exits on fail
    if (num > history.size()) {
      errorConsole.writeln("error: recalled too far");
      return ExitCode.FAILURE;
    } else if (num <= 0) {
      errorConsole.writeln("error: recalled invalid number");
      return ExitCode.FAILURE;
    }
    String cmd = history.get(num - 1); // minus one since the list starts at 1.
    // manually execute and add the command to history
    history.add(cmd);
    JShell.parseAndExecute(cmd);
    return ExitCode.SUCCESS;
  }

  /**
   * Indicates if the string word is an integer.
   * 
   * @param word the string to be checked if its a valid number.
   * @return true iff word can be parsed into an int.
   */
  private boolean isInt(String word) {
    try {
      int i = Integer.parseInt(word);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Validates that the arguments are valid for this command.
   * 
   * @param args The command arguments.
   * @return true if the arguments are valid for this command.
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Check that the form matches for the args
    boolean paramsMatches = args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 1
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
