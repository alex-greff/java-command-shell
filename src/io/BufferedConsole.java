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
package io;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A console for buffering input. Allows for easy lookup for the output of
 * commands.
 *
 * @author greff
 */
public class BufferedConsole implements Writable {

  /**
   * The stack storing all the write inputs.
   */
  private Stack<String> inputs = new Stack<>();

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   *
   * @param contents The contents to be written
   */
  @Override
  public void write(String contents) {
    String prev_line = "";

    if (!inputs.isEmpty()) {
      prev_line = inputs.pop();
    }

    inputs.push(prev_line + contents);
  }

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   *
   * @param contents The contents to be written
   */
  @Override
  public void writeln(String contents) {
    inputs.push(contents);
  }

  /**
   * Gets the most recent write to the console.
   *
   * @return returns the most recent write to the console or null if no writes
   *         exist.
   */
  public String getLastWrite() {
    if (inputs.isEmpty()) {
      return null;
    }
    return inputs.peek();
  }

  /**
   * Gets a string array of all the writes to the console. The most recent
   * writes are stored first in the array.
   *
   * @return Returns the list of all writes to the console.
   */
  public ArrayList<String> getAllWrites() {
    return new ArrayList<>(inputs);
  }

  /**
   * Gets all the writes as one whole string.
   *
   * @return Returns all the writes as a string.
   */
  public String getAllWritesAsString() {
    String[] input_arr = new String[inputs.size()];
    inputs.toArray(input_arr);

    StringBuilder ret_str = new StringBuilder();

    for (String s : input_arr) {
      ret_str.append(s).append("\n");
    }

    if (ret_str.length() > 0)
      ret_str.deleteCharAt(ret_str.length() - 1); // Remove last \n

    return ret_str.toString();
  }
}
