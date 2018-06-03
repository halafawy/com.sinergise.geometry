package com.sinergise.geometry;

import java.text.ParseException;

import com.sinergise.io.WKTReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for WKTWriter.
 */
public class WKTReaderTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public WKTReaderTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WKTReaderTest.class);
	}

	public void testEmptyPoint() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("POINT EMPTY");
			if (result.isEmpty())
				assertTrue(true);
			else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testPoint() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("POINT (30 10)");
			if (result.isEmpty())
				assertTrue(false);
			else if (result instanceof Point) {
				Point p = (Point) result;
				if (p.getX() == 30 && p.getY() == 10)
					assertTrue(true);
				else
					assertTrue(false);
			} else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testEmptyLineString() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("LINESTRING EMPTY");
			if (result.isEmpty())
				assertTrue(true);
			else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testLineString() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("LINESTRING (30 10, 10 30, 40 40)");
			if (result.isEmpty())
				assertTrue(false);
			else if (result instanceof LineString) {
				LineString p = (LineString) result;
				if (p.getNumCoords() != 3)
					assertTrue(false);
				else if (p.getX(0) != 30 && p.getY(0) != 10)
					assertTrue(false);
				else if (p.getX(1) != 10 && p.getY(1) != 30)
					assertTrue(false);
				else if (p.getX(2) != 40 && p.getY(2) != 40)
					assertTrue(false);
				else
					assertTrue(true);
			} else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testEmptyPolygon() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("POLYGON EMPTY");
			if (result.isEmpty())
				assertTrue(true);
			else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testPolygonNoHoles() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))");
			if (result.isEmpty())
				assertTrue(false);
			else if (result instanceof Polygon) {
				Polygon p = (Polygon) result;
				if (p.getNumHoles() != 0)
					assertTrue(false);
				if (p.getOuter().getNumCoords() != 5)
					assertTrue(false);
				else if (p.getOuter().getX(0) != 30 && p.getOuter().getY(0) != 10)
					assertTrue(false);
				else if (p.getOuter().getX(1) != 40 && p.getOuter().getY(1) != 40)
					assertTrue(false);
				else if (p.getOuter().getX(2) != 20 && p.getOuter().getY(2) != 40)
					assertTrue(false);
				else if (p.getOuter().getX(3) != 10 && p.getOuter().getY(3) != 20)
					assertTrue(false);
				else if (p.getOuter().getX(4) != 30 && p.getOuter().getY(4) != 10)
					assertTrue(false);
				else
					assertTrue(true);
			} else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}

	public void testPolygonWithHoles() {
		WKTReader r = new WKTReader();
		try {
			Geometry result = r.read("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30))");
			if (result.isEmpty())
				assertTrue(false);
			else if (result instanceof Polygon) {
				Polygon p = (Polygon) result;
				if (p.getNumHoles() != 1)
					assertTrue(false);
				else if (p.getOuter().getNumCoords() != 5)
					assertTrue(false);
				else if (p.getOuter().getX(0) != 30 && p.getOuter().getY(0) != 10)
					assertTrue(false);
				else if (p.getOuter().getX(1) != 40 && p.getOuter().getY(1) != 40)
					assertTrue(false);
				else if (p.getOuter().getX(2) != 20 && p.getOuter().getY(2) != 40)
					assertTrue(false);
				else if (p.getOuter().getX(3) != 10 && p.getOuter().getY(3) != 20)
					assertTrue(false);
				else if (p.getOuter().getX(4) != 30 && p.getOuter().getY(4) != 10)
					assertTrue(false);
				else if (p.getHole(0).getNumCoords() != 4)
					assertTrue(false);
				else if (p.getHole(0).getX(0) != 20 && p.getHole(0).getY(0) != 30)
					assertTrue(false);
				else if (p.getHole(0).getX(1) != 35 && p.getHole(0).getY(1) != 35)
					assertTrue(false);
				else if (p.getHole(0).getX(2) != 30 && p.getHole(0).getY(2) != 20)
					assertTrue(false);
				else if (p.getHole(0).getX(3) != 20 && p.getHole(0).getY(3) != 30)
					assertTrue(false);
				else
					assertTrue(true);
			} else
				assertTrue(false);
		} catch (ParseException e) {
			assertTrue(false);
		}
	}
}
