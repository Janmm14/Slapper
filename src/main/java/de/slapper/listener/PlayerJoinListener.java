package de.slapper.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements EventListener {

    // Only for test

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        event.getPlayer().getPermissionManager().setPermission( "*", true );
    }

}
