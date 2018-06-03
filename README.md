# Sinergise Geometry Test
Implement a task is to write a WKT format geometry writer and reader in Java.
WKTWriter method write takes Geometry object as an argument (types of objects will be Point, LineString, Polygon, GeometryCollection, MultiPoint, MultiLineString ali MultiPolygon) and returns a text stream (String) representing given geometry in WKT format.
WKTReader method read takes a WKT formatted String, transformes in into a Geometry object and returns it.

A maven project created, I imported the models for geometries in the sent JAR file, also JUnit is used

# To Test
mvn test

# To Run
mvn clean install