package unitTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import containers.CommandArgs;
import containers.CommandDescription;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class CommandDescriptionTest {
  @Test
  public void test_getters_container_with_description_and_usages() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[] {"usage 1", "usage 2"});
    
    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[] {"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[0], cd.getAdditionalComments());
  }
  
  @Test
  public void test_getters_container_with_additional_comments() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[] {"usage 1", "usage 2"}, new String[] {"some cool thing"});
    
    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[] {"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[] {"some cool thing"}, cd.getAdditionalComments());
  }
}
