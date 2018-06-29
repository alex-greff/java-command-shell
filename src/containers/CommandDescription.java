package containers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A container storing the description of a command.
 * 
 * @author greff
 *
 */
public class CommandDescription {
  private String description = "";
  private String[] usages = new String[0];
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
