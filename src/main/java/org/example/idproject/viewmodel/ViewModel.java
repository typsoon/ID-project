package org.example.idproject.viewmodel;

import org.example.idproject.common.*;

import java.util.Collection;

public interface ViewModel {
    Collection<BasicPlayerData> browsePlayers(String nickName);
    FullPlayerData getFullPlayerData(int playerId);

    Collection<BasicClanData> browseClans(String name);
    FullClanData getFullClanData(int clanId);

    boolean tryLogIn(Credentials credentials);
}
