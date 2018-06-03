package com.sinergise.io;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiPolygon;
import com.sinergise.utils.Constants;

public class WKTWriter implements Constants {

	/**
	 * Transforms the input Geometry object into WKT-formatted String. e.g.
	 * 
	 * <pre>
	 * <code>
	 * new WKTWriter().write(new LineString(new double[]{30, 10, 10, 30, 40, 40}));
	 * returns "LINESTRING (30 10, 10 30, 40 40)"
	 * </code>
	 * </pre>
	 */
	public String write(Geometry geom) {
		if (geom instanceof GeometryCollection) {
			// if GeometryCollection is instance of any Multi objects call toString function
			if (geom instanceof MultiPoint || geom instanceof MultiLineString || geom instanceof MultiPolygon)
				return geom.toString();

			@SuppressWarnings("unchecked")
			GeometryCollection<Geometry> collection = (GeometryCollection<Geometry>) geom;
			StringBuffer sb = new StringBuffer();
			sb.append(Constants.GEOMETRYCOLLECTION);
			if (collection.isEmpty())
				sb.append(Constants.EMPTY);
			else {
				sb.append(Constants.RIGHT_P);
				for (int i = 0; i < collection.size(); i++) {
					if (i > 0)
						sb.append(COMMA);
					sb.append(write(collection.get(i)));
				}
				sb.append(Constants.LEFT_P);
				return sb.toString();
			}
		} else {
			return geom.toString();
		}
		return "Incorrect input";
	}
}
