package com.sinergise.geometry;

import com.sinergise.utils.Constants;

public final class Point implements Geometry, Constants {

	private double	x	= Double.NaN;
	private double	y	= Double.NaN;

	/**
	 * Creates an empty point
	 */
	public Point() {
		super();
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isEmpty() {
		return Double.isNaN(x) && Double.isNaN(y);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(POINT);

		if (this.isEmpty())
			return sb.append(EMPTY).toString();

		sb.append(RIGHT_P + x + SPACE + y + LEFT_P);
		return sb.toString();
		// return "PT(" + x + " " + y + ")";
	}

	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(x);
		int result = 31 + (int) (temp ^ (temp >>> 32));

		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Point)) {
			return false;
		}
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}
}
