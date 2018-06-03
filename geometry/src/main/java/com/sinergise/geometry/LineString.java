package com.sinergise.geometry;

import java.util.Arrays;

import com.sinergise.utils.Constants;

public class LineString implements Geometry, Constants {

	protected double[] coords;

	/**
	 * Creates an empty linestring
	 */
	public LineString() {
		this.coords = new double[0];
	}

	/**
	 * @param coords
	 *            interlaced array of ordinates: {x0, y0, x1, y1, .. ,xn, yn}
	 */
	public LineString(double[] coords) {
		this.coords = coords;
	}

	public boolean isEmpty() {
		return coords.length == 0;
	}

	public boolean isClosed() {
		if (isEmpty())
			return true;
		int len = coords.length;
		if (len < 4)
			return false;
		return coords[0] == coords[len - 2] && coords[1] == coords[len - 1];
	}

	public int getNumCoords() {
		return coords == null ? 0 : coords.length >>> 1;
	}

	public double getX(int i) {
		return coords[i << 1];
	}

	public double getY(int i) {
		return coords[(i << 1) + 1];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(LINESTRING);

		if (isEmpty())
			return sb.append(EMPTY).toString();

		sb.append(RIGHT_P);
		for (int i = 0; i < getNumCoords(); i++) {
			if (i > 0)
				sb.append(COMMA);
			sb.append(getX(i) + SPACE + getY(i));
		}
		sb.append(LEFT_P);
		return sb.toString();
		// return getClass().getSimpleName() + "(" + Arrays.toString(coords) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(coords);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LineString)) {
			return false;
		}
		LineString cmp = (LineString) obj;
		if (cmp.coords.length != coords.length) {
			return false;
		}
		for (int i = 0; i < coords.length; i++) {
			if (cmp.coords[i] != coords[i]) {
				return false;
			}
		}
		return true;
	}
}
