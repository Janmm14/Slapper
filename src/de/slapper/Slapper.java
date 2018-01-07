package de.slapper;

import de.slapper.config.Config;
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
public class Slapper extends Plugin implements EventListener {

    public static String prefix = "§f[§eSlapper§f] ";

    private static Slapper instance;
    private static Config config;
    private static SlapperManager slapperManager;

    @Override
    public void onInstall() {
        instance = this;
        config = new Config();
        try {
            config.initialize( new File( getDataFolder(), "config.cfg" ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        slapperManager = new SlapperManager();
        slapperManager.loadEntitys();

        registerListener( this );
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

    //Test
    @EventHandler
    public void dd( PlayerJoinEvent e ){
        e.getPlayer().setGamemode( Gamemode.CREATIVE );
    }

}
