package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

import java.util.Collection;

public interface ViewModel {
//    Returns data to be displayed in searchView after nickName is searched
    Collection<BasicPlayerData> browsePlayers(String nickName);
    FullPlayerData getFullPlayerData(int playerId);
    Collection<BasicPlayerData> getPlayers();

    Collection<BasicClanData> browseClans(String name);
    FullClanData getFullClanData(int clanId);

    boolean tryLogIn(Credentials credentials);
}
