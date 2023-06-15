package cardnight.games.tictactoe;

import cardnight.Tools;

public class TicTacToeGegner {

    public static int zugMachen(String[] feld) {

        String[] spielerStrings = {"o", "x"};
        for (int i = 0; i < 2; i++) {
            String xo = spielerStrings[i];

            if (feld[0].equals(xo) && feld[1].equals(xo) && feld[2].isEmpty())
                return 2;
            else if (feld[3].equals(xo) && feld[4].equals(xo) && feld[5].isEmpty())
                return 5;
            else if (feld[6].equals(xo) && feld[7].equals(xo) && feld[8].isEmpty())
                return 8;
            else if (feld[1].equals(xo) && feld[2].equals(xo) && feld[0].isEmpty())
                return 0;
            else if (feld[4].equals(xo) && feld[5].equals(xo) && feld[3].isEmpty())
                return 3;
            else if (feld[7].equals(xo) && feld[8].equals(xo) && feld[6].isEmpty())
                return 6;
            else if (feld[0].equals(xo) && feld[2].equals(xo) && feld[1].isEmpty())
                return 1;
            else if (feld[3].equals(xo) && feld[5].equals(xo) && feld[4].isEmpty())
                return 4;
            else if (feld[6].equals(xo) && feld[8].equals(xo) && feld[7].isEmpty())
                return 7;
            else if (feld[1].equals(xo) && feld[4].equals(xo) && feld[7].isEmpty())
                return 7;
            else if (feld[2].equals(xo) && feld[5].equals(xo) && feld[8].isEmpty())
                return 8;
            else if (feld[3].equals(xo) && feld[6].equals(xo) && feld[0].isEmpty())
                return 0;
            else if (feld[4].equals(xo) && feld[7].equals(xo) && feld[1].isEmpty())
                return 1;
            else if (feld[5].equals(xo) && feld[8].equals(xo) && feld[2].isEmpty())
                return 2;
            else if (feld[0].equals(xo) && feld[6].equals(xo) && feld[3].isEmpty())
                return 3;
            else if (feld[1].equals(xo) && feld[7].equals(xo) && feld[4].isEmpty())
                return 4;
            else if (feld[2].equals(xo) && feld[8].equals(xo) && feld[5].isEmpty())
                return 5;
            else if (feld[0].equals(xo) && feld[4].equals(xo) && feld[8].isEmpty())
                return 8;
            else if (feld[4].equals(xo) && feld[8].equals(xo) && feld[0].isEmpty())
                return 0;
            else if (feld[0].equals(xo) && feld[8].equals(xo) && feld[4].isEmpty())
                return 4;
            else if (feld[6].equals(xo) && feld[4].equals(xo) && feld[2].isEmpty())
                return 2;
            else if (feld[4].equals(xo) && feld[2].equals(xo) && feld[6].isEmpty())
                return 6;
            else if (feld[6].equals(xo) && feld[2].equals(xo) && feld[4].isEmpty())
                return 4;
            else if (feld[0].equals(xo) && feld[1].equals(xo) && feld[2].isEmpty())
                return 2;
            else if (feld[3].equals(xo) && feld[4].equals(xo) && feld[5].isEmpty())
                return 5;
            else if (feld[6].equals(xo) && feld[7].equals(xo) && feld[8].isEmpty())
                return 8;
            else if (feld[1].equals(xo) && feld[2].equals(xo) && feld[0].isEmpty())
                return 0;
            else if (feld[4].equals(xo) && feld[5].equals(xo) && feld[3].isEmpty())
                return 3;
            else if (feld[7].equals(xo) && feld[8].equals(xo) && feld[6].isEmpty())
                return 6;
            else if (feld[0].equals(xo) && feld[2].equals(xo) && feld[1].isEmpty())
                return 1;
            else if (feld[3].equals(xo) && feld[5].equals(xo) && feld[4].isEmpty())
                return 4;
        }

        int zahl = Tools.random.nextInt(9);
        while (!feld[zahl].isEmpty())
            zahl = Tools.random.nextInt(9);

        return zahl;
    }
}
