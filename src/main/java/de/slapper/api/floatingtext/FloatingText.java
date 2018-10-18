package de.slapper.api.floatingtext;

import io.gomint.entity.passive.EntityHuman;
import io.gomint.math.Location;
import io.gomint.player.PlayerSkin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FloatingText {

    protected EntityHuman human;
    private String title;
    private Location location;

    /**
     * Constructor
     *
     * @param title is the text from the floatingtext
     * @param location where the floating text will be spawned
     */

    public FloatingText( String title, Location location ) {
        this.title = title;
        this.location = location;
    }

    /**
     * Create the floatingtext
     */

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

    /**
     * Remove the floatingtext
     */

    public void remove(){
        human.despawn();
    }
}
