package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import containers.*;

public class Parser {
  private static final String REWRITE_OPERATOR = ">";
  private static final String APPEND_OPERATOR = ">>";

  /**
   * Parses the given input and returns a CommandArgs instance with the parsed
   * information
   * 
   * @param input The user input string
   * @return Returns a CommandArgs instance with the parsed user input or null
   *         if the user input is incorrect
   */
  public static CommandArgs ParseUserInput(String input) {
    // Trim any leading/trailing whitespaces/tabs from the input
    input = input.trim();
    
    //System.out.println(input);
    
    // Split the user input by spaces and/or tabs
    // Handles the cases were more than 1 consecutive spaces/tabs are used
    String[] inputSplit = input.split("\\s+");
    
    // If no input parameters are found then return null
    if (inputSplit[0].equals("")) {
      return null;
    }

    // Get command name
    String cmdName = inputSplit[0];
    // Initialize an array list for all the command parameter
    ArrayList<String> paramsArrayList = new ArrayList<String>();
    // Initialize the redirect operator to its empty state
    String redirOperator = "";
    // Initialize the target destination
    String targetDest = "";

    // Iterate through the items after index 0
    for (int i = 1; i < inputSplit.length; i++) {
      // If a redirect operator is found
      if (inputSplit[i].equals(REWRITE_OPERATOR)
          || inputSplit[i].equals(APPEND_OPERATOR)) {

        // If there is not only a single item after i then the input is invalid
        if (i + 1 != inputSplit.length - 1) {
          return null;
        }

        // Set the redirect operator
        redirOperator = inputSplit[i];
        // Set the target destination
        targetDest = inputSplit[i + 1];
        // Break out of the for loop
        break;
      }

      // Add the parameters to the array list
      paramsArrayList.add(inputSplit[i]);
    }

    // Convert the parameter arraylist to an array
    String[] cmdParams =
        paramsArrayList.toArray(new String[paramsArrayList.size()]);

    // Instantiate a CommandArgs instance with the parsed user input
    CommandArgs ca =
        new CommandArgs(cmdName, cmdParams, redirOperator, targetDest);

    // Return the CommandArgs instance
    return ca;
  }
}
