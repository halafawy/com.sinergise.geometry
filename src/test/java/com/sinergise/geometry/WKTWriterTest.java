package com.sinergise.geometry;

import com.sinergise.io.WKTWriter;
import com.sinergise.utils.ClassCreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for WKTWriter.
 */
public class WKTWriterTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public WKTWriterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WKTWriterTest.class);
	}

	public void testEmptyPoint() {
		WKTWriter writer = new WKTWriter();
		Point p = ClassCreator.genPoint(true);
		String result = writer.write(p);
		if (result.equals("POINT EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testPoint() {
		WKTWriter writer = new WKTWriter();
		Point p = ClassCreator.genPoint(false);
		String result = writer.write(p);
		if (result.equals("POINT (" + p.getX() + " " + p.getY() + ")"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testEmptyLineString() {
		WKTWriter writer = new WKTWriter();
		LineString p = ClassCreator.genLineString(true);
		String result = writer.write(p);
		if (result.equals("LINESTRING EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testLineString() {
		WKTWriter writer = new WKTWriter();
		LineString p = ClassCreator.genLineString(false);
		String result = writer.write(p);
		String points = "";
		for (int i = 0; i < p.getNumCoords(); i++) {
			if (i > 0)
				points += ", ";
			points += p.getX(i) + " " + p.getY(i);
		}
		String correct = "LINESTRING (" + points + ")";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testEmptyPolygon() {
		WKTWriter writer = new WKTWriter();
		Polygon p = ClassCreator.genPolygon(true, false);
		String result = writer.write(p);
		if (result.equals("POLYGON EMPTY"))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testPolygonNoHoles() {
		WKTWriter writer = new WKTWriter();
		Polygon p = ClassCreator.genPolygon(false, false);
		String result = writer.write(p);
		LineString outer = p.getOuter();
		String points = "";
		for (int i = 0; i < outer.getNumCoords(); i++) {
			if (i > 0)
				points += ", ";
			points += outer.getX(i) + " " + outer.getY(i);
		}
		String correct = "POLYGON ((" + points + "))";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}

	public void testPolygonWithHoles() {
		WKTWriter writer = new WKTWriter();
		Polygon polygon = ClassCreator.genPolygon(false, true);
		String result = writer.write(polygon);
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
		String correct = "POLYGON ((" + outer + "), " + holes + ")";
		if (result.equals(correct))
			assertTrue(true);
		else
			assertTrue(false);
	}
}
