
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DynamicTest {

  @Test
  public void testConstructor() {
    Dynamic dynamic;

    dynamic = new Dynamic(50);
    assertEquals(50, dynamic.getVelocity());
  }

  @Test
  public void testHashCodeAndEquals() {
    Dynamic dynamic;

    dynamic = new Dynamic(Dynamic.PIANO.getVelocity());
    assertTrue(dynamic.equals(Dynamic.PIANO));
    assertTrue(dynamic.hashCode() == Dynamic.PIANO.hashCode());
  }

  @Test
  public void testString() {
    Dynamic dynamic;

    dynamic = Dynamic.fromString("ppp").get();
    assertEquals(Dynamic.PIANISSISSIMO, dynamic);

    dynamic = Dynamic.fromString("pp").get();
    assertEquals(Dynamic.PIANISSIMO, dynamic);

    dynamic = Dynamic.fromString("p").get();
    assertEquals(Dynamic.PIANO, dynamic);

    dynamic = Dynamic.fromString("mp").get();
    assertEquals(Dynamic.MEZZO_PIANO, dynamic);

    dynamic = Dynamic.fromString("mf").get();
    assertEquals(Dynamic.MEZZO_FORTE, dynamic);

    dynamic = Dynamic.fromString("f").get();
    assertEquals(Dynamic.FORTE, dynamic);

    dynamic = Dynamic.fromString("ff").get();
    assertEquals(Dynamic.FORTISSIMO, dynamic);

    dynamic = Dynamic.fromString("fff").get();
    assertEquals(Dynamic.FORTISSISSIMO, dynamic);
  }
}