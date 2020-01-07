package com.example.realweather.repository.database;

interface ChangeableValueHolder<V> {

	V getValue();

	void setValue(V value);

}
