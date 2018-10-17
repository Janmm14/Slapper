package de.slapper;

import de.slapper.config.Config;
import de.slapper.listener.EntityDamageByEntityListener;
import de.slapper.listener.PlayerJoinListener;
import de.slapper.manager.SlapperManager;
import io.gomint.i18n.LocaleManager;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@PluginName( "Slapper" )
@Version( major = 2, minor = 0 )
public class Slapper extends Plugin {

    @Getter
    private LocaleManager localeManager;

    @Getter
    public String prefix;
    @Getter
    private Config config;
    @Getter
    private SlapperManager slapperManager;

    @Override
    public void onInstall() {

        this.saveFile( this.getResourceAsStream( "de_DE.properties" ), new File( this.getDataFolder().getAbsolutePath() + "/languages", "de_DE.properties" ) );
        this.saveFile( this.getResourceAsStream( "en_US.properties" ), new File( this.getDataFolder().getAbsolutePath() + "/languages", "en_US.properties" ) );

        this.localeManager = new LocaleManager( this );
        this.localeManager.setDefaultLocale( Locale.GERMANY );
        this.localeManager.initFromLocaleFolder( new File( this.getDataFolder().getAbsolutePath() + "/language" ) );

        this.config = new Config( this );
        this.prefix = this.config.getPrefix();

        this.slapperManager = new SlapperManager( this );
        this.slapperManager.loadEntitys();

        this.registerListener( new EntityDamageByEntityListener( this ) );

        // Only for test
       // this.registerListener( new PlayerJoinListener() );
    }

    @Override
    public void onUninstall() {

    }

    /**
     * Save the file from recource folder
     *
     * @param resourceInputStream where the resource file is located
     * @param languageFile where the file is stored
     */

    private void saveFile( InputStream resourceInputStream, File languageFile ) {
        if ( !languageFile.exists() ) {
            try {
                FileUtils.copyInputStreamToFile( resourceInputStream, languageFile );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

}
