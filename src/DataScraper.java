import java.io.IOException;
import java.nio.file.*;

public class DataScraper {

    // Depth (DataScraper)
    // Time (Timer)
    // Winner (Board (evaluate))
    // Free Fields (DataScraper)
    public static void getData(int depth, int gamesToPlay) {
        System.out.println("Fetching " + gamesToPlay + " games with a depth of " + depth + ".");
        for (int i = 0; i < gamesToPlay; i++) {
            System.out.println("> Currently on game " + (i + 1));
            Board.getInstance().gameLoop(new Player(2, depth - 1), new Player(2, depth - 1), new int[42], false);
            String winner;
            if (Board.getInstance().evaluate() == 99999) {
                winner = "White";
            } else if (Board.getInstance().evaluate() == -99999) {
                winner = "Black";
            } else {
                winner = "Draw";
            }
            String dataLine = depth + " " + Timer.averageMs() + " " + winner + " " + countFreeFields() + "\n";
            try {
                Files.write(Paths.get("data.txt"), dataLine.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                System.out.println("THROW IO BRO!!!");
            }
        }
        System.out.println("Ready to import.");
    }

    private static int countFreeFields() {
        int counter = 0;
        for (int f: Board.getInstance().getPlayingBoard()) {
            if (f == 0) {
                counter++;
            }
        }
        return counter;
    }

}
