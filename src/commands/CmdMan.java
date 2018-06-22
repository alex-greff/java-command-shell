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
package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import containers.CommandArgs;
import utilities.Command;

public class CmdMan extends Command {
  private final String NAME = "man";
  private final String DOCUMENTATION_PATH = "../documentation";

  private final String errorOutput = "Error invalid input, please try again.";

  @Override
  public String execute(CommandArgs args) {
    if (isValidArgs(args) == false) {
      return errorOutput;
    }

    File cmdManFile = null;

    URL url = getClass().getResource(DOCUMENTATION_PATH);
    File manDir = new File(url.getPath());

    FilenameFilter manFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith("Man") && name.endsWith(".txt");
      }
    };

    String manCmdName = args.getCommandParameters()[0];
    String targetName = "Man" + manCmdName.substring(0, 1).toUpperCase()
        + manCmdName.substring(1) + ".txt";
    
    for (File manFile : manDir.listFiles(manFilter)) {
      if (manFile.getName().equals(targetName)) {
        cmdManFile = manFile;
        break;
      }
    }

    if (cmdManFile != null) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(cmdManFile));

        String st = "";
        String output = "";

        while ((st = br.readLine()) != null) {
          output += st + "\n";
        }

        return output.trim();

      } catch (FileNotFoundException e) {
      } catch (IOException e) {
      }
    }

    return errorOutput;
  }

  private boolean isValidArgs(CommandArgs args) {
    return args.getCommandName().equals("man")
        && args.getCommandParameters().length == 1
        && args.getRedirectOperator().equals("")
        && args.getTargetDestination().equals("");
  }


  @Override
  public String getName() {
    return NAME;
  }

}
