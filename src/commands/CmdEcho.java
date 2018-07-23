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
import io.Readable;
import containers.CommandDescription;
import filesystem.FileSystem;
import io.Writable;
import utilities.Command;
import utilities.CommandManager;
import utilities.ExitCode;
import static utilities.JShellConstants.*;

/**
 * The echo command.
 *
 * @author greff
 */
public class CmdEcho extends Command {

  /**
   * Constructs a new command instance.
   *
   * @param fileSystem The file system that the command uses.
   * @param commandManager The command manager that the command uses.
   */
  public CmdEcho(FileSystem fileSystem, CommandManager commandManager) {
    super(NAME, DESCRIPTION, fileSystem, commandManager);
  }


  /**
   * The name of the command.
   */
  private static final String NAME = "echo";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Appends or writes a string to a file or prints directly to console.",
          "echo STRING").usage("echo STRING [> OUTFILE]")
              .usage("echo STRING [>> OUTFILE]")
              .additionalComment(
                  "The \">\" character signals to overwrite the file "
                      + "conents.")
              .additionalComment(
                  "The \">>\" character signals to append to the file conents.")
              .build();

  /**
   * Executes the echo command.
   *
   * @param args The arguments for the command.
   * @param out The writable for any normal output of the command.
   * @param in The standard input
   * @param errOut The writable for any error output of the command.
   * @return Returns the ExitCode of the command, SUCCESS or FAILURE.
   */
  @Override
  protected ExitCode run(CommandArgs args, Writable<String> out, Readable in,
      Writable<String> errOut) {
    // Set the string parameter to the output
    String output = args.getCommandParameters()[0].replaceAll("\"", "");

    // If there is any output for the standard out then write to it
    if (!output.isEmpty())
      out.writeln(output + "\n");

    // Return the success exit code
    return ExitCode.SUCCESS;
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
      stringParamsMatches = stringParamsMatches && isStringParam(p);
    }

    // Return the result
    return paramsMatches && stringParamsMatches;
  }
}
