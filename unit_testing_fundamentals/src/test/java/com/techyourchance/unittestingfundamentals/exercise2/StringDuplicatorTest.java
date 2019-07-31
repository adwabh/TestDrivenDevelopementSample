package com.techyourchance.unittestingfundamentals.exercise2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringDuplicatorTest {

    StringDuplicator duplicator;
    @Before
    public void setUp() throws Exception {
        duplicator = new StringDuplicator();
    }

    @Test
    public void testDuplicator_emptyString_emptyString() {
        String duplicatorResult = duplicator.duplicate("");
        Assert.assertThat(duplicatorResult, CoreMatchers.is(""));
    }

    @Test
    public void testDuplicator_singleCharacter_duplicatedCharacterString() {
        String duplicatorResult = duplicator.duplicate("a");
        Assert.assertThat(duplicatorResult, CoreMatchers.is("aa"));
    }

    @Test
    public void testDuplicate_longSting_dobleLengthDuplicateString() {
        String duplicatorResult = duplicator.duplicate("Adwait Abhyankar");
        Assert.assertThat(duplicatorResult, CoreMatchers.is("Adwait AbhyankarAdwait Abhyankar"));
    }
}