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
    Collection<ClanMessage> getClanMessages(int clanId) throws SQLException;

    Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo) throws SQLException;

    Collection<BasicDuelData> getAllDuels() throws SQLException;

    Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo) throws SQLException;
    Collection<BasicChallengeData> getAllChallenges() throws SQLException;

    Collection<FriendData> getFriends(int playerID) throws SQLException;

    Collection<NicknameData> getNicknames(int playerID) throws SQLException;

    Collection<FriendData> getAllFriendInvites(int playerID) throws SQLException;
    Collection<FriendData> getActiveFriendInvites(int playerID) throws SQLException;

//    only one role displayed
    Collection<ClanMemberData> getCurrentMembers(int clanID) throws SQLException;

//  all changes in roles displayed - multiple records for each player
    Collection<ClanMemberData> getCurrentAndPastMembers(int clanID) throws SQLException;

    boolean tryLogIn(Credentials credentials);
}
