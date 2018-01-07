package de.slapper.listeners;

import de.slapper.Slapper;
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

        if(Slapper.getSlapperManager().removeEntity.contains( player )){
            SlapperData slapperData = Slapper.getSlapperManager().slapperDatas.get( entity.getEntityId() );
            Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );

            if(entity instanceof EntityHuman){
                if(Slapper.getConfig().getList().contains( slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                        + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName() + "~" + slapperData.getSlotId() )){

                    e.setCancelled( true );
                    Slapper.getConfig().getList().remove(  slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                            + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName() + "~" + slapperData.getSlotId() );
                    Slapper.getConfig().save();
                    entity.despawn();
                    player.sendMessage( Slapper.prefix + "§7Das §eEntity §7wurde erfolgreich entfernt" );
                }
            }else{
                if(Slapper.getConfig().getList().contains( slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                        + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()) ){
                    e.setCancelled( true );
                    Slapper.getConfig().getList().remove(  slapperData.getId() + "~" + slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" + slapperData.getWorld() + "~" + slapperData.getX() + "~" + slapperData.getY() + "~" + slapperData.getZ()
                            + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag());
                    Slapper.getConfig().save();
                    entity.despawn();
                    player.sendMessage( Slapper.prefix + "§7Das §eEntity §7wurde erfolgreich entfernt" );
                }
            }
        }
    }

}
