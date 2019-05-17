package a5;

import java.util.NoSuchElementException;

public class CoordinateIterator implements java.util.Iterator<Coordinate>
{
  private int initialX;
  private int initialY;
  private int maximumX;
  private int maximumY;
  private int dx;
  private int dy;
  private int x;
  private int y;
  
  public CoordinateIterator(int init_x, int init_y, int max_x, int max_y, int dx, int dy)
  {
    if ((init_x < 0) || (init_x > max_x) || 
      (init_y < 0) || (init_y > max_y)) {
      throw new IllegalArgumentException("illegal init_x or init_y");
    }
    
    if ((dx < 1) || (dy < 1)) {
      throw new IllegalArgumentException("dx or dy not positive");
    }
    
    initialX = init_x;
    initialY = init_y;
    maximumX = max_x;
    maximumY = max_y;
    this.dx = dx;
    this.dy = dy;
    x = initialX;
    y = initialY;
  }
  
  /*
   * Checking iterator for more values
   * returns true if y indicates there are more values besides from itself
   */
  public boolean hasNext() {
    
	  return y <= maximumY;
  }
  
  /*
   * Gets the next coordinate value in the iterator
   * Creates a coordinate object and evaluates the values x and y are valid
   * Returns the next coordinate value in the iterator 
   */
  public Coordinate next() {
    
	  if (!hasNext()) {
      throw new NoSuchElementException();
    }
    
    Coordinate next_coordenate = new Coordinate(x, y);
    x += dx;
    
    if (x > maximumX) {
      x = initialX;
      y += dy;
    }
    
    return next_coordenate;
  }
}
