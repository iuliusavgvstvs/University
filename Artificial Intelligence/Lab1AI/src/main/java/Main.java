import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class Main {
    public static double p2(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2){
        return Math.sqrt(Math.pow(((p1.getKey()-p2.getKey())),2)+Math.pow(((p1.getValue()-p2.getValue())),2));
    }

public static ArrayList<String> p4(String s){
        HashMap<String, Integer> hmap = new HashMap<>();
        HashMap<String, Integer> hmap2 = new HashMap<>();
        ArrayList<String> rez = new ArrayList<>();
        String[] s2 = s.split(" ");
        for(String cuv:s2){
            if(hmap.get(cuv)!=null&&hmap2.get(cuv)==null) {
                hmap.remove(cuv);
                hmap2.put(cuv,1);
            }
            else
                hmap.put(cuv,0);
        }
    for(String key: hmap.keySet()){
        rez.add(key);
    }
    return rez;
}

    public static String p1(String s){
        String max;
        String[] s2 = s.split(" ");
        max= s2[0];
        for(String cuv:s2){
            if(cuv.toLowerCase().compareTo(max.toLowerCase())>0)
                max=cuv;
        }
        return max;
    }

public static int p6(int[] v){
    int[] a = new int[100];
    for (int value : v)
        if (++a[value]>(v.length/2))
            return value;
    return -1;
}

    public static int p3v3(int[] v){
        int[] a = new int[v.length];
        for (int value : v)
            if (++a[value]>1)
                return value;
        return -1;
    }




    public static void main(String[] args) {
        int[] v2 = {1, 2, 3, 4, 2};
        int[] v3 = {2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2};
        System.out.println(p3v3(v2));

        String s = "ana are ana are mere rosii ana";
        String s3 = "ana are mere rosii si galbene";
        System.out.println("Cuvintele care apar o singura data in sirul : "+s+" sunt");
        for(String s2:p4(s))
            System.out.println(s2);
        System.out.println(p6(v3));
        System.out.println(p1(s3));
    }
}
