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
package utilities;

import containers.CommandArgs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static utilities.JShellConstants.*;

/**
 * The parser class.
 *
 * @author greff
 */
public class Parser {
  /**
   * The type argument indicator character.
   */
  private static final String TYPE_ARG_OPERATOR = "-";

  /**
   * Parses the given input and returns a CommandArgs instance with the parsed
   * information
   *
   * @param input The user input string
   * @return Returns a CommandArgs instance with the parsed user input or null
   *         if the user input is incorrect
   */
  public static CommandArgs parseUserInput(String input) {
    // Trim any leading/trailing whitespaces/tabs from the input
    input = input.trim();

    // Get the input split
    List<String> inputSplit = splitInput(input);

    // If no input parameters are found then return null
    if (inputSplit.size() == 0 || inputSplit.get(0).equals("")) {
      return null;
    }

    // Get and return the command args object of the input split
    return constructCommandArgs(inputSplit);
  }

  /**
   * Splits the input into a list of strings.
   *
   * @param input The input string.
   * @return The list of split strings.
   */
  private static List<String> splitInput(String input) {
    // Initialize variables
    List<String> inputSplit = new ArrayList<>();
    boolean inQuotes = false;
    StringBuilder currItem = new StringBuilder();
    String prevChar = " ";

    // Iterate through each character in the input string
    for (int i = 0; i < input.length(); i++) {
      // Get the current character
      String currChar = input.substring(i, i + 1);
      // If we find a quotation character
      if (currChar.equals("\"")) {
        // Flip the in quotes flag
        inQuotes = !inQuotes;
        // Add the quote character to the current item
        currItem.append(currChar);
        // If exiting a quotation pair then add a split and reset the tracker
        if (!inQuotes) {
          inputSplit.add(currItem.toString());
          currItem = new StringBuilder();
        }
        // Move on to the next iteration
        prevChar = currChar;
        continue;
      }
      // If inside a quotation section or the current char is a regular char
      if (inQuotes || !currChar.matches("\\s")) {
        // Add to the current split and move on to the next iteration
        currItem.append(currChar);
      }
      // If curr character is a whitespace char and the previous char was not
      // (ie the end of a split section is reached)
      // OR we're on the last iteration
      if ((!inQuotes && currChar.matches("\\s") && !prevChar.matches("[\\s\"]"))
          || (i == input.length() - 1)) {
        // Add the current split to the split list and reset the tracker
        inputSplit.add(currItem.toString());
        currItem = new StringBuilder();
      }
      prevChar = currChar;
    }
    return inputSplit;
  }

  /**
   * Attempts to construct a CommandArgs object from the given input split.
   *
   * @param inputSplit The list of split items of the input.
   * @return Returns a CommandArgs object or null if the given input is invalid.
   */
  private static CommandArgs constructCommandArgs(List<String> inputSplit) {

    // Get command name
    String cmdName = inputSplit.get(0);

    // Initialize an array list for all the command parameter
    List<String> paramsArrayList = new ArrayList<>();

    // If the command is the recall command (ie is prefixed with "!")
    if (cmdName.startsWith(COMMAND_RECALL_CHAR)) {
      // Get the suffix after "!"
      String param = cmdName.substring(1, cmdName.length());
      // If there is no string then the string is invalid
      if (param.isEmpty())
        return null;
      // Add the string to the params list
      paramsArrayList.add(param);
      // Set the command name to "!"
      cmdName = COMMAND_RECALL_NAME;
    }

    // Initialize an array list for all flags
    List<String> flagsArrayList = new ArrayList<>();
    // Initialize the hash map for the named type parameters
    HashMap<String, String> namedParamsMap = new HashMap<>();
    // Initialize the redirect operator to its empty state
    String redirOperator = "";
    // Initialize the target destination
    String targetDest = "";

    // Iterate through the items after index 0
    for (int i = 1; i < inputSplit.size(); i++) {
      // If a redirect operator is found
      if (inputSplit.get(i).equals(OVERWRITE_OPERATOR)
          || inputSplit.get(i).equals(APPEND_OPERATOR)) {
        // If there is not only a single item after i then the input is invalid
        if (i + 1 != inputSplit.size() - 1) {
          return null;
        }
        // Set the redirect operator
        redirOperator = inputSplit.get(i);
        // Set the target destination (without any quotes)
        // targetDest = inputSplit.get(i + 1).replace("\"", ""); // TODO: remove
        targetDest = inputSplit.get(i + 1);
        // Break out of the for loop
        break;
      }
      // If the item at index i is a type parameter
      if (inputSplit.size() > 0
          && inputSplit.get(i).startsWith(TYPE_ARG_OPERATOR)) {

        // Remove the - and set the key value
        String key = inputSplit.get(i).replaceFirst("-", "");

        // If the key is a flag (ie capital letters)
        if (key.matches("[A-Z]+")) {
          // Add key to the flags array list
          flagsArrayList.add(key);
        }
        // If they key is a named parameter (ie lower case letters)
        else if (key.matches("[a-z]+")) {
          // If there is no item after i then return invalid
          if (i + 1 >= inputSplit.size())
            return null;
          // If the item after is another type parameter then return invalid
          if (inputSplit.get(i + 1).startsWith(TYPE_ARG_OPERATOR))
            return null;
          // Remove any quoutes and set the value's value
          // String val = inputSplit.get(i + 1).replace("\"", ""); // TODO:
          // remove
          String val = inputSplit.get(i + 1);
          // Add to the hashmap
          namedParamsMap.put(key, val);
          // Force increment i by 1 (since we already dealt with index i + 1)
          i += 1;
        }
        // If its an invalid flag then return null
        else {
          return null;
        }
      } else {
        // Add the parameters to the array list (without any quotes
        // paramsArrayList.add(inputSplit.get(i).replace("\"", "")); // TODO:
        // remove
        paramsArrayList.add(inputSplit.get(i));
      }
    }

    // Convert the parameter and flags array list to arrays
    String[] cmdParams = paramsArrayList.toArray(new String[0]);
    String[] cmdFlags = flagsArrayList.toArray(new String[0]);

    // Instantiate a CommandArgs instance with the parsed user input and return
    // the CommandArgs instance
    return new CommandArgs(cmdName, cmdParams, cmdFlags, namedParamsMap,
        redirOperator, targetDest);
  }

  private static final String[] trueOptions =
      {"yes", "true", "y", "t", "1", "positive"};
  private static final String[] falseOptions =
      {"no", "false", "n", "f", "0", "negative"};
  private static final String[] cancelOptions = {"cancel", "c", "belay"};

  /**
   * Parses a user input to determine if the query was a true or false option
   * 
   * @param input The user input.
   * @param cancellable The flag indicating if the query is cancellable.
   * @return Returns the decision of the user.
   * @throws InvalidBooleanInputException Throws if the user inputs an invalid
   *         true/false string expression.
   */
  public static UserDecision parseBooleanDecisionInput(String input,
      boolean cancellable) throws InvalidBooleanInputException {
    // Check for true expression
    for (String s : trueOptions)
      if (input.toLowerCase().equals(s))
        return UserDecision.YES;

    // Check for false expression
    for (String s : falseOptions)
      if (input.toLowerCase().equals(s))
        return UserDecision.NO;

    // Check if cancel option, if applicable
    if (cancellable)
      for (String s : cancelOptions)
        if (input.toLowerCase().equals(s))
          return UserDecision.CANCEL;

    // No valid expression found so throw error
    throw new InvalidBooleanInputException();
  }
}
