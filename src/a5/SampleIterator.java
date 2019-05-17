package a5;

import java.util.Iterator;

public class SampleIterator implements Iterator<Pixel> {

	private CoordinateIterator coordIterator;
	private Picture src;

	public SampleIterator(Picture source, int init_x, int init_y, int dx, int dy) {
		if (source == null) {
			throw new IllegalArgumentException("picture is null");
		}

		src = source;
		coordIterator = new CoordinateIterator(init_x, init_y, src.getWidth() - 1, src.getHeight() - 1, dx, dy);
	}

	/*
	 * Checks if there is a next value in the iterator Returns CoordinatesIterator
	 * hasNext method
	 */
	@Override
	public boolean hasNext() {

		return coordIterator.hasNext();
	}

	/*
	 * Calls CoordinatesIterator next method Returns the next Pixel in the iterator
	 */
	@Override
	public Pixel next() {

		Coordinate myCoordenate = coordIterator.next();
		return src.getPixel(myCoordenate.getX(), myCoordenate.getY());
	}

}
