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
package utilities;

import containers.CommandArgs;
import containers.CommandDescription;
import filesystem.FileSystem;
import io.Writable;

/**
 * The abstract command class that all commands inherit from.
 *
 * @author greff
 */
public abstract class Command {
  /**
   * The command's name
   */
  protected String NAME;

  /**
   * The command's description
   */
  protected CommandDescription DESCRIPTION;

  protected FileSystem fileSystem;

  protected CommandManager commandManager;

  /**
   * Hide default constructor.
   */
  private Command() {

  }

  /**
   * Constructs a new command instance
   *
   * @param fileSystem The file system the command uses
   */
  public Command(FileSystem fileSystem, CommandManager commandManager) {
    this.fileSystem = fileSystem;
    this.commandManager = commandManager;
  }

  /**
   * Constructs a new command instance
   *
   * @param name The name of the command
   * @param description The description of the command
   * @param fileSystem The file system the command uses
   */
  public Command(String name, CommandDescription description,
                 FileSystem fileSystem, CommandManager commandManager) {

    this(fileSystem, commandManager);
    this.NAME = name;
    this.DESCRIPTION = description;
  }

  /**
   * Executes the command's function.
   *
   * @param args The arguments for the command call.
   * @param out The standard output console.
   * @param errorOut The error output console.
   * @return Returns the exit condition of the command.
   */
  public abstract ExitCode execute(CommandArgs args, Writable out,
                                   Writable errorOut);

  /**
   * Checks if the given args are valid for this command.
   *
   * @param args The command arguments.
   * @return Returns true iff the args are valid.
   */
  public abstract boolean isValidArgs(CommandArgs args);

  /**
   * Gets the name of the command.
   *
   * @return Returns the name of the command.
   */
  public String getName() {
    return this.NAME;
  }

  /**
   * Gets the CommandDescription object for the command.
   *
   * @return Returns the CommandDescription object for the command.
   */
  public CommandDescription getDescription() {
    return this.DESCRIPTION;
  }
}
