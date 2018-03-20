package projects.mediavle_game;

import engine.linear.entities.Entity;

/**
 * Created by finne on 20.03.2018.
 */
public class GroundMap{

    private int fields_x;
    private int fields_y;

    Field[][] fields;

    public GroundMap(int fields_x, int fields_y) {
        this.fields_x = fields_x;
        this.fields_y = fields_y;
        fields = new Field[fields_x][fields_y];

        for(int i = 0; i < fields_x; i++) {
            for(int n = 0;n < fields_y; n++) {
                fields[i][n] = new Field();
            }
        }
    }

    public void initGame(){
        /* TODO */
    }


}
