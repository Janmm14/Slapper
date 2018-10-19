package de.slapper.command;

import de.slapper.Slapper;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Permission;
import io.gomint.entity.EntityPlayer;
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;

@Name( "slapper remove" )
@Description( "Remove and entity" )
@Permission( "slapper.removeentity" )
public class CommandRemoveEntity extends Command {

    @InjectPlugin
    private Slapper plugin;
    
    @Override
    public CommandOutput execute( CommandSender commandSender, String s, Map<String, Object> map ) {
        CommandOutput output = new CommandOutput();

        if ( commandSender instanceof PlayerCommandSender ) {
            EntityPlayer player = (EntityPlayer) commandSender;

            if ( !this.plugin.getSlapperManager().getRemoveEntity().contains( player.getUUID() ) ) {
                this.plugin.getSlapperManager().getRemoveEntity().add( player.getUUID() );
                output.success( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( player.getLocale(), "canRemoveEntity" ) );
            } else {
                this.plugin.getSlapperManager().getRemoveEntity().remove( player.getUUID() );
                output.success( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( player.getLocale(), "cantRemoveEntity" ) );
            }

        } else {
            output.fail( this.plugin.getPrefix() + this.plugin.getLocaleManager().translate( "needPlayer" ) );
        }

        return output;
    }
}
