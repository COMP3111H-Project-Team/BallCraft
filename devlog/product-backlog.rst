===============
Product Backlog
===============

Entry menu
    * singleplayer - empty level
    * multiplayer - as server/as client
    * game setting - balancing point
    * list archivement
    * credits

Game play (empty level)
    * self ball
        - belongs to a player
        - skills
            + casted with mana
            + E.g., a water ball
                * water shooting: aoe, pushes enemy，direction
                * water aura: decrease enemy friction for a while
                * fire immunity
                * waterfall: have a n% chance that one cast will
                  instantly clear the cd of all the skills.
    * enemy ball:
        not controled
    * map:
        - XML format, mapreader.
        - background image.
        - static map class, dynamic map components (E.g., destoryable walls) are stored in game state.
    * [classes need interface]
    * client game loop:
        - has state, server, renderer
        - loops every 33 msecs
    * game state:
        - ball, ...data
    * game server:
        - Server.stepOnce :: GameState -> GameMove -> IO GameState
        - receive input
        - input validating
        - game logic
        - archivement counting
        - send new game state
        - loops every 33 msecs
    * game renderer:
        - renders game state.

[ongoing, to be discussed]

Game play (networking)
    * as server:
        - create
    * as client:
        - connect

Archivement system
    * counter at server
    * affects game state

Learning physic engine

Learning graphics engine

