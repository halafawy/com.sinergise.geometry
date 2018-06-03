package com.sinergise.geometry;

import com.sinergise.utils.Constants;

public final class MultiPolygon extends GeometryCollection<Polygon> implements Constants {

	public MultiPolygon() {
		super();
	}

	public MultiPolygon(Polygon[] polys) {
		super(polys);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(MULTIPOLYGON);

		if (isEmpty())
			return sb.append(EMPTY).toString();

		sb.append(RIGHT_P);
		for (int i = 0; i < size(); i++) {
			if (i > 0)
				sb.append(COMMA);
			sb.append(get(i).toString().replaceAll(POLYGON, ""));
		}
		sb.append(LEFT_P);
		return sb.toString();
	}
}
