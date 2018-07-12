package commands;

import containers.CommandArgs;
import containers.CommandDescription;
import filesystem.FileSystem;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import static utilities.JShellConstants.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
          "Loads and displays a text file form a URL", "curl URL").build();

  /**
   * Executes the curl command from the given URL location.
   *
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param errOut The writable for any error output of the command.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errorOut) {
    try {
      // Instantiate the readers
      URL url = null;
      BufferedReader br = null;

      // Get the url
      url = new URL(args.getCommandParameters()[0]);

      // Open the url file in a buffered reader
      br = new BufferedReader(new InputStreamReader(url.openStream()));

      // Write the contents of the url file to the contents string
      StringBuilder contents = new StringBuilder();
      String curr;
      while ((curr = br.readLine()) != null)
        contents.append(curr).append("\n");

      // Close the buffered reader
      br.close();

      // Get result string
      String resultStr = contents.toString();

      // If a redirect is given then attempt to write to file and return exit
      // code
      if (!args.getRedirectOperator().isEmpty())
        return writeToFile(resultStr, args.getRedirectOperator(),
            args.getTargetDestination(), errorOut);

      // If no redirect operator then...
      // Write all the contents read to the Console and return SUCCESS always
      out.write(resultStr);
      return ExitCode.SUCCESS;

    } catch (MalformedURLException e) {
      // Catch if the url is invalid
      errorOut.writeln("Error: invalid URL given");
    } catch (IOException e) {
      // Catch if there was an error opening the url file
      errorOut.writeln("Error: invalid URL given");
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
    // Make sure the NAME matches, and just 1 argument, nothing else
    return args.getCommandName().equals(NAME)
        && args.getNumberOfCommandParameters() == 1
        && args.getNumberOfCommandFieldParameters() == 0
        && args.getNumberOfNamedCommandParameters() == 0
        && (args.getRedirectOperator().equals("")
            || args.getRedirectOperator().equals(OVERWRITE_OPERATOR)
            || args.getRedirectOperator().equals(APPEND_OPERATOR));
  }

}