package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.common.BasicPlayerData;

import java.util.Collection;
import java.util.List;

public class SimpleViewModel implements ViewModel{
    @Override
    public Collection<BasicPlayerData> browsePlayers(String nickName) {
        return List.of();
    }

    @Override
    public Collection<BasicClanData> browseClans(String name) {
        return List.of();
    }
}
