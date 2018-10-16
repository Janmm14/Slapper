package de.slapper.event;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.server.entity.EntityType;
import lombok.Getter;

@Getter
public class PlayerSpawnSlapperEntity extends CancellablePlayerEvent {

    private Entity entity;

    /**
     * Create a new spawn slapper event
     *
     * @param player which spawns the entity
     * @param entity what is spawned
     */

    public PlayerSpawnSlapperEntity( EntityPlayer player, Entity entity ) {
        super( player );
        this.entity = entity;
    }

}
