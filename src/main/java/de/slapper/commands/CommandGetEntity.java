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

        if( Slapper.getInstance().getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.GetEntity" )){

                if(!Slapper.getInstance().getSlapperManager().editEntity.contains( player )){
                    Slapper.getInstance().getSlapperManager().editEntity.add( player );
                    player.sendMessage(Slapper.prefix + Slapper.getInstance().getLanguage().getSaveEntityInfo1() );
                }else{
                    Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                    player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getSaveEntityInfo2() );
                }

            }else{
                player.sendMessage( Slapper.getInstance().getLanguage().getNoPermissions() );
            }
        }else{
            if(!Slapper.getInstance().getSlapperManager().editEntity.contains( player )){
                Slapper.getInstance().getSlapperManager().editEntity.add( player );
                player.sendMessage(Slapper.prefix + Slapper.getInstance().getLanguage().getSaveEntityInfo1() );
            }else{
                Slapper.getInstance().getSlapperManager().editEntity.remove( player );
                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getSaveEntityInfo2() );
            }
        }

        return new CommandOutput();
    }
}
