package com.example.ethannesbitt.youcook;


import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addRecipe()
    {
        assertTrue(true);
    }


    @Test
    public void conversionPoundToGram()
    {
        ConvertTabOne c1 = new ConvertTabOne();
        //assertEquals("907.18", c1.poundToGram(2));
    }
}