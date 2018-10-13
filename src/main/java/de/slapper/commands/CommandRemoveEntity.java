package de.slapper.commands;

import de.slapper.Slapper;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Permission;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name( "slapper remove" )
@Description( "Remove and entity" )
@Permission( "slapper.removeentity" )
public class CommandRemoveEntity extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String s, Map<String, Object> map ) {
        CommandOutput output = new CommandOutput();
        Slapper plugin = Slapper.getInstance();

        if ( commandSender instanceof PlayerCommandSender ) {
            EntityPlayer player = (EntityPlayer) commandSender;

            if ( !plugin.getSlapperManager().getRemoveEntity().contains( player.getUUID() ) ) {
                plugin.getSlapperManager().getRemoveEntity().add( player.getUUID() );
                output.success( plugin.getLocaleManager().translate( player.getLocale(), "canRemoveEntity" ) );
            } else {
                plugin.getSlapperManager().getRemoveEntity().remove( player.getUUID() );
                output.success( plugin.getLocaleManager().translate( player.getLocale(), "cantRemoveEntity" ) );
            }

        } else {
            output.fail( plugin.getLocaleManager().translate( "needPlayer" ) );
        }

        return output;
    }
}
