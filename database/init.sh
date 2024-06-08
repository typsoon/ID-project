#!/bin/bash
psql < 'main files'/clear.sql;
psql < 'main files'/create.sql;
psql < 'main files'/functions.sql;
psql < 'main files'/views.sql;
psql < 'main files'/rules.sql;

psql < 'main files'/players.sql;
psql < 'main files'/playerNickname.sql;
psql < 'main files'/friends.sql;
psql < 'main files'/friendsChat.sql;
psql < 'main files'/clans.sql;
psql < 'main files'/clanName.sql;
psql < 'main files'/clanLogo.sql;
psql < 'main files'/playerClan.sql;
psql < 'main files'/playerRole.sql;

psql < 'main files'/duels.sql;
psql < 'main files'/clanWars.sql;
psql < 'main files'/warDuel.sql;

psql < 'main files'/challanges.sql;
psql < 'main files'/playerChallanges.sql
psql < 'main files'/clanMessege.sql;
psql < 'main files'/clanInvites.sql;
psql < 'main files'/friendInvites.sql;




