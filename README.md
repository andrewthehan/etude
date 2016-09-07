# etude

[![Build Status](https://travis-ci.org/andrewthehan/etude.svg?branch=master)](https://travis-ci.org/andrewthehan/etude)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.andrewthehan/etude/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.andrewthehan/etude)

> Java Music Theory API

**Requires Java 1.8.**

## Ports
### Official
**JavaScript (TypeScript)**: [etude.js](https://github.com/andrewthehan/etude.js).

## Installation
### From Maven Central
##### Gradle
```
dependencies {
  compile 'com.github.andrewthehan:etude:2.0.0'
}
```

##### Maven
```xml
<dependency>
  <groupId>com.github.andrewthehan</groupId>
  <artifactId>etude</artifactId>
  <version>2.0.0</version>
</dependency>
```

### Local Jar
Download the [jar](http://repo1.maven.org/maven2/com/github/andrewthehan/etude/2.0.0/).

##### Gradle
```
dependencies {
  compile files('path/to/file/etude-2.0.0.jar')
}
```

##### Maven
```xml
<dependency>
  <groupId>com.github.andrewthehan</groupId>
  <artifactId>etude</artifactId>
  <version>2.0.0</version>
  <scope>system</scope>
  <systemPath>path/to/file/etude-2.0.0.jar</systemPath>
</dependency>
```

##### CLI
Add the file to the class path. (See [Setting the Class Path](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/classpath.html))

## Examples
For extensive example usage, refer to the [tests](https://github.com/andrewthehan/etude/tree/master/src/test/java/com/github/andrewthehan/etude/test).

#### Accidental
```java
Accidental accidental = Accidental.FLAT;
System.out.print(accidental); // b
System.out.print(accidental.getOffset()); // -1

accidental = Accidental.DOUBLE_SHARP;
System.out.print(accidental); // x
System.out.print(accidental.getOffset()); // 2

accidental = Accidental.fromOffset(-3);
System.out.print(accidental); // bbb
System.out.print(accidental == Accidental.TRIPLE_FLAT); // true

accidental = Accidental.fromString("n");
System.out.print(accidental.getOffset()); // 0
System.out.println(accidental == Accidental.NATURAL); // true
```

#### Chord
```java
Chord chord = new Chord(Pitch.fromString("C4"), Chord.Quality.MAJOR);
System.out.print(chord); // [Cn4(48), En4(52), Gn4(55)]

chord = Chord
  .builder()
  .setRoot(Pitch.fromString("Ab4"))
  .add(Chord.Quality.MINOR)
  .add(Interval.fromString("M6"))
  .build();
System.out.print(chord); // [Ab4(56), Cb5(59), Eb5(63), Fn5(65)]

chord = Chord
  .builder()
  .setRoot(Pitch.fromString("C4"))
  .add(Chord.Quality.DIMINISHED_SEVENTH)
  .setInversion(Inversion.FIRST)
  .build();
System.out.print(chord); // [Eb4(51), Gb4(54), Bbb4(57), Cn5(60)]
```

#### Degree
```java
Degree degree = Degree.TONIC;
System.out.print(degree.getValue()); // 1

degree = Degree.fromValue(5);
System.out.print(degree); // DOMINANT
```

#### Direction
```java
Direction direction = Direction.ASCENDING;
System.out.print(direction == Direction.DEFAULT); // true

direction = Direction.DESCENDING;
System.out.print(direction != Direction.DEFAULT); // true
```

#### Dynamic
```java
Dynamic dynamic = Dynamic.PIANO;
System.out.print(dynamic); // p
System.out.print(dynamic.crescendo()); // mp
System.out.print(dynamic.diminuendo()); // pp

dynamic = Dynamic.fromString("mf");
System.out.print(dynamic == Dynamic.MEZZO_FORTE); // true
System.out.print(dynamic.crescendo() == Dynamic.FORTE); // true
```

#### Interval
```java
Interval interval = new Interval(Interval.Quality.MAJOR, 6);
System.out.print(interval.getOffset()); // 9

interval = Interval.between(Pitch.fromString("Eb4"), Pitch.fromString("Ab4"));
System.out.print(interval); // P4
```

#### Inversion
```java
Inversion inversion = Inversion.ROOT;
System.out.print(inversion.getValue()); // 0

inversion = Inversion.THIRD;
System.out.print(inversion.getValue()); // 3 
```

#### Key
```java
Key key = new Key(Letter.C);
System.out.print(key); // C

key = new Key(Letter.G, Accidental.SHARP);
System.out.print(key); // G#

System.out.print(key.step(1)); // A
System.out.print(key.step(2)); // A#

key = new Key(Letter.F);
KeySignature ks = new KeySignature(Key.fromString("G"), KeySignature.Quality.MAJOR);
System.out.print(key.apply(ks)); // F#

key = new Key(Letter.C, Accidental.FLAT);
System.out.print(key.sharp()); // C#
System.out.print(key.none()); // C
System.out.print(key.doubleFlat()); // Cbb
System.out.print(key.isFlat()); // true

Key anotherKey = key.getEnharmonicEquivalent(Letter.A);
System.out.print(anotherKey); // Ax
System.out.print(Key.isEnharmonic(key, anotherKey)); // true

key = Key.fromString("Eb");
System.out.print(key.getLetter() == Letter.E); // true
System.out.print(key.getAccidental() == Accidental.FLAT); // true
```

#### KeySignature
```java
KeySignature ks = new KeySignature(Key.fromString("D"), KeySignature.Quality.MAJOR);

System.out.print(ks.keyOf(Degree.MEDIANT)); // F#
System.out.print(ks.degreeOf(Key.fromString("G"))); // SUBDOMINANT

System.out.print(Arrays.toString(ks.getKeysWithAccidentals())); // [F#, C#]

KeySignature parallel = ks.getParallel();
System.out.print(parallel.getKey()); // D
System.out.print(parallel.getQuality()); // MINOR

KeySignature relative = ks.getRelative();
System.out.print(relative.getKey()); // B
System.out.print(relative.getQuality()); // MINOR

System.out.print(Arrays.toString(KeySignature.ORDER_OF_SHARPS)); // [F, C, G, D, A, E, B]
```

#### Letter
```java
Letter letter = Letter.A;
System.out.print(letter.getOffset()); // 9

letter = Letter.fromChar('c');
System.out.print(letter == Letter.C); // true

Letter[] letters = Letter
  .stream(Letter.E)
  .limit(10)
  .toArray(Letter[]::new);
System.out.print(Arrays.toString(letters)); // [E, F, G, A, B, C, D, E, F, G]

letters = Letter
  .stream(Direction.DESCENDING, Letter.C)
  .limit(8)
  .toArray(Letter[]::new);
System.out.print(Arrays.toString(letters));  // [C, B, A, G, F, E, D, C]
```

#### Mode
```java
Mode mode = Mode.IONIAN;
System.out.print(Arrays.toString(mode.getStepPattern())); // [2, 2, 1, 2, 2, 2, 1]
```

#### MusicConstants
```java
System.out.print(MusicConstants.KEYS_IN_OCTAVE); // 12
System.out.print(MusicConstants.SMALLEST_PROGRAM_NUMBER); // 0
System.out.print(MusicConstants.LARGEST_PROGRAM_NUMBER); // 127
```

#### Note
```java
Note note = new Note(Pitch.fromString("C4"), Value.QUARTER);
System.out.print(note); // C4(48)[QUARTER]

note = Note.fromString("Db5[1]");
System.out.print(note.getPitch()); // Db5(61)
System.out.print(note.getValue()); // WHOLE
```

#### Pitch
```java
Pitch pitch = new Pitch(Key.fromString("Bbb"), 4);
System.out.print(pitch); // Bbb4(57)

KeySignature ks = new KeySignature(Key.fromString("F"), KeySignature.Quality.MAJOR);
pitch = pitch.apply(ks);
System.out.print(pitch); // Bb4(58)

System.out.print(pitch.step(3)); // C#5(61)
System.out.print(pitch.step(3, Policy.prioritize(Policy.FLAT))); // Db5(61)

Interval interval = new Interval(Interval.Quality.PERFECT, 4);
System.out.print(pitch.step(interval)); // Eb5(63)

pitch = pitch.getHigherPitch(Key.fromString("C"));
System.out.print(pitch); // C5(60)

System.out.print(Pitch.isEnharmonic(Pitch.fromString("F#4"), Pitch.fromString("Gb4"))); // true

pitch = Pitch.fromProgramNumber(48);
System.out.print(pitch); // C4(48)

pitch = Pitch.fromString("Ax7");
System.out.print(pitch.getKey().equals(Key.fromString("Ax"))); // true
System.out.print(pitch.getOctave() == 7); // true
```

#### Policy
```java
Policy policy = Policy.DOUBLE_FLAT;
System.out.print(policy.test(Key.fromString("C#"))); // false
System.out.print(policy.test(Key.fromString("Ebb"))); // true

policy = Policy.SHARPS;
System.out.print(policy.test(Key.fromString("C#"))); // true
System.out.print(policy.test(Key.fromString("C#x"))); // true

policy = Policy.matchLetter(Letter.E);
System.out.print(policy.test(Key.fromString("Dn"))); // false
System.out.print(policy.test(Key.fromString("Ebb"))); // true

KeySignature ks = new KeySignature(Key.fromString("A"), KeySignature.Quality.MAJOR);
policy = Policy.matchKeySignature(ks);
System.out.print(policy.test(Key.fromString("F"))); // false
System.out.print(policy.test(Key.fromString("F#"))); // true

Key key = Key.fromString("G");
ImmutablePrioritySet<Policy> policies = Policy.prioritize(Policy.TRIPLE_SHARP, Policy.TRIPLE_FLAT, Policy.NONE_OR_NATURAL);
System.out.print(key.step(2, policies)); // Cbbb
```

#### Scale
```java
Scale scale = new Scale(Key.fromString("Ab"), Scale.Quality.MAJOR);
Key[] keys = scale
  .stream()
  .limit(8)
  .toArray(Key[]::new);
System.out.print(Arrays.toString(keys)); // [Ab, Bb, C, Db, Eb, F, G, Ab]

keys = scale
  .stream(Direction.DESCENDING)
  .limit(8)
  .toArray(Key[]::new);
System.out.print(Arrays.toString(keys)); // [Ab, G, F, Eb, Db, C, Bb, Ab]

scale = new Scale(Key.fromString("G"), Scale.Quality.HARMONIC_MINOR);
keys = scale
  .stream()
  .limit(8)
  .toArray(Key[]::new);
System.out.print(Arrays.toString(keys)); // [G, A, Bb, C, D, Eb, F#, G]

keys = scale
  .stream(Policy.prioritize(Policy.TRIPLE_FLAT, Policy.DOUBLE_FLAT))
  .limit(8)
  .toArray(Key[]::new);
/**
 * NOTE: the first key is G despite the policy prioritizing TRIPLE_FLAT and DOUBLE_FLAT
 * this is because the scale was constructed with G being the initial key
 */
System.out.print(Arrays.toString(keys)); // [G, Cbbb, Cbb, Dbb, Fbbb, Fbb, Abbb, Abb]

// start the scale using A as the initial key
scale = new Scale(Key.fromString("G").getEnharmonicEquivalent(Letter.A), Scale.Quality.HARMONIC_MINOR);
keys = scale
  .stream(Policy.prioritize(Policy.TRIPLE_FLAT, Policy.DOUBLE_FLAT))
  .limit(8)
  .toArray(Key[]::new);
/**
 * NOTE: this begins with Abb
 */
System.out.print(Arrays.toString(keys)); // [Abb, Cbbb, Cbb, Dbb, Fbbb, Fbb, Abbb, Abb]
```

#### Tempo
```java
Tempo tempo = Tempo.ANDANTE;
System.out.print(tempo.getBPM()); // 92
System.out.print(tempo.getBeatValue() == Value.QUARTER); // true
System.out.print(tempo.getTempoMarking()); // Andante

tempo = Tempo.PRESTO;
System.out.print(tempo.getBPM()); // 184
System.out.print(tempo.getBeatValue() == Value.QUARTER); // true
System.out.print(tempo.getTempoMarking()); // Presto

tempo = new Tempo(100, Value.HALF);
System.out.print(tempo.getBPM()); // 100
System.out.print(tempo.getBeatValue() == Value.HALF); // true
System.out.print(tempo.getTempoMarking()); // HALF = 100

tempo = new Tempo(150, Value.EIGHTH, "Pretty fast");
System.out.print(tempo.getBPM()); // 150
System.out.print(tempo.getBeatValue() == Value.EIGHTH); // true
System.out.print(tempo.getTempoMarking()); // Pretty fast
```

#### TimeSignature
```java
TimeSignature ts = TimeSignature.COMMON_TIME;
System.out.print(ts.getDurationOfMeasure()); // 1.0
System.out.print(ts.getBeatsPerMeasure()); // 4
System.out.print(ts.getOneBeat() == Value.QUARTER); // true

ts = new TimeSignature(6, 8);
System.out.print(ts.getDurationOfMeasure()); // 0.75
System.out.print(ts.getBeatsPerMeasure()); // 6
System.out.print(ts.getOneBeat() == Value.EIGHTH); // true

ts = new TimeSignature(3, Value.QUARTER);
System.out.print(ts.getDurationOfMeasure()); // 0.75
System.out.print(ts.getBeatsPerMeasure()); // 3
System.out.print(ts.getOneBeat() == Value.QUARTER); // true
```

#### Value
```java
Value value = Value.QUARTER;
System.out.print(value); // QUARTER
System.out.print(value.getDuration()); // 0.25

value = Value.fromString("0.25");
System.out.print(value == Value.QUARTER); // true

value = Value.fromString("QUARTER");
System.out.print(value == Value.QUARTER); // true

value = Value.fromString("1/4");
System.out.print(value == Value.QUARTER); // true

value = Value.fromDuration(0.25);
System.out.print(value == Value.QUARTER); // true
```
