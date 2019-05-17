package a5;

import java.util.Iterator;

public class SubPictureImpl extends PictureImpl {

	private Picture src;
	private int xOffSet;
	private int yOffSet;
	private int width;
	private int height;
	private String _caption;

	public SubPictureImpl(Picture source, int xoffset, int yoffset, int width, int height) {

		super(source.getCaption());
		if ((xoffset < 0) || (xoffset >= source.getWidth()) || (yoffset < 0) || (yoffset >= source.getHeight())
				|| (width < 1) || (xoffset + width > source.getWidth()) || (height < 1)
				|| (yoffset + height > source.getHeight())) {
			throw new IllegalArgumentException("Subpicture geometry is illegal");
		}

		
		src = source;
		xOffSet = xoffset;
		yOffSet = yoffset;
		this.width = width;
		this.height = height;
		_caption = source.getCaption();

	}

	/*
	 * Check for source picture validity method 
	 * Input: source from picture 
	 * Output: same source from picture
	 * Returns source, and throws exception if source is null.
	 */
	private static Picture checkSource(Picture source) {
		if (source == null) {
			throw new IllegalArgumentException("the source picture cant be null");
		}
		return source;
	}

	/*
	 * Width getter
	 * Returns width
	 */
	@Override
	public int getWidth() {

		return width;
	}

	/*
	 * Height getter
	 * Returns height
	 */
	@Override
	public int getHeight() {

		return height;
	}

	/*
	 * Pixel getter 
	 * Input: two integers that have to be within the picture bounds
	 * Output: a sum between the pixels from the SubPicture and the input integers.
	 * Returns a specific pixel of the SubPicture class as an Interval
	 */
	@Override
	public Pixel getPixel(int x, int y) {

		if ((x < 0) || (x >= getWidth())) {
			throw new IllegalArgumentException("either y or x are out of bounds");
		}

		if ((y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("either y or x are out of bounds");
		}

		return src.getPixel(xOffSet + x, yOffSet + y);
	}

	/*
	 * Painting of a pixel in the picture at the interval of x and y 
	 * Input: two integers(position) and a pixel value 
	 * Returns the pixel already painted.
	 */
	@Override
	public Picture paint(int x, int y, Pixel p) {

		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("x or y out of bounds");
		}

		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}

		Picture pictureResult = src.paint(xOffSet + x, yOffSet + y, p);
		if (pictureResult == src) {
			src = pictureResult;
			return this;
		}

		SubPicture new_sub = pictureResult.extract(getXOffset(), getYOffset(), getWidth(), getHeight());

		new_sub.setCaption(getCaption());

		return new_sub;
	}

	/*
	 * Painting of a pixel in the picture at the interval of x and y with a factor, specifying the blending factor
	 * Input: two integers(position), a pixel value and a blending factor 
	 * Returns a new SubPicture with a painted pixel that was also blended due to the double factor.
	 */
	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {

		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("x or y out of bounds");
		}

		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}

		if ((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("factor is out of bounds");
		}

		Picture pictureResult = src.paint(xOffSet + x, yOffSet + y, p, factor);
		if (pictureResult == src) {
			src = pictureResult;
			return this;
		}

		SubPicture new_sub = pictureResult.extract(getXOffset(), getYOffset(), getWidth(), getHeight());

		new_sub.setCaption(getCaption());

		return new_sub;
	}

	/*
	 * Rectangular Painting 
	 * Input: Four integers that tell the bounds of the rectangle and a pixel value 
	 * Returns A painted rectangular region defined by the positions (ax, ay)
	 * and (bx, by) with the specified pixel value
	 */

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {

		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}

		int min_x = (ax < bx) ? ax : bx;
		int max_x = (ax > bx) ? ax : bx;
		int min_y = (ay < by) ? ay : by;
		int max_y = (ay > by) ? ay : by;

		min_x = (min_x < 0) ? 0 : min_x;
		min_y = (min_y < 0) ? 0 : min_y;
		max_x = (max_x > getWidth() - 1) ? getWidth() - 1 : max_x;
		max_y = (max_y > getHeight() - 1) ? getHeight() - 1 : max_y;

		Picture pictureResult = this;

		for (int x = min_x; x <= max_x; x++) {
			for (int y = min_y; y <= max_y; y++) {
				pictureResult = pictureResult.paint(x, y, p);
			}
		}
		return pictureResult;
	}

	/*
	 * Rectangular Painting 
	 * Input: Four integers that tell the bounds of the rectangle, a pixel value and a blending factor 
	 * Returns A painted rectangular region defined by the positions (ax, ay) and (bx, by) with the blended
	 * specified pixel value
	 */
	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {

		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}

		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor out of range");
		}

		int min_x = (ax < bx) ? ax : bx;
		int max_x = (ax > bx) ? ax : bx;
		int min_y = (ay < by) ? ay : by;
		int max_y = (ay > by) ? ay : by;

		min_x = (min_x < 0) ? 0 : min_x;
		min_y = (min_y < 0) ? 0 : min_y;
		max_x = (max_x > getWidth() - 1) ? getWidth() - 1 : max_x;
		max_y = (max_y > getHeight() - 1) ? getHeight() - 1 : max_y;

		Picture pictureResult = this;

		for (int x = min_x; x <= max_x; x++) {
			for (int y = min_y; y <= max_y; y++) {
				pictureResult = pictureResult.paint(x, y, p, factor);
			}
		}
		return pictureResult;
	}

	/*
	 * Radius painting 
	 * Input: two integers, a radius value and a pixel value
	 * Output: paints all the pixels from picture that are within radius distance of the
	 * coordinate (cx, cy) to the Pixel value p. 
	 * Returns the same picture with the paint modifications
	 */
	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {

		if (p == null) {
			throw new IllegalArgumentException("Pixel can't be null");
		}

		if (radius < 0) {
			throw new IllegalArgumentException("radius can't be negative");
		}

		int minimumX = (int) (cx - (radius + 1));
		int maximunX = (int) (cx + (radius + 1));
		int minimumY = (int) (cy - (radius + 1));
		int maximunY = (int) (cy + (radius + 1));

		minimumX = (minimumX < 0) ? 0 : minimumX;
		minimumY = (minimumY < 0) ? 0 : minimumY;
		maximunX = (maximunX > getWidth() - 1) ? getWidth() - 1 : maximunX;
		maximunY = (maximunY > getHeight() - 1) ? getHeight() - 1 : maximunY;

		Picture pictureResult = this;

		for (int x = minimumX; x <= maximunX; x++) {
			for (int y = minimumY; y <= maximunY; y++) {
				if (Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y)) <= radius) {
					pictureResult = pictureResult.paint(x, y, p);
				}
			}
		}
		return pictureResult;
	}

	/*
	 * Radius painting with a blending factor 
	 * Input: two integers, a radius value and a pixel value
	 * Output: paints all the pixels from picture that are within radius distance of the coordinate (cx, cy) to the
	 * Pixel value p and blends it depending on the factor passed to it. 
	 * Returns the same picture with the paint modifications
	 */
	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {

		if (p == null) {
			throw new IllegalArgumentException("Pixel can't be null");
		}

		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor can't be out of range");
		}

		if (radius < 0) {
			throw new IllegalArgumentException("radius can't be negative");
		}

		int minimumX = (int) (cx - (radius + 1));
		int maximunX = (int) (cx + (radius + 1));
		int minimumY = (int) (cy - (radius + 1));
		int maximunY = (int) (cy + (radius + 1));

		minimumX = (minimumX < 0) ? 0 : minimumX;
		minimumY = (minimumY < 0) ? 0 : minimumY;
		maximunX = (maximunX > getWidth() - 1) ? getWidth() - 1 : maximunX;
		maximunY = (maximunY > getHeight() - 1) ? getHeight() - 1 : maximunY;

		Picture pictureResult = this;

		for (int x = minimumX; x <= maximunX; x++) {
			for (int y = minimumY; y <= maximunY; y++) {
				if (Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y)) <= radius) {
					pictureResult = pictureResult.paint(x, y, p, factor);
				}
			}
		}
		return pictureResult;
	}

	/*
	 * Paints pixels starting at position (x,y) and extending to the right and downwards. It paints them 
	 * based on the passed picture values that are passed to it 
	 * Input: two integers (starting position) and a Picture 
	 * Output: a picture that has been painted from other picture's values
	 */
	@Override
	public Picture paint(int x, int y, Picture p) {
		
		
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("x or y out of range");
		}

		if (p == null) {
			throw new IllegalArgumentException("Picture p is null");
		}

		Picture pictureResult = this;

		for (int i = 0; i < p.getWidth() && i + x < getWidth(); i++) {
			for (int j = 0; j < p.getHeight() && j + y < getHeight(); j++) {
				pictureResult = pictureResult.paint(i + x, j + y, p.getPixel(i, j));
			}
		}
		return pictureResult;
	}

	/*
	 * Paints pixels starting at position (x,y) and extending to the right and downwards. It paints them 
	 * based on the passed picture values that are passed to it and it also blends them respecfully to the blending factor
	 * Input: two integers (starting position) and a Picture 
	 * Output: a picture that has been painted and blended from other picture's values
	 */
	@Override
	public Picture paint(int x, int y, Picture p, double factor) {

		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("x or y out of range");
		}

		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor out of range");
		}

		if (p == null) {
			throw new IllegalArgumentException("Picture p is null");
		}

		Picture pictureResult = this;

		for (int i = 0; i < p.getWidth() && i + x < getWidth(); i++) {
			for (int j = 0; j < p.getHeight() && j + y < getHeight(); j++) {
				pictureResult = pictureResult.paint(i + x, j + y, p.getPixel(i, j), factor);
			}
		}
		return pictureResult;
	}
	

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return new SubPictureImpl(this, x, y, width, height);
	}

	@Override
	public Iterator<Pixel> sample(int init_x, int init_y, int dx, int dy) {
		// TODO Auto-generated method stub
		return new SampleIterator(this, init_x, init_y, dx, dy);
	}

	@Override
	public Iterator<SubPicture> window(int window_width, int window_height) {
		// TODO Auto-generated method stub
		return new WindowIterator(this, window_width, window_height);
	}

	@Override
	public Iterator<SubPicture> tile(int tile_width, int tile_height) {
		// TODO Auto-generated method stub
		return new TileIterator(this, tile_width, tile_height);
	}

	@Override
	public Iterator<Pixel> zigzag() {
		// TODO Auto-generated method stub
		return new ZigZagIterator(this);
	}

	@Override
	public int getXOffset() {
		// TODO Auto-generated method stub
		return xOffSet;
	}

	@Override
	public int getYOffset() {
		// TODO Auto-generated method stub
		return yOffSet;
	}

	@Override
	public Picture getSource() {
		// TODO Auto-generated method stub
		return src;
	}

}
