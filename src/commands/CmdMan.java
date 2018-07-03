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

/**
 * The man command.
 *
 * @author greff
 */
public class CmdMan extends Command {
  // Setup command information
  /**
   * The name of the command.
   */
  private static final String NAME = "man";
  /**
   * The description of the command.
   */
  private static final CommandDescription DESCRIPTION =
      new CommandDescription.DescriptionBuilder(
          "Gets documentation for commands.", "man COMMAND")
              .additionalComment("For some fun try \"man man\".").build();

  /**
   * Constructs a new command instance
   */
  public CmdMan() {
    super(NAME, DESCRIPTION);
  }

  /**
   * Executes the man command with the arguments args
   *
   * @param args The command arguments
   * @return Returns the output of the command
   */
  @Override
  public ExitCode execute(CommandArgs args, Writable out, Writable errOut) {
    // Get the command name from the parameters
    String cmdName = args.getCommandParameters()[0];
    // Get the description of the command
    CommandDescription cmdDesc = commandManager.getCommandDescription(cmdName);

    // If the command does not have a command description object
    if (cmdDesc == null) {
      // Write to an error message
      errOut.writeln(
          "Error: No description found for command \"" + cmdName + "\"");
      // Return the failure exit code
      return ExitCode.FAILURE;
    }

    // In the case that there is a command description object
    // Initialize a string builder object
    StringBuilder output = new StringBuilder();

    // Build the header for the command documentation
    output.append("\"").append(cmdName).append("\" Command Documentation\n")
        // Build the description section
        .append("Description:\n\t").append(cmdDesc.getDescription())
        .append("\n")
        // Build the usage section
        .append("Usage:");
    
    for (String usage : cmdDesc.getUsages()) 
      output.append("\n\t- ").append(usage);

    // If applicable, build the additional comments section
    if (!cmdDesc.getAdditionalComments().isEmpty()) {
      output.append("\nAdditional Comments:");
      for (String comment : cmdDesc.getAdditionalComments())
        output.append("\n\t- ").append(comment);
    }

    // Write the output to the given out
    out.writeln(output.toString());

    // Return the success exit code
    return ExitCode.SUCCESS;
  }

  /**
   * Checks if args is a valid CommandArgs instance for this command
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
}
