package de.slapper.config;

import com.blackypaw.simpleconfig.SimpleConfig;
import com.blackypaw.simpleconfig.annotation.Comment;
import de.slapper.Slapper;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config extends SimpleConfig {

    public Config() {
        if( !Slapper.getInstance().getDataFolder().exists() ){
            Slapper.getInstance().getDataFolder().mkdir();
        }
    }

    @Comment("Hier kannst du die Permissions aktivieren oder deaktivieren")
    @Getter
    private boolean usePermissions = true;

    @Comment("Hier sind alle Entitys gespeichert")
    @Getter
    public List<String> list = new ArrayList<>();

    public void save(){
        try {
            FileWriter writer = new FileWriter( new File( Slapper.getInstance().getDataFolder(), "config.cfg" ) );
            write( writer );
            writer.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
