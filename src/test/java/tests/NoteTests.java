
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTests{

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
}
