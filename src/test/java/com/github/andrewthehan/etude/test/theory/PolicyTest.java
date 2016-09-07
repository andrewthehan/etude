
package com.github.andrewthehan.etude.test.theory;

import com.github.andrewthehan.etude.theory.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class PolicyTest{
  @Test
  public void testConstants(){
    Policy policy;

    policy = Policy.NONE_OR_NATURAL;
    assertTrue(policy.test(Key.fromString("C")));
    assertTrue(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.SHARP;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertTrue(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.DOUBLE_SHARP;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertTrue(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.TRIPLE_SHARP;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertTrue(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.FLAT;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertTrue(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.DOUBLE_FLAT;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertTrue(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.TRIPLE_FLAT;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertTrue(policy.test(Key.fromString("Cbbb")));

    policy = Policy.SHARPS;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertTrue(policy.test(Key.fromString("C#")));
    assertTrue(policy.test(Key.fromString("Cx")));
    assertTrue(policy.test(Key.fromString("C#x")));
    assertFalse(policy.test(Key.fromString("Cb")));
    assertFalse(policy.test(Key.fromString("Cbb")));
    assertFalse(policy.test(Key.fromString("Cbbb")));

    policy = Policy.FLATS;
    assertFalse(policy.test(Key.fromString("C")));
    assertFalse(policy.test(Key.fromString("Cn")));
    assertFalse(policy.test(Key.fromString("C#")));
    assertFalse(policy.test(Key.fromString("Cx")));
    assertFalse(policy.test(Key.fromString("C#x")));
    assertTrue(policy.test(Key.fromString("Cb")));
    assertTrue(policy.test(Key.fromString("Cbb")));
    assertTrue(policy.test(Key.fromString("Cbbb")));
  }
}