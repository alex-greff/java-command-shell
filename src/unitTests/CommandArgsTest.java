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

import static org.junit.Assert.*;

import org.junit.Test;

import containers.CommandArgs;

public class CommandArgsTest {

  @Test
  public void testGetters1() {
    CommandArgs ca = new CommandArgs("myCommand");
    assertEquals("myCommand", ca.getCommandName());
    assertArrayEquals(new String[0], ca.getCommandParameters());
    assertEquals("", ca.getRedirectOperator());
    assertEquals("", ca.getTargetDestination());
  }

  @Test
  public void testGetters2() {
    CommandArgs ca = new CommandArgs("myCommand", new String[]{"arg1", "arg2"},
        ">", "hello.txt");
    assertEquals("myCommand", ca.getCommandName());
    assertArrayEquals(new String[]{"arg1", "arg2"}, ca.getCommandParameters());
    assertEquals(">", ca.getRedirectOperator());
    assertEquals("hello.txt", ca.getTargetDestination());
  }


  @Test
  public void testEquals1() {
    CommandArgs ca1 = new CommandArgs("myCommand");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEquals2() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEquals3() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertNotEquals(ca1, ca2);
  }

  @Test
  public void testEquals4() {
    CommandArgs ca1 = new CommandArgs("myCommand", new String[0]);
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEquals5() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertNotEquals(ca1, ca2);
  }

  @Test
  public void testEquals6() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    assertEquals(ca1, ca2);
  }
}
