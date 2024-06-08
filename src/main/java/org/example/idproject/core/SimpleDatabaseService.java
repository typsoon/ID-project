package org.example.idproject.core;


import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.common.*;
import org.example.idproject.viewmodel.DatabaseService;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleDatabaseService implements DatabaseService {
    private String url;
    private Credentials credentials;
    private static final String urlBase = "jdbc:postgresql://localhost:5432/";
    @Override
    public Collection<BasicPlayerData> browsePlayers(String nickName) throws SQLException {

        Collection<BasicPlayerData> players = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from SearchPlayers(\'" + nickName + "\');");
            while (rs.next()) {
                players.add(new BasicPlayerData(rs.getInt(1),rs.getString(2)));
               // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return players;
    }



    @Override
    public FullPlayerData getFullPlayerData(int playerId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from FullPlayerData where player_ID = (\'" + playerId + "\');");
            if (rs.next()) {
                return
                new FullPlayerData(
                        rs.getLong(1),rs.getString(2),rs.getString(3), null,
                        new BasicPlayerData(rs.getInt(4),rs.getString(5))
                );
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public Collection<BasicPlayerData> getAllPlayers() throws SQLException {
        Collection<BasicPlayerData> players = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select player_ID, PlayerNickname(player_ID) from players");
            while (rs.next()) {
                players.add(new BasicPlayerData(rs.getInt(1),rs.getString(2)));
                //System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return players;
    }

    @Override
    public boolean insertPlayer(String login, String password, String nickName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            stmt.execute( "insert into fullplayerdata (password_hash, login, playernickname) values (" + password.hashCode() + ",'" + login + "','" + nickName + "');" );
            return true;
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean insertClan(int leaderID, LocalDate dateFrom, String clanName, String logoFilePath) throws SQLException {
        return false;
    }

    @Override
    public Collection<BasicClanData> browseClans(String name) throws SQLException {
        Collection<BasicClanData> clans = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from searchclans(\'" + name + "\');");
            while (rs.next()) {
                clans.add(new BasicClanData(rs.getInt(1),rs.getString(2)));
                //System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return clans;
    }

    @Override
    public FullClanData getFullClanData(int clanId) throws SQLException {

        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from fullclandata where clan_ID = (\'" + clanId + "\');");
        if(rs.next())
            return new FullClanData(rs.getString(1), new BasicClanData(rs.getInt(2),rs.getString(3)));
        return null;
    }

    @Override
    public Collection<BasicClanData> getAllClans() throws SQLException {
        Collection<BasicClanData> clans = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select clan_id, clanname(clan_id) from clans");
            while (rs.next()) {
                clans.add(new BasicClanData(rs.getInt(1),rs.getString(2)));
                //System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return clans;
    }

    @Override
    public Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo) {
        return List.of();
    }


    @Override
    public Collection<BasicDuelData> getAllDuels() {
        return List.of();
    }

    @Override
    public Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo) {
        return List.of();
    }

    @Override
    public Collection<BasicChallengeData> getAllChallenges() {
        return List.of();
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
        SimpleDatabaseService simpleViewModel = new SimpleDatabaseService();
        simpleViewModel.tryLogIn(new Credentials("riper", "aaa"));
        try
        {
           FullClanData CL = simpleViewModel.getFullClanData(1);

           return;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
