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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import containers.CommandArgs;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class CommandArgsTest {

  @Test
  public void testGettersSimpleCommand() {
    CommandArgs ca = new CommandArgs("myCommand");
    assertEquals("myCommand", ca.getCommandName());
    assertArrayEquals(new String[0], ca.getCommandParameters());
    assertEquals("", ca.getRedirectOperator());
    assertEquals("", ca.getTargetDestination());

    Set<String> hs = new HashSet<>();
    HashMap<String, String> hm = new HashMap<>();
    assertEquals(hs, ca.getSetOfNamesCommandParameterKeys());
    assertNull(ca.getNamedCommandParameter("hi"));
    assertEquals(hm, ca.getNamedCommandParametersMap());
    assertEquals(0, ca.getNumberOfNamedCommandParameters());
  }

  @Test
  public void testCettersComplexCommand() {
    HashMap<String, String> hm_in = new HashMap<>();
    hm_in.put("type1", "value1");
    hm_in.put("type2", "value2");
    CommandArgs ca = new CommandArgs("myCommand",
        new String[]{"arg1", "arg2"},
        hm_in, ">", "hello.txt");
    assertEquals("myCommand", ca.getCommandName());
    assertArrayEquals(new String[]{"arg1", "arg2"},
        ca.getCommandParameters());
    assertEquals(">", ca.getRedirectOperator());
    assertEquals("hello.txt", ca.getTargetDestination());

    Set<String> hs = new HashSet<>();
    hs.add("type1");
    hs.add("type2");
    HashMap<String, String> hm = new HashMap<>();
    hm.put("type1", "value1");
    hm.put("type2", "value2");
    assertEquals(hs, ca.getSetOfNamesCommandParameterKeys());
    assertNull(ca.getNamedCommandParameter("hi"));
    assertEquals("value1", ca.getNamedCommandParameter("type1"));
    assertEquals("value2", ca.getNamedCommandParameter("type2"));
    assertEquals(hm, ca.getNamedCommandParametersMap());
    assertEquals(2, ca.getNumberOfNamedCommandParameters());
  }


  @Test
  public void testEqualsSimpleCommand() {
    CommandArgs ca1 = new CommandArgs("myCommand");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEqualsWithArgs() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    assertEquals(ca1, ca2);
  }

  @Test
  public void testNotEqualsWithArgs() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[]{"arg1", "arg2"});
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertNotEquals(ca1, ca2);
  }

  @Test
  public void testEqualsWithNoArgs() {
    CommandArgs ca1 = new CommandArgs("myCommand", new String[0]);
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testNotEqualsOneWithRedirect() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertNotEquals(ca1, ca2);
  }

  @Test
  public void testEqualsBothWithRedirects() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEqualsBothWithFieldsAndRedirect() {
    HashMap<String, String> hm_1 = new HashMap<>();
    hm_1.put("type1", "value1");
    hm_1.put("type2", "value2");

    HashMap<String, String> hm_2 = new HashMap<>();
    hm_2.put("type2", "value2");
    hm_2.put("type1", "value1");

    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], hm_1, ">",
            "hello.txt");
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[0], hm_2, ">",
            "hello.txt");
    assertEquals(ca1, ca2);
  }

  @Test
  public void testEqualsBothWithFields() {
    HashMap<String, String> hm_1 = new HashMap<>();
    hm_1.put("type1", "value1");
    hm_1.put("type2", "value2");

    HashMap<String, String> hm_2 = new HashMap<>();
    hm_2.put("type2", "value2");
    hm_2.put("type1", "value1");

    CommandArgs ca1 = new CommandArgs("myCommand", hm_1);
    CommandArgs ca2 = new CommandArgs("myCommand", hm_2);
    assertEquals(ca1, ca2);
  }
}
