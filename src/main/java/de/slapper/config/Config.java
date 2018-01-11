package de.slapper.config;

import com.blackypaw.simpleconfig.SimpleConfig;
import com.blackypaw.simpleconfig.annotation.Comment;
import de.slapper.Slapper;

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
    private boolean usePermissions = true;

    public boolean isUsePermissions() {
        return usePermissions;
    }

    @Comment("Hier sind alle Entitys gespeichert")
    public List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

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
