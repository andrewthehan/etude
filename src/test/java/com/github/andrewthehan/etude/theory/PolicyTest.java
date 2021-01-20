
package com.github.andrewthehan.etude.theory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PolicyTest {
  @Test
  public void testConstants() {
    Policy policy;

    policy = Policy.NONE_OR_NATURAL;
    assertTrue(policy.test(Key.fromString("C").get()));
    assertTrue(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.SHARP;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertTrue(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.DOUBLE_SHARP;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertTrue(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.TRIPLE_SHARP;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertTrue(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.FLAT;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertTrue(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.DOUBLE_FLAT;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertTrue(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.TRIPLE_FLAT;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertTrue(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.SHARPS;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertTrue(policy.test(Key.fromString("C#").get()));
    assertTrue(policy.test(Key.fromString("Cx").get()));
    assertTrue(policy.test(Key.fromString("C#x").get()));
    assertFalse(policy.test(Key.fromString("Cb").get()));
    assertFalse(policy.test(Key.fromString("Cbb").get()));
    assertFalse(policy.test(Key.fromString("Cbbb").get()));

    policy = Policy.FLATS;
    assertFalse(policy.test(Key.fromString("C").get()));
    assertFalse(policy.test(Key.fromString("Cn").get()));
    assertFalse(policy.test(Key.fromString("C#").get()));
    assertFalse(policy.test(Key.fromString("Cx").get()));
    assertFalse(policy.test(Key.fromString("C#x").get()));
    assertTrue(policy.test(Key.fromString("Cb").get()));
    assertTrue(policy.test(Key.fromString("Cbb").get()));
    assertTrue(policy.test(Key.fromString("Cbbb").get()));
  }
}