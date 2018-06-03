package com.sinergise.geometry;

import com.sinergise.utils.Constants;

public final class MultiPoint extends GeometryCollection<Point> implements Constants {

	public MultiPoint() {
		super();
	}

	public MultiPoint(Point[] points) {
		super(points);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(MULTIPOINT);

		if (isEmpty())
			return sb.append(EMPTY).toString();

		sb.append(RIGHT_P);
		for (int i = 0; i < size(); i++) {
			if (i > 0)
				sb.append(COMMA);
			sb.append(get(i).toString().replaceAll(POINT, ""));
		}
		sb.append(LEFT_P);
		return sb.toString();
	}
}
