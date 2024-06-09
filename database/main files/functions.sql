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
    INSERT INTO ArchivedDuels (duel_ID, sender, receiver, date_from, date_to, outcome)
    SELECT duel_ID, sender, receiver, date_from, date_to, outcome
    FROM Duels
    WHERE date_to < CURRENT_DATE - days_old * INTERVAL '1 day';

    DELETE FROM Duels
    WHERE duel_ID IN (SELECT duel_ID FROM ArchivedDuels);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION archive_Waruels(days_old INTEGER)
RETURNS void AS $$
BEGIN
    INSERT INTO ArchivedWarDuels (duel_ID, clan_war_ID)
    SELECT duel_ID, clan_war_ID
    FROM WarDuels
    WHERE duel_ID IN (
        select duel_ID from ArchivedDuels);

    DELETE FROM WarDuels
    WHERE duel_ID IN (SELECT duel_ID FROM ArchivedWarDuels);
END;
$$ LANGUAGE plpgsql;


create or replace function createTournament (arr int[])
    returns void as
$$
declare
    id int;
begin
    if(array_length(arr,1) < 2 ) then
        raise exception 'need more players';
    end if;
    if(power(2,floor(log(2,array_length(arr,1)))) <> array_length(arr,1) ) then
        raise exception 'number must be power of two';
    end if;
    id = nextval('tournamentID');
    for i in 1..array_length(arr,1) by 2 loop
            insert into duels(sender,receiver) values(arr[i],arr[i+1]);
            insert into Tournaments (tournament_id,duel_id)
            values (id,(select max(duel_id)from duels));
        end loop;
end
$$language plpgsql;

create or replace function winner(matchup int)
    returns int as
$$
begin
    return (
        select (
                   case when outcome=true then
                            sender
                        else
                            receiver
                       end
                   ) from tournaments t
                              join duels d on d.duel_id = t.duel_id
        where t.matchup_id = matchup

    );
end
$$language plpgsql;

create or replace function nextRound (tID int)
    returns void as
$$
declare
    r record;
    i int;
    prev int;
begin
    for r in (select * from tournaments t
                                join duels d on t.duel_id=d.duel_id
              where t.tournament_id = tID) loop
            if(r.outcome is null) then
                raise exception 'previouse round not ended';
            end if;
        end loop;
    i=0;
    for r in (select * from tournaments t where t.tournament_id = tID
                                            AND not exists (
            select * from tournaments tn where tn.left_child = t.matchup_id
                                            OR tn.right_child = t.matchup_id
        ) ) loop
            i=i+1;
            if(i%2=1) then
                prev = r.matchup_id;
            else
                insert into duels(sender,receiver) values(winner(prev),winner(r.matchup_id));
                insert into Tournaments (tournament_id,duel_id,left_child,right_child)
                values (tID,(select max(duel_id)from duels),prev,r.matchup_id);
            end if;
        end loop;
end
$$language plpgsql;

create or replace function IWon(duelID int,playerID int)
    returns int as
$$
declare
    r record;
begin
    for r in (select * from duels where duel_id = duelID) loop
            if playerID <> r.sender AND playerID <> r.receiver then
                raise exception 'not your duel';
            end if;
            if (playerID = r.sender AND r.outcome = true)
                OR (playerID = r.receiver AND r.outcome = false) then
                return 1;
            end if;
        end loop;
    return -1;
end
$$language plpgsql;

create or replace function DuelPoints(duelID int)
    returns int as
$$
declare
    p int;
    r record;
begin
    p = (select points from duelpoints where duel_id = duelID);
    if p is not null then
        return p;
    end if;
    p=0;
    for r in (select * from duels where duel_id = duelID) loop
            p = 0.1 * abs(GetPoints(r.sender,r.date_from) - GetPoints(r.receiver,r.date_from));
        end loop;
    p = p + 5;

    insert into DuelPoints (duel_id,points) values (duelID,p);
    return p;
end
$$language plpgsql;



create or replace function GetPoints(playerID int,t timestamp)
    returns int as
$$
begin
    return coalesce((
                        select sum(DuelPoints(duel_id) * IWon(duel_id,playerID)) from duels
                        where (sender = playerID or receiver = playerID)
                          AND date_to <= t
                    ),0) + (select ranking_base from players where player_id=playerID);
end
$$language plpgsql;

create or replace function GetMembers(clanID int,t timestamp)
    returns table(
                     player_ID     int,
                     password_hash NUMERIC(13),
                     login         VARCHAR(40),
                     ranking_base  integer
                 ) as
$$
begin
    return query(
        select p.* from playerclan pc
                            join players p on pc.player_id = p.player_id
        where (t between pc.date_from and coalesce(pc.date_to,current_timestamp))
          and pc.clan_id = clanID
    );
end
$$language plpgsql;

create or replace function GetClanPoints(clanID int,t timestamp)
    returns int as
$$
begin
    return(
        select sum(getpoints(player_id,t)) from getmembers(clanID,t)
    );
end
$$language plpgsql;


CREATE OR REPLACE FUNCTION get_level(matchupID INTEGER)
    RETURNS INTEGER AS $$
BEGIN
    IF matchupID IS NULL THEN
        RETURN 0;
    END IF;

    return 1 + get_level(
            (select left_child from tournaments where matchup_id = matchupID)
               );
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION AcceptMember(acceptor INTEGER,applier integer)
    RETURNS void AS $$
BEGIN
    IF PlayerClanID(acceptor) IS NULL THEN
        raise exception 'wrong acceptor';
    END IF;
    IF GetCurrentRole(acceptor) <> 'Leader' AND GetCurrentRole(acceptor) <> 'Elder'  IS NULL THEN
        raise exception 'wrong acceptor';
    END IF;
    if (select * from Applications
        where clan_id = PlayerClanID(acceptor) and player_id = applier) is null then
        raise exception 'wrong applier';
    END IF;
    delete from Applications where clan_id = PlayerClanID(acceptor) and player_id = applier;
    insert into playerclan (clan_ID,player_ID,who_accepted) values (PlayerClanID(acceptor),applier,acceptor);
END;
$$ LANGUAGE plpgsql;