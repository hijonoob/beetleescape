package hijonoob.beetleescape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    SurfaceHolder holder;

    public static int globalxSpeed = 10;

    Bitmap background;
    Bitmap beetlebmp;

    //int xx = 0;
    int pontos = 0;
    int contadorx = 0;

    private List<Background> backgroundList = new ArrayList<Background>();
    private List<Beetle> beetleList = new ArrayList<Beetle>();
    //private Beetle beetlePlayer;
    //private static SharedPreferences prefs;

    private String Menu = "Mainmenu";

    public GameView(Context context) {
        super(context);
        //prefs = context.getSharedPreferences("hijonoob.beetleescape",Context.MODE_PRIVATE);
        //String spackage ="hijonoob.beetleescape";
        gameLoop = new GameLoop(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceDestroyed(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                gameLoop.running = false;
            }

            public void surfaceCreated(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                gameLoop.setRunning();
                gameLoop.start();
            }

            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

        });

        background = BitmapFactory.decodeResource(getResources(), R.drawable.cenarioclaro);
        beetlebmp = BitmapFactory.decodeResource(getResources(), R.drawable.beetlesprite);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){

        for(Beetle bbeetle: beetleList){
            bbeetle.ontouch(e.getY());
        }

        if (Menu.equals("Mainmenu")) {
            startGame();
        }
/*        if (Menu =="Mainmenu")
        {
            for(int i = 0; i < buttons.size(); i++){
                if (buttons.get(i).getState() == 1){   // Restart
                    if ((buttons.get(i).getX()<e.getX() && buttons.get(i).getX()+84>e.getX())){
                        if (buttons.get(i).getY()<e.getY() && buttons.get(i).getY()+32>e.getY()){
                            Menu = "Running";
                            startGame();}
                    }
                }

            }
        }*/
        return false;
    }

    public void update(){
        if(Menu.equals("Running")){
            updatetimers();
            pontos+= globalxSpeed;
        }
        if(Menu.equals("Mainmenu")) {
            // TODO
            //updatetimers();
        }
    }

    public void updatetimers(){
        // enum
        if (Menu.equals("Running")) {
            movebackground();
            contadorx++;
            if(contadorx >= 100) {
                globalxSpeed++;
                contadorx = 0;
            }
        }
        if(Menu.equals("Mainmenu")) {
            //TODO
        }
    }


    public void addbackground(){
        backgroundList.add(new Background(this,background,0,0));
        backgroundList.add(new Background(this,background,this.getWidth()*2,0));
    }

    public void movebackground(){

        //if (backgroundList.get(0).returnX()/10 == -this.getWidth()/10) {
        //    backgroundList.add(new Background(this,background,this.getWidth(),0));
        //}
/*        if (backgroundList.get(0).returnX() == -this.getWidth()*2) {
            backgroundList.remove(0);
        }
*/
        /*for (int i = backgroundList.size()-1;i >= 0; i--) {
            int backgroundx = backgroundList.get(i).returnX();
            if (backgroundx <= -this.getWidth() * 2) {
                backgroundList.remove(i);
            }
        }*/
        for (int i = backgroundList.size()-1;i >= 0; i--) {
            int backgroundx = backgroundList.get(i).returnX();
            if (backgroundx <= -this.getWidth() * 2) {
                backgroundList.get(i).setX(this.getWidth()*2);
            }
        }
/*
        for (int i = backgroundList.size()-1;i >= 0; i--)
        {
            int backgroundx = backgroundList.get(i).returnX();

            if (backgroundx<=-this.getWidth()){
                pontos = 0;
                //backgroundList.remove(i);
                backgroundList.add(new Background(this,background,this.getWidth(),0));
            }
            if (backgroundx<=-this.getWidth()*2){
                pontos = 0;
                backgroundList.remove(i);
                //backgroundList.add(new Background(this,background,this.getWidth(),0));
            }
        }
*/
    }
   public void startGame(){
       Menu="Running";
       addbackground();
       beetleList.add(new Beetle(this,beetlebmp));
       /* for(int i = 0; i < buttons.size(); i++){
            buttons.remove(i);
        }*/
    }

   public void endGame(){
       beetleList.remove(0);
       Menu="Mainmenu";
       //buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2,3));
       //buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2+48,1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        update();
        // If the game is running, draw it
        if (Menu.equals("Running")){
            //addbackground();
            for(Background bbackground: backgroundList){
                bbackground.onDraw(canvas);
            }
            for(Beetle bbeetle: beetleList) {
                bbeetle.onDraw(canvas);
            }

            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(32);
            canvas.drawText("Distância: " +  pontos/10 + " cm ", 0, 40, textpaint);
        }
        // If the menu is Main menu, draw the button
        if (Menu.equals("Mainmenu")) {

            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(32);
            canvas.drawText("Iniciar Jogo", canvas.getWidth()/2-100, canvas.getHeight()/2, textpaint);
            /* for(Buttons bbuttons: buttons)
            {
                bbuttons.onDraw(canvas);
            }*/
        }
    }
}