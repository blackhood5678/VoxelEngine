package core;

import core.Utils.Consts;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;


public class EngineManager {

    public static final long NANOSECOND = (long) 1000000000F;
    public static final long FRAMERATE = 1000;
    private static int fps;
    private static float frametime=1.0f/FRAMERATE;
    private boolean isRunning;
    private WindowManager window;
    private GLFWErrorCallback errorCallback;

    private void init()throws Exception{
        //This will print errors if they occur with GLFW
        GLFW.glfwSetErrorCallback(errorCallback=GLFWErrorCallback.createPrint(System.err));

        //Singleton for the WindowManager class
        window=Launcher.getWindow();
        window.init();
    }

    public void Start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }
    public void run(){
        isRunning=true;
        int frames=0;
        long frameCounter=0;
        long lastTime=System.nanoTime();
        double unprocessedTime=0;

        while (isRunning){
            boolean render=false;
            long startTime=System.nanoTime();
            long passedTime=startTime-lastTime;
            lastTime=startTime;
            unprocessedTime+=passedTime/(double)NANOSECOND;
            frameCounter+=passedTime;

            input();

            while (unprocessedTime>frametime){
                render=true;
                unprocessedTime-=frametime;

                if (window.windowShouldCLose()){
                    stop();
                }
                if (frameCounter>=NANOSECOND){
                    setFps(frames);
                    frames=0;
                    frameCounter=0;
                    window.setTitle(Consts.TITLE + getFps());
                }
            }
            if (render){
                update();
                render();
                frames++;
            }
        }
        cleanup();
    }
    private void stop(){
        if (!isRunning){
            return;
        }
        isRunning=false;
    }
    private void input(){

    }
    private void render(){
        window.update();
    }
    private void update(){

    }
    private void cleanup(){
        window.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
