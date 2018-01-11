package de.slapper.commands.passive;

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
import io.gomint.entity.passive.EntityHuman;
import io.gomint.server.inventory.item.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Name("slapper human")
@Description("Spawn ein EntityHuman")
@Overload( {
        @Parameter( name = "nameTag", validator = StringValidator.class, arguments = {".*"}, optional = true ),
        @Parameter( name = "showNameTag", validator = BooleanValidator.class, arguments = {"false", "true"}, optional = true )
} )
public class CommandSlapperHuman extends Command {

    private EntityTypes type = EntityTypes.HUMAN;

    @Override
    public CommandOutput execute( EntityPlayer player, String s, Map<String, Object> map ) {

        if(Slapper.getConfig().isUsePermissions()){
            if(player.hasPermission( "Slapper.SpawnEntity" )){
                String nameTag = (String) map.getOrDefault( "nametag", player.getNameTag());
                Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", true );

                Slapper.getSlapperManager().spawnHuman( type, player, nameTag, showNameTag, player.getLocation(), player.getSkin(), player.getInventory().getItemInHandSlot(), (ItemStack) player.getInventory().getItemInHand() );

            }else{
                player.sendMessage( Slapper.getLanguage().getNoPermissions() );
            }
        }else{
            String nameTag = (String) map.getOrDefault( "nametag", player.getNameTag());
            Boolean showNameTag = (Boolean) map.getOrDefault( "showNameTag", true );

            Slapper.getSlapperManager().spawnHuman( type, player, nameTag, showNameTag, player.getLocation(), player.getSkin(), player.getInventory().getItemInHandSlot(), (ItemStack) player.getInventory().getItemInHand() );
        }

        return new CommandOutput();
    }
}
