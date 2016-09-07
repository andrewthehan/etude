
package com.github.andrewthehan.etude.theory;

public class Tempo{
  /*
   * Values based on the mean of the values given in
   * https://en.wikipedia.org/wiki/Tempo
   */
  public static final Tempo LARGHISSIMO = new Tempo(24, "Larghissimo");
  public static final Tempo GRAVE = new Tempo(35, "Grave");
  public static final Tempo LARGO = new Tempo(50, "Largo");
  public static final Tempo LENTO = new Tempo(53, "Lento");
  public static final Tempo LARGHETTO = new Tempo(63, "Larghetto");
  public static final Tempo ADAGIO = new Tempo(71, "Adagio");
  public static final Tempo ADAGIETTO = new Tempo(74, "Adagietto");
  public static final Tempo ANDANTE = new Tempo(92, "Andante");
  public static final Tempo ANDANTINO = new Tempo(94, "Andantino");
  public static final Tempo MARCIA_MODERATO = new Tempo(84, "Marcia Moderato");
  public static final Tempo ANDANTE_MODERATO = new Tempo(102, "Andante Moderato");
  public static final Tempo MODERATO = new Tempo(114, "Moderato");
  public static final Tempo ALLEGRETTO = new Tempo(116, "Allegretto");
  public static final Tempo ALLEGRO_MODERATO = new Tempo(118, "Allegro Moderato");
  public static final Tempo ALLEGRO = new Tempo(144, "Allegro");
  public static final Tempo VIVACE = new Tempo(172, "Vivace");
  public static final Tempo VIVACISSIMO = new Tempo(174, "Vivacissimo");
  public static final Tempo ALLEGRISSIMO = new Tempo(174, "Allegrissimo");
  public static final Tempo ALLEGRO_VIVACE = new Tempo(174, "Allegro Vivace");
  public static final Tempo PRESTO = new Tempo(184, "Presto");
  public static final Tempo PRESTISSIMO = new Tempo(200, "Prestissimo");

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
