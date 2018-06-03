package com.sinergise.utils;

import java.util.Random;

import com.sinergise.geometry.LineString;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiPolygon;
import com.sinergise.geometry.Point;
import com.sinergise.geometry.Polygon;

public class ClassCreator {

	private static Random	randomNum	= new Random();
	private static int		min			= 1;
	private static int		max1		= 3;
	private static int		max2		= 100;

	public static Point genPoint(boolean isEmpty) {
		if (isEmpty)
			return new Point();
		else {
			int x = min + randomNum.nextInt(max2);
			int y = min + randomNum.nextInt(max2);
			return new Point(x, y);
		}
	}

	public static MultiPoint genMultiPoint(boolean isEmpty) {
		if (isEmpty)
			return new MultiPoint();
		else {
			int numberOfPoints = min + randomNum.nextInt(max1);
			Point[] points = new Point[numberOfPoints];
			for (int i = 0; i < points.length; i++) {
				points[i] = genPoint(false);
			}
			return new MultiPoint(points);
		}
	}

	public static LineString genLineString(boolean isEmpty) {
		if (isEmpty)
			return new LineString();
		else {
			int numberOfPoints = min + randomNum.nextInt(max1);
			return new LineString(genPoints(numberOfPoints));
		}
	}

	public static MultiLineString genMultiLineString(boolean isEmpty) {
		if (isEmpty)
			return new MultiLineString();
		else {
			int numberOfLines = min + randomNum.nextInt(max1);
			LineString[] lines = new LineString[numberOfLines];
			for (int i = 0; i < lines.length; i++) {
				lines[i] = genLineString(false);
			}
			return new MultiLineString(lines);
		}
	}

	public static Polygon genPolygon(boolean isEmpty, boolean hasHoles) {
		if (isEmpty)
			return new Polygon();
		if (!hasHoles) {
			return new Polygon(new LineString(genPointsForPolygon(4)), new LineString[] {});
		} else {
			int numberOfOuters = min + randomNum.nextInt(max1);
			LineString[] outers = new LineString[numberOfOuters];
			for (int i = 0; i < outers.length; i++) {
				outers[i] = new LineString(genPointsForPolygon(4));
			}
			return new Polygon(new LineString(genPointsForPolygon(4)), outers);
		}
	}

	public static MultiPolygon genMultiPolygon(boolean isEmpty) {
		if (isEmpty)
			return new MultiPolygon();
		else {
			int numberOfPolygons = min + randomNum.nextInt(max1);
			Polygon[] polygons = new Polygon[numberOfPolygons];
			for (int i = 0; i < polygons.length; i++) {
				polygons[i] = genPolygon(false, true);
			}
			return new MultiPolygon(polygons);
		}
	}

	private static double[] genPoints(int numberOfPoints) {
		double[] points = new double[numberOfPoints * 2];
		for (int i = 0; i < points.length; i++) {
			points[i] = min + randomNum.nextInt(max2);
		}
		return points;
	}

	private static double[] genPointsForPolygon(int numberOfPoints) {
		double[] points = new double[numberOfPoints * 2];
		int startX = min + randomNum.nextInt(max2);
		int startY = min + randomNum.nextInt(max2);
		points[0] = startX;
		points[1] = startY;
		for (int i = 2; i < points.length - 1; i++) {
			points[i] = min + randomNum.nextInt(max2);
		}
		points[points.length - 2] = startX;
		points[points.length - 1] = startY;
		return points;
	}

}
