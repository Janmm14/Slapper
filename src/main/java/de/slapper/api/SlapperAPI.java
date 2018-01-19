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
        return Slapper.getInstance().getSlapperManager().entitys.contains( entity );
    }

    public static List<Entity> getSlappers(){
        return Slapper.getInstance().getSlapperManager().entitys;
    }

    public static String getSlapperNameTag( Entity entity ){
        if(isSlapper( entity )){
            return Slapper.getInstance().getSlapperManager().getNameTag.get( entity ).getTitle();
        }else{
            return "This entity isn't a slapper. ";
        }
    }

    public static void setSlapperNameTag( Entity entity, String title, Location location ){
        if(isSlapper( entity )){
            FloatingText floatingText;
            floatingText = Slapper.getInstance().getSlapperManager().getNameTag.get( entity );
            floatingText.remove();

            floatingText = new FloatingText( title, location );
            floatingText.create();

            SlapperData slapperData = Slapper.getInstance().getSlapperManager().slapperDatas.get( entity.getEntityId() );
            slapperData.setNameTag( title );

            Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), slapperData );

            Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );

            if(entity instanceof EntityHuman){
                Slapper.getInstance().getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName()  + "~" + slapperData.getSlotId());
            }else{
                Slapper.getInstance().getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag() );
            }
            Slapper.getInstance().getConfig().save();

        }
    }

    public static void removeSlapperNameTag( Entity entity ){
        if(isSlapper( entity )){
            FloatingText floatingText = Slapper.getInstance().getSlapperManager().getNameTag.get( entity );
            floatingText.remove();

            SlapperData slapperData = Slapper.getInstance().getSlapperManager().slapperDatas.get( entity.getEntityId() );
            slapperData.setNameTag( "null" );
            slapperData.setShowNameTag( false );

            Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), slapperData );

            Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );

            if(entity instanceof EntityHuman){
                Slapper.getInstance().getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag()  + "~" + slapperData.getItemCalssName()  + "~" + slapperData.getSlotId());
            }else{
                Slapper.getInstance().getConfig().getList().set( slapperData.getId() - 1, slapperData.getId() + "~" +slapperData.getSpawnedBy() + "~" + slapperData.getType() + "~" +  slapperData.getWorld() + "~" + slapperData.getX() + "~" +
                        slapperData.getY() + "~" + slapperData.getZ() + "~" + slapperData.getYaw() + "~" + slapperData.getPitch() + "~" + slapperData.isShowNameTag() + "~" + slapperData.getNameTag() );
            }
            Slapper.getInstance().getConfig().save();
        }
    }

}
