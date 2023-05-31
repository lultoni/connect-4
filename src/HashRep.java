import java.util.HashMap;
import java.util.Random;

public class HashRep {

    private final HashMap<Long, Integer> table = new HashMap<>();
    private static final HashMap<String, Integer> openingBook = new HashMap<>();

    protected int prevSearch(long positionId) {
        if (table.containsKey(positionId)) {
            return table.get(positionId);
        }
        return 69420;
    }

    protected void add(long positionId, int eval) {
        table.put(positionId, eval);
    }

    protected void clear() {
        table.clear();
    }

    static protected long decode(int[] position) {
        return calculateZobristHash(position);
    }

    protected int size() {
        return table.size();
    }

    static protected int findOpening(String position) {
        if (openingBook.containsKey(position)) {
            return openingBook.get(position);
        }
        return 0;
    }

    static protected void addOpening(String position, int move) {
        openingBook.put(position, move);
    }

    private static final int BOARD_SIZE = 42;
    private static final int NUM_PLAYERS = 2;
    private static final long[][] zobristTable = new long[BOARD_SIZE][NUM_PLAYERS];

    private static final Random random = new Random();

    public static void initializeZobristTable() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                zobristTable[i][j] = random.nextLong();
            }
        }
    }

    public static long calculateZobristHash(int[] board) {
        long hash = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            int player = board[i] + 1; // Adjusting player values to be 0-based index

            if (player != 0) {
                hash ^= zobristTable[i][player - 1];
            }
        }

        return hash;
    }

}
