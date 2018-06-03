package com.sinergise.geometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GeometryCollection<T extends Geometry> implements Geometry, Iterable<T> {

	protected List<T> elements;

	/**
	 * Creates an empty collection
	 */
	public GeometryCollection() {
		this.elements = Collections.emptyList();
	}

	public GeometryCollection(T[] elements) {
		this.elements = Arrays.asList(elements);
	}

	public GeometryCollection(List<? extends T> elements) {
		this.elements = Collections.unmodifiableList(elements);
	}

	public int size() {
		return elements.size();
	}

	public T get(int index) {
		return elements.get(index);
	}

	public Iterator<T> iterator() {
		return elements.iterator();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		for (Geometry g : this) {
			sb.append(g);
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return elements.equals(((GeometryCollection<?>) obj).elements);
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}
}
