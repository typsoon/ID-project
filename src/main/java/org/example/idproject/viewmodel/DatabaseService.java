package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface DatabaseService {
//    Returns data to be displayed in searchView after nickName is searched
    Collection<BasicPlayerData> browsePlayers(String nickName) throws SQLException;
    FullPlayerData getFullPlayerData(int playerId) throws SQLException;
    Collection<BasicPlayerData> getAllPlayers() throws SQLException;

    boolean insertPlayer(String login, String password, String nickName) throws SQLException;
    boolean insertClan(int leaderID, Date dateFrom, String clanName, String logoFilePath) throws SQLException;
    boolean insertDuel(int player1ID, int player2ID, Date dateFrom, Date dateTo) throws SQLException;

    Collection<BasicClanData> browseClans(String name) throws SQLException;
    FullClanData getFullClanData(int clanId) throws SQLException;
    Collection<BasicClanData> getAllClans() throws SQLException;
    Collection<ClanMessage> getClanMessages(int clanId) throws SQLException;
    Collection<ClanNameData> getClanNames(int clanID) throws SQLException;
    void passLeader(int clanID, int newLeaderID) throws SQLException;
    void removeMember(int clanID, int memberID, Integer whoKicked) throws SQLException;

    boolean sendClanMessage(int playerID, String message) throws SQLException;

    Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo) throws SQLException;
    Collection<BasicDuelData> getAllDuels() throws SQLException;
    Collection<BasicDuelData> getSingleDuelData(int duelID) throws SQLException;

    Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo) throws SQLException;
    Collection<BasicChallengeData> getAllChallenges() throws SQLException;

    Collection<FriendData> getCurrentFriends(int playerID) throws SQLException;
    Collection<FriendData> getAllFriends(int playerID) throws SQLException;
    void sendFriendMessage(int senderID, int receiverID, String message) throws SQLException;

    Collection<NicknameData> getNicknames(int playerID) throws SQLException;

    Collection<FriendInvite> getAllFriendInvites(int playerID) throws SQLException;
    Collection<FriendInvite> getActiveFriendInvites(int playerID) throws SQLException;

    void endDuel(int duelID, boolean firstWon) throws SQLException;

    void applyToClan(int applierID, int clanID) throws SQLException;
    void acceptMember(int whoAccepts, int acceptedID) throws SQLException;

//    only one role displayed
    Collection<ClanMemberData> getCurrentMembers(int clanID) throws SQLException;

//  all changes in roles displayed - multiple records for each player
    Collection<ClanMemberData> getCurrentAndPastMembers(int clanID) throws SQLException;

    Collection<ClanApplication> getClanApplications(int clanID) throws SQLException;

    void moveDuelsToArchive() throws SQLException;

    boolean tryLogIn(Credentials credentials);

    Collection<TournamentData> browseTournaments(String tournamentName) throws SQLException;
    Collection<TournamentData> getAllTournaments() throws SQLException;
    Collection<TournamentMatch> getTournamentMatches(int tournamentID) throws SQLException;
}
