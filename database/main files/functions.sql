create or replace function PlayerNickname(PlayerID int)
    returns varchar as
$$
begin
    return (
        select nickname from PlayerNickname
        where player_ID = PlayerID
        order by date_from desc
        limit 1
    );
end
$$language plpgsql;


create or replace function PlayerClanID(PlayerID int)
    returns int as
$$
begin
    return (
        select clan_ID from PlayerClan
        where player_ID = PlayerID AND date_to is null
    );
end
$$language plpgsql;

create or replace function ClanName(ClanID int)
    returns varchar as
$$
begin
    return (
        select cl_name from ClanName
        where clan_ID = ClanID
        order by date_from desc
        limit 1
    );
end
$$language plpgsql;

create or replace function ClanImage(ClanID int)
    returns varchar as
$$
begin
    return (
        select image_address from clanlogos
        join Logos on Logos.logo_ID = clanlogos.logo_ID
        where clan_ID = ClanID
        order by date_from desc
        limit 1
    );
end
$$language plpgsql;

create or replace function SearchPlayers(Nick varchar)
    returns table(
                     playerID int,
                     current_nickname varchar
                 ) as
$$
begin
    return query
        select p.player_ID, PlayerNickname(p.player_ID) from Players p
        where p.player_ID in (
            select player_ID from playerNickname pc
            where lower(pc.nickname) = lower(Nick)
        )
    ;
end
$$language plpgsql;

create or replace function SearchClans(Name varchar)
    returns table(
                     clanID int,
                     current_name varchar
                 ) as
$$
begin
    return query
        select c.clan_ID, ClanName(c.clan_id) from clans c
        where c.clan_id in (
            select clan_id from clanname cn
            where lower(cn.cl_name) = lower(Name)
        )
    ;
end
$$language plpgsql;

create or replace function GetDuels(playerID int, dateFrom timestamp,dateTo timestamp)
    returns table(
                     duel_ID int,
                     fp_ID int,
                     sd_ID int,
                     date_from timestamp,
                     date_to timestamp,
                     outcome bool
                 ) as
$$
begin
    return query
        select * from duels d
        where (sender = playerID OR receiver = playerID)
          AND d.date_from between dateFrom AND dateTo
    ;
end
$$language plpgsql;


create or replace function GetChallenges
(obj varchar, dateFrom timestamp,dateTo timestamp)
    returns table(
                     challenge_id int ,
                     date_from TIMESTAMP ,
                     date_to TIMESTAMP ,
                     objective INTEGER ,
                     description VARCHAR(200)
                 ) as
$$
begin
    return query
        select * from challenges c
        where c.description = obj
          AND c.date_from between dateFrom AND dateTo
    ;
end
$$language plpgsql;

create or replace function GetClanLeader (clanID int)
    returns int as
$$
begin
    return
        (
            select pc.player_ID from playerclan pc
                              join playerrole pr on pc.player_ID = pr.player_ID
                              join roles r on r.rank_ID = pr.rank_ID
            where pc.date_to IS NULL AND pc.clan_ID = clanID AND
                rank_name = 'Leader'
            order by pr.date_from desc
            limit 1
        );
end
$$language plpgsql;

create or replace function GetFriends (playerID int)
    returns table(
                     friendID int,
                     dateFrom timestamp,
                     dateTo timestamp
                 ) as
$$
begin
    return query
        (
            select (case when player1_id = playerID then player2_id else player1_id end),date_from,date_to from friends
            where (player1_id = playerID or player2_id=playerID) AND date_to is null
        );
end
$$language plpgsql;

create or replace function GetAllFriends (playerID int)
    returns table(
                     friendID int,
                     dateFrom timestamp,
                     dateTo timestamp
                 ) as
$$
begin
    return query
        (
            select (case when player1_id = playerID then player2_id else player1_id end),date_from,date_to from friends
            where (player1_id = playerID or player2_id=playerID)
        );
end
$$language plpgsql;

create or replace function GetCurrentRole (playerID int)
    returns varchar as
$$
begin
    return
        (
            select rank_name from playerrole pr
                                      join roles r on r.rank_id = pr.rank_id
            where pr.player_id = playerID
            order by date_from desc
            limit 1
        );
end
$$language plpgsql;

create or replace function GetRole (playerID int, dateTo timestamp)
    returns varchar as
$$
begin
    if(dateTo is null) then
        return GetCurrentRole(playerID);
    end if;
    return
        (
            select rank_name from playerrole pr
                                      join roles r on r.rank_id = pr.rank_id
            where pr.player_id = playerID and pr.date_from < dateTo
            order by date_from desc
            limit 1
        );
end
$$language plpgsql;

create or replace function PassLeader (clanID int, leaderID int)
    returns void as
$$
begin
    insert into playerrole (player_ID,rank_ID) values(GetClanLeader(clanID),2);
    insert into playerrole (player_ID,rank_ID) values(leaderID,1);
end
$$language plpgsql;


CREATE OR REPLACE FUNCTION archive_duels(days_old INTEGER)
RETURNS void AS $$
BEGIN
    INSERT INTO ArchiveDuels (duel_ID, sender, receiver, date_from, date_to, outcome)
    SELECT duel_ID, sender, receiver, date_from, date_to, outcome
    FROM Duels
    WHERE date_to < CURRENT_DATE - days_old * INTERVAL '1 day';
    
    DELETE FROM Duels
    WHERE duel_ID IN (SELECT duel_ID FROM ArchiveDuels);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION archive_Waruels(days_old INTEGER)
RETURNS void AS $$
BEGIN
    INSERT INTO ArchiveWarDuels (duel_ID, clan_war_ID)
    SELECT duel_ID, clan_war_ID
    FROM WarDuels
    WHERE duel_ID IN (
        select duel_ID from ArchivedDuels);
    
    DELETE FROM WarDuels
    WHERE duel_ID IN (SELECT duel_ID FROM ArchiveWarDuels);
END;
$$ LANGUAGE plpgsql;
