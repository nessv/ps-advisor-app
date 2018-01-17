package org.fundacionparaguaya.advisorapp.fragments;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The scoped fragment file for Dagger.
 */

@Documented
@Scope
@Retention(RUNTIME)
public @interface FragementScoped {
}
