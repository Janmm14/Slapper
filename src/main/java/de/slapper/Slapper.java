package de.slapper;

import de.slapper.config.Config;
import de.slapper.language.Language;
import de.slapper.listeners.EntityDamageByEntityListener;
import de.slapper.manager.SlapperManager;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import io.gomint.world.Gamemode;

import java.io.File;
import java.io.IOException;

@PluginName( "Slapper" )
@Version( minor = 1, major = 0)
public class Slapper extends Plugin {

    public static String prefix;

    private static Slapper instance;
    private static Config config;
    private static Language language;
    private static SlapperManager slapperManager;

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

        registerListener( new EntityDamageByEntityListener() );
    }

    @Override
    public void onUninstall() {

    }

    public static Slapper getInstance() {
        return instance;
    }

    public static Config getConfig() {
        return config;
    }

    public static SlapperManager getSlapperManager() {
        return slapperManager;
    }

    public static Language getLanguage() {
        return language;
    }
}
