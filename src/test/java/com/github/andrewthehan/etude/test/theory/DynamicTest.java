
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class DynamicTest{

  @Test
  public void testString(){
    Dynamic dynamic;

    dynamic = Dynamic.fromString("ppp");
    assertEquals(Dynamic.PIANISSISSIMO, dynamic);
    assertEquals("ppp", Dynamic.PIANISSISSIMO.toString());

    dynamic = Dynamic.fromString("pp");
    assertEquals(Dynamic.PIANISSIMO, dynamic);
    assertEquals("pp", Dynamic.PIANISSIMO.toString());

    dynamic = Dynamic.fromString("p");
    assertEquals(Dynamic.PIANO, dynamic);
    assertEquals("p", Dynamic.PIANO.toString());
    
    dynamic = Dynamic.fromString("mp");
    assertEquals(Dynamic.MEZZO_PIANO, dynamic);
    assertEquals("mp", Dynamic.MEZZO_PIANO.toString());
    
    dynamic = Dynamic.fromString("mf");
    assertEquals(Dynamic.MEZZO_FORTE, dynamic);
    assertEquals("mf", Dynamic.MEZZO_FORTE.toString());
    
    dynamic = Dynamic.fromString("f");
    assertEquals(Dynamic.FORTE, dynamic);
    assertEquals("f", Dynamic.FORTE.toString());
    
    dynamic = Dynamic.fromString("ff");
    assertEquals(Dynamic.FORTISSIMO, dynamic);
    assertEquals("ff", Dynamic.FORTISSIMO.toString());
    
    dynamic = Dynamic.fromString("fff");
    assertEquals(Dynamic.FORTISSISSIMO, dynamic);
    assertEquals("fff", Dynamic.FORTISSISSIMO.toString());
  }

  @Test
  public void testCrescendoAndDimunuendo(){
    Dynamic dynamic;

    dynamic = Dynamic.PIANISSISSIMO;

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.PIANISSIMO, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.PIANO, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.MEZZO_PIANO, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.MEZZO_FORTE, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.FORTE, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.FORTISSIMO, dynamic);

    dynamic = dynamic.crescendo();
    assertEquals(Dynamic.FORTISSISSIMO, dynamic);

    try{
      dynamic.crescendo();
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Unable to apply crescendo on fff", e.getMessage());
    }

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.FORTISSIMO, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.FORTE, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.MEZZO_FORTE, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.MEZZO_PIANO, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.PIANO, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.PIANISSIMO, dynamic);

    dynamic = dynamic.diminuendo();
    assertEquals(Dynamic.PIANISSISSIMO, dynamic);

    try{
      dynamic.diminuendo();
      fail("Expected an exception.");
    }
    catch(Exception e){
      assertEquals("Unable to apply diminuendo on ppp", e.getMessage());
    }
  }
}