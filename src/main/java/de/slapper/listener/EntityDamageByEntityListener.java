package de.slapper.listener;

import de.slapper.Slapper;
import de.slapper.event.PlayerHitSlapperEvent;
import de.slapper.event.PlayerRemoveSlapperEvent;
import de.slapper.manager.SlapperData;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements EventListener {

    private Slapper plugin;

    public EntityDamageByEntityListener( Slapper plugin ) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHitSlapper( EntityDamageByEntityEvent event ) {
        Entity entity = event.getEntity();

        if ( event.getAttacker() instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) event.getAttacker();
            SlapperData slapperData = this.plugin.getSlapperManager().getSlapperDataMap().get( entity.getEntityId() );

            if ( this.plugin.getSlapperManager().getSlapperDataMap().containsKey( entity.getEntityId() ) ) {
                PlayerHitSlapperEvent playerHitSlapperEvent = this.plugin.getPluginManager().callEvent( new PlayerHitSlapperEvent( player, entity ) );
                if ( playerHitSlapperEvent.isCancelled() ) {
                    event.setCancelled( true );
                }
            }

            if ( this.plugin.getSlapperManager().getRemoveEntity().contains( player.getUUID() ) ) {
                if ( !(entity instanceof EntityPlayer)) {
                    PlayerRemoveSlapperEvent playerRemoveSlapperEvent = this.plugin.getPluginManager().callEvent( new PlayerRemoveSlapperEvent( player, entity ) );
                    if ( !playerRemoveSlapperEvent.isCancelled() ) {
                        if(slapperData != null && this.plugin.getSlapperManager().getSlapperDataMap().containsValue( slapperData )){
                            event.setCancelled( true );
                            this.plugin.getConfig().getSlapperData().remove( slapperData );
                            this.plugin.getConfig().saveFile( this.plugin );
                            if(this.plugin.getSlapperManager().getFloatingTextMap().containsKey( entity.getEntityId() ))
                                this.plugin.getSlapperManager().getFloatingTextMap().get( entity.getEntityId() ).remove();
                            entity.despawn();
                            player.sendMessage( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( player.getLocale(), "removeEntitySuccessful" ) );
                        }
                    }
                }
            }

        }

    }

}
