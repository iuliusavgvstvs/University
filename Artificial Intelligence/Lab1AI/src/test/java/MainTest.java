
import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void p4() {
        String s1 = "ana are mere";
        String s2 = "ana ana are mere";
        String s3 = "ana ana are are mere";
        String s4 = "ana ana are are mere mere";
        assertEquals(Main.p4(s1).size(),3);
        assertEquals(Main.p4(s2).size(),2);
        assertEquals(Main.p4(s3).size(),1);
        assertEquals(Main.p4(s4).size(),0);
        assertEquals(Main.p4(s1).get(0),"ana");
        assertEquals(Main.p4(s1).get(1),"are");
        assertEquals(Main.p4(s1).get(2),"mere");
        assertEquals(Main.p4(s2).get(0),"are");
        assertEquals(Main.p4(s2).get(1),"mere");
        assertEquals(Main.p4(s3).get(0),"mere");
    }

    @Test
    public void p3v3() {
        int[] v = {1, 2, 3, 4, 2};
        int[] v2 = {1, 2, 3, 4, 5, 6, 7, 4, 8};
        int[] v3 = {1, 1, 2, 3, 4, 5};
        int[] v4 = {1, 2, 3, 3, 4, 5};
        int[] v5 = {1, 2, 3, 4, 5, 6, 9, 7, 8, 9};
        assertEquals(Main.p3v3(v),2);
        assertEquals(Main.p3v3(v2),4);
        assertEquals(Main.p3v3(v3),1);
        assertEquals(Main.p3v3(v4),3);
        assertEquals(Main.p3v3(v5),9);
    }

    @Test
    public void p2() {
        Pair<Integer, Integer> pair1 = new Pair(1,5);
        Pair<Integer, Integer> pair2 = new Pair(4,1);
        Pair<Integer, Integer> pair3 = new Pair(5,2);
        Pair<Integer, Integer> pair4 = new Pair(2,3);
        assertEquals(Main.p2(pair1,pair2),5.0,0.0f);
        assertEquals(Main.p2(pair1,pair3),5.0,0.0f);
        assertEquals(Main.p2(pair2,pair4),2.8,0.1);
    }

    @Test
    public void p6() {
        int[] v = {1, 2, 2, 4, 2};
        int[] v2 = {1, 3, 3, 4, 3, 6, 7, 3, 3};
        int[] v3 = {1, 1, 2, 3, 1, 1};
        int[] v4 = {1, 5, 3, 5, 4, 5, 5};
        assertEquals(Main.p6(v),2);
        assertEquals(Main.p6(v2),3);
        assertEquals(Main.p6(v3),1);
        assertEquals(Main.p6(v4),5);
    }
    @Test
    public void p1(){
        String s1 = "ana are mere galbene si rosii";
        String s2 = "ana ana mere roz ";
        String s3 = "ana are mere roz si sare sus";
        String s4 = "ana ana mere si zazzz";
        assertEquals(Main.p1(s1),"si");
        assertEquals(Main.p1(s2),"roz");
        assertEquals(Main.p1(s3),"sus");
        assertEquals(Main.p1(s4),"zazzz");
    }
}