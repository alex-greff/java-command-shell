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
    System.err.println(contents);;
  }

}
