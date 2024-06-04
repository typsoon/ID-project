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