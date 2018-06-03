package com.sinergise.geometry;

import com.sinergise.utils.Constants;

public final class MultiLineString extends GeometryCollection<LineString> implements Constants {

	public MultiLineString() {
		super();
	}

	public MultiLineString(LineString[] lines) {
		super(lines);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(MULTILINESTRING);

		if (isEmpty())
			return sb.append(EMPTY).toString();

		sb.append(RIGHT_P);
		for (int i = 0; i < size(); i++) {
			if (i > 0)
				sb.append(COMMA);
			sb.append(get(i).toString().replaceAll(LINESTRING, ""));
		}
		sb.append(LEFT_P);
		return sb.toString();
	}
}
