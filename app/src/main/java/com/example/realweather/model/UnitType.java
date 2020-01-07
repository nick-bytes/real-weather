package com.example.realweather.model;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@StringDef(value = {UnitType.METRIC, UnitType.IMPERIAL})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface UnitType {

    String METRIC = "METRIC";
    String IMPERIAL = "IMPERIAL";


}