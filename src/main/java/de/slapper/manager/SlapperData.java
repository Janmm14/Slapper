package de.slapper.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
