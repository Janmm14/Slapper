package de.slapper.commands;

import de.slapper.Slapper;
import de.slapper.manager.SlapperData;
import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.validator.BooleanValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.math.Location;
import io.gomint.player.PlayerSkin;

import java.util.ArrayList;
import java.util.Map;

@Name("slapper edit")
@Description("Editiere ein Slapper")
@Overload( {
        @Parameter( name = "nameTag", validator = StringValidator.class, arguments = {".*"}, optional = true ),
        @Parameter( name = "showNameTag", validator = BooleanValidator.class, arguments = {"false", "true"}, optional = true )
} )
public class CommandEditSlapper extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String s, Map<String, Object> map ) {

        if( Slapper.getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.EditSlapper" )){


                if(Slapper.getSlapperManager().editEntity.contains( player )){
                    Entity entity = Slapper.getSlapperManager().getEntity.get( player );
                    SlapperData oldSlapperData = Slapper.getSlapperManager().slapperDatas.get( entity.getEntityId() );

                    String nameTag = (String) map.getOrDefault( "nameTag", oldSlapperData.getNameTag() );
                    Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", oldSlapperData.isShowNameTag() );

                    Location location = new Location( GoMint.instance().getWorld( oldSlapperData.getWorld() ), oldSlapperData.getX(), oldSlapperData.getY(), oldSlapperData.getZ(), oldSlapperData.getYaw(), oldSlapperData.getPitch() );

                    if(entity instanceof EntityHuman){
                        if(Slapper.getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId())){

                            oldSlapperData.setNameTag( nameTag );
                            oldSlapperData.setShowNameTag( showNameTag );


                            Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                            Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
                            Slapper.getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId());
                            Slapper.getConfig().save();

                            PlayerSkin playerSkin = Slapper.getSlapperManager().getSkin.get( entity );

                            entity.despawn();

                            EntityHuman editEntity = (EntityHuman) Slapper.getSlapperManager().getEntity( oldSlapperData.getType() );
                            editEntity.setSkin( playerSkin );
                            editEntity.setNameTag( nameTag );
                            editEntity.setNameTagAlwaysVisible( showNameTag );
                            editEntity.setTicking( false );
                            editEntity.spawn( location );

                            Slapper.getSlapperManager().getSkin.remove( player );
                            Slapper.getSlapperManager().getEntity.remove( player );
                            Slapper.getSlapperManager().editEntity.remove( player );

                            player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                        }
                    }else{
                        if(Slapper.getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() )){

                            oldSlapperData.setNameTag( nameTag );
                            oldSlapperData.setShowNameTag( showNameTag );


                            Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                            Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
                            Slapper.getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() );
                            Slapper.getConfig().save();

                            entity.despawn();

                            Entity editEntity = Slapper.getSlapperManager().getEntity( oldSlapperData.getType() );
                            editEntity.setNameTag( nameTag );
                            editEntity.setNameTagAlwaysVisible( showNameTag );
                            editEntity.setTicking( false );
                            editEntity.spawn( location );

                            Slapper.getSlapperManager().getEntity.remove( player );
                            Slapper.getSlapperManager().editEntity.remove( player );

                            player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                        }
                    }
                }else{
                    player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntityCommandHelp() );
                }

            }else{
                player.sendMessage( Slapper.getLanguage().getNoPermissions() );
            }
        }else{

            if(Slapper.getSlapperManager().editEntity.contains( player )){
                Entity entity = Slapper.getSlapperManager().getEntity.get( player );
                SlapperData oldSlapperData = Slapper.getSlapperManager().slapperDatas.get( entity.getEntityId() );

                String nameTag = (String) map.getOrDefault( "nameTag", oldSlapperData.getNameTag() );
                Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", oldSlapperData.isShowNameTag() );

                Location location = new Location( GoMint.instance().getWorld( oldSlapperData.getWorld() ), oldSlapperData.getX(), oldSlapperData.getY(), oldSlapperData.getZ(), oldSlapperData.getYaw(), oldSlapperData.getPitch() );

                if(entity instanceof EntityHuman){
                    if(Slapper.getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                            oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId())){

                        oldSlapperData.setNameTag( nameTag );
                        oldSlapperData.setShowNameTag( showNameTag );


                        Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                        Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
                        Slapper.getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId());
                        Slapper.getConfig().save();

                        PlayerSkin playerSkin = Slapper.getSlapperManager().getSkin.get( entity );

                        entity.despawn();

                        EntityHuman editEntity = (EntityHuman) Slapper.getSlapperManager().getEntity( oldSlapperData.getType() );
                        editEntity.setSkin( playerSkin );
                        editEntity.setNameTag( nameTag );
                        editEntity.setNameTagAlwaysVisible( showNameTag );
                        editEntity.setTicking( false );
                        editEntity.spawn( location );

                        Slapper.getSlapperManager().getSkin.remove( player );
                        Slapper.getSlapperManager().getEntity.remove( player );
                        Slapper.getSlapperManager().editEntity.remove( player );

                        player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                    }
                }else{
                    if(Slapper.getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                            oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() )){

                        oldSlapperData.setNameTag( nameTag );
                        oldSlapperData.setShowNameTag( showNameTag );


                        Slapper.getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                        Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
                        Slapper.getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() );
                        Slapper.getConfig().save();

                        entity.despawn();

                        Entity editEntity = Slapper.getSlapperManager().getEntity( oldSlapperData.getType() );
                        editEntity.setNameTag( nameTag );
                        editEntity.setNameTagAlwaysVisible( showNameTag );
                        editEntity.setTicking( false );
                        editEntity.spawn( location );

                        Slapper.getSlapperManager().getEntity.remove( player );
                        Slapper.getSlapperManager().editEntity.remove( player );

                        player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                    }
                }
            }else{
                player.sendMessage( Slapper.prefix + Slapper.getLanguage().getEditEntityCommandHelp() );
            }

        }

        return new CommandOutput();
    }
}
