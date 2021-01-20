# etude

[![Build Status](https://travis-ci.org/andrewthehan/etude.svg?branch=master)](https://travis-ci.org/andrewthehan/etude)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.andrewthehan/etude/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.andrewthehan/etude)

etude is a music theory library focused on the fundamentals of music.

- **Deterministic**: All concepts covered by etude are outlined by nonrandom rules. This means etude cannot analyze a chord progression or develop a complex melody. However, it can tell you the pitches for a chord or the intervals between the notes of a melody.
- **Immutable**: etude objects are not only immutable but also _strongly_ immutable to enforce immutability with inheritance. You can learn about the benefits of immutability [here](https://github.com/facebook/immutable-js/#the-case-for-immutability).
- **MIDI-compatible**: etude follows the [MIDI](https://en.wikipedia.org/wiki/MIDI) standards.

**Requires Java 1.8.**

## Links

- [Documentation](http://andrewthehan.github.io/etude/javadoc/index.html)
- [jar](http://repo1.maven.org/maven2/com/github/andrewthehan/etude/3.0.0/etude-3.0.0.jar)
- [javadoc jar](http://repo1.maven.org/maven2/com/github/andrewthehan/etude/3.0.0/etude-3.0.0-javadoc.jar)
- [sources jar](http://repo1.maven.org/maven2/com/github/andrewthehan/etude/3.0.0/etude-3.0.0-sources.jar)

## Ports

All official ports will follow the same major and minor version increments.

### Official

**JavaScript (TypeScript)**: [etude.js](https://github.com/andrewthehan/etude.js).

## Installation

### From Maven Central

##### Gradle

```
dependencies {
  compile "com.github.andrewthehan:etude:3.0.0"
}
```

##### Maven

```xml
<dependency>
  <groupId>com.github.andrewthehan</groupId>
  <artifactId>etude</artifactId>
  <version>3.0.0</version>
</dependency>
```

### Local Jar

Download the [jar](http://repo1.maven.org/maven2/com/github/andrewthehan/etude/3.0.0/etude-3.0.0.jar).

##### Gradle

```
dependencies {
  compile files("path/to/file/etude-3.0.0.jar")
}
```

##### Maven

```xml
<dependency>
  <groupId>com.github.andrewthehan</groupId>
  <artifactId>etude</artifactId>
  <version>3.0.0</version>
  <scope>system</scope>
  <systemPath>path/to/file/etude-3.0.0.jar</systemPath>
</dependency>
```

##### CLI

Add the file to the class path. (See [Setting the Class Path](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/classpath.html))

## Development

### Requirements

- Java 1.8
- [Gradle](https://gradle.org/) 4.2

### Build

Clone the repo.

```
git clone https://github.com/andrewthehan/etude.git
```

Build and run tests.

```
gradle build
```

### Contributions

Any contributions towards documentation, bug fixes, [TODO](#todo) features are welcome. If you wish to implement a feature that is not listed in the [TODO](#todo) section, please submit an [issue](https://github.com/andrewthehan/etude/issues) first.

When fixing bugs or implementing new features, please add accompanying tests.

## TODO

- [Articulation](<https://en.wikipedia.org/wiki/Articulation_(music)>)
- [Cadences](<https://en.wikipedia.org/wiki/Cadence_(music)>)
- [Chord Progression](https://en.wikipedia.org/wiki/Chord_progression)
- More dynamics
- More scale qualities
- [Phrase](<https://en.wikipedia.org/wiki/Phrase_(music)>)
- Phrase manipulations
  - [Imitation](<https://en.wikipedia.org/wiki/Imitation_(music)>)
  - [Inversion](<https://en.wikipedia.org/wiki/Inversion_(music)>)
  - [Repetition](<https://en.wikipedia.org/wiki/Repetition_(music)>)
  - [Retrograde](<https://en.wikipedia.org/wiki/Retrograde_(music)>)
  - [Sequence](<https://en.wikipedia.org/wiki/Sequence_(music)>)
  - [Transposition](<https://en.wikipedia.org/wiki/Transposition_(music)>)
- [Rests](<https://en.wikipedia.org/wiki/Rest_(music)>)

## Made with etude

If you made something cool with etude, feel free to add your project here.
