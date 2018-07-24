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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import filesystem.Directory;
import filesystem.FSElement;

public class FSElementTest {
  @Test
  public void testGetName() {
    FSElement fse = new FSElement("myName", null);
    assertEquals("myName", fse.getName());
  }

  @Test
  public void testGetParentNoParent() {
    FSElement fse = new FSElement("myName", null);
    assertNull(fse.getParent());
  }

  @Test
  public void testGetParentWithParent() {
    Directory parent = new Directory("myParent", null);
    FSElement fse = new FSElement("myName", parent);
    parent.addChild(fse);

    assertEquals("myParent", fse.getParent().getName());
  }

  @Test
  public void testRenameWithParent() {
    Directory parent = new Directory("myParent", null);
    FSElement fse = new FSElement("myName", parent);
    parent.addChild(fse);
    fse.rename("myNewName");

    assertNotNull(parent.getChildByName("myNewName"));
  }

  @Test
  public void testRenameWithoutParent() {
    FSElement fse = new FSElement("myName", null);
    fse.rename("myNewName");

    assertNull(fse.getParent());
  }

  @Test
  public void testChangeParent() {
    FSElement fse = new FSElement("myName", null);
    assertNull(fse.getParent());
    Directory newParent = new Directory("newParent", null);
    fse.changeParent(newParent);
    assertEquals(newParent, fse.getParent());
  }
  
  @Test
  public void testCloneWithParent() {
    Directory parent = new Directory("myParent", null);
    FSElement fse = new FSElement("myName", parent);
    
    FSElement new_fse = fse.copy();
    assertEquals("myName", new_fse.getName());
    assertEquals("myParent", new_fse.getParent().getName());
  }
  
  @Test
  public void testCloneNoParent() {
    FSElement fse = new FSElement("myName", null);
    
    FSElement new_fse = fse.copy();
    assertEquals("myName", new_fse.getName());
    assertNull(new_fse.getParent());
  }
}
