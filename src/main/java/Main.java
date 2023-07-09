import org.Main.core.WindowManager;

public class Main {
    public static void main(String[] args) {
        WindowManager window=new WindowManager("test",1600,900,false);
        window.init();
        while (!window.windowShouldCLose()){
            window.update();
        }
        window.cleanup();
    }
}