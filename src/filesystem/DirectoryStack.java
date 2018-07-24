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
package filesystem;

import java.util.Stack;

/**
 * A singleton stack of directories used for popd and pushd commands Stores the
 * paths of directories added to it.
 *
 * @author anton
 */
public class DirectoryStack extends Stack<String> {

  /**
   * The instance of the directory stack.
   */
  private static DirectoryStack ourInstance = null;

  /**
   * Private default constructor.
   */
  private DirectoryStack() {
  }

  /**
   * Gets the singleton directory stack instance.
   *
   * @return Returns the singleton directory stack instance.
   */
  public static DirectoryStack getInstance() {
    if (ourInstance == null) {
      ourInstance = new DirectoryStack();
    }
    return ourInstance;
  }
}
