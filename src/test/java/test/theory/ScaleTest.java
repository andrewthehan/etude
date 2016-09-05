
package test.theory;

import etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Iterator;

public class ScaleTest{

  @Test
  public void testKeys(){
    Scale scale;
    KeySignature ks;
    String keys;

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,En,Fn,Gn,An,Bn");

    ks = new KeySignature(Key.fromString("G"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Gn,An,Bn,Cn,Dn,En,F#");

    ks = new KeySignature(Key.fromString("C"), Mode.IONIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,En,Fn,Gn,An,Bn");

    ks = new KeySignature(Key.fromString("C"), Mode.DORIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,Eb,Fn,Gn,An,Bb");

    ks = new KeySignature(Key.fromString("C"), Mode.PHRYGIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Db,Eb,Fn,Gn,Ab,Bb");

    ks = new KeySignature(Key.fromString("C"), Mode.LYDIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,En,F#,Gn,An,Bn");

    ks = new KeySignature(Key.fromString("C"), Mode.MIXOLYDIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,En,Fn,Gn,An,Bb");

    ks = new KeySignature(Key.fromString("C"), Mode.AEOLIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Dn,Eb,Fn,Gn,Ab,Bb");

    ks = new KeySignature(Key.fromString("C"), Mode.LOCRIAN);
    scale = new Scale(ks);
    keys = Arrays
      .stream(scale.getKeys())
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Cn,Db,Eb,Fn,Gb,Ab,Bb");
  }

  @Test
  public void testStream(){
    KeySignature ks;
    Scale scale;
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
      .filter(k -> !k.equals(Key.fromString("Cn")))
      .map(Key::toString)
      .collect(Collectors.joining(","));
    assertEquals(keys, "Dn,En,Fn,Gn,An,Bn");
  }

  @Test
  public void testIterator(){
    KeySignature ks;
    Scale scale;
    Key[] keys;
    Iterator<Key> it;

    ks = new KeySignature(Key.fromString("C"), Mode.MAJOR);
    scale = new Scale(ks);
    keys = scale.getKeys();
    it = scale.iterator();
    for(int i = 0; i < keys.length * 2 ; ++i){
      assertEquals(it.next(), keys[i % keys.length]);
    }

    ks = new KeySignature(Key.fromString("A"), Mode.NATURAL_MINOR);
    scale = new Scale(ks);
    keys = scale.getKeys();
    it = scale.iterator();
    for(int i = 0; i < keys.length * 2; ++i){
      assertEquals(it.next(), keys[i % keys.length]);
    }
  }
}