package com.vikashyap.foodify.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Vikas on 5/25/2016.
 * copyright © Fueled
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UiSched {
}
