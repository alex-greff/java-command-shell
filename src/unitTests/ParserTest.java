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
package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import containers.CommandArgs;
import java.util.HashMap;
import org.junit.Test;
import utilities.Parser;

public class ParserTest {

  /*
   * Tests with simple input
   */
  @Test
  public void testUserInputMultipleArguments() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputOnlyCommand() {
    CommandArgs p = Parser.parseUserInput("myCmd");
    CommandArgs c_wanted = new CommandArgs("myCmd");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleArgumentsAndFileOverwrite() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 > hi.txt");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">",
                        "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleArgumentsAndFileAppend() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 >> hi.txt");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">>",
                        "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testInvalidUserInputMultipleArgsAndIncompleteRedirect() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 > ");
    assertNull(p);
  }

  @Test
  public void testInvalidUserInputNoArgsAndIncompleteRedirect() {
    CommandArgs p = Parser.parseUserInput("myCmd >");
    assertNull(p);
  }

  @Test
  public void testInvalidUserInputBlankString() {
    CommandArgs p = Parser.parseUserInput("");
    assertNull(p);
  }

  @Test
  public void testInvalidUserInputOnlySpaces() {
    CommandArgs p = Parser.parseUserInput("    ");
    assertNull(p);
  }

  @Test
  public void testInvalidUserInputOnlyTab() {
    CommandArgs p = Parser.parseUserInput("\t");
    assertNull(p);
  }

  @Test
  public void testInvalidUserInputOnlyTabsAndSpaces() {
    CommandArgs p = Parser.parseUserInput("   \t  \t ");
    assertNull(p);
  }

  @Test
  public void testUserInputMultipleArgsAndSpaces() {
    CommandArgs p = Parser.parseUserInput("myCmd    arg1   arg2\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputNoArgsMultipleSpacesAndTabs() {
    CommandArgs p = Parser.parseUserInput("     myCmd\t");
    CommandArgs c_wanted = new CommandArgs("myCmd");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputArgsAndRedirectWithMultipleSpacesTabs() {
    CommandArgs p = Parser.parseUserInput("  myCmd arg1 arg2 >   hi.txt\t\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">",
                        "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputOnlyTabSeparators() {
    CommandArgs p = Parser.parseUserInput("\tmyCmd\targ1\targ2\t>>\thi.txt\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">>",
                        "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputStringParameter() {
    CommandArgs p = Parser.parseUserInput("echo \"this is a string\"");

    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"this is a string"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputStringArgumentsAndRedirect() {
    CommandArgs p =
        Parser.parseUserInput(
            "echo \"this is a string\" > \"myFile.txt\"");

    CommandArgs c_wanted = new CommandArgs("echo",
                                           new String[]{"this is a string"},
                                           ">", "myFile.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleStringArguments() {
    CommandArgs p = Parser
        .parseUserInput(
            "mkdir \"myFile1.txt\" \"myFile2.txt\" myFile3.txt");

    CommandArgs c_wanted = new CommandArgs("mkdir",
                                           new String[]{"myFile1.txt",
                                               "myFile2.txt", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleStringArgsWithMultipleSpacesTabs() {
    CommandArgs p = Parser.parseUserInput(
        "mkdir    \"myFile1.txt\" \t \"myFile2.txt\" \tmyFile3.txt");

    CommandArgs c_wanted = new CommandArgs("mkdir",
                                           new String[]{"myFile1.txt",
                                               "myFile2.txt", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputRedirectCharacterAsStringArgument() {
    CommandArgs p = Parser.parseUserInput("echo myString \">\" ");

    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"myString", ">"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithFields() {
    CommandArgs p = Parser.parseUserInput("find someDir -type d -name hi");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "d");
    map.put("name", "hi");

    CommandArgs c_wanted =
        new CommandArgs("find", new String[]{"someDir"}, map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testInvalidUserInputWithEmptyFeilds() {
    CommandArgs p = Parser.parseUserInput("find someDir -type -name hi");

    assertNull(p);
  }

  @Test
  public void testUserInputWithStringFeilds() {
    CommandArgs p =
        Parser
            .parseUserInput("find -type \"d\" -name \"some string\"");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "d");
    map.put("name", "some string");

    CommandArgs c_wanted = new CommandArgs("find", map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithStringFeildsMultipleSpaceTabs() {
    CommandArgs p = Parser.parseUserInput(
        "  \t  find\t\tsomeDir\t  \t  -type d    -name    \"hi\"    ");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "d");
    map.put("name", "hi");

    CommandArgs c_wanted =
        new CommandArgs("find", new String[]{"someDir"}, map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithNewlineInsideOfStringField() {
    CommandArgs p = Parser.parseUserInput("echo \"some\ntext\"");

    CommandArgs c_wanted = new CommandArgs("echo",
                                           new String[]{"some\ntext"});
    assertEquals(p, c_wanted);
  }
}
