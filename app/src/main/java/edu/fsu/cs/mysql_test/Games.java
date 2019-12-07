package edu.fsu.cs.mysql_test;

public class Games {

    private int GameLogo;
    private String GameName;

    public Games(int gameLogo, String gameName) {
        GameLogo = gameLogo;
        GameName = gameName;
    }

    public int getGameLogo() {

        return GameLogo;
    }

    public String getGameName() {

        return GameName;
    }
}

