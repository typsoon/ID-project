import os

# List of files in the required order with '../' added before 'main files'
my_input_files = [
    # "../main files/clear.sql",
    "../main files/create.sql",
    "../main files/functions.sql",
    "../main files/views.sql",
    "../main files/rules.sql",
    "../main files/players.sql",
    "../main files/playerNickname.sql",
    "../main files/friends.sql",
    "../main files/friendsChat.sql",
    "../main files/clans.sql",
    "../main files/clanName.sql",
    "../main files/clanLogo.sql",
    "../main files/playerClan.sql",
    "../main files/playerRole.sql",
    "../main files/duels.sql",
    "../main files/clanWars.sql",
    "../main files/warDuel.sql",
    "../main files/challanges.sql",
    "../main files/playerChallanges.sql",
    "../main files/clanMessege.sql",
    "../main files/clanInvites.sql",
    "../main files/friendInvites.sql",
    "../main files/triggersToTest.sql"
]

my_output_file = '../final/create.sql'


def concatenate_files_with_titles(input_files, output_file):
    with open(output_file, 'w') as outfile:
        for input_file in input_files:
            # Write the title (filename) to the output file
            title = os.path.basename(input_file)
            outfile.write(f"-- {title}\n")
            outfile.write("--" + "=" * len(title) + "\n\n")

            # Write the content of the input file to the output file
            with open(input_file, 'r') as infile:
                outfile.write(infile.read())

            # Add a separator between files (optional)
            outfile.write("\n\n")


concatenate_files_with_titles(my_input_files, my_output_file)

print(f"Files have been concatenated into {my_output_file}")
