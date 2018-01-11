package de.slapper.events;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerEvent;

public class PlayerSpawnSlapperEntity extends PlayerEvent {

    private Entity entity;

    public PlayerSpawnSlapperEntity( EntityPlayer player, Entity entity ) {
        super( player );

        this.entity = entity;

    }

    public Entity getEntity() {
        return entity;
    }
}
