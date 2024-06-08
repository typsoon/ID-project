package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

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

    Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo);
    Collection<BasicDuelData> getAllDuels();

    Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo);
    Collection<BasicChallengeData> getAllChallenges() throws SQLException;

    boolean tryLogIn(Credentials credentials);
}
