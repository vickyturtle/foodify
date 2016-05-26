package com.vikashyap.foodify.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Vikas on 5/23/2016.
 * Custom scope for activity scoped components
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
