import java.util.HashMap;

public class HashRep {

    private final HashMap<Integer, Integer> table = new HashMap<>();

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

    protected int decode(int[] position) {
        int code = 0;
        for (int i = 0; i < position.length; i++) {
            switch (position[i]) {
                case 0 -> code += 3^i;
                case 1 -> code += 2 * (3^i);
                case -1 -> code += 3 * (3^i);
            }
        }
        return code;
    }

    protected int size() {
        return table.size();
    }

}
