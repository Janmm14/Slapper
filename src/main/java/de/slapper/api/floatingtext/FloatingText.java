package de.slapper.api.floatingtext;

import io.gomint.entity.passive.EntityHuman;
import io.gomint.math.Location;
import io.gomint.player.PlayerSkin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloatingText {

    protected EntityHuman human;
    private String title;
    private Location location;

    public FloatingText( String title, Location location ) {
        this.title = title;
        this.location = location;
    }

    public void create(){
        if(title != null && location != null){
            human = EntityHuman.create();
            human.setSkin( PlayerSkin.empty() );
            human.setScale( (float) 0.0 );
            human.setNameTag( title );
            human.setNameTagAlwaysVisible( true );
            human.setTicking( false );
            human.spawn( location );
        }
    }

    public void remove(){
        human.despawn();
    }
}
