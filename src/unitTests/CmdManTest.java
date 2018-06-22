package unitTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import commands.CmdMan;
import containers.CommandArgs;
import utilities.Command;

public class CmdManTest {
  @Test
  public void testGetters1() {
    CommandArgs args = new CommandArgs("man", new String[] { "man" });
    
    Command cmd = new CmdMan();
    String out_actual = cmd.execute(args);
    
    String out_expected = "TODO"; // TODO:
    
    System.out.println(out_actual);
    
    assertEquals(out_expected, out_actual);
  }
}
