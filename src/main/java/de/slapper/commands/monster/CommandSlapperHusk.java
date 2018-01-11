package de.slapper.commands.monster;

import de.slapper.Slapper;
import de.slapper.events.PlayerSpawnSlapperEntity;
import de.slapper.manager.EntityTypes;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.validator.BooleanValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.monster.EntityHusk;

import java.util.Map;

@Name("slapper husk")
@Description("Spawn ein EntityHusk")
@Overload( {
        @Parameter( name = "nameTag", validator = StringValidator.class, arguments = {".*"}, optional = true ),
        @Parameter( name = "showNameTag", validator = BooleanValidator.class, arguments = {"false", "true"}, optional = true )
} )
public class CommandSlapperHusk extends Command {

    private EntityTypes type = EntityTypes.HUSK;

    @Override
    public CommandOutput execute( EntityPlayer player, String s, Map<String, Object> map ) {

        if(Slapper.getConfig().isUsePermissions()){
            if(player.hasPermission( "Slapper.SpawnEntity" )){
                String nameTag = (String) map.getOrDefault( "nameTag", type.getName());
                Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", true );

                Slapper.getSlapperManager().spawnEntity( type, player, nameTag, showNameTag, player.getLocation() );

            }else{
                player.sendMessage( Slapper.getLanguage().getNoPermissions() );
            }
        }else{
            String nameTag = (String) map.getOrDefault( "nameTag", type.getName());
            Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", true );

            Slapper.getSlapperManager().spawnEntity( type, player, nameTag, showNameTag, player.getLocation() );
        }

        return new CommandOutput();
    }
}