package algorithms;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class OptionAnalysisTest {
    public void shouldReturnSingleLargest() {
        OptionAnalysis oa = new OptionAnalysis(4.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getCost(), 3.2);
        assertEquals(space.getFillers().size(), 1);
        assertEquals(space.getFillers().get(0).getArea(), 4.0);
    }

    public void shouldGetSingleLargeAndSingleSmallInterMediate() {
        OptionAnalysis oa = new OptionAnalysis(4.5);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
        assertEquals(space.getFillers().get(1).getArea(), 4.0);
    }

    public void shouldGetSingleLargeAndSingleSmallExact() {
        OptionAnalysis oa = new OptionAnalysis(5.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
        assertEquals(space.getFillers().get(1).getArea(), 4.0);
    }

    public void shouldGetSingleLargeAndSingleMidIntermediate() {
        OptionAnalysis oa = new OptionAnalysis(5.5);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 2.0);
        assertEquals(space.getFillers().get(1).getArea(), 4.0);
    }

    public void shouldGetSingleLargeAndSingleMidExact() {
        OptionAnalysis oa = new OptionAnalysis(6.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 2.0);
        assertEquals(space.getFillers().get(1).getArea(), 4.0);
    }

    public void shouldGetOneOfEach() {
        OptionAnalysis oa = new OptionAnalysis(6.5);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 3);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
        assertEquals(space.getFillers().get(1).getArea(), 2.0);
        assertEquals(space.getFillers().get(2).getArea(), 4.0);
    }

    public void shouldGetTwoLargesSmallerArea() {
        OptionAnalysis oa = new OptionAnalysis(7.1);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 4.0);
        assertEquals(space.getFillers().get(1).getArea(), 4.0);
    }

    public void shouldGetSingleMidAndSingleSmall() {
        OptionAnalysis oa = new OptionAnalysis(3.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getFillers().size(), 2);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
        assertEquals(space.getFillers().get(1).getArea(), 2.0);
    }

    public void shouldGetSingleMid() {
        OptionAnalysis oa = new OptionAnalysis(2.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getCost(), 1.8);
        assertEquals(space.getFillers().size(), 1);
        assertEquals(space.getFillers().get(0).getArea(), 2.0);
    }

    public void shouldGetSingleMidIntermediateArea() {
        OptionAnalysis oa = new OptionAnalysis(1.1);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getCost(), 1.8);
        assertEquals(space.getFillers().size(), 1);
        assertEquals(space.getFillers().get(0).getArea(), 2.0);
    }

    public void shouldGetSingleSmall() {
        OptionAnalysis oa = new OptionAnalysis(1.0);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getCost(), 1.0);
        assertEquals(space.getFillers().size(), 1);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
    }

    public void shouldGetSingleSmallIntermediate() {
        OptionAnalysis oa = new OptionAnalysis(0.5);
        OptionAnalysis.Space space = oa.evaluateSpace();
        assertEquals(space.getCost(), 1.0);
        assertEquals(space.getFillers().size(), 1);
        assertEquals(space.getFillers().get(0).getArea(), 1.0);
    }
}