package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicPlayerData;

import java.util.Collection;
import java.util.List;

public class SimpleViewModel implements ViewModel{
    @Override
    public Collection<BasicPlayerData> browsePlayers(String nickName) {
        return List.of();
    }

    @Override
    public Collection<BasicPlayerData> browseClans(String name) {
        return List.of();
    }
}
