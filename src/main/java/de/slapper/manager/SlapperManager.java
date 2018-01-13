package de.slapper.manager;

import de.slapper.Slapper;
import de.slapper.events.PlayerSpawnSlapperEntity;
import de.slapper.floatingtext.FloatingText;
import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.monster.*;
import io.gomint.entity.passive.*;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.player.PlayerSkin;
import io.gomint.world.World;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlapperManager {

    private File file = new File( Slapper.getInstance().getDataFolder() + "/Skins/" );
    public Map<Long, SlapperData> slapperDatas = new HashMap<>();
    public Map<EntityPlayer, Entity> getEntity = new HashMap<>();
    public Map<Entity, PlayerSkin> getSkin = new HashMap<>();
    public Map<Entity, FloatingText> getNameTag = new HashMap<>();

    public ArrayList<EntityPlayer> removeEntity = new ArrayList<>();
    public ArrayList<EntityPlayer> editEntity = new ArrayList<>();

    public SlapperManager() {
        if(!file.exists()){
            file.mkdir();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadEntitys(){
        try{
            for(String list : Slapper.getConfig().getList()){
                String[] data = list.split( "~" );
                //id, type, world, x, y, z, yaw, pitch, showNameTag, nameTag
                int id = Integer.parseInt( data[0] );
                String name = data[1];
                String type = data[2];
                World world = GoMint.instance().getWorld( data[3] );
                float x = Float.parseFloat( data[4] );
                float y = Float.parseFloat( data[5] );
                float z = Float.parseFloat( data[6] );
                float yaw = Float.parseFloat( data[7] );
                float pitch = Float.parseFloat( data[8] );
                boolean showNameTag = Boolean.parseBoolean( data[9] );
                String nameTag = data[10] != null ? data[10] : "null";

                Location location = new Location( world, x, y, z , yaw, pitch );
                Entity entity = getEntity( type );

                if(entity instanceof EntityHuman){
                    String item = data[11] != null ? data[11] : "null";
                    int slotID = data[12] != null ? Integer.parseInt( data[12] ) : 0;

                    slapperDatas.put( entity.getEntityId(), new SlapperData( id, name, type, world.getWorldName(), x, y, z, yaw, pitch, showNameTag, nameTag, item, slotID ));

                    entity.setNameTag( nameTag );

                    if(showNameTag){
                        entity.setNameTagAlwaysVisible( true );
                    }

                    InputStream inputStream = new BufferedInputStream( new FileInputStream( new File( Slapper.getInstance().getDataFolder() + "/Skins/" + name + ".png") ) );
                    PlayerSkin playerSkin = GoMint.instance().createPlayerSkin( inputStream );

                    ((EntityHuman)entity).setSkin( playerSkin );
                    try {
                        Class clazz = Class.forName( "io.gomint.inventory.item." + item );
                        ((EntityHuman)entity).getInventory().setItem( slotID, GoMint.instance().createItemStack( clazz, 1 ) );
                    } catch ( ClassNotFoundException e ) {
                        e.printStackTrace();
                    }
                }else{
                    if(entity != null){
                        slapperDatas.put( entity.getEntityId(), new SlapperData( id, name, type, world.getWorldName(), x, y, z, yaw, pitch, showNameTag, nameTag));
                    }
                }

                if ( entity != null ) {
                    entity.setTicking(false);
                    entity.spawn( location );

                    float eyeHeight = (float) (entity.getEyeHeight() * 1.7);
                    Location floatingTextLocation = new Location( world, location.getX(), location.getY() + eyeHeight, location.getZ() );

                    FloatingText floatingText = new FloatingText( nameTag, floatingTextLocation );
                    if(!(entity instanceof EntityHuman)){
                        if(showNameTag){
                            getNameTag.put( entity, floatingText );
                            floatingText.create();
                        }
                    }
                }


            }
        }catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    public void spawnEntity( EntityTypes type, EntityPlayer player, String nameTag, boolean showNameTag, Location location ){

        Entity entity = getEntity( type.getName() );

        if(entity != null){

            float eyeHeight = (float) (entity.getEyeHeight() * 1.7);
            Location floatingTextLocation = new Location( location.getWorld(), location.getX(), location.getY() + eyeHeight, location.getZ() );
            FloatingText floatingText = new FloatingText( nameTag, floatingTextLocation );

            entity.setTicking( false );
            entity.spawn( location );
            if(showNameTag){
                floatingText.create();
                getNameTag.put( entity, floatingText );
            }
        }


        int id = Slapper.getConfig().getList().size();
        id+=1;

        Slapper.getSlapperManager().saveMobs( player, type, showNameTag, nameTag );
        player.sendMessage( Slapper.prefix + Slapper.getLanguage().getSpawnEntity().replace( "[type]", type.getName() ) );

        if ( entity != null ) {
            slapperDatas.put( entity.getEntityId(), new SlapperData( id , player.getName(), type.getName(), location.getWorld().getWorldName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), showNameTag, nameTag ));
            player.sendMessage( "Height: " + entity.getEyeHeight() );
        }

        PlayerSpawnSlapperEntity playerSpawnSlapperEntity = new PlayerSpawnSlapperEntity( player, entity, type );
        Slapper.getInstance().getPluginManager().callEvent( playerSpawnSlapperEntity );

    }

    public void spawnHuman( EntityTypes type, EntityPlayer player, String nameTag, boolean showNameTag, Location location, PlayerSkin playerSkin, int slotID, ItemStack itemStack ){
        EntityHuman entity = EntityHuman.create();
        entity.setNameTag( nameTag );
        if(!showNameTag){
            entity.setNameTag( "" );
            entity.setNameTagAlwaysVisible( true );
        }
        entity.setSkin( playerSkin );
        entity.getInventory().setItem( slotID, itemStack );
        entity.setTicking( false );
        entity.spawn( location );

        int id = Slapper.getConfig().getList().size();
        id+=1;

        Slapper.getSlapperManager().saveHuman( player, type, showNameTag, nameTag );
        player.sendMessage( Slapper.prefix + Slapper.getLanguage().getSpawnEntity().replace( "[type]", type.getName() ) );

        slapperDatas.put( entity.getEntityId(), new SlapperData( id , player.getName(), type.getName(), location.getWorld().getWorldName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), showNameTag, nameTag, itemStack.getClass().getSimpleName(), slotID ));

        PlayerSpawnSlapperEntity playerSpawnSlapperEntity = new PlayerSpawnSlapperEntity( player, entity, type );
        Slapper.getInstance().getPluginManager().callEvent( playerSpawnSlapperEntity );
    }

    public void saveMobs( EntityPlayer player, EntityTypes type, Boolean showNameTag, String nameTag ){
        //Save locations from entity
        Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
        int id = Slapper.getConfig().getList().size();

        if(Slapper.getConfig().getList().isEmpty()){
            Slapper.getConfig().getList().add( 1 + "~" + player.getName() + "~" + type.getName() + "~" + player.getWorld().getWorldName() + "~" + player.getPositionX()  + "~" + player.getPositionY() + "~" + player.getPositionZ() +
                    "~" + player.getYaw() + "~" + player.getPitch() + "~" + showNameTag.toString() + "~" + nameTag );
        }else{
            id+=1;
            Slapper.getConfig().getList().add( id + "~" + player.getName() + "~" + type.getName() + "~" + player.getWorld().getWorldName() + "~" + player.getPositionX()  + "~" + player.getPositionY() + "~" + player.getPositionZ() +
                    "~" + player.getYaw() + "~" + player.getPitch() + "~" + showNameTag.toString() + "~" + nameTag );
        }
        Slapper.getConfig().save();
    }

    public void saveHuman( EntityPlayer player, EntityTypes type, Boolean showNameTag, String nameTag ){
        //Save locations from entity
        Slapper.getConfig().list = new ArrayList<>( Slapper.getConfig().getList() );
        int id = Slapper.getConfig().getList().size();

        if(Slapper.getConfig().getList().isEmpty()){
            Slapper.getConfig().getList().add( 1 + "~" + player.getName() + "~" + type.getName() + "~" + player.getWorld().getWorldName() + "~" + player.getPositionX()  + "~" + player.getPositionY() + "~" + player.getPositionZ() +
                    "~" + player.getYaw() + "~" + player.getPitch() + "~" + showNameTag.toString() + "~" + nameTag + "~" + player.getInventory().getItemInHand().getClass().getSimpleName() + "~" + player.getInventory().getItemInHandSlot());
        }else{
            id+=1;
            Slapper.getConfig().getList().add( id + "~" + player.getName() + "~" + type.getName() + "~" + player.getWorld().getWorldName() + "~" + player.getPositionX()  + "~" + player.getPositionY() + "~" + player.getPositionZ() +
                    "~" + player.getYaw() + "~" + player.getPitch() + "~" + showNameTag.toString() + "~" + nameTag + "~" + player.getInventory().getItemInHand().getClass().getSimpleName() + "~" + player.getInventory().getItemInHandSlot());
        }
        Slapper.getConfig().save();

        try {
            player.getSkin().saveSkinTo( new FileOutputStream( new File( Slapper.getInstance().getDataFolder() + "/Skins", player.getName() + ".png" ) ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public Entity getEntity(String types){
        if(types.equals( EntityTypes.BLAZE.getName() )){
            return EntityBlaze.create();
        }else if(types.equals( EntityTypes.CAVE_SPIDER.getName() )){
            return EntityCaveSpider.create();
        }else if(types.equals( EntityTypes.CREEPER.getName() )){
            return EntityCreeper.create();
        }else if(types.equals( EntityTypes.ELDER_GUARDIAN.getName() )){
            return EntityElderGuardian.create();
        }else if(types.equals( EntityTypes.ENDER_DRAGON.getName() )){
            return EntityEnderDragon.create();
        }else if(types.equals( EntityTypes.ENDERMAN.getName() )){
            return EntityEnderman.create();
        }else if(types.equals( EntityTypes.ENDERMITE.getName() )){
            return EntityEndermite.create();
        }else if(types.equals( EntityTypes.GHAST.getName() )){
            return EntityGhast.create();
        }else if(types.equals( EntityTypes.GUARDIAN.getName() )){
            return EntityGuardian.create();
        }else if(types.equals( EntityTypes.HUSK.getName() )){
            return EntityHusk.create();
        }else if(types.equals( EntityTypes.MAGMA_CUBE.getName() )){
            return EntityMagmaCube.create();
        }else if(types.equals( EntityTypes.POLAR_BEAR.getName() )){
            return EntityPolarBear.create();
        }else if(types.equals( EntityTypes.SHULKER.getName() )){
            return EntityShulker.create();
        }else if(types.equals( EntityTypes.SILVERFISH.getName() )){
            return EntitySilverfish.create();
        }else if(types.equals( EntityTypes.SKELETON.getName() )){
            return EntitySkeleton.create();
        }else if(types.equals( EntityTypes.SLIME.getName() )){
            return EntitySlime.create();
        }else if(types.equals( EntityTypes.SPIDER.getName() )){
            return EntitySpider.create();
        }else if(types.equals( EntityTypes.STRAY.getName() )){
            return EntityStray.create();
        }else if(types.equals( EntityTypes.VEX.getName() )){
            return EntityVex.create();
        }else if(types.equals( EntityTypes.WITCH.getName())){
            return EntityWitch.create();
        }else if(types.equals( EntityTypes.WITHER.getName() )){
            return EntityWither.create();
        }else if(types.equals( EntityTypes.WITHER_SKELETON.getName() )){
            return EntityWitherSkeleton.create();
        }else if(types.equals( EntityTypes.ZOMBIE.getName() )){
            return EntityZombie.create();
        }else if(types.equals( EntityTypes.ZOMBIE_PIGMAN.getName() )){
            return EntityZombiePigman.create();
        }else if(types.equals( EntityTypes.ZOMBIE_VILLAGER.getName() )){
            return EntityZombieVillager.create();
        }else if(types.equals( EntityTypes.BAT.getName() )){
            return EntityBat.create();
        }else if(types.equals( EntityTypes.CHICKEN.getName() )){
            return EntityChicken.create();
        }else if(types.equals( EntityTypes.COW.getName() )){
            return EntityCow.create();
        }else if(types.equals( EntityTypes.DONKEY.getName() )){
            return EntityDonkey.create();
        }else if(types.equals( EntityTypes.HORSE.getName() )){
            return EntityHorse.create();
        }else if(types.equals( EntityTypes.HUMAN.getName() )){
            return EntityHuman.create();
        }else if(types.equals( EntityTypes.LAMA.getName() )){
            return EntityLama.create();
        }else if(types.equals( EntityTypes.MOOSHROOM.getName() )){
            return EntityMooshroom.create();
        }else if(types.equals( EntityTypes.MULE.getName() )){
            return EntityMule.create();
        }else if(types.equals( EntityTypes.OCELOT.getName() )){
            return EntityOcelot.create();
        }else if(types.equals( EntityTypes.PIG.getName() )){
            return EntityPig.create();
        }else if(types.equals( EntityTypes.RABBIT.getName() )){
            return EntityRabbit.create();
        }else if(types.equals( EntityTypes.SHEEP.getName() )){
            return EntitySheep.create();
        }else if(types.equals( EntityTypes.SKELETON_HORSE.getName() )){
            return EntitySkeletonHorse.create();
        }else if(types.equals( EntityTypes.SQUID.getName() )){
            return EntitySquid.create();
        }else if(types.equals( EntityTypes.VILLAGER.getName() )){
            return EntityVillager.create();
        }else if(types.equals( EntityTypes.WOLF.getName() )){
            return EntityWolf.create();
        }else if(types.equals( EntityTypes.ZOMBIE_HOSE.getName() )){
            return EntityZombieHorse.create();
        }
        return null;
    }

}
