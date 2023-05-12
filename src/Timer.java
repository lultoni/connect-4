import java.util.ArrayList;

public class Timer {

    static long startTime;
    static long endTime;

    static ArrayList<Long> times = new ArrayList<>(0);

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void end() {
        endTime = System.nanoTime();
        long outTime = Math.round((endTime - startTime) / 1e+6);
        times.add(outTime);
//        System.out.println("Time needed: " + outTime + " ms");
    }

    public static long averageMs() {
        long out = 0;
        for (long l: times) {
            out += l;
        }
        try {
            return out / times.size();
        } catch (ArithmeticException use) {
            return 0;
        }
    }

}
