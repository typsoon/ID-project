package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

import java.time.LocalDate;
import java.util.Collection;

public interface DatabaseService {
//    Returns data to be displayed in searchView after nickName is searched
    Collection<BasicPlayerData> browsePlayers(String nickName);
    FullPlayerData getFullPlayerData(int playerId);
    Collection<BasicPlayerData> getAllPlayers();

    boolean insertPlayer(String login, String password, String nickName);

    Collection<BasicClanData> browseClans(String name);
    FullClanData getFullClanData(int clanId);
    Collection<BasicClanData> getAllClans();

    Collection<BasicDuelData> browseDuels(String tookPart, LocalDate dateFrom, LocalDate dateTo);
    Collection<BasicDuelData> getAllDuels();

    Collection<BasicChallengeData> browseChallenges(String objective, LocalDate dateFrom, LocalDate dateTo);
    Collection<BasicChallengeData> getAllChallenges();

    boolean tryLogIn(Credentials credentials);
}
