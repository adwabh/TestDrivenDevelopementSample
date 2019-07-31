package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector intervalsAdjacencyDetector;
    @Before
    public void setUp() throws Exception {
        intervalsAdjacencyDetector = new IntervalsAdjacencyDetector();
    }
    //interval1 is before interval2 with no adjacency or overlap

    @Test
    public void testIntervalAdjacency_interval1beforeInterval2_returnFalse() {
        Interval interval1 = new Interval(-2,1);
        Interval interval2 = new Interval(3,5);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }

    //interval1 is after interval2 with no adjacency or overlap
    @Test
    public void testIntervalAdjacency_interval1afterInterval2_returnFalse() {
        Interval interval1 = new Interval(7,9);
        Interval interval2 = new Interval(-3,5);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
    //interval1 is after interval2 and adjacent
    @Test
    public void testIntervalAdjacency_interval1afterInterval2Adjacent_returnTrue() {
        Interval interval1 = new Interval(5,7);
        Interval interval2 = new Interval(3,5);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertTrue(adjacencyResult);
    }
    //interval1 is before interval2 and adjacent
    @Test
    public void testIntervalAdjacency_interval1beforeInterval2Adjacent_returnTrue() {
        Interval interval1 = new Interval(-1,3);
        Interval interval2 = new Interval(3,5);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertTrue(adjacencyResult);
    }
    //interval1 is after interval2 and overlaps at start
    @Test
    public void testIntervalAdjacency_interval1afterInterval2Overlap_returnFalse() {
        Interval interval1 = new Interval(-1,7);
        Interval interval2 = new Interval(3,9);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
    //interval1 is before interval2 and overlaps at end
    @Test
    public void testIntervalAdjacency_interval1beforeInterval2Overlap_returnFalse() {
        Interval interval1 = new Interval(-1,4);
        Interval interval2 = new Interval(3,5);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
    //interval1 is contained within interval2
    @Test
    public void testIntervalAdjacency_interval1isContainedInInterval2_returnFalse() {
        Interval interval1 = new Interval(4,5);
        Interval interval2 = new Interval(3,7);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
    //interval2 is contained within interval1
    @Test
    public void testIntervalAdjacency_interval2isContainedInInterval1_returnFalse() {
        Interval interval1 = new Interval(-1,7);
        Interval interval2 = new Interval(3,6);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
    //interval1 equals interval2
    @Test
    public void testIntervalAdjacency_interval1EqualsInterval2_returnFalse() {
        Interval interval1 = new Interval(3,6);
        Interval interval2 = new Interval(3,6);
        boolean adjacencyResult = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        Assert.assertFalse(adjacencyResult);
    }
}