package utilities;

import java.util.*;
import java.util.regex.*;
import containers.*;

public class Parser {
  private static final String OVERWRITE_OPERATOR = ">";
  private static final String APPEND_OPERATOR = ">>";

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

    // Split the user input by quotes, spaces and/or tabs 
    // Handles the cases were more than 1 consecutive spaces/tabs are used    
    List<String> inputSplit = new ArrayList<String>();
    // Apply the regex expression to the input string
    Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
    // Add the items from the matcher to the input split list
    while (m.find())
      inputSplit.add(m.group(1));
    
    // If no input parameters are found then return null
    if (inputSplit.size() == 0 || inputSplit.get(0).equals("")) {
      return null;
    }

    // Get command name
    String cmdName = inputSplit.get(0);
    // Initialize an array list for all the command parameter
    List<String> paramsArrayList = new ArrayList<>();
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
        targetDest = inputSplit.get(i + 1).replace("\"", "");
        // Break out of the for loop
        break;
      }

      // Add the parameters to the array list (without any quotes
      paramsArrayList.add(inputSplit.get(i).replace("\"", ""));
    }

    // Convert the parameter arraylist to an array
    String[] cmdParams = paramsArrayList.toArray(new String[0]);

    // Instantiate a CommandArgs instance with the parsed user input and return
    // the CommandArgs instance
    return new CommandArgs(cmdName, cmdParams, redirOperator, targetDest);
  }
}
