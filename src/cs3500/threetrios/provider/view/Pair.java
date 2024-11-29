package cs3500.threetrios.provider.view;

/**
 * Implementation of a Pair class
 * @param <T> Item 1
 * @param <T1> Item 2
 */
public class Pair<T, T1> {

  private final T val1;
  private final T1 val2;

  public Pair(T x, T1 y) {
    this.val1 = x;
    this.val2 = y;
  }

  /**
   * Gets first value.
   * @return Returns first value.
   */
  public T firstVal() {
    return this.val1;
  }

  /**
   * Gets first value.
   * @return Returns first value.
   */
  public T1 secondVal() {
    return this.val2;
  }
}
