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
import utilities.InvalidBooleanInputException;
import utilities.Parser;
import utilities.UserDecision;

public class ParserTest {

  /*
   * Tests with simple input
   */
  @Test
  public void testUserInputMultipleArguments() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"});
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
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"}, ">", "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleArgumentsAndFileAppend() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 >> hi.txt");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"}, ">>", "hi.txt");
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
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"});
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
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"}, ">", "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputOnlyTabSeparators() {
    CommandArgs p = Parser.parseUserInput("\tmyCmd\targ1\targ2\t>>\thi.txt\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[] {"arg1", "arg2"}, ">>", "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputStringParameter() {
    CommandArgs p = Parser.parseUserInput("echo \"this is a string\"");

    CommandArgs c_wanted =
        new CommandArgs("echo", new String[] {"\"this is a string\""});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputStringArgumentsAndRedirect() {
    CommandArgs p =
        Parser.parseUserInput("echo \"this is a string\" > \"myFile.txt\"");

    CommandArgs c_wanted = new CommandArgs("echo",
        new String[] {"\"this is a string\""}, ">", "\"myFile.txt\"");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleStringArguments() {
    CommandArgs p = Parser
        .parseUserInput("mkdir \"myFile1.txt\" \"myFile2.txt\" myFile3.txt");

    CommandArgs c_wanted = new CommandArgs("mkdir",
        new String[] {"\"myFile1.txt\"", "\"myFile2.txt\"", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputMultipleStringArgsWithMultipleSpacesTabs() {
    CommandArgs p = Parser.parseUserInput(
        "mkdir    \"myFile1.txt\" \t \"myFile2.txt\" \tmyFile3.txt");

    CommandArgs c_wanted = new CommandArgs("mkdir",
        new String[] {"\"myFile1.txt\"", "\"myFile2.txt\"", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputRedirectCharacterAsStringArgument() {
    CommandArgs p = Parser.parseUserInput("echo myString \">\" ");

    CommandArgs c_wanted =
        new CommandArgs("echo", new String[] {"myString", "\">\""});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithFields() {
    CommandArgs p = Parser.parseUserInput("find someDir -type d -name hi");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "d");
    map.put("name", "hi");

    CommandArgs c_wanted =
        new CommandArgs("find", new String[] {"someDir"}, map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testInvalidUserInputWithEmptyFeilds() {
    CommandArgs p = Parser.parseUserInput("find someDir -type -name hi");

    assertNull(p);
  }

  @Test
  public void testUserInputWithNamedStringFeilds() {
    CommandArgs p =
        Parser.parseUserInput("find -type \"d\" -name \"some string\"");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "\"d\"");
    map.put("name", "\"some string\"");

    CommandArgs c_wanted = new CommandArgs("find", map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithNamedStringFeildsMultipleSpaceTabs() {
    CommandArgs p = Parser.parseUserInput(
        "  \t  find\t\tsomeDir\t  \t  -type d    -name    \"hi\"    ");

    HashMap<String, String> map = new HashMap<>();
    map.put("type", "d");
    map.put("name", "\"hi\"");

    CommandArgs c_wanted =
        new CommandArgs("find", new String[] {"someDir"}, map);
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithSringFeilds() {
    CommandArgs p = Parser.parseUserInput("ls -R");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {},
        new String[] {"R"}, new HashMap());

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithSringFeildsAndParams() {
    CommandArgs p = Parser.parseUserInput("ls -R something");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"R"}, new HashMap());

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithSringFeildsAndParamsAndNamedParams() {
    CommandArgs p = Parser.parseUserInput("ls -R something -f lol");

    HashMap<String, String> map = new HashMap<>();
    map.put("f", "lol");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"R"}, map);

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithMultipleSringFeildsAndParamsAndNamedParams() {
    CommandArgs p =
        Parser.parseUserInput("ls -P -R something -f lol -d \"cool\"");

    HashMap<String, String> map = new HashMap<>();
    map.put("f", "lol");
    map.put("d", "\"cool\"");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"P", "R"}, map);

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithSringFeildsAndParamsWithMultipleSpaceTabs() {
    CommandArgs p = Parser.parseUserInput("ls   \t-R  something   ");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"R"}, new HashMap());

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithSringFeildsAndParamsAndNamedParamsWithSpaces() {
    CommandArgs p = Parser.parseUserInput("\tls\t\t-R something  -f    lol");

    HashMap<String, String> map = new HashMap<>();
    map.put("f", "lol");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"R"}, map);

    assertEquals(p, c_wanted);
  }

  @Test
  public void testInputMultipleSringFeildsAndParamsAndNamedParamsWithSpaces() {
    CommandArgs p = Parser.parseUserInput(
        "\tls  \t -P  -R   \tsomething\t\t-f lol -d   \"cool\"");

    HashMap<String, String> map = new HashMap<>();
    map.put("f", "lol");
    map.put("d", "\"cool\"");

    CommandArgs c_wanted = new CommandArgs("ls", new String[] {"something"},
        new String[] {"P", "R"}, map);

    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithNewlineInsideOfStringField() {
    CommandArgs p = Parser.parseUserInput("echo \"some\ntext\"");

    CommandArgs c_wanted =
        new CommandArgs("echo", new String[] {"\"some\ntext\""});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithRecallCommandWithSmallNumber() {
    CommandArgs p = Parser.parseUserInput("!3");

    CommandArgs c_wanted = new CommandArgs("recall", new String[] {"3"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithRecallCommandWithLargeNumber() {
    CommandArgs p = Parser.parseUserInput("!567");

    CommandArgs c_wanted = new CommandArgs("recall", new String[] {"567"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testInvalidUserInputWithRecallCommandWithNoNumber() {
    CommandArgs p = Parser.parseUserInput("!");

    assertNull(p);
  }

  @Test
  public void testUserInputWithRecallCommandWithSmallNumberWithMultSpaces() {
    CommandArgs p = Parser.parseUserInput("\t  !3 \t \t    ");

    CommandArgs c_wanted = new CommandArgs("recall", new String[] {"3"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInputWithRecallCommandWithOtherParams() {
    CommandArgs p = Parser.parseUserInput("!567 lol \"hi there\"");

    CommandArgs c_wanted =
        new CommandArgs("recall", new String[] {"567", "lol", "\"hi there\""});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testBooleanDecisionInputWithTrueInput()
      throws InvalidBooleanInputException {
    UserDecision ud = Parser.parseBooleanDecisionInput("yes", false);
    assertEquals(UserDecision.YES, ud);
    ud = Parser.parseBooleanDecisionInput("y", false);
    assertEquals(UserDecision.YES, ud);
    ud = Parser.parseBooleanDecisionInput("true", false);
    assertEquals(UserDecision.YES, ud);
    ud = Parser.parseBooleanDecisionInput("1", false);
    assertEquals(UserDecision.YES, ud);
    ud = Parser.parseBooleanDecisionInput("t", false);
    assertEquals(UserDecision.YES, ud);
    ud = Parser.parseBooleanDecisionInput("positive", false);
    assertEquals(UserDecision.YES, ud);
  }

  @Test
  public void testBooleanDecisionInputWithFalseInput()
      throws InvalidBooleanInputException {
    UserDecision ud = Parser.parseBooleanDecisionInput("no", false);
    assertEquals(UserDecision.NO, ud);
    ud = Parser.parseBooleanDecisionInput("n", false);
    assertEquals(UserDecision.NO, ud);
    ud = Parser.parseBooleanDecisionInput("false", false);
    assertEquals(UserDecision.NO, ud);
    ud = Parser.parseBooleanDecisionInput("0", false);
    assertEquals(UserDecision.NO, ud);
    ud = Parser.parseBooleanDecisionInput("f", false);
    assertEquals(UserDecision.NO, ud);
    ud = Parser.parseBooleanDecisionInput("negative", false);
    assertEquals(UserDecision.NO, ud);
  }

  @Test(expected = InvalidBooleanInputException.class)
  public void testBooleanDecisionInputWithCancelWithNotCancellableOptionSet()
      throws InvalidBooleanInputException {
    UserDecision ud = Parser.parseBooleanDecisionInput("cancel", false);
  }

  @Test
  public void testBooleanDecisionInputWithCancelWithCancellableOptionSet()
      throws InvalidBooleanInputException {
    UserDecision ud = Parser.parseBooleanDecisionInput("cancel", true);
    assertEquals(UserDecision.CANCEL, ud);
    ud = Parser.parseBooleanDecisionInput("c", true);
    assertEquals(UserDecision.CANCEL, ud);
    ud = Parser.parseBooleanDecisionInput("belay", true);
    assertEquals(UserDecision.CANCEL, ud);
    ud = Parser.parseBooleanDecisionInput("2", true);
    assertEquals(UserDecision.CANCEL, ud);
    ud = Parser.parseBooleanDecisionInput("-1", true);
    assertEquals(UserDecision.CANCEL, ud);
  }
}
