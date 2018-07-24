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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import containers.CommandDescription;
import java.util.Collections;
import org.junit.Test;

public class CommandDescriptionTest {

  @Test
  public void testGettersContainerWithDescriptionAndUsages() {
    CommandDescription cd =
        new CommandDescription.DescriptionBuilder("some basic description",
                                                  "usage 1").usage("usage 2")
            .build();

    assertEquals(asList("some basic description"), cd.getDescription());
    assertEquals(asList("usage 1", "usage 2"), cd.getUsages());
    assertEquals(Collections.emptyList(), cd.getAdditionalComments());
  }

  @Test
  public void testGettersContainerWithAdditionalComments() {
    CommandDescription cd =
        new CommandDescription.DescriptionBuilder("some basic description",
                                                  "usage 1").usage("usage 2")
            .additionalComment("some cool thing")
            .build();

    assertEquals(asList("some basic description"), cd.getDescription());
    assertEquals(asList("usage 1", "usage 2"), cd.getUsages());
    assertEquals(Collections.singletonList("some cool thing"),
                 cd.getAdditionalComments());
  }
}
