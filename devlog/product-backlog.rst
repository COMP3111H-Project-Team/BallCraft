===============
Product Backlog
===============

Entry menu: singleplayer - empty level
            multiplayer - as server/as client
            game setting - balancing point
            list archivement
            credits

Game play (empty level):
    self ball:
        player can move
        skills:
            with mana
            <water ball>:
                water shooting: aoe, 推人，指向，需要手指来划
                water aura: 一段时间降低敌人摩擦
                fire immutability: 无视火球的喷火
                waterfall: 有几率放技能之后立刻冷却
    enemy ball:
        not controled
    map:
        XML format, mapreader.
        background image.
        static map class, dynamic map components (E.g., destoryable walls)
         are stored in game state.
    [classes need interface]
    client game loop:
        has state, server, renderer
        loops every 33 msecs
    game state:
        ball, ...data
    game server:
        Server.stepOnce :: GameState -> GameMove -> IO GameState
        receive input
        input validating
        game logic
        archivement counting
        send new game state
        loops every 33 msecs
    game renderer:
        renders game state.

[ongoing, to be discussed]
Game play (networking):
    as server:
        create
    as client:
        connect

Archivement system:
    counter at server
    affects game state

Learning physic engine.

Learning graphics engine.

