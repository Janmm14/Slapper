package de.slapper.event;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import lombok.Getter;

@Getter
public class PlayerRemoveSlapperEvent extends CancellablePlayerEvent {

    private Entity entity;

    /**
     * Create a new slapper remove event
     *
     * @param player which removes the entity
     * @param entity which is removed
     */

    public PlayerRemoveSlapperEvent( EntityPlayer player, Entity entity ) {
        super( player );
        this.entity = entity;
    }
}
