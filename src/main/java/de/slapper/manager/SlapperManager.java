package de.slapper.manager;

import de.slapper.Slapper;
import de.slapper.event.PlayerSpawnSlapperEntity;
import de.slapper.api.floatingtext.FloatingText;
import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.monster.*;
import io.gomint.entity.passive.*;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.player.PlayerSkin;
import io.gomint.server.entity.EntityType;
import io.gomint.world.World;
import lombok.Getter;

import java.io.*;
import java.util.*;

public class SlapperManager {

    private Slapper plugin;

    public SlapperManager( Slapper plugin ) {
        this.plugin = plugin;
    }

    @Getter
    private List<UUID> removeEntity = new ArrayList<>();

    @Getter
    private Map<Long, SlapperData> slapperDataMap = new HashMap<>();
    @Getter
    private Map<Long, FloatingText> floatingTextMap = new HashMap<>();

    public void loadEntitys(){
        for( SlapperData slapperData : this.plugin.getConfig().getSlapperData() ){
            try {
                Class<? extends Entity> clazz = (Class<? extends Entity>) Class.forName( Slapper.getInstance().getSlapperManager().getMobClassPath( slapperData.getEntityClass().replace( "Entity", "" ) ) + slapperData.getEntityClass() );
                Entity entity = GoMint.instance().createEntity( clazz );

                if(entity instanceof EntityHuman){
                    EntityHuman entityHuman = (EntityHuman) entity;
                    entityHuman.setNameTag( slapperData.getNameTag() );
                    entityHuman.setNameTagAlwaysVisible( slapperData.isShowNameTag() );
                    entityHuman.setSneaking( slapperData.isSneaking() );
                    entityHuman.setTicking( slapperData.isTicking() );

                    try {
                        PlayerSkin playerSkin = GoMint.instance().createPlayerSkin(  new FileInputStream(  new File( this.plugin.getDataFolder() + "/skins" , slapperData.getSkinName() + ".png" )  ) );
                        Class itemClass = Class.forName( "io.gomint.inventory.item." + slapperData.getItemCalssName() );
                        Class helmetClass = Class.forName( "io.gomint.inventory.item." + slapperData.getHelemtClassName() );
                        Class chestplateClass = Class.forName( "io.gomint.inventory.item." + slapperData.getChestplateClassName() );
                        Class leggingsClass = Class.forName( "io.gomint.inventory.item." + slapperData.getLeggingsClassName() );
                        Class bootsClass = Class.forName( "io.gomint.inventory.item." + slapperData.getBootsClassName() );

                        entityHuman.getArmorInventory().setHelmet( GoMint.instance().createItemStack( helmetClass, 1 ) );
                        entityHuman.getArmorInventory().setChestplate( GoMint.instance().createItemStack( chestplateClass, 1 ) );
                        entityHuman.getArmorInventory().setLeggings( GoMint.instance().createItemStack( leggingsClass, 1 ) );
                        entityHuman.getArmorInventory().setBoots( GoMint.instance().createItemStack( bootsClass,1 ) );
                        entityHuman.getInventory().setItem( 0, GoMint.instance().createItemStack( itemClass, 1 ) );

                        entityHuman.setSkin( playerSkin );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                    entityHuman.spawn( new Location( GoMint.instance().getWorld( slapperData.getWorld() ), slapperData.getX(), slapperData.getY(), slapperData.getZ(), slapperData.getHeadYaw(), slapperData.getYaw(), slapperData.getPitch()) );
                    this.slapperDataMap.put( entityHuman.getEntityId(), slapperData );
                }else{
                    if(slapperData.isShowNameTag()){
                        FloatingText floatingText = new FloatingText();
                        floatingText.setTitle( slapperData.getNameTag() );
                        floatingText.setLocation( new Location( GoMint.instance().getWorld( slapperData.getWorld() ), slapperData.getX(), (float) (slapperData.getY() + entity.getEyeHeight() * 1.7), slapperData.getZ(), slapperData.getYaw(), slapperData.getPitch()) );
                        floatingText.create();
                        this.floatingTextMap.put( entity.getEntityId(), floatingText );
                    }
                    entity.setTicking( slapperData.isTicking() );
                    entity.spawn( new Location( GoMint.instance().getWorld( slapperData.getWorld() ), slapperData.getX(), slapperData.getY(), slapperData.getZ(), slapperData.getYaw(), slapperData.getPitch()) );
                    this.slapperDataMap.put( entity.getEntityId(), slapperData );
                }

            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
        }
        this.plugin.getLogger().info( this.plugin.getLocaleManager().translate( "entitiesLoaded" ) );
    }

    public String getMobClassPath( String entityType ) {

        switch ( entityType ) {

            case "PrimedTNT":
                return "io.gomint.entity.active.";

            case "Blaze": case "CaveSpider": case "Cod": case "Creeper": case "Drowned": case "ElderGuardian": case "EnderDragon": case "Enderman": case "Endermite": case "Evoker": case "Ghast": case "Guardian":
            case "Husk": case "MagmaCube": case "PolarBear": case "Salmon": case "Shulker": case "Silverfish": case "Skeleton": case "Slime": case "Spider": case "Stray": case "TropicalFish": case "Vex": case "Vindicator":
            case "Witch": case "Wither": case "WitherSkeleton": case "Zombie": case "ZombiePigman": case "ZombieVillager":
                return "io.gomint.entity.monster.";

            case "ArmorStand": case "Bat": case "Chicken": case "Cow": case "Donkey": case "FallingBlock": case "Horse": case "Human": case "ItemDrop": case "Lama": case "Mooshroom": case "Mule": case "Ocelot":
            case "Parrot": case "Pig": case "Rabbit": case "Sheep": case "SkeletonHorse": case "Squid": case "Turtle": case "Villager": case "Wolf": case "XPOrb": case "ZombieHorse":
                return "io.gomint.entity.passive.";

            case "Arrow": case "Enderpearl": case "ExpBottle": case "FishingHook": case "Snowball":
                return "io.gomint.entity.projectile.";
        }

        return "null";
    }

}
