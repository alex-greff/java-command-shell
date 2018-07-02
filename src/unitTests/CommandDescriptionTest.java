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

import containers.CommandDescription;
import org.junit.Test;

public class CommandDescriptionTest {

  @SuppressWarnings("deprecation")
  @Test
  public void testGettersContainerWithDescriptionAndUsages() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[]{"usage 1", "usage 2"});

    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[]{"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[0], cd.getAdditionalComments());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testGettersContainerWithAdditionalComments() {
    CommandDescription cd = new CommandDescription("some basic description",
        new String[]{"usage 1", "usage 2"}, new String[]{"some cool thing"});

    assertEquals("some basic description", cd.getDescription());
    assertEquals(new String[]{"usage 1", "usage 2"}, cd.getUsages());
    assertEquals(new String[]{"some cool thing"}, cd.getAdditionalComments());
  }
}
