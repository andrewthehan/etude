
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class KeySignatureTest{

  @Test
  public void testDegreeKey(){
    Degree degree;
    Key key;

    KeySignature ks = new KeySignature(Key.fromString("E"), Mode.MAJOR);

    degree = Degree.TONIC;
    assertEquals(ks.keyOf(degree).toString(), "En");
    degree = Degree.SUPERTONIC;
    assertEquals(ks.keyOf(degree).toString(), "F#");
    degree = Degree.MEDIANT;
    assertEquals(ks.keyOf(degree).toString(), "G#");
    degree = Degree.SUBDOMINANT;
    assertEquals(ks.keyOf(degree).toString(), "An");
    degree = Degree.DOMINANT;
    assertEquals(ks.keyOf(degree).toString(), "Bn");
    degree = Degree.SUBMEDIANT;
    assertEquals(ks.keyOf(degree).toString(), "C#");
    degree = Degree.LEADING_TONE;
    assertEquals(ks.keyOf(degree).toString(), "D#");

    key = Key.fromString("E");
    assertEquals(ks.degreeOf(key), Degree.TONIC);
    key = Key.fromString("Fn");
    assertEquals(ks.degreeOf(key), Degree.SUPERTONIC);
    key = Key.fromString("G#");
    assertEquals(ks.degreeOf(key), Degree.MEDIANT);
    key = Key.fromString("Ax");
    assertEquals(ks.degreeOf(key), Degree.SUBDOMINANT);
    key = Key.fromString("Bb");
    assertEquals(ks.degreeOf(key), Degree.DOMINANT);
    key = Key.fromString("Cbb");
    assertEquals(ks.degreeOf(key), Degree.SUBMEDIANT);
    key = Key.fromString("D");
    assertEquals(ks.degreeOf(key), Degree.LEADING_TONE);
  }
}
