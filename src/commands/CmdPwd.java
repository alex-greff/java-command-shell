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
import filesystem.FileSystem;
import io.Console;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

/**
 * The pwd command class that inherits from command.
 *
 * @author ursu
 */
public class CmdPwd extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdPwd(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  /**
   * Constant instance variable for the command name.
   */
  private static final String NAME = "pwd";

  /**
   * Container built for the command's description.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder("Print working directory.",
                                                "pwd").build();

  /**
   * Executes the pwd command with the given arguments. Pwd prints the working
   * directory.
   *
   * @param args The command arguments container.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, always SUCCESS.
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
    // Set the result string as the working directory path
    String resultStr = fileSystem.getWorkingDirPath() + "\n";

    // If no redirect operator then...
    // Write all the contents read to the Console and return SUCCESS always
    console.write(resultStr);
    return ExitCode.SUCCESS;
  }

  /**
   * Helper function to check if the arguments passed are valid for this
   * command. Pwd expects no arguments.
   *
   * @param args The command arguments container.
   * @return Returns true iff the arguments are valid, false otherwise.
   */
  @Override
  public boolean isValidArgs(CommandArgs args) {
    // Make sure the NAME matches, nothing else
    return args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 0
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
        || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
        || args.getRedirectOperator().equals(APPEND_OPERATOR));
  }
}
