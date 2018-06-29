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
import io.Writable;
import utilities.Command;
import utilities.ExitCode;

public class CmdMan extends Command {

  // Command information constants
  private final String NAME = "man";
  /*
   * private final String DESCRIPTION = "" + "Man Command Documentation\n" +
   * "Description:\n" + "    - man: gets documentation for commands\n" +
   * "    \n" + "Usage:\r\n" + "    - man COMMAND\n" + "    \n" +
   * "Additional Comments:\n" + "    - For some fun try \"man man\".\n";
   */
  private CommandDescription DESCRIPTION = new CommandDescription(
      "Gets documentation for commands.", new String[]{"man COMMAND"},
      new String[]{"For some fun try \"man man\"."});

  /**
   * Executes the man command with the arguments args
   *
   * @param args The command arguments
   * @return Returns the output of the command
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out,
      Writable errOut) {
    // Get the command name from the parameters
    String cmdName = args.getCommandParameters()[0];
    // Get the description of the command
    // Return the command description
    // return commandManager.getCommandDescription(cmdName); // TODO: fix
    CommandDescription cmdDesc = commandManager
        .getCommandDescription(cmdName);

    if (cmdDesc == null) {
      errOut.writeln(
          "Error: No description found for command \"" + cmdName
              + "\"");
      return ExitCode.FAILURE;
    }

    StringBuilder output = new StringBuilder();

    output.append("\"" + cmdName + "\" Command Documentation\n");
    output
        .append("Description:\n\t" + cmdDesc.getDescription() + "\n");
    output.append("Usage:");
    for (String usage : cmdDesc.getUsages()) {
      output.append("\n\t- " + usage);
    }
    if (cmdDesc.getAdditionalComments().length > 0) {
      output.append("\nAdditional Comments:");
      for (String comment : cmdDesc.getAdditionalComments()) {
        output.append("\n\t- " + comment);
      }
    }

    // Write the output to the given out
    out.writeln(output.toString());

    return ExitCode.SUCCESS;
  }

  /**
   * A helper checking if args is a valid CommandArgs instance for
   * this command
   *
   * @param args The command arguments
   * @return Returns true iff args is a valid for this command
   */
  public boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals(NAME)
        && args.getCommandParameters().length == 1
        && args.getNumberOfNamedCommandParameters() == 0
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }


  /**
   * Gets the name of the command
   *
   * @return Returns the name of the command
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   * Gets the documentation for this command
   *
   * @return The command description
   */
  @Override
  public CommandDescription getDescription() {
    return DESCRIPTION;
  }
}
