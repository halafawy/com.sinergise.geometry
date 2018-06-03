package com.sinergise.geometry;

import com.sinergise.io.WKTWriter;
import com.sinergise.utils.ClassCreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for WKTWriter.
 */
public class WKTWriterMultiTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public WKTWriterMultiTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WKTWriterMultiTest.class);
	}

	public void testEmptyMultiPoint() {
		WKTWriter writer = new WKTWriter();
		MultiPoint p = ClassCreator.genMultiPoint(true);
		String result = writer.write(p);
		if (result.equals("MULTIPOINT EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testMultiPoint() {
		WKTWriter writer = new WKTWriter();
		MultiPoint mp = ClassCreator.genMultiPoint(false);
		String result = writer.write(mp);
		String points = "";
		for (int i = 0; i < mp.size(); i++) {
			if (i > 0)
				points += ", ";
			Point p = mp.get(i);
			points += "(" + p.getX() + " " + p.getY() + ")";
		}
		String correct = "MULTIPOINT (" + points + ")";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testEmptyMultiLineString() {
		WKTWriter writer = new WKTWriter();
		MultiLineString p = ClassCreator.genMultiLineString(true);
		String result = writer.write(p);
		if (result.equals("MULTILINESTRING EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testMultiLineString() {
		WKTWriter writer = new WKTWriter();
		MultiLineString mLines = ClassCreator.genMultiLineString(false);
		String result = writer.write(mLines);
		String lines = "";
		for (int i = 0; i < mLines.size(); i++) {
			if (i > 0)
				lines += ", ";
			LineString line = mLines.get(i);
			String points = "";
			for (int j = 0; j < line.getNumCoords(); j++) {
				if (j > 0)
					points += ", ";
				points += line.getX(j) + " " + line.getY(j);
			}
			lines += "(" + points + ")";
		}
		String correct = "MULTILINESTRING (" + lines + ")";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testEmptyMultiPolygon() {
		WKTWriter writer = new WKTWriter();
		MultiPolygon p = ClassCreator.genMultiPolygon(true);
		String result = writer.write(p);
		if (result.equals("MULTIPOLYGON EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testMultiPolygon() {
		WKTWriter writer = new WKTWriter();
		MultiPolygon mp = ClassCreator.genMultiPolygon(false);
		String result = writer.write(mp);

		String polygons = "";
		for (int x = 0; x < mp.size(); x++) {
			Polygon polygon = mp.get(x);
			LineString o = polygon.getOuter();
			String outer = "";
			for (int i = 0; i < o.getNumCoords(); i++) {
				if (i > 0)
					outer += ", ";
				outer += o.getX(i) + " " + o.getY(i);
			}
			String holes = "";
			for (int i = 0; i < polygon.getNumHoles(); i++) {
				if (i > 0)
					holes += ", ";
				String hole = "";
				for (int j = 0; j < polygon.getHole(i).getNumCoords(); j++) {
					if (j > 0)
						hole += ", ";
					hole += polygon.getHole(i).getX(j) + " " + polygon.getHole(i).getY(j);
				}
				holes += "(" + hole + ")";
			}
			String polygonStr = "((" + outer + "), " + holes + ")";
			if (x > 0)
				polygons += ", ";
			polygons += polygonStr;
		}

		String correct = "MULTIPOLYGON (" + polygons + ")";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}

}
