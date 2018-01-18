package de.slapper.api;

import de.slapper.Slapper;
import de.slapper.api.floatingtext.FloatingText;
import de.slapper.manager.SlapperData;
import io.gomint.entity.Entity;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.math.Location;

import java.util.ArrayList;
import java.util.List;

public class SlapperAPI {

    public static boolean isSlapper( Entity entity ){
        return Slapper.getSlapperManager().entitys.contains( entity );
    }

    public static List<Entity> getSlappers(){
        return Slapper.getSlapperManager().entitys;
    }

    public static String getSlapperNameTag( Entity entity ){
        if(isSlapper( entity )){
            return Slapper.getSlapperManager().getNameTag.get( entity ).getTitle();
        }else{
            return "This entity isn't a slapper. ";
        }
    }

    public static void setSlapperNameTag( Entity entity, String title, Location location ){
        if(isSlapper( entity )){
            FloatingText floatingText;
            floatingText = Slapper.getSlapperManager().getNameTag.get( entity );
            floatingText.remove();

            floatingText = new FloatingText( title, location );
            floatingText.create();

            SlapperData slapperData = Slapper.getSlapperManager().slapperDatas.get( entity.getEntityId() );
            slapperData.setNameTag( title );

            Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), slapperData );

            Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );

            if(entity instanceof EntityHuman){
                Slapper.getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName()  + "~" + slapperData.getSlotId());
            }else{
                Slapper.getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag() );
            }
            Slapper.getConfig().save();

        }
    }

    public static void removeSlapperNameTag( Entity entity ){
        if(isSlapper( entity )){
            FloatingText floatingText = Slapper.getSlapperManager().getNameTag.get( entity );
            floatingText.remove();

            SlapperData slapperData = Slapper.getSlapperManager().slapperDatas.get( entity.getEntityId() );
            slapperData.setNameTag( "null" );
            slapperData.setShowNameTag( false );

            Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), slapperData );

            Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );

            if(entity instanceof EntityHuman){
                Slapper.getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName()  + "~" + slapperData.getSlotId());
            }else{
                Slapper.getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag() );
            }
            Slapper.getConfig().save();
        }
    }

}
