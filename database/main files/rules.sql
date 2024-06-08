create or replace rule add_player as on insert
    to FullPlayerData
    do instead
    (
        insert into Players (password_hash,login) values (new.password_hash,new.login);
        insert into PlayerNickname (player_ID, nickname) values
        ( (select player_ID from Players where Players.login = new.login),
          new.playernickname
        )
    );


create or replace rule add_clan as on insert
    to FullClanData
    do instead
    (
    insert into clans (ranking_base) values(200);
    insert into playerclan (clan_ID,player_ID,date_from) values((select max(clan_ID) from clans),new.leader,new.time);
    insert into playerRole (player_ID,rank_ID,date_from) values (new.leader,(select rank_ID from roles where rank_name='Leader' limit 1),new.time);
    insert into clanlogos (clan_ID,logo_ID,date_from) values ((select max(clan_ID) from clans),(select logo_ID from logos where image_address = new.clanimage limit 1),new.time);
    insert into clanname (clan_ID,cl_name,date_from) values ((select max(clan_ID) from clans),new.clanname,new.time);
    );
