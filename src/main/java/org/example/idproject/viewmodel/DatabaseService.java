package org.example.idproject.viewmodel;

import org.example.idproject.common.*;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public interface DatabaseService {
//    Returns data to be displayed in searchView after nickName is searched
    Collection<BasicPlayerData> browsePlayers(String nickName) throws SQLException;
    FullPlayerData getFullPlayerData(int playerId) throws SQLException;
    Collection<BasicPlayerData> getAllPlayers() throws SQLException;

    boolean insertPlayer(String login, String password, String nickName) throws SQLException;
    boolean insertClan(int leaderID, LocalDate dateFrom, String clanName, String logoFilePath) throws SQLException;
    
    Collection<BasicClanData> browseClans(String name) throws SQLException;
    FullClanData getFullClanData(int clanId) throws SQLException;
    Collection<BasicClanData> getAllClans() throws SQLException;

    Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo) throws SQLException;
    Collection<BasicDuelData> getAllDuels() throws SQLException;

    Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo) throws SQLException;
    Collection<BasicChallengeData> getAllChallenges() throws SQLException;

    boolean tryLogIn(Credentials credentials);
}
