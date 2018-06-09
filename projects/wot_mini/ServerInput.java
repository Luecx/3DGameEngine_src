package projects.wot_mini;

import udp.udp_content.UDPContent;

public class ServerInput extends UDPContent {

    private boolean[] values;

    public ServerInput(boolean... values) {
        this.values = values;
    }

    public boolean[] getValues() {
        return values;
    }

    public void setValues(boolean... values) {
        this.values = values;
    }

}
