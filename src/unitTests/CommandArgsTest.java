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
    CommandArgs ca = new CommandArgs("myCommand", new String[] {"arg1", "arg2"},
        ">", "hello.txt");
    assertEquals("myCommand", ca.getCommandName());
    assertArrayEquals(new String[] {"arg1", "arg2"}, ca.getCommandParameters());
    assertEquals(">", ca.getRedirectOperator());
    assertEquals("hello.txt", ca.getTargetDestination());
  }


  @Test
  public void testEquals1() {
    CommandArgs ca1 = new CommandArgs("myCommand");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(true, ca1.equals(ca2));
  }

  @Test
  public void testEquals2() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[] {"arg1", "arg2"});
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[] {"arg1", "arg2"});
    assertEquals(true, ca1.equals(ca2));
  }

  @Test
  public void testEquals3() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[] {"arg1", "arg2"});
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(false, ca1.equals(ca2));
  }

  @Test
  public void testEquals4() {
    CommandArgs ca1 = new CommandArgs("myCommand", new String[0]);
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(true, ca1.equals(ca2));
  }

  @Test
  public void testEquals5() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 = new CommandArgs("myCommand");
    assertEquals(false, ca1.equals(ca2));
  }
  
  @Test
  public void testEquals6() {
    CommandArgs ca1 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    CommandArgs ca2 =
        new CommandArgs("myCommand", new String[0], ">", "hello.txt");
    assertEquals(true, ca1.equals(ca2));
  }
}
