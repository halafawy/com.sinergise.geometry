package com.sinergise.io;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.LineString;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiPolygon;
import com.sinergise.geometry.Point;
import com.sinergise.geometry.Polygon;
import com.sinergise.utils.Constants;

public class WKTReader {

	private StreamTokenizer streamTokenizer;

	/**
	 * Transforms the input WKT-formatted String into Geometry object
	 * 
	 * @param wktString
	 * @return
	 * @throws ParseException
	 */
	public Geometry read(String wktString) throws ParseException {
		StringReader reader = new StringReader(wktString);
		try {
			return read(reader);
		} finally {
			reader.close();
		}
	}

	private Geometry read(Reader reader) throws ParseException {
		streamTokenizer = new StreamTokenizer(reader);
		// set streamTokenizer to NOT parse numbers
		streamTokenizer.resetSyntax();
		streamTokenizer.wordChars('a', 'z');
		streamTokenizer.wordChars('A', 'Z');
		streamTokenizer.wordChars(128 + 32, 255);
		streamTokenizer.wordChars('0', '9');
		streamTokenizer.wordChars('-', '-');
		streamTokenizer.wordChars('+', '+');
		streamTokenizer.wordChars('.', '.');
		streamTokenizer.whitespaceChars(0, ' ');
		streamTokenizer.commentChar('#');

		try {
			return readGeometryTaggedText();
		} catch (IOException e) {
			throw new ParseException(e.toString(), 0);
		}
	}

	/**
	 * Parses the next number in the stream. Numbers with exponents are handled. NaN values are handled correctly, and the case of the "NaN" symbol is not significant.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private double getNextNumber() throws IOException, ParseException {
		int type = streamTokenizer.nextToken();
		switch (type) {
			case StreamTokenizer.TT_WORD: {
				if (streamTokenizer.sval.equalsIgnoreCase(Constants.NAN_SYMBOL)) {
					return Double.NaN;
				} else {
					try {
						return Double.parseDouble(streamTokenizer.sval);
					} catch (NumberFormatException ex) {
						parseErrorWithLine("Invalid number: " + streamTokenizer.sval);
					}
				}
			}
		}
		parseErrorExpected("number");
		return 0.0;
	}

	/**
	 * Returns the next EMPTY or Constants.LEFT_P in the stream as uppercase text.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private String getNextEmptyOrLeftParen() throws IOException, ParseException {
		String nextWord = getNextWord();
		if (nextWord.equals(Constants.EMPTY) || nextWord.equals(Constants.LEFT_P)) {
			return nextWord;
		}
		parseErrorExpected(Constants.EMPTY + " or " + Constants.LEFT_P);
		return null;
	}

	/**
	 * Returns the next RIGHT_P or COMMA in the stream.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private String getNextRightParenOrComma() throws IOException, ParseException {
		String nextWord = getNextWord();
		if (nextWord.equals(Constants.COMMA_NOSPACE) || nextWord.equals(Constants.RIGHT_P)) {
			return nextWord;
		}
		parseErrorExpected(Constants.COMMA_NOSPACE + " or " + Constants.RIGHT_P);
		return null;
	}

	/**
	 * Returns the next RIGHT PAREN in the stream.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private String getNextRightParen() throws IOException, ParseException {
		String nextWord = getNextWord();
		if (nextWord.equals(Constants.RIGHT_P)) {
			return nextWord;
		}
		parseErrorExpected(Constants.RIGHT_P);
		return null;
	}

	/**
	 * Returns the next word in the stream.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private String getNextWord() throws IOException, ParseException {
		int type = streamTokenizer.nextToken();
		switch (type) {
			case StreamTokenizer.TT_WORD:

				String word = streamTokenizer.sval;
				if (word.equalsIgnoreCase(Constants.EMPTY))
					return Constants.EMPTY;
				return word;

			case '(':
				return Constants.LEFT_P;
			case ')':
				return Constants.RIGHT_P;
			case ',':
				return Constants.COMMA_NOSPACE;
		}
		parseErrorExpected("word");
		return null;
	}

	/**
	 * Throws a formatted ParseException reporting that the current token was unexpected.
	 * 
	 * @param expected
	 * @throws ParseException
	 */
	private void parseErrorExpected(String expected) throws ParseException {
		String tokenStr = tokenString();
		parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
	}

	private void parseErrorWithLine(String msg) throws ParseException {
		throw new ParseException(msg + " (line " + streamTokenizer.lineno() + ")", 0);
	}

	/**
	 * Gets a description of the current token
	 * 
	 * @return
	 */
	private String tokenString() {
		switch (streamTokenizer.ttype) {
			case StreamTokenizer.TT_NUMBER:
				return "<NUMBER>";
			case StreamTokenizer.TT_EOL:
				return "End-of-Line";
			case StreamTokenizer.TT_EOF:
				return "End-of-Stream";
			case StreamTokenizer.TT_WORD:
				return "'" + streamTokenizer.sval + "'";
		}
		return "'" + (char) streamTokenizer.ttype + "'";
	}

	private Geometry readGeometryTaggedText() throws IOException, ParseException {
		String type = null;

		try {
			type = getNextWord();
		} catch (IOException e) {
			return null;
		} catch (ParseException e) {
			return null;
		}

		if (type.equalsIgnoreCase("POINT")) {
			return readPointText();
		} else if (type.equalsIgnoreCase("LINESTRING")) {
			return readLineStringText();
		} else if (type.equalsIgnoreCase("POLYGON")) {
			return readPolygonText();
		} else if (type.equalsIgnoreCase("MULTIPOINT")) {
			return readMultiPointText();
		} else if (type.equalsIgnoreCase("MULTILINESTRING")) {
			return readMultiLineStringText();
		} else if (type.equalsIgnoreCase("MULTIPOLYGON")) {
			return readMultiPolygonText();
		} else if (type.equalsIgnoreCase("GEOMETRYCOLLECTION")) {
			return readGeometryCollectionText();
		}
		parseErrorWithLine("Unknown geometry type: " + type);
		// should never reach here
		return null;
	}

	/**
	 * Creates a Point using the next token in the stream.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private Point readPointText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new Point();
		}
		double x = getNextNumber();
		double y = getNextNumber();

		Point point = new Point(x, y);
		getNextRightParen();
		return point;
	}

	/**
	 * Creates a MultiPoint using the next token in the stream.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private MultiPoint readMultiPointText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new MultiPoint(new Point[0]);
		}

		ArrayList<Point> points = new ArrayList<Point>();
		Point point = readPointText();
		points.add(point);
		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			point = readPointText();
			points.add(point);
			nextToken = getNextRightParenOrComma();
		}
		Point[] array = new Point[points.size()];
		return new MultiPoint((Point[]) points.toArray(array));
	}

	/**
	 * Creates a LineString using the next token in the stream
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private LineString readLineStringText() throws IOException, ParseException {
		return new LineString(getCoordinates());
	}

	/**
	 * Creates a MultiLineString using the next token in the stream
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private MultiLineString readMultiLineStringText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new MultiLineString(new LineString[] {});
		}
		ArrayList<LineString> lineStrings = new ArrayList<LineString>();
		LineString lineString = readLineStringText();
		lineStrings.add(lineString);
		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			lineString = readLineStringText();
			lineStrings.add(lineString);
			nextToken = getNextRightParenOrComma();
		}
		LineString[] array = new LineString[lineStrings.size()];
		return new MultiLineString((LineString[]) lineStrings.toArray(array));
	}

	/**
	 * Creates a Polygon using the next token in the stream
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private Polygon readPolygonText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new Polygon();
		}
		ArrayList<LineString> holes = new ArrayList<LineString>();
		LineString outer = readLineStringText();
		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			LineString hole = readLineStringText();
			holes.add(hole);
			nextToken = getNextRightParenOrComma();
		}
		LineString[] array = new LineString[holes.size()];
		return new Polygon(outer, (LineString[]) holes.toArray(array));
	}

	/**
	 * Creates a MultiPolygon using the next token in the stream
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private MultiPolygon readMultiPolygonText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new MultiPolygon();
		}
		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		Polygon polygon = readPolygonText();
		polygons.add(polygon);
		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			polygon = readPolygonText();
			polygons.add(polygon);
			nextToken = getNextRightParenOrComma();
		}
		Polygon[] array = new Polygon[polygons.size()];
		return new MultiPolygon((Polygon[]) polygons.toArray(array));
	}

	/**
	 * Creates a GeometryCollection using the next token in the stream
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private GeometryCollection<Geometry> readGeometryCollectionText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new GeometryCollection<Geometry>();
		}
		ArrayList<Geometry> geometries = new ArrayList<Geometry>();
		Geometry geometry = readGeometryTaggedText();
		geometries.add(geometry);
		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			geometry = readGeometryTaggedText();
			geometries.add(geometry);
			nextToken = getNextRightParenOrComma();
		}
		Geometry[] array = new Geometry[geometries.size()];
		return new GeometryCollection<Geometry>((Geometry[]) geometries.toArray(array));
	}

	private double[] getCoordinates() throws IOException, ParseException {
		String nextToken = getNextEmptyOrLeftParen();
		if (nextToken.equals(Constants.EMPTY)) {
			return new double[] {};
		}
		ArrayList<Double> coordinates = new ArrayList<Double>();
		if (isNumberNext()) {
			double x = getNextNumber();
			double y = getNextNumber();
			coordinates.add(x);
			coordinates.add(y);
		} else {
			throw new ParseException("Invalid coordinate (line " + streamTokenizer.lineno() + ")", 0);
		}

		nextToken = getNextRightParenOrComma();
		while (nextToken.equals(Constants.COMMA_NOSPACE)) {
			double x = getNextNumber();
			double y = getNextNumber();
			coordinates.add(x);
			coordinates.add(y);
			nextToken = getNextRightParenOrComma();
		}
		double[] array = new double[coordinates.size()];
		for (int i = 0; i < coordinates.size(); ++i) {
			array[i] = coordinates.get(i);
		}
		return array;
	}

	private boolean isNumberNext() throws IOException {
		int type = streamTokenizer.nextToken();
		streamTokenizer.pushBack();
		return type == StreamTokenizer.TT_WORD;
	}
}
