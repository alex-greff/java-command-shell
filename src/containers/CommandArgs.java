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
import java.util.HashMap;
import java.util.Set;

/***
 * A container for storing parsed user input
 *
 * @author greff
 *
 */
public class CommandArgs {

  // Storage variables
  /**
   * The name of the command.
   */
  private String cmdName;
  /**
   * The array of parameters, if any.
   */
  private String[] cmdParams;
  /**
   * The map of named command parameters, if any.
   */
  private HashMap<String, String> namedCmdParams;
  /**
   * The redirect operator, if any.
   */
  private String redirOperator;
  /**
   * The target destination file, if any.
   */
  private String targetDest;

  /**
   * Constructor initializing with only a command name
   *
   * @param cmdName the name of the command
   */
  public CommandArgs(String cmdName) {
    this(cmdName, new String[0], new HashMap<>(), "", "");
  }

  /**
   * Constructor initializing with command name and command arguments
   *
   * @param cmdName the name of the command
   * @param cmdParams the array of command parameters
   */
  public CommandArgs(String cmdName, String[] cmdParams) {
    this(cmdName, cmdParams, new HashMap<>(), "", "");
  }

  /**
   * Constructor initializing with command name and named command arguments
   *
   * @param cmdName the name of the command
   * @param namedCmdParams the parameters that are mapped by type
   */
  public CommandArgs(String cmdName, HashMap<String, String> namedCmdParams) {
    this(cmdName, new String[0], namedCmdParams, "", "");
  }

  /**
   * Constructor initializing with command name, command arguments and named
   * command arguments
   *
   * @param cmdName the command name
   * @param cmdParams the command arguments
   * @param namedCmdParams the parameters that are mapped by type
   */
  public CommandArgs(String cmdName, String[] cmdParams,
                     HashMap<String, String> namedCmdParams) {
    this(cmdName, cmdParams, namedCmdParams, "", "");
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
    this(cmdName, new String[0], new HashMap<>(), redirOperator, targetDest);
  }

  /**
   * Constructor initializing with command name, command arguments, redirect
   * operator and target destination
   *
   * @param cmdName the name of the command
   * @param cmdArgs the command arguments
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, String[] cmdArgs, String redirOperator,
                     String targetDest) {
    this(cmdName, cmdArgs, new HashMap<>(), redirOperator, targetDest);
  }

  /**
   * Constructor initializing with command name, named command arguments,
   * redirect operator and target destination
   *
   * @param cmdName the name of the command
   * @param namedCmdArgs the parameters that are mapped by type
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, HashMap<String, String> namedCmdArgs,
                     String redirOperator, String targetDest) {
    this(cmdName, new String[0], namedCmdArgs, redirOperator, targetDest);
  }

  /**
   * Constructor initializing with command name, command arguments, named
   * command arguments, the redirect operator and the target destination
   *
   * @param cmdName the command name
   * @param cmdParams the command arguments
   * @param namedCmdParams the parameters that are mapped by type
   * @param redirOperator the redirect operator
   * @param targetDest the target destination of the redirect
   */
  public CommandArgs(String cmdName, String[] cmdParams,
                     HashMap<String, String> namedCmdParams,
                     String redirOperator,
                     String targetDest) {
    this.cmdName = cmdName;
    this.cmdParams = cmdParams;
    this.namedCmdParams = namedCmdParams;
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
   * there are none
   */
  public String[] getCommandParameters() {
    return cmdParams;
  }

  /**
   * Gets the named command parameter with a given name.
   *
   * @param name The name of the type.
   * @return Returns the parameter mapped to name or null it is it not mapped.
   */
  public String getNamedCommandParameter(String name) {
    return this.namedCmdParams.get(name);
  }

  /**
   * Gets a copy of the hash map for the named command parameters
   *
   * @return Returns a copy of the hash map for the named command parameters
   */
  public HashMap<String, String> getNamedCommandParametersMap() {
    return new HashMap<>(this.namedCmdParams);
  }

  /**
   * Gets the set of keys for the named command parameters
   *
   * @return Returns the set of keys for the named command parameters
   */
  public Set<String> getSetOfNamesCommandParameterKeys() {
    return this.namedCmdParams.keySet();
  }

  /**
   * Gets the number of named command parameters
   *
   * @return Returns the number of named command parameters
   */
  public int getNumberOfNamedCommandParameters() {
    return this.namedCmdParams.keySet().size();
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
   * none
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
    StringBuilder ret_str = new StringBuilder("Cmd: " + cmdName);

    // If there are command arguments then add them to the string representation
    if (cmdParams.length != 0) {
      ret_str.append("  Params: ").append(Arrays.toString(this.cmdParams));
    }

    // If there are named command arguments then add them to the string
    // representation
    if (namedCmdParams.keySet().size() > 0) {
      ret_str.append("  Named Params: [");

      for (String name : namedCmdParams.keySet()) {
        ret_str.append("(").append(name).append(" -> ")
            .append(namedCmdParams.get(name)).append("), ");
      }

      ret_str = new StringBuilder(ret_str.substring(0, ret_str.length() - 2));
      ret_str.append("]");
    }

    // If there is a redirect then add that to the string representation as well
    if (!redirOperator.equals("") && !targetDest.equals("")) {
      ret_str.append("  Redirect: ").append(redirOperator);
      ret_str.append("  Destination: ").append(targetDest);
    }

    // Return the constructed string representation
    return ret_str.toString();
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
          && this.namedCmdParams
          .equals(((CommandArgs) other).getNamedCommandParametersMap())
          && this.redirOperator.equals(cmdArgs_other.getRedirectOperator())
          && this.targetDest.equals(cmdArgs_other.getTargetDestination());
    } else {
      return false;
    }
  }
}
