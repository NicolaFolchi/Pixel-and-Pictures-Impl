package a5;

import java.util.Iterator;

public class TileIterator implements Iterator<SubPicture> {

	private CoordinateIterator coordIterator;
	private int tileWidth;
	private int tileHeight;
	private Picture src;

	public TileIterator(Picture source, int tile_width, int tile_height) {
		if (source == null) {
			throw new IllegalArgumentException("picture is null");
		}
		
		src = source;

		if ((tile_width < 1) || (tile_height < 1)
				|| (tile_height > src.getHeight())
				|| (tile_width > src.getWidth())) {
			throw new IllegalArgumentException("window size is illegal");
		}

		tileWidth = tile_width;
		tileHeight = tile_height;

		coordIterator = new CoordinateIterator(0, 0, src.getWidth() - tile_width, src.getHeight() - tile_height,
				tileWidth, tileHeight);
	}

	/*
	 * Checks if there is a next value in the iterator
	 * Returns CoordinatesIterator hasNext method
	 */
	@Override
	public boolean hasNext() {

		return coordIterator.hasNext();
	}

	/*
	 * Calls CoordinatesIterator next method and returns it
	 */
	@Override
	public SubPicture next() {

		Coordinate myCoordinate = coordIterator.next();
		return src.extract(myCoordinate.getX(), myCoordinate.getY(), tileWidth, tileHeight);
	}
}