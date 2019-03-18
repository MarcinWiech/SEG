package seg.tests;//package seg.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seg.java.models.RedeclarationComputer;
import seg.java.models.Runway;

import static org.junit.Assert.assertEquals;

public class RedeclarationComputerTester {

    private RedeclarationComputer redeclarationComputer;
    private Runway runway;

    @Before
    public void setUp() throws Exception
    {
        redeclarationComputer = new RedeclarationComputer();
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void test1()
    {
        runway = new Runway("09L", "27R", 3902.0, 3902.0, 3902.0, 3596.0, 306.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(-50.0, 3646.0, 0.0, 12.0);
        if(redeclarationComputer.needsRecalculation(-50.0, 3646.0, 0.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 3346, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 3346, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 3346, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 2986, 0.00001);
    }

    @Test
    public void test2()
    {
        runway = new Runway("27R", "09L", 3884.0, 3962.0, 3884.0, 3884.0, 0.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(-50.0, 3646.0, 0.0, 12.0);
        if(redeclarationComputer.needsRecalculation(-50.0, 3646.0, 0.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 2986, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 2986, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 2986, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 3346, 0.00001);
    }

    @Test
    public void test3()
    {
        runway = new Runway("09R", "27L", 3660.0, 3660.0, 3660.0, 3353.0, 307.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(500.0, 2853.0, -20.0, 25.0);
        if(redeclarationComputer.needsRecalculation(500.0, 2853.0, -20.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 1850, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 1850, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 1850, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 2553, 0.00001);
    }

    @Test
    public void test4()
    {
        runway = new Runway("27L", "09R", 3660.0, 3660.0, 3660.0, 3660.0, 0.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(500.0, 2853.0, -20.0, 25.0);
        if(redeclarationComputer.needsRecalculation(500.0, 2853.0, -20.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 2860, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 2860, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 2860, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 1850, 0.00001);
    }

    @Test
    public void test5()
    {
        runway = new Runway("09R", "27L", 3660.0, 3660.0, 3660.0, 3353.0, 307.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(3203.0, 150.0, 60.0, 15.0);
        if(redeclarationComputer.needsRecalculation(3203.0, 150.0, 60.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 2903, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 2903, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 2903, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 2393, 0.00001);
    }

    @Test
    public void test6()
    {
        runway = new Runway("27L", "09R", 3660.0, 3660.0, 3660.0, 3660.0, 0.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(3203.0, 150.0, 60.0, 15.0);
        if(redeclarationComputer.needsRecalculation(3203.0, 150.0, 60.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 2393, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 2393, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 2393, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 2903, 0.00001);
    }

    @Test
    public void test7()
    {
        runway = new Runway("09L", "27R", 3902.0, 3902.0, 3902.0, 3596.0, 306.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(3546.0, 50.0, 20.0, 20.0);
        if(redeclarationComputer.needsRecalculation(3546.0, 50.0, 20.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 2792, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 2792, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 2792, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 3246, 0.00001);
    }

    @Test
    public void test8()
    {
        runway = new Runway("27R", "09L", 3884.0, 3962.0, 3884.0, 3884.0, 0.0);
        redeclarationComputer.setRunway(runway);
        redeclarationComputer.setObstacleDetails(3546.0, 50.0, 20.0, 20.0);
        if(redeclarationComputer.needsRecalculation(3546.0, 50.0, 20.0))
            redeclarationComputer.calculate();

        assertEquals(redeclarationComputer.getTora(), 3534, 0.00001);
        assertEquals(redeclarationComputer.getToda(), 3612, 0.00001);
        assertEquals(redeclarationComputer.getAsda(), 3534, 0.00001);
        assertEquals(redeclarationComputer.getLda(), 2774, 0.00001);
    }
}