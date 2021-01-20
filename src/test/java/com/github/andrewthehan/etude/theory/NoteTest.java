
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.andrewthehan.etude.exception.EtudeException;

import org.junit.Test;

public class NoteTest {

  @Test
  public void testString() {
    Note note;

    note = Note.fromString("C4[WHOLE(1)]").get();
    assertEquals("C4(48)[WHOLE(1.0)]", note.toString());

    note = Note.fromString("C4(48)[WHOLE(1.0)]").get();
    assertEquals("C4(48)[WHOLE(1.0)]", note.toString());

    note = Note.fromString("C4[WHOLE(1.0)]").get();
    assertEquals("C4(48)[WHOLE(1.0)]", note.toString());

    try {
      Note.fromString(null).get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("  ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4[ ").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString(" [WHOLE(1)]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4[").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("[C4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("]C4").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4[WHOLE(1)][WHOLE(1)]").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4[WHOLE(1)").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }

    try {
      Note.fromString("C4[WHOLE(1)]1").get();
      fail("Expected an exception.");
    } catch (Exception e) {
      assertTrue(e instanceof EtudeException);
    }
  }

  @Test
  public void testHashCodeAndEquals() {
    Note a, b;

    a = Note.fromString("C4[WHOLE(1.0)]").get();
    b = Note.fromString("C4[WHOLE(1.0)]").get();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[WHOLE(1)]").get();
    b = Note.fromString("C4[WHOLE(1.0)]").get();
    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());

    a = Note.fromString("C#4[WHOLE(1.0)]").get();
    b = Note.fromString("Db4[WHOLE(1.0)]").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[WHOLE(1.0)]").get();
    b = Note.fromString("C4[HALF(0.5)]").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[WHOLE(1.0)]").get();
    b = Note.fromString("D4[WHOLE(1.0)]").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[WHOLE(1.0)]").get();
    b = Note.fromString("C5[WHOLE(1.0)]").get();
    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());

    a = Note.fromString("C4[WHOLE(1.0)]").get();
    assertTrue(a.equals(a));
  }
}
