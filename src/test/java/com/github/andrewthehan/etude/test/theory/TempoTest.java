
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class TempoTest{
  @Test
  public void testConstants(){
    Tempo tempo;

    tempo = Tempo.LARGHISSIMO;
    assertEquals(24, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Larghissimo", tempo.getTempoMarking());

    tempo = Tempo.GRAVE;
    assertEquals(35, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Grave", tempo.getTempoMarking());
    
    tempo = Tempo.LARGO;
    assertEquals(50, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Largo", tempo.getTempoMarking());
    
    tempo = Tempo.LENTO;
    assertEquals(53, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Lento", tempo.getTempoMarking());
    
    tempo = Tempo.LARGHETTO;
    assertEquals(63, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Larghetto", tempo.getTempoMarking());
    
    tempo = Tempo.ADAGIO;
    assertEquals(71, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Adagio", tempo.getTempoMarking());
    
    tempo = Tempo.ADAGIETTO;
    assertEquals(74, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Adagietto", tempo.getTempoMarking());
    
    tempo = Tempo.ANDANTE;
    assertEquals(92, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Andante", tempo.getTempoMarking());
    
    tempo = Tempo.ANDANTINO;
    assertEquals(94, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Andantino", tempo.getTempoMarking());
    
    tempo = Tempo.MARCIA_MODERATO;
    assertEquals(84, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Marcia Moderato", tempo.getTempoMarking());
    
    tempo = Tempo.ANDANTE_MODERATO;
    assertEquals(102, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Andante Moderato", tempo.getTempoMarking());
    
    tempo = Tempo.MODERATO;
    assertEquals(114, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Moderato", tempo.getTempoMarking());
    
    tempo = Tempo.ALLEGRETTO;
    assertEquals(116, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Allegretto", tempo.getTempoMarking());
    
    tempo = Tempo.ALLEGRO_MODERATO;
    assertEquals(118, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Allegro Moderato", tempo.getTempoMarking());
    
    tempo = Tempo.ALLEGRO;
    assertEquals(144, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Allegro", tempo.getTempoMarking());
    
    tempo = Tempo.VIVACE;
    assertEquals(172, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Vivace", tempo.getTempoMarking());
    
    tempo = Tempo.VIVACISSIMO;
    assertEquals(174, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Vivacissimo", tempo.getTempoMarking());
    
    tempo = Tempo.ALLEGRISSIMO;
    assertEquals(174, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Allegrissimo", tempo.getTempoMarking());
    
    tempo = Tempo.ALLEGRO_VIVACE;
    assertEquals(174, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Allegro Vivace", tempo.getTempoMarking());
    
    tempo = Tempo.PRESTO;
    assertEquals(184, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Presto", tempo.getTempoMarking());
    
    tempo = Tempo.PRESTISSIMO;
    assertEquals(200, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("Prestissimo", tempo.getTempoMarking());
  }

  @Test
  public void testConstructor(){
    Tempo tempo;

    tempo = new Tempo(100);
    assertEquals(100, tempo.getBPM());
    assertEquals(Value.QUARTER, tempo.getBeatValue());
    assertEquals("QUARTER = 100", tempo.getTempoMarking());

    tempo = new Tempo(100, Value.HALF);
    assertEquals(100, tempo.getBPM());
    assertEquals(Value.HALF, tempo.getBeatValue());
    assertEquals("HALF = 100", tempo.getTempoMarking());

    tempo = new Tempo(100, Value.WHOLE, "Slow");
    assertEquals(100, tempo.getBPM());
    assertEquals(Value.WHOLE, tempo.getBeatValue());
    assertEquals("Slow", tempo.getTempoMarking());
  }
}