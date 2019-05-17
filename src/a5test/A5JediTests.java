package a5test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import org.junit.Test;

import a5.*;

public class A5JediTests {

	@Test
	public void inheritanceStructureTest() {
		Class<?> mpa_picture = MutablePixelArrayPicture.class;
		Class<?> ipa_picture = ImmutablePixelArrayPicture.class;
		Class<?> pa_picture = PixelArrayPicture.class;
		Class<?> spimpl_picture = SubPictureImpl.class;
		Class<?> picture_impl = PictureImpl.class;

		assertEquals(pa_picture, mpa_picture.getSuperclass());
		assertEquals(pa_picture, ipa_picture.getSuperclass());
		assertEquals(picture_impl, spimpl_picture.getSuperclass());
		assertEquals(picture_impl, pa_picture.getSuperclass());
	}

	@Test
	public void confirmNoFieldsInPixelArrayClassesTest() {
		Class<?> mpa_picture = MutablePixelArrayPicture.class;
		Field[] fields = mpa_picture.getFields();						
		if (fields.length != 0) {
			fail("Expected MutablePixelArrayPicture to have no instance fields");
		}
		Class<?> ipa_picture = ImmutablePixelArrayPicture.class;
		fields = mpa_picture.getDeclaredFields();						
		if (fields.length != 0) {
			fail("Expected ImmutablePixelArrayPicture to have no instance fields");
		}
	}

	@Test
	public void confirmPixelArrayLocationInHierarchyTest() {
		Class<?> pa_picture = PixelArrayPicture.class;
		Field[] fields = pa_picture.getDeclaredFields();
		Class<?> pixel_double_array_class = Pixel[][].class;

		boolean found_pixel_array = false;
		for (Field f : fields) {
			if (f.getType() == pixel_double_array_class) {
				found_pixel_array = true;
				break;
			}
		}
		if (!found_pixel_array) {
			fail("Expected to find double array of Pixels in PixelArrayPicture");
		}

		Class<?> picture_impl = PictureImpl.class;
		fields = picture_impl.getDeclaredFields();

		for (Field f : fields) {
			if (f.getType() == pixel_double_array_class) {
				fail("Found double array of Pixels in PictureImpl which should not be there");
			}
		}
	}

	@Test
	public void confirmCaptionGetterAndSetterInHierarchyTest() {
		Class<?> picture_impl = PictureImpl.class;

		try {
			picture_impl.getMethod("getCaption");
			picture_impl.getMethod("setCaption", String.class);
		} catch (NoSuchMethodException e) {
			fail("Did not find getCaption method in PictureImpl");
		} catch (SecurityException e) {
			fail("Unexpected exception durint test");
		}

		Class<?> pa_picture = PixelArrayPicture.class;
		try {
			assertEquals(picture_impl, pa_picture.getMethod("getCaption").getDeclaringClass());
			assertEquals(picture_impl, pa_picture.getMethod("setCaption", String.class).getDeclaringClass());
		} catch (Exception e) {
			fail("Unexpected exception during test");
		}

		Class<?> mpa_picture = MutablePixelArrayPicture.class;
		try {
			assertEquals(picture_impl, mpa_picture.getMethod("getCaption").getDeclaringClass());
			assertEquals(picture_impl, mpa_picture.getMethod("setCaption", String.class).getDeclaringClass());
		} catch (Exception e) {
			fail("Unexpected exception during test");
		}

		Class<?> ipa_picture = ImmutablePixelArrayPicture.class;
		try {
			assertEquals(picture_impl, ipa_picture.getMethod("getCaption").getDeclaringClass());
			assertEquals(picture_impl, ipa_picture.getMethod("setCaption", String.class).getDeclaringClass());
		} catch (Exception e) {
			fail("Unexpected exception during test");
		}

		Class<?> sub_pic_impl = SubPictureImpl.class;
		try {
			assertEquals(picture_impl, sub_pic_impl.getMethod("getCaption").getDeclaringClass());
			assertEquals(picture_impl, sub_pic_impl.getMethod("setCaption", String.class).getDeclaringClass());
		} catch (Exception e) {
			fail("Unexpected exception during test");
		}
	}
	
	@Test
	public void confirmGeometryGettersInHierarchyTest() {
		Class<?> picture_impl = PictureImpl.class;
		Class<?> pa_picture = PixelArrayPicture.class;
		Class<?> mpa_picture = MutablePixelArrayPicture.class;
		Class<?> ipa_picture = ImmutablePixelArrayPicture.class;
		Class<?> sub_pic_impl = SubPictureImpl.class;
		
		try {
			assertEquals(pa_picture, mpa_picture.getMethod("getWidth").getDeclaringClass());
			assertEquals(pa_picture, ipa_picture.getMethod("getWidth").getDeclaringClass());
			assertEquals(pa_picture, mpa_picture.getMethod("getHeight").getDeclaringClass());
			assertEquals(pa_picture, ipa_picture.getMethod("getHeight").getDeclaringClass());
			assertEquals(sub_pic_impl, sub_pic_impl.getMethod("getWidth").getDeclaringClass());
			assertEquals(sub_pic_impl, sub_pic_impl.getMethod("getHeight").getDeclaringClass());
			
			assertTrue(Modifier.isAbstract(picture_impl.getMethod("getWidth").getModifiers()));
			assertTrue(Modifier.isAbstract(picture_impl.getMethod("getHeight").getModifiers()));
			
		} catch (Exception e) {
			fail("Unexpected exception during test");
		}
	}
}