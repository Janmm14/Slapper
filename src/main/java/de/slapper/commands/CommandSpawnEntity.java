package de.slapper.commands;

import de.slapper.Slapper;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Name( "spawn" )
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

                    player.sendMessage( String.valueOf( player.getYaw() ) );
                    player.sendMessage( String.valueOf( player.getHeadYaw() ) );

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

                    Slapper.getInstance().getConfig().getSlapperData().add( slapperData );
                    Slapper.getInstance().getConfig().saveFile( Slapper.getInstance() );
                    Slapper.getInstance().getSlapperManager().getSlapperDataMap().put( entityHuman.getEntityId(), slapperData );

                } else {
                    if ( map.containsKey( "nameTag" ) ) {
                        String nameTag = (String) map.get( "nameTag" );
                        entity.setNameTag( nameTag );
                        entity.setNameTagAlwaysVisible( true );
                    }
                    entity.spawn( player.getLocation() );
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
