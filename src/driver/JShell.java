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
// UT Student #: 1004331992
// Author: Anton Kaminsky
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;
import containers.*;
import commands.*;
import utilities.*;

public class JShell {
	//to be reimplemented with other directory solution
	private static String defaultPath = "/#";
	private static String addPath = "";
	// probably wanna store path as some other object?

	// rethink exitCondition object
	private static boolean exitCond=true;

	

  public static void main(String[] args) {
    // create means of attaining User Input (scanner may be replaced)
    String rawInput;
		Scanner scanIn = new Scanner(System.in);
		// create while loop which only exits once the exit command is called
		// send user input to parser, then validate, then execute
    while(exitCond){
    	System.out.print(defaultPath + addPath + " ");
    	rawInput = scanIn.nextLine();
    	CommandArgs parsedInput = Parser.parseUserInput(rawInput);
    	if (parsedInput != null){
				// execute that shit
			}
    	// find means of exiting shell
		}

    // use means of outputting to output data to the right destination
    // which can be out to system or file

  }

}
