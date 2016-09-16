
package com.github.andrewthehan.etude.theory;

import com.github.andrewthehan.etude.util.ArrayUtil;
import com.github.andrewthehan.etude.util.CircularIterator;
import com.github.andrewthehan.etude.util.ImmutablePrioritySet;
import com.github.andrewthehan.etude.util.InfiniteIterator;
import com.github.andrewthehan.etude.util.StreamUtil;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public final class Scale{
  public static class Quality{
    public static final Quality MAJOR = new Quality(new int[]{2, 2, 1, 2, 2, 2, 1});
    public static final Quality NATURAL_MINOR = new Quality(new int[]{2, 1, 2, 2, 1, 2, 2});
    public static final Quality HARMONIC_MINOR = new Quality(new int[]{2, 1, 2, 2, 1, 3, 1});
    public static final Quality MELODIC_MINOR = new Quality(new int[]{2, 1, 2, 2, 2, 2, 1}, new int[]{-2, -2, -1, -2, -2, -1, -2});
    public static final Quality CHROMATIC = new Quality(new int[]{1});
    public static final Quality WHOLE_TONE = new Quality(new int[]{2});

    private final int[] ascending;
    private final int[] descending;

    public Quality(int[] stepPattern){
      // reverse array and negate all values (e.g. [1, 3, 5] -> [-5, -3, -1])
      this(stepPattern, IntStream.of(ArrayUtil.reverse(stepPattern)).map(Math::negateExact).toArray());
    }

    public Quality(int[] ascending, int[] descending){
      this.ascending = ascending;
      this.descending = descending;
    }

    public final int[] getStepPattern(){
      return getStepPattern(Direction.DEFAULT);
    }

    public final int[] getStepPattern(Direction direction){
      switch(direction){
        case ASCENDING: return ascending;
        case DESCENDING: return descending;
        default: throw new AssertionError();
      }
    }

    public final boolean isOctaveRepeating(){
      return isOctaveRepeating(Direction.DEFAULT);
    }

    public final boolean isOctaveRepeating(Direction direction){
      return Math.abs(IntStream.of(getStepPattern(direction)).sum()) == MusicConstants.KEYS_IN_OCTAVE;
    }
  }

  private final Key key;
  private final Quality quality;

  public Scale(Key key, int[] stepPattern){
    this(key, new Quality(stepPattern));
  }

  public Scale(Key key, int[] ascending, int[] descending){
    this(key, new Quality(ascending, descending));
  }

  public Scale(Key key, Quality quality){
    this.key = key;
    this.quality = quality;
  }

  public ImmutablePrioritySet<Policy> getDefaultPolicy(Direction direction){
    if(quality.isOctaveRepeating(direction) && quality.getStepPattern(direction).length == Letter.values().length){
      Iterator<Letter> it = Letter.iterator(direction, key.getLetter());
      // first key is already determined by initial value in InfiniteIterator (which in this case is 'key')
      it.next();
      return Policy.prioritize(new Policy(){
        private Letter current = it.next();

        @Override
        public boolean test(Key k){
          boolean pass = k.getLetter() == current;
          if(pass){
            current = it.next();
          }
          return pass;
        }
      });
    }
    else{
      return Policy.DEFAULT_PRIORITY;
    }
  }

  public final Stream<Key> stream(){
    return stream(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Stream<Key> stream(Direction direction){
    return stream(direction, getDefaultPolicy(direction));
  }

  public final Stream<Key> stream(ImmutablePrioritySet<Policy> policies){
    return stream(Direction.DEFAULT, policies);
  }

  public final Stream<Key> stream(Direction direction, ImmutablePrioritySet<Policy> policies){
    return StreamUtil.fromIterator(iterator(direction, policies));
  }

  public final Iterator<Key> iterator(){
    return iterator(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Iterator<Key> iterator(Direction direction){
    return iterator(direction, getDefaultPolicy(direction));
  }

  public final Iterator<Key> iterator(ImmutablePrioritySet<Policy> policies){
    return iterator(Direction.DEFAULT, policies);
  }

  public final Iterator<Key> iterator(Direction direction, ImmutablePrioritySet<Policy> policies){
    CircularIterator<Integer> it = CircularIterator.of(quality.getStepPattern(direction));
    return InfiniteIterator.of(key, previous -> previous.step(it.next(), policies));
  }

  public final Key[] getKeys(){
    return getKeys(Direction.DEFAULT, getDefaultPolicy(Direction.DEFAULT));
  }

  public final Key[] getKeys(Direction direction){
    return getKeys(direction, getDefaultPolicy(direction));
  }

  public final Key[] getKeys(ImmutablePrioritySet<Policy> policies){
    return getKeys(Direction.DEFAULT, policies);
  }

  public final Key[] getKeys(Direction direction, ImmutablePrioritySet<Policy> policies){
    return stream(direction, policies).limit(quality.getStepPattern(direction).length).toArray(Key[]::new);
  }

  @Override
  public String toString(){
    return toString(Direction.DEFAULT);
  }

  public String toString(Direction direction){
    return Arrays.toString(getKeys(direction));
  }

  public final Key getKey(){
    return key;
  }

  public final Quality getQuality(){
    return quality;
  }
}