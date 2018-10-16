package de.slapper.config;

import de.slapper.Slapper;
import de.slapper.manager.SlapperData;
import io.gomint.config.Comment;
import io.gomint.config.InvalidConfigurationException;
import io.gomint.config.YamlConfig;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config extends YamlConfig {

    public Config( Slapper plugin ) {
        try {
            this.init( new File( plugin.getDataFolder(), "config.yml" ));
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
    }

    @Getter
    private String prefix = "§f[§eSlapper§f] ";

    @Comment( "Here you can activate or deactivate permissions" )
    @Getter
    private boolean usePermissions = true;

    @Comment( "All Entities are stored here" )
    @Getter
    private List<SlapperData> slapperData = new ArrayList<>();

    /**
     * Save the config file
     *
     * @param plugin is the main class.
     */

    public void saveFile( Slapper plugin ) {
        try {
            this.save( new File( plugin.getDataFolder(), "config.yml" ) );
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
    }

}
