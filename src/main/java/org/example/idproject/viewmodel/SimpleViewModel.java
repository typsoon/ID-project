package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleViewModel implements ViewModel{
    private String url;
    private Credentials credentials;
    private static final String urlBase = "jdbc:postgresql://localhost:5432/";
    @Override
    public Collection<BasicPlayerData> browsePlayers(String nickName) {

        Collection<BasicPlayerData> players = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from SearchPlayers(\'" + nickName + "\');");
            while (rs.next()) {
                players.add(new BasicPlayerData(Integer.parseInt(rs.getString(1)),rs.getString(2)));
               // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return players;
    }

    @Override
    public Collection<BasicPlayerData> getPlayers() {
        Collection<BasicPlayerData> players = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select player_ID, PlayerNickname(player_ID) from players");
            while (rs.next()) {
                players.add(new BasicPlayerData(Integer.parseInt(rs.getString(1)),rs.getString(2)));
                //System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return players;
    }

    @Override
    public FullPlayerData getFullPlayerData(int playerId) {
        return null;
    }

    @Override
    public Collection<BasicClanData> browseClans(String name) {
        return List.of();
    }

    @Override
    public FullClanData getFullClanData(int clanId) {

        return null;
    }

    @Override
    public boolean tryLogIn(Credentials credentials) {
        this.credentials = credentials;
        url=urlBase+credentials.username();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            return true;
        }
        catch (Exception e) {
           return false;
        }
    }

    public static void main(String[] args) {
        SimpleViewModel simpleViewModel = new SimpleViewModel();
        simpleViewModel.tryLogIn(new Credentials("riper", "aaa"));
        Collection<BasicPlayerData> players = simpleViewModel.getPlayers();
    }
}
