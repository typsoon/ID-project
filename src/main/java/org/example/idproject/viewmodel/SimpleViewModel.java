package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.common.FullClanData;
import org.example.idproject.common.FullPlayerData;

import java.util.Collection;
import java.util.List;

public class SimpleViewModel implements ViewModel{
    @Override
    public Collection<BasicPlayerData> browsePlayers(String nickName) {
        return List.of();
    }

    @Override
    public FullPlayerData getFullPlayerData(int playerId) {
        return null;
    }

    @Override
    public Collection<BasicClanData> browseClans(String name) {
        return List.of();
    }

    @Override
    public FullClanData getFullClanData(int clanId) {
        return null;
    }
}
