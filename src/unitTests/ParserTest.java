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
    System.out.println(p.toString());
    
    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"this is a string"});
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput16() {
    CommandArgs p = Parser.parseUserInput("echo \"this is a string\" > \"myFile.txt\"");
    System.out.println(p.toString());
    
    CommandArgs c_wanted =
        new CommandArgs("echo", new String[]{"this is a string"}, ">", "myFile.txt");
    assertEquals(p, c_wanted);
  }
  
  @Test
  public void testUserInput17() {
    CommandArgs p = Parser.parseUserInput("mkdir \"myFile1.txt\" \"myFile2.txt\" myFile3.txt");
    System.out.println(p.toString());
    
    CommandArgs c_wanted =
        new CommandArgs("mkdir", new String[]{"myFile1.txt", "myFile2.txt", "myFile3.txt"});
    assertEquals(p, c_wanted);
  }
}
