
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Iterator;

public class ScaleTest{
  @Test
  public void testStepPatternConstructor(){
    int[] stepPattern;
    Scale scale;
    
    stepPattern = new int[]{1, 3, 5};
    scale = new Scale(Key.fromString("C"), stepPattern);
    assertEquals("[C, C#, E, A]", Arrays.toString(scale.stream(Direction.ASCENDING).limit(4).toArray(Key[]::new)));
    assertEquals("[C, C#, E, A, A#, C#, F#]", Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Key[]::new)));
    
    stepPattern = new int[]{4, 3, 5};
    scale = new Scale(Key.fromString("C"), stepPattern);
    assertEquals("[C, E, G, C, E, G, C]", Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Key[]::new)));
    assertEquals("[C, G, E, C, G, E, C]", Arrays.toString(scale.stream(Direction.DESCENDING).limit(7).toArray(Key[]::new)));

    scale = new Scale(Key.fromString("Eb"), stepPattern);
    assertEquals("[Eb, G, Bb, Eb, G, Bb, Eb]", Arrays.toString(
      scale
        .stream(
          Policy.prioritize(
            Policy.matchKeySignature(new KeySignature(Key.fromString("Eb"), KeySignature.Quality.MAJOR))
          )
        )
        .limit(7)
        .toArray(Key[]::new)
    ));
    
    stepPattern = new int[]{1, 2, 1, 2, 1, 2, 1};
    scale = new Scale(Key.fromString("C"), stepPattern);
    assertEquals("[C, C#, D#, E, F#, G, A, A#]", Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new)));
    assertEquals("[C, B, A, G#, F#, F, D#, D]", Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new)));
    
    stepPattern = new int[]{2, -1};
    scale = new Scale(Key.fromString("C"), stepPattern);
    assertEquals(
      "[C, D, C#, D#, D, E, D#, F, E, F#, F, G, F#, G#, G, A, G#, A#, A, B, A#, C, B, C#, C]",
      Arrays.toString(scale.stream(Direction.ASCENDING).limit(25).toArray(Key[]::new))
    );
    assertEquals(
      "[C, C#, B, C, A#, B, A, A#, G#, A, G, G#, F#, G, F, F#, E, F, D#, E, D, D#, C#, D, C]",
      Arrays.toString(scale.stream(Direction.DESCENDING).limit(25).toArray(Key[]::new))
    );

    int[] ascending = new int[]{2};
    int[] descending = new int[]{-1};
    scale = new Scale(Key.fromString("C"), ascending, descending);
    assertEquals("[C, D, E, F#, G#, A#, C]", Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Key[]::new)));
    assertEquals("[C, B, A#, A, G#, G, F#, F, E, D#, D, C#, C]", Arrays.toString(scale.stream(Direction.DESCENDING).limit(13).toArray(Key[]::new)));
  }

  @Test
  public void testStream(){
    Scale scale;
    String keys;

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, D, E, F, G, A, B, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, B, A, G, F, E, D, C]", keys);

    scale = new Scale(Key.fromString("G"), Scale.Quality.MAJOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[G, A, B, C, D, E, F#, G]", keys);

    scale = new Scale(Key.fromString("G"), Scale.Quality.MAJOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[G, F#, E, D, C, B, A, G]", keys);

    scale = new Scale(Key.fromString("A"), Scale.Quality.NATURAL_MINOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[A, B, C, D, E, F, G, A]", keys);

    scale = new Scale(Key.fromString("A"), Scale.Quality.NATURAL_MINOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[A, G, F, E, D, C, B, A]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.NATURAL_MINOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, D, Eb, F, G, Ab, Bb, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.NATURAL_MINOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, Bb, Ab, G, F, Eb, D, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.HARMONIC_MINOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, D, Eb, F, G, Ab, B, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.HARMONIC_MINOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, B, Ab, G, F, Eb, D, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.MELODIC_MINOR);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, D, Eb, F, G, A, B, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.MELODIC_MINOR);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(8).toArray(Key[]::new));
    assertEquals("[C, Bb, Ab, G, F, Eb, D, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(13).toArray(Key[]::new));
    assertEquals("[C, C#, D, D#, E, F, F#, G, G#, A, A#, B, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(13).toArray(Key[]::new));
    assertEquals("[C, B, A#, A, G#, G, F#, F, E, D#, D, C#, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT)).limit(13).toArray(Key[]::new));
    assertEquals("[C, Db, D, Eb, E, F, Gb, G, Ab, A, Bb, B, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING, Policy.prioritize(Policy.NONE_OR_NATURAL, Policy.FLAT)).limit(13).toArray(Key[]::new));
    assertEquals("[C, B, Bb, A, Ab, G, Gb, F, E, Eb, D, Db, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.WHOLE_TONE);
    keys = Arrays.toString(scale.stream(Direction.ASCENDING).limit(7).toArray(Key[]::new));
    assertEquals("[C, D, E, F#, G#, A#, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.WHOLE_TONE);
    keys = Arrays.toString(scale.stream(Direction.DESCENDING).limit(7).toArray(Key[]::new));
    assertEquals("[C, A#, G#, F#, E, D, C]", keys);

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    assertFalse(scale.stream().limit(8).toArray() == scale.stream(Direction.DEFAULT).limit(8).toArray());
    assertEquals(Arrays.toString(scale.stream().limit(8).toArray()), Arrays.toString(scale.stream(Direction.DEFAULT).limit(8).toArray()));
  }

  @Test
  public void testIterator(){
    KeySignature ks;
    Scale scale;
    String[] keys;
    Iterator<Key> it;

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    keys = new String[]{"C", "D", "E", "F", "G", "A", "B"};
    it = scale.iterator();
    for(int i = 0; i < keys.length * 2 ; ++i){
      assertEquals(it.next().toString(), keys[i % keys.length]);
    }

    scale = new Scale(Key.fromString("A"), Scale.Quality.NATURAL_MINOR);
    keys = new String[]{"A", "B", "C", "D", "E", "F", "G"};
    it = scale.iterator();
    for(int i = 0; i < keys.length * 2; ++i){
      assertEquals(it.next().toString(), keys[i % keys.length]);
    }
  }

  @Test
  public void testString(){
    Scale scale;

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    assertEquals("[C, D, E, F, G, A, B]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.MAJOR);
    assertEquals("[C, B, A, G, F, E, D]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("G"), Scale.Quality.MAJOR);
    assertEquals("[G, A, B, C, D, E, F#]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("G"), Scale.Quality.MAJOR);
    assertEquals("[G, F#, E, D, C, B, A]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("A"), Scale.Quality.NATURAL_MINOR);
    assertEquals("[A, B, C, D, E, F, G]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("A"), Scale.Quality.NATURAL_MINOR);
    assertEquals("[A, G, F, E, D, C, B]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.NATURAL_MINOR);
    assertEquals("[C, D, Eb, F, G, Ab, Bb]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.NATURAL_MINOR);
    assertEquals("[C, Bb, Ab, G, F, Eb, D]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.HARMONIC_MINOR);
    assertEquals("[C, D, Eb, F, G, Ab, B]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.HARMONIC_MINOR);
    assertEquals("[C, B, Ab, G, F, Eb, D]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.MELODIC_MINOR);
    assertEquals("[C, D, Eb, F, G, A, B]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.MELODIC_MINOR);
    assertEquals("[C, Bb, Ab, G, F, Eb, D]", scale.toString(Direction.DESCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    assertEquals("[C]", scale.toString(Direction.ASCENDING));

    scale = new Scale(Key.fromString("C"), Scale.Quality.CHROMATIC);
    assertEquals("[C]", scale.toString(Direction.DESCENDING));
  }
}