package unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import containers.CommandDescription;
import org.junit.Test;
import utilities.CommandManager;

public class CommandManagerTest {

  private CommandManager CM = CommandManager.getInstance();

  @Test
  public void testInitialization() {
    CM.initializeCommands();
    assertFalse(CM.isCmdMapEmpty());
  }

  @Test
  public void testGetCommandDescription() {
    CommandDescription desc = CM.getCommandDescription("man");
    assertNotNull(desc);
  }

}
