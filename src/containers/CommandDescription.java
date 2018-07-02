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

/**
 * A container storing the description of a command.
 *
 * @author greff
 */
public class CommandDescription {
  /**
   * The description of the command.
   */
  private String description = "";
  /**
   * The usages of the command.
   */
  private String[] usages = new String[0];
  /**
   * The additional comments for the command, if any.
   */
  private String[] additionalComments = new String[0];

  /**
   * Constructor for initializing the command description.
   *
   * @param description A brief description of the command.
   * @param usages A list of possible usages of the command.
   * @param additionalComments Any additional comments of the command.
   */
  public CommandDescription(String description, String[] usages,
      String[] additionalComments) {
    this.description = description;
    this.usages = usages;
    this.additionalComments = additionalComments;
  }

  /**
   * Constructor for initializing the command description.
   *
   * @param description A brief description of the command.
   * @param usages A list of possible usages of the command.
   */
  public CommandDescription(String description, String[] usages) {
    this(description, usages, new String[0]);
  }

  /**
   * Gets the description of the command.
   *
   * @return Returns the description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the usages for the command.
   *
   * @return Returns an array of usages.
   */
  public String[] getUsages() {
    return usages;
  }

  /**
   * Gets the additional comments for the command.
   *
   * @return Returns any additional comments.
   */
  public String[] getAdditionalComments() {
    return additionalComments;
  }
}
