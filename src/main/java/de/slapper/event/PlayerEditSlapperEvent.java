package de.slapper.event;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.server.entity.EntityType;
import lombok.Getter;

@Getter
public class PlayerEditSlapperEvent extends CancellablePlayerEvent {

    private Entity entity;
    private EntityType types;

    public PlayerEditSlapperEvent( EntityPlayer player, Entity entity, EntityType types ) {
        super( player );
        this.entity = entity;
        this.types = types;
    }
}
