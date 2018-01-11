package de.slapper.manager;

public class SlapperData {

    private int id;
    private String spawnedBy;
    private String type;
    private String world;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float pitch;
    private boolean showNameTag;
    private String nameTag;
    private String itemCalssName;
    private int slotId;

    public SlapperData( int id, String spawnedBy, String type, String world, float x, float y, float z, float yaw, float pitch, boolean showNameTag, String nameTag, String itemCalssName, int slotId ) {
        this.id = id;
        this.spawnedBy = spawnedBy;
        this.type = type;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.showNameTag = showNameTag;
        this.nameTag = nameTag;
        this.itemCalssName = itemCalssName;
        this.slotId = slotId;
    }

    public SlapperData( int id, String spawnedBy, String type, String world, float x, float y, float z, float yaw, float pitch, boolean showNameTag, String nameTag ) {
        this.id = id;
        this.spawnedBy = spawnedBy;
        this.type = type;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.showNameTag = showNameTag;
        this.nameTag = nameTag;
    }

    public int getId() {
        return id;
    }

    public String getSpawnedBy() {
        return spawnedBy;
    }

    public String getType() {
        return type;
    }

    public String getWorld() {
        return world;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isShowNameTag() {
        return showNameTag;
    }

    public String getNameTag() {
        return nameTag;
    }

    public String getItemCalssName() {
        return itemCalssName;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setSpawnedBy( String spawnedBy ) {
        this.spawnedBy = spawnedBy;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public void setWorld( String world ) {
        this.world = world;
    }

    public void setX( float x ) {
        this.x = x;
    }

    public void setY( float y ) {
        this.y = y;
    }

    public void setZ( float z ) {
        this.z = z;
    }

    public void setYaw( float yaw ) {
        this.yaw = yaw;
    }

    public void setPitch( float pitch ) {
        this.pitch = pitch;
    }

    public void setShowNameTag( boolean showNameTag ) {
        this.showNameTag = showNameTag;
    }

    public void setNameTag( String nameTag ) {
        this.nameTag = nameTag;
    }

    public void setItemCalssName( String itemCalssName ) {
        this.itemCalssName = itemCalssName;
    }

    public void setSlotId( int slotId ) {
        this.slotId = slotId;
    }
}
