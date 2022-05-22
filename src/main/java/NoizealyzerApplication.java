import config.ApplicationConfig;
import maincycle.MainCycle;

public class NoizealyzerApplication {

    public static void main(String[] args) {
        //readConfig
        ApplicationConfig config = null;
        MainCycle.getInstance(config).start();
        //selfcheck (at least mic) - at the main cycle
        //connectToServer - at the
        //startCycle
    }
}
