
package com.github.andrewthehan.etude.theory;

public class Tempo{
  /*
   * Values based on the mean of the values given in
   * https://en.wikipedia.org/wiki/Tempo
   */
  public static final Tempo LARGHISSIMO = new Tempo(24, "LARGHISSIMO");
  public static final Tempo GRAVE = new Tempo(35, "GRAVE");
  public static final Tempo LARGO = new Tempo(50, "LARGO");
  public static final Tempo LENTO = new Tempo(53, "LENTO");
  public static final Tempo LARGHETTO = new Tempo(63, "LARGHETTO");
  public static final Tempo ADAGIO = new Tempo(71, "ADAGIO");
  public static final Tempo ADAGIETTO = new Tempo(74, "ADAGIETTO");
  public static final Tempo ANDANTE = new Tempo(92, "ANDANTE");
  public static final Tempo ANDANTINO = new Tempo(94, "ANDANTINO");
  public static final Tempo MARCIA_MODERATO = new Tempo(84, "MARCIA_MODERATO");
  public static final Tempo ANDANTE_MODERATO = new Tempo(102, "ANDANTE_MODERATO");
  public static final Tempo MODERATO = new Tempo(114, "MODERATO");
  public static final Tempo ALLEGRETTO = new Tempo(116, "ALLEGRETTO");
  public static final Tempo ALLEGRO_MODERATO = new Tempo(118, "ALLEGRO_MODERATO");
  public static final Tempo ALLEGRO = new Tempo(144, "ALLEGRO");
  public static final Tempo VIVACE = new Tempo(172, "VIVACE");
  public static final Tempo VIVACISSIMO = new Tempo(174, "VIVACISSIMO");
  public static final Tempo ALLEGRISSIMO = new Tempo(174, "ALLEGRISSIMO");
  public static final Tempo ALLEGRO_VIVACE = new Tempo(174, "ALLEGRO_VIVACE");
  public static final Tempo PRESTO = new Tempo(184, "PRESTO");
  public static final Tempo PRESTISSIMO = new Tempo(200, "PRESTISSIMO");

  private final int bpm; // beats per minute
  private final Value beatValue;
  private final String tempoMarking;

  public Tempo(int bpm){
    this(bpm, Value.QUARTER);
  }

  public Tempo(int bpm, Value beatValue){
    this(bpm, beatValue, beatValue + " = " + bpm);
  }

  public Tempo(int bpm, String tempoMarking){
    this(bpm, Value.QUARTER, tempoMarking);
  }

  public Tempo(int bpm, Value beatValue, String tempoMarking){
    this.bpm = bpm;
    this.beatValue = beatValue;
    this.tempoMarking = tempoMarking;
  }

  public final int getBPM(){
    return bpm;
  }

  public final Value getBeatValue(){
    return beatValue;
  }

  public final String getTempoMarking(){
    return tempoMarking;
  }
}
