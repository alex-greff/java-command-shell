package unitTests;

import static org.junit.Assert.assertEquals;
import containers.CommandDescription;
import org.junit.Test;

public class CommandDescriptionTest {
  @SuppressWarnings("deprecation")
  @Test
  public void testGettersContainerWithDescriptionAndUsages() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[] {"usage 1", "usage 2"});
    
    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[] {"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[0], cd.getAdditionalComments());
  }
  
  @SuppressWarnings("deprecation")
  @Test
  public void testGettersContainerWithAdditionalComments() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[] {"usage 1", "usage 2"}, new String[] {"some cool thing"});
    
    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[] {"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[] {"some cool thing"}, cd.getAdditionalComments());
  }
}
