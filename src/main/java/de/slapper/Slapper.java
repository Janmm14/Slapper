package de.slapper;

import de.slapper.config.Config;
import de.slapper.language.Language;
import de.slapper.listener.EntityDamageByEntityListener;
import de.slapper.manager.SlapperManager;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

@PluginName( "Slapper" )
@Version( major = 1, minor = 14)
public class Slapper extends Plugin {

    public static String prefix;
    @Getter
    private static Slapper instance;
    @Getter
    private Config config;
    @Getter
    private Language language;
    @Getter
    private SlapperManager slapperManager;

    @Override
    public void onInstall() {
        instance = this;
        config = new Config();
        language = new Language();
        try {
            config.initialize( new File( getDataFolder(), "config.cfg" ) );
            language.initialize(  new File( getDataFolder(), "language.cfg" )  );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        prefix = language.getPrefix();

        slapperManager = new SlapperManager();
        slapperManager.loadEntitys();

       this.registerListener( new EntityDamageByEntityListener() );
    }

    @Override
    public void onUninstall() {

    }
}
