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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A console for buffering input. Allows for easy lookup for the output of
 * commands.
 *
 * @param <T> The object that are written/read by the console.
 * @author greff
 */
public class BufferedConsole<T> extends Console<T> {

  /**
   * The queue storing all the write inputs.
   */
  // private Stack<String> inputs = new Stack<>();

  private List<List<T>> inputs = new ArrayList<>();

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   *
   * @param contents The contents to be written.
   */
  @Override
  public void write(T contents) {
    // Add the contents to the most recent input line
    if (this.inputs.isEmpty()) {
      this.inputs.add(new ArrayList<>(Arrays.asList(contents)));
    } else {
      this.inputs.get(0).add(contents);
    }
  }

  /**
   * Writes the content input to a list with the most recent at the head.
   * Newlines at the end of the content string are trimmed.
   *
   * @param contents The contents to be written.
   */
  @Override
  public void writeln(T contents) {
    this.inputs.add(new LinkedList<>(Arrays.asList(contents)));
  }

  /**
   * Gets the most recent write to the console.
   *
   * @return returns the most recent write to the console or an empty string if
   *         no writes exist.
   */
  public String getLastWrite() {
    StringBuilder ret_str = new StringBuilder();

    if (!inputs.isEmpty()) {
      List<T> lastLine = inputs.get(inputs.size() - 1);

      for (T item : lastLine)
        ret_str.append(item);
    }
    
    return ret_str.toString();
  }

  /**
   * Gets a string array of all the writes to the console. The most recent
   * writes are stored first in the array.
   *
   * @return Returns the list of all writes to the console.
   */
  public ArrayList<String> getAllWrites() {
    ArrayList<String> ret_arr = new ArrayList<>();

    for (List<T> line : inputs) {
      StringBuilder line_str = new StringBuilder();
      for (T item : line) 
        line_str.append(item);

      ret_arr.add(line_str.toString());
    }

    return ret_arr;
  }

  /**
   * Gets all the writes as one whole string.
   *
   * @return Returns all the writes as a string.
   */
  public String getAllWritesAsString() {
    StringBuilder ret_str_bldr = new StringBuilder();

    // Iterate through each line
    for (List<T> line : inputs) {
      // Iterate through each item in the current line
      for (T item : line) {
        // Add the item to the return string
        ret_str_bldr.append(item);
      }
      // Add a newline to the return string
      ret_str_bldr.append("\n");
    }

    String ret_str = ret_str_bldr.toString();

    // Remove last newline if there is one
    if (ret_str.length() > 0) {
      ret_str = ret_str.substring(0, ret_str.length() - 1);
    }

    // Return the string representation of the file data
    return ret_str;
  }

  /**
   * Read text from the console.
   *
   * @return The text read from the console.
   */
  @Override
  public String read() {
    // Initialize new scanner object then read and return the input
    Scanner input = new Scanner(System.in);
    return input.nextLine();
  }
}
