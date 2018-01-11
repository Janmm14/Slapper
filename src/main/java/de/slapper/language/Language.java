package de.slapper.language;

import com.blackypaw.simpleconfig.SimpleConfig;
import com.blackypaw.simpleconfig.annotation.Comment;
import de.slapper.Slapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Language extends SimpleConfig {

    public Language() {
        if( !Slapper.getInstance().getDataFolder().exists() ){
            Slapper.getInstance().getDataFolder().mkdir();
        }
    }

    @Comment("Prefix")
    private String prefix = "§f[§eSlapper§f] ";

    public String getPrefix() {
        return prefix;
    }

    private String spawnEntity = "§7Du hast das Entity §e[type] §7gespawnt";

    public String getSpawnEntity() {
        return spawnEntity;
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter( new File( Slapper.getInstance().getDataFolder(), "language.cfg" ) );
            write( writer );
            writer.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
