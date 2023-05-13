import java.util.HashMap;

public class HashRep {

    private final HashMap<int[], Integer> table = new HashMap<>();

    protected int prevSearch(int[] position) {
        if (table.containsKey(position)) {
            return table.get(position);
        }
        return 69420;
    }

    protected void add(int[] position, int eval) {
        table.put(position, eval);
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
