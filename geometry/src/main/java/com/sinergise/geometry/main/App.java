package com.sinergise.geometry.main;

import java.text.ParseException;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.io.WKTReader;
import com.sinergise.io.WKTWriter;
import com.sinergise.utils.ClassCreator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		WKTWriter o = new WKTWriter();

		System.out.println("################################################################################################");
		System.out.println("###################################### TEST WKT WRITER #########################################");
		System.out.println("################################################################################################");

		System.out.println("TEST POINT : ");
		System.out.println(o.write(ClassCreator.genPoint(true)));
		System.out.println(o.write(ClassCreator.genPoint(false)));
		System.out.println();

		System.out.println("TEST MULTIPOINT : ");
		System.out.println(o.write(ClassCreator.genMultiPoint(true)));
		System.out.println(o.write(ClassCreator.genMultiPoint(false)));
		System.out.println();

		System.out.println("TEST LINESTRING : ");
		System.out.println(o.write(ClassCreator.genLineString(true)));
		System.out.println(o.write(ClassCreator.genLineString(false)));
		System.out.println();

		System.out.println("TEST MULTILINESTRING : ");
		System.out.println(o.write(ClassCreator.genMultiLineString(true)));
		System.out.println(o.write(ClassCreator.genMultiLineString(false)));
		System.out.println();

		System.out.println("TEST POLYGON : ");
		System.out.println(o.write(ClassCreator.genPolygon(true, false)));
		System.out.println(o.write(ClassCreator.genPolygon(false, false)));
		System.out.println(o.write(ClassCreator.genPolygon(false, true)));
		System.out.println();

		System.out.println("TEST MULTIPOLYGON : ");
		System.out.println(o.write(ClassCreator.genMultiPolygon(true)));
		System.out.println(o.write(ClassCreator.genMultiPolygon(false)));
		System.out.println();

		System.out.println("TEST GEOMETRYCOLLECTION : ");
		System.out.println(o.write(new GeometryCollection<Geometry>(new Geometry[] { ClassCreator.genPoint(false), ClassCreator.genLineString(false), ClassCreator.genPolygon(false, true) })));
		System.out.println();

		System.out.println("################################################################################################");
		System.out.println("###################################### TEST WKT READER #########################################");
		System.out.println("################################################################################################");

		WKTReader r = new WKTReader();
		try {
			System.out.println("TEST POINT : ");
			@SuppressWarnings("unused")
			Geometry result = r.read("POINT (30 10)");
			result = r.read("POINT EMPTY");
			System.out.println();

			System.out.println("TEST MULTIPOINT : ");
			result = r.read("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))");
			System.out.println();

			System.out.println("TEST LINESTRING : ");
			result = r.read("LINESTRING (30 10, 10 30, 40 40)");
			result = r.read("LINESTRING EMPTY");
			System.out.println();

			System.out.println("TEST MULTILINESTRING  : ");
			result = r.read("MULTILINESTRING ((10 10, 20 20, 10 40), (40 40, 30 30, 40 20, 30 10))");
			System.out.println();

			System.out.println("TEST POLYGON : ");
			result = r.read("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))");
			result = r.read("POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))");
			result = r.read("MULTIPOLYGON EMPTY");
			System.out.println();

			System.out.println("TEST MULTIPOLYGON : ");
			result = r.read("MULTIPOLYGON (((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 10 20, 5 10, 15 5)))");
			result = r.read("MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 10 30, 10 10, 30 5, 45 20, 20 35), (30 20, 20 15, 20 25, 30 20)))");
			System.out.println();

			System.out.println("TEST GEOMETRYCOLLECTION : ");
			result = r.read("GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))");
			System.out.println();

		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
	}
}
