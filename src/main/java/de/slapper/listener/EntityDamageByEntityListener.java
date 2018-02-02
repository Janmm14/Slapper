package de.slapper.listener;

import de.slapper.Slapper;
import de.slapper.event.PlayerHitSlapperEvent;
import de.slapper.event.PlayerRemoveSlapperEvent;
import de.slapper.manager.EntityTypes;
import de.slapper.manager.SlapperData;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class EntityDamageByEntityListener implements EventListener {

    @EventHandler
    public void onPlayerHitSlapper( EntityDamageByEntityEvent e ){
        EntityPlayer player = (EntityPlayer) e.getAttacker();
        Entity entity = e.getEntity();
        SlapperData slapperData = Slapper.getInstance().getSlapperManager().slapperDatas.get( entity.getEntityId() );
        EntityTypes types = EntityTypes.valueOf( slapperData.getType().toUpperCase() );

        if(Slapper.getInstance().getSlapperManager().entitys.contains( entity )){
            PlayerHitSlapperEvent playerHitSlapperEvent = new PlayerHitSlapperEvent( player, entity, types );
            if(playerHitSlapperEvent.isCancelled()){
                e.setCancelled(true);
            }
            Slapper.getInstance().getPluginManager().callEvent( playerHitSlapperEvent );
        }

        if(Slapper.getInstance().getSlapperManager().removeEntity.contains( player )){
            Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );

            if(!(entity instanceof EntityPlayer)){
                if(entity instanceof EntityHuman){
                    PlayerRemoveSlapperEvent playerRemoveSlapperEvent = new PlayerRemoveSlapperEvent( player, entity, EntityTypes.valueOf( slapperData.getType().toUpperCase() ) );
                    if(!playerRemoveSlapperEvent.isCancelled()){
                        if(Slapper.getInstance().getConfig().getList().contains( slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                                + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName() + "~" + slapperData.getSlotId() )){

                            e.setCancelled( true );
                            Slapper.getInstance().getConfig().getList().remove(  slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                                    + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName() + "~" + slapperData.getSlotId() );
                            Slapper.getInstance().getConfig().save();
                            entity.despawn();
                            if(Slapper.getInstance().getSlapperManager().getNameTag.get( entity ) != null){
                                Slapper.getInstance().getSlapperManager().getNameTag.get( entity ).remove();
                            }
                            player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntity() );
                        }
                    }
                    Slapper.getInstance().getPluginManager().callEvent( playerRemoveSlapperEvent );
                }else{
                    PlayerRemoveSlapperEvent playerRemoveSlapperEvent = new PlayerRemoveSlapperEvent( player, entity, EntityTypes.valueOf( slapperData.getType().toUpperCase() ) );
                    if(!playerRemoveSlapperEvent.isCancelled()){
                        if(Slapper.getInstance().getConfig().getList().contains( slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                                + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()) ){
                            e.setCancelled( true );
                            Slapper.getInstance().getConfig().getList().remove(  slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                                    + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag());
                            Slapper.getInstance().getConfig().save();
                            entity.despawn();
                            Slapper.getInstance().getSlapperManager().getNameTag.get( entity ).remove();

                            player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntity() );
                        }
                    }
                    Slapper.getInstance().getPluginManager().callEvent( playerRemoveSlapperEvent );
                }
            }
        }
        if(Slapper.getInstance().getSlapperManager().editEntity.contains( player )){
            e.setCancelled( true );
            Slapper.getInstance().getSlapperManager().getEntity.put( player, entity );
            if(entity instanceof EntityHuman){
                Slapper.getInstance().getSlapperManager().getSkin.put( entity, ((EntityHuman)entity).getSkin() );
            }
            player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getSaveEntitySuccessful() );
        }
    }

}
