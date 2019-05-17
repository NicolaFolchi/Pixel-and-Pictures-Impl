package a5;

import java.util.Iterator;

public class ZigZagIterator implements Iterator<Pixel> {

	private int x;
	private int y;
	private Picture src;

	public ZigZagIterator(Picture source) {
		
		if (source == null) {
			throw new IllegalArgumentException("picture can't be null");
		}
		
		src = source;
		x = 0;
		y = 0;
	}

	/*
	 * Checks for upcoming values checking if x and y are less than the total width and height 
	 */
	@Override
	public boolean hasNext() {

		return (x < src.getWidth()) && (y < src.getHeight());
	}

	/*
	 * Creates a Pixel Object and gets the next pixel interval in the iterator
	 * Returns the next pixel interval
	 */
	@Override
	public Pixel next() {

		Pixel myPixel = src.getPixel(x, y);

		if ((x + y) % 2 == 0) {
			if (x == src.getWidth() - 1) {
				y += 1;
			} else if (y == 0) {
				x += 1;
			} else {
				y -= 1;
				x += 1;
			}
			
		} else if (y == src.getHeight() - 1) {
			x += 1;
		} else if (x == 0) {
			y += 1;
		} else {
			x -= 1;
			y += 1;
		}
		return myPixel;
	}

}
