# Sinergise Geometry Test
Implement a task is to write a WKT format geometry writer and reader in Java.
WKTWriter method write takes Geometry object as an argument (types of objects will be Point, LineString, Polygon, GeometryCollection, MultiPoint, MultiLineString ali MultiPolygon) and returns a text stream (String) representing given geometry in WKT format.
WKTReader method read takes a WKT formatted String, transformes in into a Geometry object and returns it.

A maven project created, I imported the models for geometries in the sent JAR file, also JUnit is used

# To Test
mvn test

# To Run
mvn clean install

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.sinergise.geometry.WKTReaderTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 sec
Running com.sinergise.geometry.WKTWriterMultiTest
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec
Running com.sinergise.geometry.WKTWriterTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec

Results :

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0