package org.example.idproject.viewmodel;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.common.BasicPlayerData;

import java.util.Collection;

public interface ViewModel {
    Collection<BasicPlayerData> browsePlayers(String nickName);

    Collection<BasicClanData> browseClans(String name);
}
