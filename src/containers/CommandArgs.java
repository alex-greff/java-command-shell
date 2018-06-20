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
  private String[] cmdParams;
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
   * @param cmdParams the array of command parameters
   */
  public CommandArgs(String cmdName, String[] cmdParams) {
    this(cmdName, cmdParams, "", "");
  }

  /**
   * Constructor initializing with command name, redirect operator and target
   * destination
   * 
   * @param cmdName the name of the command
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, String redirOperator, String targetDest) {
    this(cmdName, new String[0], redirOperator, targetDest);
  }

  /**
   * Constructor initializing with command name, command arguments, the redirect
   * operator and the target destination
   * 
   * @param cmdName the command name
   * @param cmdParams the command arguments
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, String[] cmdParams, String redirOperator,
      String targetDest) {
    this.cmdName = cmdName;
    this.cmdParams = cmdParams;
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
  public String[] getCommandParameters() {
    return cmdParams;
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
    if (cmdParams.length != 0)
      ret_str += "  Params: " + Arrays.toString(this.cmdParams);

    // If there is a redirect then add that to the string representation as well
    if (!redirOperator.equals("") && !targetDest.equals("")) {
      ret_str += "  Redirect: " + redirOperator;
      ret_str += "  Destination: " + targetDest;
    }

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
          && Arrays.equals(this.cmdParams, cmdArgs_other.cmdParams)
          && this.redirOperator.equals(cmdArgs_other.getRedirectOperator())
          && this.targetDest.equals(cmdArgs_other.getTargetDestination());
    } else
      return false;
  }
}
