package de.slapper.commands;

import de.slapper.Slapper;
import de.slapper.event.PlayerEditSlapperEvent;
import de.slapper.manager.EntityTypes;
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

        if( Slapper.getInstance().getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.EditSlapper" )){


                if(Slapper.getInstance().getSlapperManager().editEntity.contains( player )){
                    Entity entity = Slapper.getInstance().getSlapperManager().getEntity.get( player );
                    SlapperData oldSlapperData = Slapper.getInstance().getSlapperManager().slapperDatas.get( entity.getEntityId() );

                    String nameTag = (String) map.getOrDefault( "nameTag", oldSlapperData.getNameTag() );
                    Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", oldSlapperData.isShowNameTag() );

                    Location location = new Location( GoMint.instance().getWorld( oldSlapperData.getWorld() ), oldSlapperData.getX(), oldSlapperData.getY(), oldSlapperData.getZ(), oldSlapperData.getYaw(), oldSlapperData.getPitch() );

                    if(entity instanceof EntityHuman){
                        PlayerEditSlapperEvent editSlapperEvent = new PlayerEditSlapperEvent( player, entity, EntityTypes.valueOf( oldSlapperData.getType() ) );
                        Slapper.getInstance().getPluginManager().callEvent( editSlapperEvent );
                        if(!editSlapperEvent.isCancelled()){
                            if(Slapper.getInstance().getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId())){

                                oldSlapperData.setNameTag( nameTag );
                                oldSlapperData.setShowNameTag( showNameTag );


                                Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                                Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );
                                Slapper.getInstance().getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                        oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId());
                                Slapper.getInstance().getConfig().save();

                                PlayerSkin playerSkin = Slapper.getInstance().getSlapperManager().getSkin.get( entity );

                                entity.despawn();

                                EntityHuman editEntity = (EntityHuman) Slapper.getInstance().getSlapperManager().getEntity( oldSlapperData.getType() );
                                editEntity.setSkin( playerSkin );
                                editEntity.setNameTag( nameTag );
                                editEntity.setNameTagAlwaysVisible( showNameTag );
                                editEntity.setTicking( false );
                                editEntity.spawn( location );

                                Slapper.getInstance().getSlapperManager().getSkin.remove( player );
                                Slapper.getInstance().getSlapperManager().getEntity.remove( player );
                                Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );

                            }
                        }

                    }else{
                        PlayerEditSlapperEvent editSlapperEvent = new PlayerEditSlapperEvent( player, entity, EntityTypes.valueOf( oldSlapperData.getType() ) );
                        Slapper.getInstance().getPluginManager().callEvent( editSlapperEvent );
                        if(!editSlapperEvent.isCancelled()){
                            if(Slapper.getInstance().getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() )){

                                oldSlapperData.setNameTag( nameTag );
                                oldSlapperData.setShowNameTag( showNameTag );


                                Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                                Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );
                                Slapper.getInstance().getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                        oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() );
                                Slapper.getInstance().getConfig().save();

                                entity.despawn();

                                Entity editEntity = Slapper.getInstance().getSlapperManager().getEntity( oldSlapperData.getType() );
                                editEntity.setNameTag( nameTag );
                                editEntity.setNameTagAlwaysVisible( showNameTag );
                                editEntity.setTicking( false );
                                editEntity.spawn( location );

                                Slapper.getInstance().getSlapperManager().getEntity.remove( player );
                                Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                            }

                        }
                    }
                }else{
                    player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntityCommandHelp() );
                }

            }else{
                player.sendMessage( Slapper.getInstance().getLanguage().getNoPermissions() );
            }
        }else{

            if(Slapper.getInstance().getSlapperManager().editEntity.contains( player )){
                Entity entity = Slapper.getInstance().getSlapperManager().getEntity.get( player );
                SlapperData oldSlapperData = Slapper.getInstance().getSlapperManager().slapperDatas.get( entity.getEntityId() );

                String nameTag = (String) map.getOrDefault( "nameTag", oldSlapperData.getNameTag() );
                Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", oldSlapperData.isShowNameTag() );

                Location location = new Location( GoMint.instance().getWorld( oldSlapperData.getWorld() ), oldSlapperData.getX(), oldSlapperData.getY(), oldSlapperData.getZ(), oldSlapperData.getYaw(), oldSlapperData.getPitch() );

                if(entity instanceof EntityHuman){
                    PlayerEditSlapperEvent editSlapperEvent = new PlayerEditSlapperEvent( player, entity, EntityTypes.valueOf( oldSlapperData.getType() ) );
                    Slapper.getInstance().getPluginManager().callEvent( editSlapperEvent );
                    if(!editSlapperEvent.isCancelled()){
                        if(Slapper.getInstance().getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId())){

                            oldSlapperData.setNameTag( nameTag );
                            oldSlapperData.setShowNameTag( showNameTag );


                            Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                            Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );
                            Slapper.getInstance().getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag()  + "~" + oldSlapperData.getItemCalssName()  + "~" + oldSlapperData.getSlotId());
                            Slapper.getInstance().getConfig().save();

                            PlayerSkin playerSkin = Slapper.getInstance().getSlapperManager().getSkin.get( entity );

                            entity.despawn();

                            EntityHuman editEntity = (EntityHuman) Slapper.getInstance().getSlapperManager().getEntity( oldSlapperData.getType() );
                            editEntity.setSkin( playerSkin );
                            editEntity.setNameTag( nameTag );
                            editEntity.setNameTagAlwaysVisible( showNameTag );
                            editEntity.setTicking( false );
                            editEntity.spawn( location );

                            Slapper.getInstance().getSlapperManager().getSkin.remove( player );
                            Slapper.getInstance().getSlapperManager().getEntity.remove( player );
                            Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                            player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );

                        }
                    }

                }else{
                    PlayerEditSlapperEvent editSlapperEvent = new PlayerEditSlapperEvent( player, entity, EntityTypes.valueOf( oldSlapperData.getType() ) );
                    Slapper.getInstance().getPluginManager().callEvent( editSlapperEvent );
                    if(!editSlapperEvent.isCancelled()){
                        if(Slapper.getInstance().getConfig().getList().contains( oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() )){

                            oldSlapperData.setNameTag( nameTag );
                            oldSlapperData.setShowNameTag( showNameTag );


                            Slapper.getInstance().getSlapperManager().slapperDatas.put( entity.getEntityId(), oldSlapperData );

                            Slapper.getInstance().getConfig().list = new ArrayList<>( Slapper.getInstance().getConfig().getList() );
                            Slapper.getInstance().getConfig().getList().set( oldSlapperData.getId() - 1, oldSlapperData.getId() + "~" +oldSlapperData.getSpawnedBy() + "~" + oldSlapperData.getType() + "~" +  oldSlapperData.getWorld() + "~" + oldSlapperData.getX() + "~" +
                                    oldSlapperData.getY() + "~" + oldSlapperData.getZ() + "~" + oldSlapperData.getYaw() + "~" + oldSlapperData.getPitch() + "~" + oldSlapperData.isShowNameTag() + "~" + oldSlapperData.getNameTag() );
                            Slapper.getInstance().getConfig().save();

                            entity.despawn();

                            Entity editEntity = Slapper.getInstance().getSlapperManager().getEntity( oldSlapperData.getType() );
                            editEntity.setNameTag( nameTag );
                            editEntity.setNameTagAlwaysVisible( showNameTag );
                            editEntity.setTicking( false );
                            editEntity.spawn( location );

                            Slapper.getInstance().getSlapperManager().getEntity.remove( player );
                            Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                            player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntitySuccessful().replace( "[type]", oldSlapperData.getType() ) );
                        }

                    }
                }
            }else{
                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getEditEntityCommandHelp() );
            }

        }

        return new CommandOutput();
    }
}
