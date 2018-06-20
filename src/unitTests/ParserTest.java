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
import org.junit.Test;
import utilities.Parser;

public class ParserTest {
  /*
   * Tests with simple input
   */
  @Test
  public void testUserInput1() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput2() {
    CommandArgs p = Parser.parseUserInput("myCmd");
    CommandArgs c_wanted =
        new CommandArgs("myCmd");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput3() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 > hi.txt");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">",
            "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput4() {
    CommandArgs p = Parser
        .parseUserInput("myCmd arg1 arg2 >> hi.txt");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">>",
            "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput5() {
    CommandArgs p = Parser.parseUserInput("myCmd arg1 arg2 > ");
    assertNull(p);
  }

  @Test
  public void testUserInput6() {
    CommandArgs p = Parser.parseUserInput("myCmd >");
    assertNull(p);
  }

  /*
   * Tests with extra whitespace and tabs
   */
  @Test
  public void testUserInput7() {
    CommandArgs p = Parser.parseUserInput("");
    assertNull(p);
  }

  @Test
  public void testUserInput8() {
    CommandArgs p = Parser.parseUserInput("    ");
    assertNull(p);
  }

  @Test
  public void testUserInput9() {
    CommandArgs p = Parser.parseUserInput("\t");
    assertNull(p);
  }

  @Test
  public void testUserInput10() {
    CommandArgs p = Parser.parseUserInput("   \t   ");
    assertNull(p);
  }

  @Test
  public void testUserInput11() {
    CommandArgs p = Parser.parseUserInput("myCmd    arg1   arg2\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"});
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput12() {
    CommandArgs p = Parser.parseUserInput("     myCmd\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput13() {
    CommandArgs p = Parser
        .parseUserInput("  myCmd arg1 arg2 >   hi.txt\t\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">",
            "hi.txt");
    assertEquals(p, c_wanted);
  }

  @Test
  public void testUserInput14() {
    CommandArgs p = Parser
        .parseUserInput("\tmyCmd\targ1\targ2\t>>\thi.txt\t");
    CommandArgs c_wanted =
        new CommandArgs("myCmd", new String[]{"arg1", "arg2"}, ">>",
            "hi.txt");
    assertEquals(p, c_wanted);
  }
  
  /*
   * Tests with string parameters
   */
  @Test
  public void testUserInput15() {
    CommandArgs p = Parser.parseUserInput("echo \"this is a string\"");
    
    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"this is a string"});
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput16() {
    CommandArgs p = Parser.parseUserInput("echo \"this is a string\" > \"myFile.txt\"");
    
    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"this is a string"}, ">", "myFile.txt");
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput17() {
    CommandArgs p = Parser.parseUserInput("mkdir \"myFile1.txt\" \"myFile2.txt\" myFile3.txt");
    
    CommandArgs c_wanted =
        new CommandArgs("mkdir", new String[]{"myFile1.txt", "myFile2.txt", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput18() {
    CommandArgs p = Parser.parseUserInput("mkdir    \"myFile1.txt\" \t \"myFile2.txt\" \tmyFile3.txt");
    
    CommandArgs c_wanted =
        new CommandArgs("mkdir", new String[]{"myFile1.txt", "myFile2.txt", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput19() {
    CommandArgs p = Parser.parseUserInput("echo myString \">\" ");
    
    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"myString", ">"});
    assertEquals(p, c_wanted);
  }
}
