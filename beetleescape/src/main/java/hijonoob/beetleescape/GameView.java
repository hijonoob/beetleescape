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

    int xx = 0;
    int pontos = 0;
    int contadorx = 0;

    private List<Background> backgroundList = new ArrayList<Background>();
    private List<Beetle> beetleList = new ArrayList<Beetle>();
    //private Beetle beetlePlayer;
    //private static SharedPreferences prefs;

    private String Menu = "Running";

    public GameView(Context context) {
        super(context);
        //beetlePlayer = new Beetle(this);
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
        beetleList.add(new Beetle(this,beetlebmp));
        //player.add(new Player(this,playerbmp,50,50));
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){

        for(Beetle bbeetle: beetleList){
            bbeetle.ontouch(e.getY());
        }
        //for(Player pplayer: player)
        //{
        //    pplayer.ontouch();
        //}
        //beetle.ontouch(e.getY());
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
            pontos++;
        }
    }

    public void updatetimers(){
        // enum
        contadorx++;
        if(contadorx >= 100) {
            globalxSpeed++;
            contadorx = 0;
        }

        if (Menu.equals("Running")){
            deletebackground();
        }
    }


    public void addbackground(){

        while(xx < 2*this.getWidth())
        {
            backgroundList.add(new Background(this,background,xx,0));
            xx += this.getWidth();
        }

    }

    public void deletebackground(){

        for (int i = backgroundList.size()-1;i >= 0; i--)
        {
            int backgroundx = backgroundList.get(i).returnX();

            if (backgroundx<=-this.getWidth()){
                backgroundList.remove(i);
                backgroundList.add(new Background(this,background,backgroundx+2*this.getWidth(),0));
            }
        }

    }
 /*   public void startGame(){
        for(int i = 0; i < buttons.size(); i++){
            buttons.remove(i);
        }
        //player.add(new Player(this,playerbmp,50,50));
    }*/

  /*  public void endGame(){
        Menu="Mainmenu";
        buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2,3));
        buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2+48,1));

        //player.remove(0);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        update();
        // If the menu is Main menu, draw the button
 /*       if (Menu.equals("Mainmenu"))
        {
            for(Buttons bbuttons: buttons)
            {
                bbuttons.onDraw(canvas);
            }
        }*/
        // If the game is running, draw it
        if (Menu.equals("Running")){
            addbackground();

            for(Background bbackground: backgroundList){
                bbackground.onDraw(canvas);
            }
            for(Beetle bbeetle: beetleList) {
                bbeetle.onDraw(canvas);
            }
            //beetlePlayer.onDraw(canvas);

            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(32);
            canvas.drawText("Pontuação: " +  pontos/10, 0, 40, textpaint);


            //for(Player pplayer: player)
            //{
            //    pplayer.onDraw(canvas);
            //}
        }
        if (Menu.equals("Mainmenu")) {
            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(32);
            canvas.drawText("arrgh2 ", canvas.getWidth()/2, canvas.getHeight()/2, textpaint);
        }
    }
}