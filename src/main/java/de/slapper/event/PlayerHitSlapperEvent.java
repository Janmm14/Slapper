package de.slapper.event;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import lombok.Getter;

@Getter
public class PlayerHitSlapperEvent extends CancellablePlayerEvent {

    private Entity entity;

    /**
     * Create a new slapper hit event
     *
     * @param player which hits the entity
     * @param entity what is hit
     */

    public PlayerHitSlapperEvent( EntityPlayer player, Entity entity ) {
        super( player );
        this.entity = entity;
    }

}
