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
    
    FSElement new_fse = fse.clone();
    assertEquals("myName", new_fse.getName());
    assertEquals("myParent", new_fse.getParent().getName());
  }
  
  @Test
  public void testCloneNoParent() {
    FSElement fse = new FSElement("myName", null);
    
    FSElement new_fse = fse.clone();
    assertEquals("myName", new_fse.getName());
    assertNull(new_fse.getParent());
  }
}
