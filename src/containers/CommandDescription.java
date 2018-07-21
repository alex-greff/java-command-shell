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

import java.util.ArrayList;

/**
 * A container storing the description of a command.
 *
 * @author greff
 */
public class CommandDescription {

  /**
   * The description of the command.
   */
  private ArrayList<String> description;
  /**
   * The usages of the command.
   */
  private ArrayList<String> usages;
  /**
   * The additional comments for the command, if any.
   */
  private ArrayList<String> additionalComments;

  /**
   * Private constructor.
   *
   * @param description The description of the command.
   * @param usages The usages for the command.
   * @param additionalComments Any additional comments.
   */
  private CommandDescription(ArrayList<String> description, ArrayList<String> usages,
                             ArrayList<String> additionalComments) {
    this.description = description;
    this.usages = usages;
    this.additionalComments = additionalComments;
  }

  /**
   * Gets the description of the command.
   *
   * @return Returns the description.
   */
  public ArrayList<String> getDescription() {
    return description;
  }

  /**
   * Gets the usages for the command.
   *
   * @return Returns an array of usages.
   */
  public ArrayList<String> getUsages() {
    return usages;
  }

  /**
   * Gets the additional comments for the command.
   *
   * @return Returns any additional comments.
   */
  public ArrayList<String> getAdditionalComments() {
    return additionalComments;
  }

  /**
   * Internal builder class used to simplify the creation of the comments
   * class.
   *
   * @author anton
   */
  public static class DescriptionBuilder {

    // mirror all the fields of the comments class
    /**
     * The description.
     */
    private ArrayList<String> description = new ArrayList<>();
    /**
     * The usages.
     */
    private ArrayList<String> usages = new ArrayList<>();
    /**
     * Additional comments, if any.
     */
    private ArrayList<String> comments = new ArrayList<>();

    /**
     * Creates a new description builder with required params
     *
     * @param description The description of the command
     * @param usage Usage info for the command
     */
    public DescriptionBuilder(String description, String usage) {
      this.description.add(description);
      usages.add(usage);
    }

    public DescriptionBuilder description(String description) {
      this.description.add(description);
      return this;
    }
    
    /**
     * Adds additional usage info
     *
     * @param usage A string with some usage info
     * @return The current description builder for chaining
     */
    public DescriptionBuilder usage(String usage) {
      usages.add(usage);
      return this;
    }

    /**
     * add additiona
     *
     * @param comment An additional comment
     * @return Current description builder for chaining
     */
    public DescriptionBuilder additionalComment(String comment) {
      comments.add(comment);
      return this;
    }

    /**
     * Builds an instance of a CommandDescription with the currently set
     * parameters
     *
     * @return A new CommandDescription instance with all the configuration set
     */
    public CommandDescription build() {
      return new CommandDescription(description, usages, comments);
    }
  }
}
