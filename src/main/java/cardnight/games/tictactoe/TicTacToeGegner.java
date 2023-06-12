package cardnight.games.tictactoe;

public class TicTacToeGegner {

    public static int zugMachen(String[] feld) {

        String[] spielerStrings = {"o", "x"};
        for (int i = 0 ; i < 2 ; i++) {
            String xo = spielerStrings[i];

            if (feld[0].equals(xo) && feld[1].equals(xo) && feld[2].equals(""))
                return 2;
            else if (feld [3].equals(xo) && feld [4].equals(xo) && feld[5].equals(""))
                return 5;
            else if (feld[6].equals(xo) && feld [7].equals(xo) && feld[8].equals(""))
                return 8;
            else if(feld[1].equals(xo) && feld [2].equals(xo) && feld [0].equals(""))
                return 0;
            else if(feld [4].equals(xo) && feld [5].equals(xo) && feld [3].equals(""))
                return 3;
            else if (feld[7].equals(xo) && feld [8].equals(xo) && feld [6].equals(""))
                return 6;
            else if(feld[0].equals(xo) && feld [2].equals(xo) && feld [1].equals(""))
                return 1;
            else if(feld [3].equals(xo) && feld [5].equals(xo) && feld [4].equals(""))
                return 4;
            else if (feld[6].equals(xo) && feld [8].equals(xo) && feld [7].equals(""))
                return 7;
            else if(feld[1].equals(xo) && feld [4].equals(xo) && feld [7].equals(""))
                return 7;
            else if(feld [2].equals(xo) && feld [5].equals(xo) && feld [8].equals(""))
                return 8;
            else if(feld[3].equals(xo) && feld [6].equals(xo) && feld [0].equals(""))
                return 0;
            else if(feld [4].equals(xo) && feld [7].equals(xo) && feld [1].equals(""))
                return 1;
            else if(feld[5].equals(xo) && feld [8].equals(xo) && feld [2].equals(""))
                return 2;
            else if(feld[0].equals(xo) && feld [6].equals(xo) && feld [3].equals(""))
                return 3;
            else if(feld [1].equals(xo) && feld [7].equals(xo) && feld [4].equals(""))
                return 4;
            else if(feld[2].equals(xo) && feld [8].equals(xo) && feld [5].equals(""))
                return 5;
            else if(feld[0].equals(xo) && feld [4].equals(xo)&& feld [8].equals(""))
                return 8;
            else if(feld[4].equals(xo) && feld [8].equals(xo) && feld [0].equals(""))
                return 0;
            else if(feld[0].equals(xo) && feld [8].equals(xo) && feld [4].equals(""))
                return 4;
            else if(feld[6].equals(xo) && feld [4].equals(xo) && feld [2].equals(""))
                return 2;
            else if(feld[4].equals(xo) && feld [2].equals(xo) && feld [6].equals(""))
                return 6;
            else if(feld[6].equals(xo) && feld [2].equals(xo) && feld [4].equals(""))
                return 4;
            else if (feld[0].equals(xo) && feld[1].equals(xo) && feld [2].equals(""))
                return 2;
            else if (feld [3].equals(xo) && feld [4].equals(xo) && feld [5].equals(""))
                return 5;
            else if (feld[6].equals(xo) && feld [7].equals(xo) && feld [8].equals(""))
                return 8;
            else if(feld[1].equals(xo) && feld [2].equals(xo) && feld [0].equals(""))
                return 0;
            else if(feld [4].equals(xo) && feld [5].equals(xo) && feld [3].equals(""))
                return 3;
            else if (feld[7].equals(xo) && feld [8].equals(xo) && feld [6].equals(""))
                return 6;
            else if(feld[0].equals(xo) && feld [2].equals(xo) && feld [1].equals(""))
                return 1;
            else if(feld [3].equals(xo) && feld [5].equals(xo) && feld [4].equals(""))
                return 4;
        }

        int zahl = (int) (Math.random() * 8);
        while (!feld[zahl].equals(""))
            zahl = (int) (Math.random() * 8);

        return zahl;
    }
}
