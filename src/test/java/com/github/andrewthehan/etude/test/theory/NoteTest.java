
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTest{

  @Test
  public void testString(){
    Note note;

    note = Note.fromString("C4[1]");
    assertEquals("C4(48)[WHOLE]", note.toString());

    note = Note.fromString("C4(48)[1]");
    assertEquals("C4(48)[WHOLE]", note.toString());

    note = Note.fromString("C4[1.0]");
    assertEquals("C4(48)[WHOLE]", note.toString());

    note = Note.fromString("C4[WHOLE]");
    assertEquals("C4(48)[WHOLE]", note.toString());

    try{
      Note.fromString(null);
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: null", e.getMessage());
    }

    try{
      Note.fromString("");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string:  (blank)", e.getMessage());
    }

    try{
      Note.fromString("  ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string:    (blank)", e.getMessage());
    }

    try{
      Note.fromString("C4[ ");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4[  (missing information)", e.getMessage());
    }

    try{
      Note.fromString(" [1]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string:  [1] (missing information)", e.getMessage());
    }

    try{
      Note.fromString("C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4 (missing information)", e.getMessage());
    }

    try{
      Note.fromString("C4[");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4[ (missing information)", e.getMessage());
    }

    try{
      Note.fromString("C4]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4] (missing information)", e.getMessage());
    }

    try{
      Note.fromString("[C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: [C4 (missing information)", e.getMessage());
    }

    try{
      Note.fromString("]C4");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: ]C4 (missing information)", e.getMessage());
    }

    try{
      Note.fromString("C4[1][1]");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4[1][1] (contains extra information)", e.getMessage());
    }

    try{
      Note.fromString("C4[1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4[1 (missing closing bracket)", e.getMessage());
    }

    try{
      Note.fromString("C4[1]1");
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Invalid note string: C4[1]1 (contains extra information)", e.getMessage());
    }
  }

  @Test
  public void testHashCodeAndEquals(){
    Note a, b;

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[1]");
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[1.0]");
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Note.fromString("C#4[1]");
    b = Note.fromString("Db4[1]");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C4[0.5]");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("D4[1]");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    b = Note.fromString("C5[1]");
    assertFalse(a.equals(b));
    assertTrue(a.hashCode() != b.hashCode());

    a = Note.fromString("C4[1]");
    assertFalse(a.equals("C4[1]"));

    a = Note.fromString("C4[1]");
    assertTrue(a.equals(a));
  }
}
