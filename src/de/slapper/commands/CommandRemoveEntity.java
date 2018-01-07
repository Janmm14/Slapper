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

        if( Slapper.getConfig().isUsePermissions() ){
            if(player.hasPermission( "Slapper.RemoveEntity" )){
                if(!Slapper.getSlapperManager().removeEntity.contains( player )){
                    Slapper.getSlapperManager().removeEntity.add( player );
                    player.sendMessage( Slapper.prefix + "§7Schlage das §eEntity §7was du entfernen willst" );
                }else{
                    Slapper.getSlapperManager().removeEntity.remove( player );
                    player.sendMessage( Slapper.prefix + "§cDu kannst nun kein §eEntity §cmehr entfernen" );
                }
            }else{
                player.sendMessage( "§cDafür hast du keine Rechte!" );
            }
        }else{
            if(!Slapper.getSlapperManager().removeEntity.contains( player )){
                Slapper.getSlapperManager().removeEntity.add( player );
                player.sendMessage( Slapper.prefix + "§7Schlage das §eEntity §7was du entfernen willst" );
            }else{
                Slapper.getSlapperManager().removeEntity.remove( player );
                player.sendMessage( Slapper.prefix + "§cDu kannst nun kein §eEntity §cmehr entfernen" );
            }
        }

        return new CommandOutput();
    }
}
