package de.slapper.commands;

import de.slapper.Slapper;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("slapper remove")
@Description("Entferne ein Entity")
public class CommandRemoveEntity extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String s, Map<String, Object> map ) {

        if( Slapper.getInstance().getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.RemoveEntity" )){
                if(!Slapper.getInstance().getSlapperManager().removeEntity.contains( player )){
                    Slapper.getInstance().getSlapperManager().removeEntity.add( player );
                    player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntityInfo1() );
                }else{
                    Slapper.getInstance().getSlapperManager().removeEntity.remove( player );
                    player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntityInfo2() );
                }
            }else{
                player.sendMessage( Slapper.getInstance().getLanguage().getNoPermissions() );
            }
        }else{
            if(!Slapper.getInstance().getSlapperManager().removeEntity.contains( player )){
                Slapper.getInstance().getSlapperManager().removeEntity.add( player );
                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntityInfo1() );
            }else{
                Slapper.getInstance().getSlapperManager().removeEntity.remove( player );
                player.sendMessage( Slapper.prefix + Slapper.getInstance().getLanguage().getRemoveEntityInfo2() );
            }
        }

        return new CommandOutput();
    }
}
