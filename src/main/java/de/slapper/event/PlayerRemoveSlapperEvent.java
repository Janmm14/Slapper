package de.slapper.event;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.server.entity.EntityType;
import lombok.Getter;

@Getter
public class PlayerRemoveSlapperEvent extends CancellablePlayerEvent {

    private Entity entity;

    public PlayerRemoveSlapperEvent( EntityPlayer player, Entity entity ) {
        super( player );
        this.entity = entity;
    }
}
