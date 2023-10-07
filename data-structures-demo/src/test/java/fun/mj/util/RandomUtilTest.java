package fun.mj.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilTest {
    @Test
    public void testRangeRandom() {
        System.out.println(Arrays.toString(RandomUtil.rangeRandom(10, 1, 100,true)));
    }
}