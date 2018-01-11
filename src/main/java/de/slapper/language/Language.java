package de.slapper.language;

import com.blackypaw.simpleconfig.SimpleConfig;
import de.slapper.Slapper;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Getter
public class Language extends SimpleConfig {

    public Language() {
        if( !Slapper.getInstance().getDataFolder().exists() ){
            Slapper.getInstance().getDataFolder().mkdir();
        }
    }

    private String prefix = "§f[§eSlapper§f] ";

    private String noPermissions = "§cDafür hast du keine Rechte!";

    private String spawnEntity = "§7Du hast das Entity §e[type] §7gespawnt";

    private String removeEntity = "§7Das §eEntity §7wurde erfolgreich entfernt";

    private String saveEntitySuccessful = "§7Du hast das §eEntity §7 erfolgreich gespeichert";

    private String removeEntityInfo1 = "§7Schlage das §eEntity §7was du entfernen willst";

    private String removeEntityInfo2 = "§cDu kannst nun kein §eEntity §cmehr entfernen";

    private String saveEntityInfo1 = "§7Schlag nun ein Entity um es zu speichern";

    private String saveEntityInfo2 = "§cDu kannst nun nicht mehr das Entity speichern";

    private String editEntitySuccessful = "§7Du hast das Entity §e[type] §7erfolgreich editiert";

    private String editEntityCommandHelp = "§cBitte benutze den Command /slapper getentity und schlag ein Entity und versuche es erneut ";

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
