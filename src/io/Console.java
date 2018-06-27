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

import java.util.Scanner;

/**
 * Class representing the JShell console
 *
 * @author anton
 */
public class Console implements Writable, Readable {

  private static Console ourInstance = new Console();

  private Scanner input = new Scanner(System.in);

  private Console() {
  }

  /**
   * Gets the singleton instance of Console
   *
   * @return The Console instance
   */
  public static Console getInstance() {
    return ourInstance;
  }

  /**
   * Write text to the console
   *
   * @param contents The text to be written
   */
  @Override
  public void write(String contents) {
    System.out.print(contents);
  }

  /**
   * Read text from the console
   *
   * @return The text read from the console
   */
  @Override
  public String read() {
    return input.nextLine();
  }
}
