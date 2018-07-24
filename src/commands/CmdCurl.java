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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;

public class CmdCurl extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdCurl(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }

  // Setup command information
  /**
   * The name of the command.
   */
  private static final String NAME = "curl";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Loads and displays the contents of a file from a URL", "curl URL")
          .build();

  /**
   * Executes the curl command from the given URL location.
   *
   * @param args The arguments for the command.
   * @param console The standard console.
   * @param queryConsole The query console.
   * @param errorConsole The error console.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  protected ExitCode run(CommandArgs args, Console<String> console,
      Console<String> queryConsole, Console<String> errorConsole) {
    try {
      // Get the url
      URL url = new URL(args.getCommandParameters()[0]);

      // Open the url file in a buffered reader
      BufferedReader br =
          new BufferedReader(new InputStreamReader(url.openStream()));

      // Write the contents of the url file to the contents string
      StringBuilder contents = new StringBuilder();
      String curr;
      while ((curr = br.readLine()) != null) {
        contents.append(curr).append("\n");
      }

      // Close the buffered reader
      br.close();

      // Get result string
      String resultStr = contents.toString();

      // Write all the contents read to the Console and return SUCCESS always
      console.write(resultStr);
      return ExitCode.SUCCESS;

    } catch (MalformedURLException e) {
      // Catch if the url is invalid
      errorConsole.writeln("Error: invalid URL given");
    } catch (IOException e) {
      // Catch if there was an error opening the url file
      errorConsole.writeln("Error: unable to load URL given");
    }
    // If it gets down here then an error occured so return the failure exit
    // code
    return ExitCode.FAILURE;
  }

  /**
   * Checks if args is a valid CommandArgs instance for this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
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
