package de.slapper.commands;

import de.slapper.Slapper;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("slapper getentity")
@Description("Speicher ein Entity um es zu bearbeiten")
public class CommandGetEntity extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String s, Map<String, Object> map ) {

        if( Slapper.getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.GetEntity" )){

                if(!Slapper.getSlapperManager().editEntity.contains( player )){
                    Slapper.getSlapperManager().editEntity.add( player );
                    player.sendMessage(Slapper.prefix + "§7Schlag nun ein Entity um es zu speichern" );
                }else{
                    Slapper.getSlapperManager().editEntity.remove( player );
                    player.sendMessage( Slapper.prefix + "§cDu kannst nun nicht mehr das Entity speichern" );
                }

            }else{
                player.sendMessage( "§cDafür hast du keine Rechte!" );
            }
        }else{
            if(!Slapper.getSlapperManager().editEntity.contains( player )){
                Slapper.getSlapperManager().editEntity.add( player );
                player.sendMessage(Slapper.prefix + "§7Schlag nun ein Entity um es zu speichern" );
            }else{
                Slapper.getSlapperManager().editEntity.remove( player );
                player.sendMessage( Slapper.prefix + "§cDu kannst nun nicht mehr das Entity speichern" );
            }
        }

        return new CommandOutput();
    }
}
