Some constraints are listed in Opis.pdf

login is unique

ranking_base not null (0 if not specified)

Friends
	player1_ID < player2_ID

Friend invites
	constraints described in Opis.pdf

Friends chat
	Only friends can exchange private messages
	
Player clan
	A player can have only one clan, who_kicked must point to a player who is a member of the clan and has appropriate role (member, elder - kick and add, leader - superuser)
	
	Whenever a player joins a clan a record should be inserted here

Clans
	Under no circumstances should we update data about a clan with no leader (such clan does no longer exist)
	
	Leader can't leave a clan unless he is the only player in it.
	
	A clan has only one leader.
	
	A player who is the first one to join a clan must immediately become a leader
	
	Ensure the integrity of data in PlayerRole (first remove redundancy)
	
	No records can be created for someone who doesnt have a clan	

All applications are invalidated upon joining a clan.

It is already listed in Opis.pdf but all clans have a logo and a name.

ClanWars
	Clan can participate in only one Clan war at a time
	
	clan1_ID != clan2_ID 
	
WarDuels
	Players fighting in a duel must be members of respective clans (different clans!!!)
	
	
ClanChat
	Only members of a clan can post here
	
Duels 
	player1 != player2