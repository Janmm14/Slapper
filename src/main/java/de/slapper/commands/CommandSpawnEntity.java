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
        @Parameter( name = "ticking", validator = BooleanValidator.class )
} )
public class CommandSpawnEntity extends Command {

    @InjectPlugin
    private Slapper plugin;

    @Override
    public CommandOutput execute( CommandSender commandSender, String s, Map<String, Object> map ) {
        CommandOutput output = new CommandOutput();

        if ( commandSender instanceof PlayerCommandSender ) {
            EntityPlayer player = (EntityPlayer) commandSender;
            String entityName = String.valueOf( map.get( "entity" ) );
            try {
                Class<? extends Entity> clazz = (Class<? extends Entity>) Class.forName( Slapper.getInstance().getSlapperManager().getMobClassPath( entityName ) + "Entity" + entityName );
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
                        entityHuman.setSkin( player.getSkin() );
                        entityHuman.setTicking( map.containsKey( "ticking" ) ? (boolean) map.get( "ticking" ) : true );
                        entityHuman.setSneaking( player.isSneaking() );
                        entityHuman.getArmorInventory().setHelmet( player.getArmorInventory().getHelmet() );
                        entityHuman.getArmorInventory().setChestplate( player.getArmorInventory().getChestplate() );
                        entityHuman.getArmorInventory().setLeggings( player.getArmorInventory().getLeggings() );
                        entityHuman.getArmorInventory().setBoots( player.getArmorInventory().getBoots() );
                        entityHuman.getInventory().setItem( player.getInventory().getItemInHandSlot(), player.getInventory().getItemInHand() );
                        entityHuman.spawn( player.getLocation() );

                        try {
                            File skinFolder = new File( Slapper.getInstance().getDataFolder() + "/skins" );
                            if ( !skinFolder.exists() ) skinFolder.mkdirs();
                            player.getSkin().saveSkinTo( new FileOutputStream( new File( Slapper.getInstance().getDataFolder() + "/skins", entityHuman.getNameTag() + ".png" ) ) );
                        } catch ( IOException e ) {
                            e.printStackTrace();
                        }

                        SlapperData slapperData = SlapperData.builder()
                                .entityClass( entityHuman.getClass().getSimpleName() )
                                .world( entityHuman.getWorld().getWorldName() )
                                .x( entityHuman.getPositionX() )
                                .y( entityHuman.getPositionY() )
                                .z( entityHuman.getPositionZ() )
                                .yaw( entityHuman.getYaw() )
                                .headYaw( entityHuman.getHeadYaw() )
                                .pitch( entityHuman.getPitch() )
                                .nameTag( entityHuman.getNameTag() )
                                .showNameTag( entityHuman.isNameTagAlwaysVisible() )
                                .ticking( entityHuman.isTicking() )
                                .sneaking( entityHuman.isSneaking() )
                                .skinName( entityHuman.getNameTag() )
                                .itemCalssName( entityHuman.getInventory().getItemInHand().getClass().getSimpleName() )
                                .helemtClassName( entityHuman.getArmorInventory().getHelmet().getClass().getSimpleName() )
                                .chestplateClassName( entityHuman.getArmorInventory().getChestplate().getClass().getSimpleName() )
                                .leggingsClassName( entityHuman.getArmorInventory().getLeggings().getClass().getSimpleName() )
                                .bootsClassName( entityHuman.getArmorInventory().getBoots().getClass().getSimpleName() )
                                .build();

                        this.plugin.getConfig().getSlapperData().add( slapperData );
                        this.plugin.getConfig().saveFile( Slapper.getInstance() );
                        this.plugin.getSlapperManager().getSlapperDataMap().put( entityHuman.getEntityId(), slapperData );
                    }

                } else {
                    PlayerSpawnSlapperEntity playerSpawnSlapperEntity = this.plugin.getPluginManager().callEvent( new PlayerSpawnSlapperEntity( player, entity ) );
                    if ( !playerSpawnSlapperEntity.isCancelled() ) {
                        if ( map.containsKey( "nameTag" ) ) {
                            String nameTag = (String) map.get( "nameTag" );
                            FloatingText floatingText = new FloatingText();
                            floatingText.setTitle( nameTag );
                            floatingText.setLocation( new Location( player.getWorld(), player.getPositionX(), player.getPositionY(), player.getPositionZ(), player.getHeadYaw(), player.getYaw(), player.getPitch() ) );
                            floatingText.create();
                            this.plugin.getSlapperManager().getFloatingTextMap().put( entity.getEntityId(), floatingText );
                        }
                        entity.spawn( player.getLocation() );

                        SlapperData slapperData = SlapperData.builder()
                                .entityClass( entity.getClass().getSimpleName() )
                                .world( entity.getWorld().getWorldName() )
                                .x( entity.getPositionX() )
                                .y( entity.getPositionY() )
                                .z( entity.getPositionZ() )
                                .yaw( entity.getYaw() )
                                .headYaw( entity.getHeadYaw() )
                                .pitch( entity.getPitch() )
                                .nameTag( entity.getNameTag() )
                                .showNameTag( entity.isNameTagAlwaysVisible() )
                                .ticking( entity.isTicking() )
                                .build();

                        this.plugin.getConfig().getSlapperData().add( slapperData );
                        this.plugin.getConfig().saveFile( Slapper.getInstance() );
                        this.plugin.getSlapperManager().getSlapperDataMap().put( entity.getEntityId(), slapperData );
                    }
                }

            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
        } else {
            output.fail( Slapper.getInstance().getLocaleManager().translate( "needPlayer" ) );
        }
        return output;
    }

}
