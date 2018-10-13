package de.slapper.listener;

import de.slapper.Slapper;
import de.slapper.event.PlayerHitSlapperEvent;
import de.slapper.event.PlayerRemoveSlapperEvent;
import de.slapper.manager.SlapperData;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class EntityDamageByEntityListener implements EventListener {

    @EventHandler
    public void onPlayerHitSlapper( EntityDamageByEntityEvent event ) {
        Entity entity = event.getEntity();

        if ( event.getAttacker() instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) event.getAttacker();



        }

    }

}
