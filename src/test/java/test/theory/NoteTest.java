
package test.theory;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTest{

  @Test
  public void testString(){
    Note note;

    note = Note.fromString("C4[1]");
    assertEquals(note.toString(), "C4(48)[1.0]");

    note = Note.fromString("C4(48)[1]");
    assertEquals(note.toString(), "C4(48)[1.0]");

    note = Note.fromString("C4[1.0]");
    assertEquals(note.toString(), "C4(48)[1.0]");

    try{
      Note.fromString("C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4 (missing information)");
    }

    try{
      Note.fromString("C4[");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4[ (missing information)");
    }

    try{
      Note.fromString("C4]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4] (missing information)");
    }

    try{
      Note.fromString("[C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: [C4 (missing information)");
    }

    try{
      Note.fromString("]C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: ]C4 (missing information)");
    }

    try{
      Note.fromString("C4[1][1]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4[1][1] (contains extra information)");
    }

    try{
      Note.fromString("C4[1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4[1 (missing closing bracket)");
    }

    try{
      Note.fromString("C4[1]1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals(e.getMessage(), "Invalid note string: C4[1]1 (contains extra information)");
    }
  }

  @Test
  public void testHashCodeAndEquals(){
    Note a, b;

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[1]");
    assert(a.equals(b));
    assert(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[1.0]");
    assert(a.equals(b));
    assert(a.hashCode() == b.hashCode());

    a = Note.fromString("C#4[1]");
    b = Note.fromString("Db4[1]");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[0.5]");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("D4[1]");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C5[1]");
    assert(!a.equals(b));
    assert(a.hashCode() != b.hashCode());
  }
}
