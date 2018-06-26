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

/**
 * A class representing the JShell error console 
 * 
 * @author greff
 *
 */
public class ErrorConsole implements Writable {
  private static ErrorConsole ourInstance = new ErrorConsole();
  
  private ErrorConsole () {
    
  }
  
  /**
   * Gets the singleton instance of ErrorConsole
   * 
   * @return The Console instance
   */
  public static ErrorConsole getInstance() {
    return ourInstance;
  }
  
  /**
   * Writes a string to the error console
   * 
   * @param contents The output contents
   */
  @Override
  public void write(String contents) {
    //System.err.println(contents);
    System.out.println(contents);
  }

}