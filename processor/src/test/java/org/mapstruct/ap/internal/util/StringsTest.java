/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Filip Hrisafov
 */
public class StringsTest {

    private static final Locale TURKEY_LOCALE = getTurkeyLocale();
    private Locale defaultLocale;

    @Before
    public void before() {
        defaultLocale = Locale.getDefault();
    }

    @After
    public void after() {
        Locale.setDefault( defaultLocale );
    }

    @Test
    public void testCapitalize() {
        assertThat( Strings.capitalize( null ) ).isNull();
        assertThat( Strings.capitalize( "c" ) ).isEqualTo( "C" );
        assertThat( Strings.capitalize( "capitalize" ) ).isEqualTo( "Capitalize" );
        assertThat( Strings.capitalize( "AlreadyCapitalized" ) ).isEqualTo( "AlreadyCapitalized" );
        assertThat( Strings.capitalize( "notCapitalized" ) ).isEqualTo( "NotCapitalized" );
    }

    @Test
    public void testDecapitalize() {
        assertThat( Strings.decapitalize( null ) ).isNull();
        assertThat( Strings.decapitalize( "c" ) ).isEqualTo( "c" );
        assertThat( Strings.decapitalize( "capitalize" ) ).isEqualTo( "capitalize" );
        assertThat( Strings.decapitalize( "AlreadyCapitalized" ) ).isEqualTo( "alreadyCapitalized" );
        assertThat( Strings.decapitalize( "notCapitalized" ) ).isEqualTo( "notCapitalized" );
    }

    @Test
    public void testJoin() {
        assertThat( Strings.join( new ArrayList<String>(), "-" ) ).isEqualTo( "" );
        assertThat( Strings.join( Arrays.asList( "Hello", "World" ), "-" ) ).isEqualTo( "Hello-World" );
        assertThat( Strings.join( Arrays.asList( "Hello" ), "-" ) ).isEqualTo( "Hello" );
    }

    @Test
    public void testJoinAndCamelize() {
        assertThat( Strings.joinAndCamelize( new ArrayList<String>() ) ).isEqualTo( "" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "Hello", "World" ) ) ).isEqualTo( "HelloWorld" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "Hello", "world" ) ) ).isEqualTo( "HelloWorld" );
        assertThat( Strings.joinAndCamelize( Arrays.asList( "hello", "world" ) ) ).isEqualTo( "helloWorld" );
    }

    @Test
    public void testIsEmpty() {
        assertThat( Strings.isEmpty( null ) ).isTrue();
        assertThat( Strings.isEmpty( "" ) ).isTrue();
        assertThat( Strings.isEmpty( " " ) ).isFalse();
        assertThat( Strings.isEmpty( "not empty" ) ).isFalse();
    }

    @Test
    public void testGetSaveVariableNameWithArrayExistingVariables() {
        assertThat( Strings.getSafeVariableName( "int[]" ) ).isEqualTo( "intArray" );
        assertThat( Strings.getSafeVariableName( "Extends" ) ).isEqualTo( "extends1" );
        assertThat( Strings.getSafeVariableName( "class" ) ).isEqualTo( "class1" );
        assertThat( Strings.getSafeVariableName( "Class" ) ).isEqualTo( "class1" );
        assertThat( Strings.getSafeVariableName( "Case" ) ).isEqualTo( "case1" );
        assertThat( Strings.getSafeVariableName( "Synchronized" ) ).isEqualTo( "synchronized1" );
        assertThat( Strings.getSafeVariableName( "prop", "prop", "prop_" ) ).isEqualTo( "prop1" );
        assertThat( Strings.getSafeVariableName( "_Test" ) ).isEqualTo( "test" );
        assertThat( Strings.getSafeVariableName( "__Test" ) ).isEqualTo( "test" );
    }

    @Test
    public void testGetSaveVariableNameVariablesEndingOnNumberVariables() {
        assertThat( Strings.getSafeVariableName( "prop1", "prop1" ) ).isEqualTo( "prop1_1" );
        assertThat( Strings.getSafeVariableName( "prop1", "prop1", "prop1_1" ) ).isEqualTo( "prop1_2" );
    }

    @Test
    public void testGetSaveVariableNameWithCollection() {
        assertThat( Strings.getSafeVariableName( "int[]", new ArrayList<>() ) ).isEqualTo( "intArray" );
        assertThat( Strings.getSafeVariableName( "Extends", new ArrayList<>() ) ).isEqualTo( "extends1" );
        assertThat( Strings.getSafeVariableName( "prop", Arrays.asList( "prop", "prop1" ) ) ).isEqualTo( "prop2" );
        assertThat( Strings.getSafeVariableName( "prop.font", Arrays.asList( "propFont", "propFont_" ) ) )
            .isEqualTo( "propFont1" );
        assertThat( Strings.getSafeVariableName( "_Test", new ArrayList<>() ) ).isEqualTo( "test" );
        assertThat( Strings.getSafeVariableName( "__Test", Arrays.asList( "test" ) ) ).isEqualTo( "test1" );
        assertThat( Strings.getSafeVariableName( "___", new ArrayList<>() ) ).isEqualTo( "___" );
    }

    @Test
    public void testSanitizeIdentifierName() {
        assertThat( Strings.sanitizeIdentifierName( "test" ) ).isEqualTo( "test" );
        assertThat( Strings.sanitizeIdentifierName( "int[]" ) ).isEqualTo( "intArray" );
        assertThat( Strings.sanitizeIdentifierName( "_Test" ) ).isEqualTo( "Test" );
        assertThat( Strings.sanitizeIdentifierName( "_int[]" ) ).isEqualTo( "intArray" );
        assertThat( Strings.sanitizeIdentifierName( "__int[]" ) ).isEqualTo( "intArray" );
        assertThat( Strings.sanitizeIdentifierName( "test_" ) ).isEqualTo( "test_" );
        assertThat( Strings.sanitizeIdentifierName( "___" ) ).isEqualTo( "___" );
    }

    @Test
    public void findMostSimilarWord() {
        String mostSimilarWord = Strings.getMostSimilarWord(
            "fulName",
            Arrays.asList( "fullAge", "fullName", "address", "status" )
        );
        assertThat( mostSimilarWord ).isEqualTo( "fullName" );
    }

    @Test
    public void capitalizeEnglish() {
        Locale.setDefault( Locale.ENGLISH );
        String international = Strings.capitalize( "international" );
        assertThat( international ).isEqualTo( "International" );
    }

    @Test
    public void decapitalizeEnglish() {
        Locale.setDefault( Locale.ENGLISH );
        String international = Strings.decapitalize( "International" );
        assertThat( international ).isEqualTo( "international" );
    }

    @Test
    public void capitalizeTurkish() {
        Locale.setDefault( TURKEY_LOCALE );
        String international = Strings.capitalize( "international" );
        assertThat( international ).isEqualTo( "International" );
    }

    @Test
    public void decapitalizeTurkish() {
        Locale.setDefault( TURKEY_LOCALE );
        String international = Strings.decapitalize( "International" );
        assertThat( international ).isEqualTo( "international" );
    }

    private static Locale getTurkeyLocale() {
        Locale turkeyLocale = Locale.forLanguageTag( "tr" );

        if ( turkeyLocale == null ) {
            throw new IllegalStateException( "Can't find Turkey locale." );
        }

        return turkeyLocale;
    }
}
