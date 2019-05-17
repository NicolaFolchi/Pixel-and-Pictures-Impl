package a5;

import java.util.Iterator;

public class WindowIterator implements Iterator<SubPicture> {

	private CoordinateIterator coordinatesIterator;
	private int windowWidth;
	private int windowHeight;
	private Picture src;

	/*
	 * Constructor checks for valid values and creates a new 
	 * coordinatesIterator object to work as WindowIterator
	 */
	public WindowIterator(Picture source, int window_width, int window_height) {

		if (source == null) {
			throw new IllegalArgumentException("picture can't be null");
		}
		src = source;


		if ((window_width < 1)  || (window_height < 1) 
				|| (window_height > src.getHeight()) 
				|| (window_width > src.getWidth())) {
			throw new IllegalArgumentException("window isn't legal");
		}

		windowWidth = window_width;
		windowHeight = window_height;

		coordinatesIterator = new CoordinateIterator(0, 0, src.getWidth() - window_width,
				src.getHeight() - window_height, 1, 1);
	}

	/*
	 * Checks if there is a next value in the iterator
	 * Returns CoordinatesIterator hasNext method
	 */
	@Override
	public boolean hasNext() {

		return coordinatesIterator.hasNext();
	}

	/*
	 * Calls CoordinatesIterator next method and returns it
	 */
	@Override
	public SubPicture next() {

		Coordinate myCoordinate = coordinatesIterator.next();
		return src.extract(myCoordinate.getX(), myCoordinate.getY(), windowWidth, windowHeight);
	}

}
