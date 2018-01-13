# Slapper
Slapper ist ein Plugin womit du verschiedene Entitys spawnen kannst und denen funktionen zuweisen kannst die dann ausgeführt werden sobald 
sie geschlagen werden.

# Commands
- /slapper [type] [nameTag] [showNameTag]
- /slapper remove
- /slapper getentity
- /slapper edit [nameTag] [showNameTag]

Beispiel:
- /slapper Human oder /slapper Human TTT true

# Q&A

Q: Wie kann ich ein Entity spawnen ?

A: Das ist ganz einfach du gibst im Chat z.B "/slapper human GunGame true" ein der "human" ist der type des Entity und "GunGame" ist der NameTag und das "true" ist dafür da das der NameTag immer angezigt wird du kannst es natürlich auch auf false stellen.

Q: Wie kann ich ein Entity entfernen ?

A: Du gibst im Chat "/slapper remove" ein und schlägst das Entity was du entfernen willst.

Q: Wie kann ich ein Entity editieren ?

A: Als erstes musst du im Chat "/slapper getentity" eingeben und dann das Entity schlagen um es zu speichern. Als nächstes gibst du im Chat folgendes ein "/slapper [nameTag] [showNameTag]" ein, nun ist dein Entity editiert.

Q: Wie kann ich die neue FloatingText API nutzen?

A: 
FloatingText floatingText = new FloatingText( Location, Title );
floatingText.create();

oder 

floatingText.remove();

# Permissions
- Slapper.SpawnEntity
- Slapper.RemoveEntity
- Slapper.GetEntity
- Slapper.EditSlapper

# TODO
- Command / Funktion speichern wenn man ein Entity schlägt

# Download
https://github.com/LucGamesYT/Slapper/releases
