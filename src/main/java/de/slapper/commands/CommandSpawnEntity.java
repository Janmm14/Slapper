package de.slapper.commands;

import de.slapper.Slapper;
import de.slapper.api.floatingtext.FloatingText;
import de.slapper.event.PlayerSpawnSlapperEntity;
import de.slapper.manager.SlapperData;
import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.BooleanValidator;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.math.Location;
import io.gomint.plugin.injection.InjectPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Name( "slapper spawn" )
@Description( "Spawn an entity" )
@Permission( "slapper.spawnentity" )

@Overload( {
        @Parameter( name = "entity", validator = EnumValidator.class, arguments = { "PrimedTNT", "Blaze", "CaveSpider", "Cod", "Creeper", "Drowned", "ElderGuardian", "EnderDragon",
                "Enderman", "Endermite", "Evoker", "Ghast", "Guardian", "Husk", "MagmaCube", "PolarBear", "Pufferfish", "Salmon", "Shulker", "Silverfish", "Skeleton", "Slime", "Spider",
                "Stray", "TropicalFish", "Vex", "Vindicator", "Witch", "WitherSkeleton", "Zombie", "ZombiePigman", "ZombieVillager", "ArmorStand", "Bat", "Chicken", "Cow", "Donkey",
                "FallingBlock", "Horse", "Human", "ItemDrop", "Lama", "Mooshroom", "Mule", "Ocelot", "Parrot", "Pig", "Rabbit", "Sheep", "SkeletonHorse", "Squid", "Turtle", "Villager",
                "Wolf", "XPOrb", "ZombieHorse", "Arrow", "Enderpearl", "ExpBottle", "FishingHook", "Snowball" } ),
        @Parameter( name = "nameTag", validator = StringValidator.class, arguments = { ".*" }, optional = true ),
        @Parameter( name = "ticking", validator = BooleanValidator.class, optional = true )
} )
public class CommandSpawnEntity extends Command {

    @InjectPlugin
    private Slapper plugin;

    @Override
    public CommandOutput execute( CommandSender commandSender, String s, Map<String, Object> map ) {
        CommandOutput output = new CommandOutput();

        if ( commandSender instanceof PlayerCommandSender ) {
            EntityPlayer player = (EntityPlayer) commandSender;

            if ( map.containsKey( "entity" ) ) {
                String entityName = String.valueOf( map.get( "entity" ) );
                try {
                    Class<? extends Entity> clazz = (Class<? extends Entity>) Class.forName( this.plugin.getSlapperManager().getMobClassPath( entityName ) + "Entity" + entityName );
                    Entity entity = GoMint.instance().createEntity( clazz );

                    if ( entity instanceof EntityHuman ) {
                        EntityHuman entityHuman = (EntityHuman) entity;
                        PlayerSpawnSlapperEntity playerSpawnSlapperEntity = this.plugin.getPluginManager().callEvent( new PlayerSpawnSlapperEntity( player, entityHuman ) );
                        if ( !playerSpawnSlapperEntity.isCancelled() ) {
                            if ( map.containsKey( "nameTag" ) ) {
                                String nameTag = (String) map.get( "nameTag" );
                                entityHuman.setNameTag( nameTag );
                                entityHuman.setNameTagAlwaysVisible( true );
                            }
                            entityHuman.setSkin( playerSpawnSlapperEntity.getPlayer().getSkin() );
                            entityHuman.setTicking( map.containsKey( "ticking" ) ? (boolean) map.get( "ticking" ) : true );
                            entityHuman.setSneaking( playerSpawnSlapperEntity.getPlayer().isSneaking() );
                            entityHuman.getArmorInventory().setHelmet( playerSpawnSlapperEntity.getPlayer().getArmorInventory().getHelmet() );
                            entityHuman.getArmorInventory().setChestplate( playerSpawnSlapperEntity.getPlayer().getArmorInventory().getChestplate() );
                            entityHuman.getArmorInventory().setLeggings( playerSpawnSlapperEntity.getPlayer().getArmorInventory().getLeggings() );
                            entityHuman.getArmorInventory().setBoots( playerSpawnSlapperEntity.getPlayer().getArmorInventory().getBoots() );
                            entityHuman.getInventory().setItem( playerSpawnSlapperEntity.getPlayer().getInventory().getItemInHandSlot(), playerSpawnSlapperEntity.getPlayer().getInventory().getItemInHand() );
                            entityHuman.spawn( playerSpawnSlapperEntity.getPlayer().getLocation() );

                            try {
                                File skinFolder = new File( this.plugin.getDataFolder() + "/skins" );
                                if ( !skinFolder.exists() ) skinFolder.mkdirs();
                                playerSpawnSlapperEntity.getPlayer().getSkin().saveSkinTo( new FileOutputStream( new File( this.plugin.getDataFolder() + "/skins", playerSpawnSlapperEntity.getEntity().getNameTag() + ".png" ) ) );
                            } catch ( IOException e ) {
                                e.printStackTrace();
                            }

                            if ( playerSpawnSlapperEntity.getEntity() instanceof EntityHuman ) {
                                EntityHuman eventHuman = (EntityHuman) playerSpawnSlapperEntity.getEntity();

                                SlapperData slapperData = SlapperData.builder()
                                        .entityClass( eventHuman.getClass().getSimpleName() )
                                        .world( eventHuman.getWorld().getWorldName() )
                                        .x( eventHuman.getPositionX() )
                                        .y( eventHuman.getPositionY() )
                                        .z( eventHuman.getPositionZ() )
                                        .yaw( eventHuman.getYaw() )
                                        .headYaw( eventHuman.getHeadYaw() )
                                        .pitch( eventHuman.getPitch() )
                                        .nameTag( eventHuman.getNameTag() )
                                        .showNameTag( eventHuman.isNameTagAlwaysVisible() )
                                        .ticking( eventHuman.isTicking() )
                                        .sneaking( eventHuman.isSneaking() )
                                        .skinName( eventHuman.getNameTag() )
                                        .itemCalssName( eventHuman.getInventory().getItemInHand().getClass().getSimpleName() )
                                        .helemtClassName( eventHuman.getArmorInventory().getHelmet().getClass().getSimpleName() )
                                        .chestplateClassName( eventHuman.getArmorInventory().getChestplate().getClass().getSimpleName() )
                                        .leggingsClassName( eventHuman.getArmorInventory().getLeggings().getClass().getSimpleName() )
                                        .bootsClassName( eventHuman.getArmorInventory().getBoots().getClass().getSimpleName() )
                                        .build();

                                this.plugin.getConfig().getSlapperData().add( slapperData );
                                this.plugin.getConfig().saveFile( this.plugin );
                                this.plugin.getSlapperManager().getSlapperDataMap().put( eventHuman.getEntityId(), slapperData );
                            }
                        }

                    } else {
                        if ( entity != null ) {
                            PlayerSpawnSlapperEntity playerSpawnSlapperEntity = this.plugin.getPluginManager().callEvent( new PlayerSpawnSlapperEntity( player, entity ) );
                            if ( !playerSpawnSlapperEntity.isCancelled() ) {
                                if ( map.containsKey( "nameTag" ) ) {
                                    String nameTag = (String) map.get( "nameTag" );
                                    FloatingText floatingText = new FloatingText();
                                    floatingText.setTitle( nameTag );
                                    floatingText.setLocation( new Location( playerSpawnSlapperEntity.getPlayer().getWorld(), playerSpawnSlapperEntity.getPlayer().getPositionX(), (float) (playerSpawnSlapperEntity.getPlayer().getPositionY() + entity.getEyeHeight() * 1.7), playerSpawnSlapperEntity.getPlayer().getPositionZ(), playerSpawnSlapperEntity.getPlayer().getHeadYaw(), playerSpawnSlapperEntity.getPlayer().getYaw(), playerSpawnSlapperEntity.getPlayer().getPitch() ) );
                                    floatingText.create();
                                    this.plugin.getSlapperManager().getFloatingTextMap().put( playerSpawnSlapperEntity.getEntity().getEntityId(), floatingText );
                                }
                                playerSpawnSlapperEntity.getEntity().setTicking( map.containsKey( "ticking" ) ? (boolean) map.get( "ticking" ) : true );
                                playerSpawnSlapperEntity.getEntity().spawn( playerSpawnSlapperEntity.getPlayer().getLocation() );

                                SlapperData slapperData = SlapperData.builder()
                                        .entityClass( playerSpawnSlapperEntity.getEntity().getClass().getSimpleName() )
                                        .world( playerSpawnSlapperEntity.getEntity().getWorld().getWorldName() )
                                        .x( playerSpawnSlapperEntity.getEntity().getPositionX() )
                                        .y( playerSpawnSlapperEntity.getEntity().getPositionY() )
                                        .z( playerSpawnSlapperEntity.getEntity().getPositionZ() )
                                        .yaw( playerSpawnSlapperEntity.getEntity().getYaw() )
                                        .headYaw( playerSpawnSlapperEntity.getEntity().getHeadYaw() )
                                        .pitch( playerSpawnSlapperEntity.getEntity().getPitch() )
                                        .nameTag( map.get( "nameTag" ) != null ? (String) map.get( "nameTag" ) : "" )
                                        .showNameTag( playerSpawnSlapperEntity.getEntity().isNameTagAlwaysVisible() )
                                        .ticking( playerSpawnSlapperEntity.getEntity().isTicking() )
                                        .build();
                                this.plugin.getConfig().getSlapperData().add( slapperData );
                                this.plugin.getConfig().saveFile( this.plugin );
                                this.plugin.getSlapperManager().getSlapperDataMap().put( playerSpawnSlapperEntity.getEntity().getEntityId(), slapperData );
                            }
                        }
                    }

                } catch ( ClassNotFoundException e ) {
                    e.printStackTrace();
                }
            }else{
                output.fail( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( player.getLocale(), "entityNotFound" ) );
            }
        } else {
            output.fail( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( "needPlayer" ) );
        }
        return output;
    }

}
