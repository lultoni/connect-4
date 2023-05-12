import java.util.HashMap;

public class HashRep {

    private final HashMap<Integer, Integer> table = new HashMap<>();
    // Code-ID, Eval

    protected int prevSearch(int[] position) {
        int code = decode(position);
        if (table.containsKey(code)) {
            return table.get(code);
        }
        return 69420;
    }

    protected void add(int[] position, int eval) {
        table.put(decode(position), eval);
    }

    protected void clear() {
        table.clear();
    }

    private int decode(int[] position) {
        int code = 0;
        for (int i = 0; i < position.length; i++) {
            code += i * position[i] + 1;
        }
        return code;
    }

}
