package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.common.FullClanData;
import org.example.idproject.common.FullPlayerData;

import java.util.Collection;

public interface ViewModel {
    Collection<BasicPlayerData> browsePlayers(String nickName);
    FullPlayerData getFullPlayerData(int playerId);

    Collection<BasicClanData> browseClans(String name);
    FullClanData getFullClanData(int clanId);
}
