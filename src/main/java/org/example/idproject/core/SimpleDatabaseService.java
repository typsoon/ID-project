package org.example.idproject.core;


import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.common.*;
import org.example.idproject.viewmodel.DatabaseService;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

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
                        rs.getLong(1),rs.getString(2),rs.getString(3),
                        rs.getInt(4),
                        new BasicPlayerData(rs.getInt(5),rs.getString(6))
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
            return stmt.execute( "insert into fullplayerdata (password_hash, login, playernickname) values (" + password.hashCode() + ",'" + login + "','" + nickName + "');" );

        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean insertClan(int leaderID, java.util.Date dateFrom, String clanName, String logoFilePath) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            return stmt.execute( "insert into fullclandata (clanimage, clanname, leader,time) values (\'" +
                    logoFilePath + "\',\'" + clanName +"\',\'" + leaderID +  "\',\'" + dateFrom + "\')"
            );

        }
    }

    @Override
    public boolean insertDuel(int player1ID, int player2ID, Date dateFrom, Date dateTo) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();

            return  stmt.execute( "insert into duels (sender, receiver, date_from) values (\'" +
                        player1ID + "\',\'" + player2ID +"\',\'" + dateFrom +  "\',)" );
        }
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
    public Collection<Message> getClanMessages(int clanId) throws SQLException {
        Collection<Message> massages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select clanchat.*,playernickname(sender_id) from clanchat where clan_ID = (\'" + clanId + "\');" );
            while (rs.next()) {
                massages.add( new Message(
                        rs.getString(1),rs.getInt(2),rs.getString(5),rs.getString(4)
                        ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return massages;
    }

    @Override
    public Collection<ClanNameData> getClanNames(int clanID) throws SQLException {
        Collection<ClanNameData> names = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from ClanName where clan_ID = (\'" + clanID + "\');" );
            while (rs.next()) {
                names.add( new ClanNameData(rs.getString(2),rs.getInt(1),rs.getString(3)

                         ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return names;
    }

    @Override
    public void passLeader(int clanID, int newLeaderID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            stmt.execute( "select PassLeader(" + clanID + " , " + newLeaderID + ");" );
        }
    }

    @Override
    public boolean removeMember(int clanID, int memberID, Integer whoKicked) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();

            return stmt.execute( "update playerclan set date_to = current_timestamp, who_kicked = " + whoKicked +" where date_to is null and  player_ID=" + memberID +  " AND clan_ID = " + clanID + ";" );
        }
    }

    @Override
    public boolean sendClanMessage(int playerID, String message) throws SQLException {

        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            return stmt.execute( "insert into ClanChat (sender_ID, msg_text, clan_ID) values (" +
                    playerID + ",\'" + message +"\',PlayerClanID(" + playerID +  "))"
            );

        }
    }

    @Override
    public void createWar(int clan1ID, int clan2ID, Date dateFrom) throws SQLException {

    }

    @Override
    public void setWarOutcome(int clanWarID, boolean outcome) throws SQLException {

    }

    @Override
    public void setDuelWarDuel(int clanWarID, int duelID) throws SQLException {

    }

    @Override
    public void changeName(int clanID, String newName) throws SQLException {

    }

    @Override
    public Map<String, Integer> addressToLogoIDMapping() {
        return Map.of();
    }

    @Override
    public void changeLogo(int clanID, int newLogoID) throws SQLException {

    }

    @Override
    public Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        int tookPartID = Integer.parseInt(tookPart);
        Collection<BasicDuelData> duels = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
       // System.out.println("select * from Getduels(" + tookPartID +",\'" + dateFrom + "\',\'" +  dateTo +"\');");
        ResultSet rs = stmt.executeQuery("select * from Getduels(" + tookPartID +",\'" + dateFrom + "\',\'" +  dateTo +"\');");
        while (rs.next()) {
            duels.add(new BasicDuelData(rs.getInt(1),rs.getInt(2),rs.getInt(3),
            rs.getTimestamp(4).toString(),rs.getTimestamp(5).toString(),rs.getBoolean(6)));
            //System.out.println(rs.getString(1) + " " + rs.getString(2) );
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return duels;
    }

    @Override
    public Collection<BasicDuelData> getAllDuels() throws SQLException {
        Collection<BasicDuelData> duels = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from duels");
        while (rs.next()) {
            duels.add(new BasicDuelData(rs.getInt(1),rs.getInt(2),rs.getInt(3),
                    rs.getTimestamp(4).toString(),rs.getTimestamp(5).toString(),rs.getBoolean(6)));
            //System.out.println(rs.getString(1) + " " + rs.getString(2) );
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return duels;
    }

    @Override
    public Collection<BasicDuelData> getSingleDuelData(int duelID) throws SQLException {
        Collection<BasicDuelData> duels = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from duels where duel_id = " + duelID);
        while (rs.next()) {
            duels.add(new BasicDuelData(rs.getInt(1),rs.getInt(2),rs.getInt(3),
                    rs.getTimestamp(4).toString(),rs.getTimestamp(5).toString(),rs.getBoolean(6)));
        }

        return duels;
    }

    @Override
    public Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        Collection<BasicChallengeData> challenges = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from GetChallenges(\'" + objective +"\',\'" + dateFrom + "\',\'" +  dateTo +"\');");
        while (rs.next()) {
            challenges.add(new BasicChallengeData( rs.getInt(1),rs.getTimestamp(2),
                    rs.getTimestamp(3), 0, rs.getString(4)
            ));
            //System.out.println(rs.getString(1) + " " + rs.getString(2) );
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return challenges;
    }

    @Override
    public Collection<BasicChallengeData> getAllChallenges() throws SQLException {
        Collection<BasicChallengeData> challenges = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from challenges");
        while (rs.next()) {
            challenges.add(new BasicChallengeData( rs.getInt(1),rs.getTimestamp(2),
                    rs.getTimestamp(3), 0, rs.getString(4)
            ));
            //System.out.println(rs.getString(1) + " " + rs.getString(2) );
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return challenges;
    }

    @Override
    public Collection<FriendData> getCurrentFriends(int playerID) throws SQLException {
        Collection<FriendData> friends = new ArrayList<>();
        Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from friends where date_to is not null AND (player1_ID  =\'" + playerID + "\' or player2_ID =\'" + playerID + "\');");
        while (rs.next()) {
            if(rs.getInt(1)==playerID)
                friends.add(new FriendData(rs.getString(3),rs.getString(4),getFullPlayerData(rs.getInt(2)).basicPlayerData()));
            else
                friends.add(new FriendData(rs.getString(3),rs.getString(4),getFullPlayerData(rs.getInt(1)).basicPlayerData()));
            //System.out.println(rs.getString(1) + " " + rs.getString(2) );
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return friends;
    }

    @Override
    public Collection<FriendData> getAllFriends(int playerID) throws SQLException {
        Collection<FriendData> friends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from getallfriends(\'"+ playerID+ "\') ;");
            while (rs.next()) {
                friends.add(new FriendData(
                        rs.getString(2),rs.getString(3),getFullPlayerData(rs.getInt(1)).basicPlayerData()
                        ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return friends;
    }

    @Override
    public void sendFriendMessage(int senderID, int receiverID, String message) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            //return
                    stmt.execute( "insert into FriendsChat (sender_ID, receiver_ID, msg_text) values (\'" +
                    senderID + "\'," + receiverID +",\'" + message +"\')"
            );

        }
    }

    @Override
    public Collection<NicknameData> getNicknames(int playerID) throws SQLException {
        Collection<NicknameData> nicks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from playernickname where player_id =\'" + playerID+ "\';");
            while (rs.next()) {
                nicks.add(new NicknameData(rs.getInt(1),rs.getString(3),rs.getString(2)));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return nicks;
    }

    @Override
    public void changeNickname(int playerID, String newNickName) throws SQLException {

    }

    @Override
    public Collection<FriendInvite> getAllFriendInvites(int playerID) throws SQLException {
        Collection<FriendInvite> invites = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from friendsinvites where player1_ID  =\'"+ playerID+"\' or player2_ID =\'" + playerID+ "\';");
            while (rs.next()) {
                invites.add(new FriendInvite(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4))
                        );
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return invites;
    }

    @Override
    public Collection<FriendInvite> getActiveFriendInvites(int playerID) throws SQLException {
        Collection<FriendInvite> invites = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from friendsinvites where date_to is null AND (player1_ID  =\'"+ playerID+"\' or player2_ID =\'" + playerID+ "\');");
            while (rs.next()) {
                invites.add(new FriendInvite(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4))
                );
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return invites;
    }

    @Override
    public void endDuel(int duelID, boolean firstWon) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            //return
            stmt.execute( "update duels set date_to = current_timestamp , outcome = " + firstWon + " where duel_id= " + duelID
            );

        }
    }

    @Override
    public void applyToClan(int applierID, int clanID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            //return
            stmt.execute( "insert into applications (clan_ID,player_id) values (" +
                    clanID + "," + applierID + ")"
            );

        }
    }

    @Override
    public void acceptMember(int whoAccepts, int acceptedID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            //return
            stmt.execute( "select AcceptMember( "+ whoAccepts + "," + acceptedID +");" );

        }
    }

    @Override
    public Collection<Message> getFriendChatMessages(int playerID, int friendID) throws SQLException {
        return List.of();
    }

    @Override
    public Collection<ClanMemberData> getCurrentMembers(int clanID) throws SQLException {
        Collection<ClanMemberData> memberData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select playerclan.*, PlayerNickname(player_ID),getcurrentrole(player_id),PlayerNickname(who_kicked) from playerclan where date_to is null and clan_id =\'" +clanID+"\';");
            while (rs.next()) {
                memberData.add(new ClanMemberData(
                        rs.getString(1),rs.getInt(3),rs.getString(7),rs.getString(8),
                        rs.getString(4),rs.getInt(5),rs.getString(9)
                ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return memberData;
    }

    @Override
    public Collection<ClanMemberData> getCurrentAndPastMembers(int clanID) throws SQLException {
        Collection<ClanMemberData> memberData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select playerclan.*, PlayerNickname(player_ID),getrole(player_id,date_to),PlayerNickname(who_kicked) from playerclan where clan_id =\'" +clanID+"\';");
            while (rs.next()) {
                memberData.add(new ClanMemberData(
                        rs.getString(1),rs.getInt(3),rs.getString(7),rs.getString(8),
                        rs.getString(4),rs.getInt(5),rs.getString(9)
                ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return memberData;
    }

    @Override
    public Collection<ClanApplication> getClanApplications(int clanID) throws SQLException {
        Collection<ClanApplication> tournaments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from Applications where clan_ID =" + clanID + " ;");
            while (rs.next()) {
                tournaments.add(new ClanApplication(
                        rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4)
                ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return tournaments;
    }

    @Override
    public void moveDuelsToArchive() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            stmt.execute( "select archive_duels(90);" );
        }
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

    @Override
    public void createChallenge(BasicChallengeData challenge) throws SQLException {

    }

    @Override
    public Collection<TournamentData> browseTournaments(String tournamentName) throws SQLException {
       return List.of();
    }

    @Override
    public Collection<TournamentData> getAllTournaments() throws SQLException {
        Collection<TournamentData> tournaments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select distinct tournament_id from tournaments ;");
            while (rs.next()) {
                tournaments.add(new TournamentData(
                        rs.getInt(1),""
                ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return tournaments;
    }

    @Override
    public Collection<TournamentMatch> getTournamentMatches(int tournamentID) throws SQLException {
        Collection<TournamentMatch> matches = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, credentials.username(), credentials.password())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from tournaments where tournament_id  =\'"+ tournamentID + "\';");
            while (rs.next()) {
                matches.add(new TournamentMatch(
                        rs.getInt(1),"",rs.getInt(3), rs.getInt(4),rs.getInt(5)
                ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) );
            }
        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return matches;
    }

    @Override
    public void createTournament(String tournamentName, Collection<Integer> players) throws SQLException {

    }

    public static void main(String[] args) {
        SimpleDatabaseService simpleViewModel = new SimpleDatabaseService();
        simpleViewModel.tryLogIn(new Credentials("riper", "aaa"));
        try
        {
            //  Collection<FriendData> memberData = simpleViewModel.getAllFriends(8);
           FullPlayerData fullPlayerData = simpleViewModel.getFullPlayerData(1);
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date = format.parse("2024-12-12 00:00:00");
           simpleViewModel.passLeader(1,1376);
           return;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
