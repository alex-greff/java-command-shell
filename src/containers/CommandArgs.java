package containers;

import java.util.Arrays;

/***
 * A container for storing parsed user input
 * 
 * @author greff
 *
 */
public class CommandArgs {
  // Storage variables
  private String cmdName;
  private String[] cmdArgs;
  private String redirOperator;
  private String targetDest;

  /**
   * Constructor initializing with only a command name
   * 
   * @param cmdName the name of the command
   */
  public CommandArgs(String cmdName) {
    this(cmdName, new String[0], "", "");
  }

  /**
   * Constructor initializing with command name and command arguments
   * 
   * @param cmdName the name of the command
   * @param cmdArgs the array of command arguments
   */
  public CommandArgs(String cmdName, String[] cmdArgs) {
    this(cmdName, cmdArgs, "", "");
  }

  /**
   * Constructor initializing with command name, redirect operator and target
   * destination
   * 
   * @param cmdName the name of the command
   * @param redirOperator the redirect operator
   * @param targetDest the target desination of the redirect
   */
  public CommandArgs(String cmdName, String redirOperator, String targetDest) {
    this(cmdName, new String[0], redirOperator, targetDest);
  }

  /**
   * Constructor initializing with command name, command arguments, the redirect
   * operator and the target destination
   * 
   * @param cmdName the command name
   * @param cmdArgs the command arguments
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, String[] cmdArgs, String redirOperator,
      String targetDest) {
    this.cmdName = cmdName;
    this.cmdArgs = cmdArgs;
    this.redirOperator = redirOperator;
    this.targetDest = targetDest;
  }

  /**
   * Gets the command name or returns "" if there is none
   * 
   * @return Returns the command name. Returns "" if there are none
   */
  public String getCommandName() {
    return cmdName;
  }

  /**
   * Gets the command arguments or returns an empty array if there are none
   * 
   * @return Returns the array of command arguments. Returns an empty array if
   *         there are none
   */
  public String[] getCommandArguments() {
    return cmdArgs;
  }

  /**
   * Gets the redirect operator or returns "" if there is none
   * 
   * @return Returns the redirect operator. Returns "" if there is none
   */
  public String getRedirectOperator() {
    return redirOperator;
  }

  /**
   * Gets the target redirect destination or returns "" if there is none
   * 
   * @return Returns the target redirect destination. Returns "" if there is
   *         none
   */
  public String getTargetDestination() {
    return targetDest;
  }

  /**
   * Gets the string representation of self
   * 
   * @return Returns the string representation of self
   */
  @Override
  public String toString() {
    // Initialize the string representation and get the command name
    String ret_str = "Cmd: " + cmdName;

    // If there are command arguments then add them to the string representation
    if (cmdArgs.length != 0)
      ret_str += " Args: " + cmdArgs.toString();

    // If there is a redirect then add that to the string representation as well
    if (redirOperator != "" && targetDest != "")
      ret_str += " Redirect: " + redirOperator + " " + targetDest;

    // Return the constructed string representation
    return ret_str;
  }

  /**
   * Returns true iff other is equal to self
   * 
   * @param other The other CommandArgs being compared
   * @return Returns if the other object is equal to self
   */
  @Override
  public boolean equals(Object other) {
    // Check if other is a CommandArg
    if (other instanceof CommandArgs) {
      // Store other casted to CommandArgs
      CommandArgs cmdArgs_other = (CommandArgs) other;

      // Return the result of if other == this
      return this.cmdName.equals(cmdArgs_other.getCommandName())
          && Arrays.equals(this.cmdArgs, cmdArgs_other.cmdArgs)
          && this.redirOperator.equals(cmdArgs_other.getRedirectOperator())
          && this.targetDest.equals(cmdArgs_other.getTargetDestination());
    } else
      return false;
  }
}
