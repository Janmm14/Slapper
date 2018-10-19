package de.slapper.manager;

import io.gomint.config.YamlConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlapperData extends YamlConfig {

    private String entityClass;
    private String world;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;
    private float pitch;
    private float scale;
    private String nameTag;
    private boolean showNameTag;
    private boolean ticking;
    private boolean sneaking;
    private String skinName;
    private String itemCalssName;
    private String helemtClassName;
    private String chestplateClassName;
    private String leggingsClassName;
    private String bootsClassName;

}
