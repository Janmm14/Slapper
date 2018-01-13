package de.slapper.events;

import de.slapper.manager.EntityTypes;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerEvent;
import lombok.Getter;

@Getter
public class PlayerRemoveSlapperEvent extends PlayerEvent {

    private Entity entity;
    private EntityTypes types;

    public PlayerRemoveSlapperEvent( EntityPlayer player, Entity entity, EntityTypes types ) {
        super( player );
        this.entity = entity;
        this.types = types;
    }
}
