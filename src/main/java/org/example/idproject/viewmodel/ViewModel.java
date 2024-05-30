package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicPlayerData;

import java.util.Collection;

public interface ViewModel {
    Collection<BasicPlayerData> browsePlayers(String nickName);

    Collection<BasicPlayerData> browseClans(String name);
}
