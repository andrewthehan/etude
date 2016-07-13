
package tests;

import jmtapi.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Iterator;

public class ScaleTest{

  @Test
  public void testStream(){
    Scale scale;
    KeySignature ks;
    String keys;

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = scale
      .stream()
      .limit(8)
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,En,Fn,Gn,An,Bn,Cn");

    ks = new KeySignature(Key.fromString("D"), Mode.NATURAL_MINOR);
    scale = new Scale(ks);
    keys = scale
      .stream()
      .limit(8)
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Dn,En,Fn,Gn,An,Bb,Cn,Dn");

    ks = new KeySignature(Key.fromString("B"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = scale
      .stream()
      .limit(8)
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Bn,C#,D#,En,F#,G#,A#,Bn");

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = scale
      .stream()
      .skip(5)
      .limit(8)
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "An,Bn,Cn,Dn,En,Fn,Gn,An");

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = scale
      .stream()
      .limit(8)
      .filter(k -> !k.equals(Key.fromString("En")))
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,Fn,Gn,An,Bn,Cn");
  }
}