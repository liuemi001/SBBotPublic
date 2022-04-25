# Sci-Bowl-Discord-Bot
Discord bot made using Java Discord API (https://github.com/DV8FromTheWorld/JDA). Pulls science bowl questions from SciBowlDB (https://scibowldb.com/). 
To use, navigate to HelloWorld.java within the SciBowl2022 folder. Look for "INSERT TOKEN HERE". This is where you should insert your actual Discord bot token (be sure not to release it publicly online). 
# How to Use Within Discord
To get started, use the !game command to create a new game. Only you will be able to answer questions in that game.
If you want to focus on certain categories, put the subject abbreviation associated with a specific category after the !game command.
The subject abbreviations are:
Physics: phys
Energy: nrg
Earth and Space: es
Earth Science: earth
Chemistry: chem
Biology: bio
Astronomy: astro
Math: math
For example, if you only wanted Chemistry and Biology questions, you would send "!game bio chem"
After a game has been started, the bot will start sending tossups. Reply with your answer. There is no need to buzz in.
In order to end your current game, send !end, which will end your current game after the current tossup is finished.
Two players cannot play in the same channel. Additionally, a single player cannot play multiple games.
Hope you have fun!
