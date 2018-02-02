package de.slapper.event;

import de.slapper.manager.EntityTypes;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.event.player.PlayerEvent;
import lombok.Getter;

@Getter
public class PlayerSpawnSlapperEntity extends CancellablePlayerEvent {

    private Entity entity;
    private EntityTypes types;

    public PlayerSpawnSlapperEntity( EntityPlayer player, Entity entity, EntityTypes types ) {
        super( player );
        this.entity = entity;
        this.types = types;
    }

}
