package hijonoob.beetleescape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    SurfaceHolder holder;

    public static int globalxSpeed;
    public static int globalySpeed;

    boolean nivelando = false;
    int contaNivel;
    int buracoNivel;

    Bitmap background;
    Bitmap beetlebmp;
    Bitmap barreirabmp;
    Bitmap buracobmp;
    Bitmap menubmp;

    //int xx = 0;
    int pontos = 0;
    int contadorx = 0;
    int contadorBarreira;
    int contadorBuraco;
    int alturaBarreira = 1;
    int alturaBuraco = 1;

    int niveis;
    int nivelCima;
    int nivelBaixo;

    final Random barreiraRandom = new Random();
    final Random buracoRandom = new Random();

    private List<Background> backgroundList = new ArrayList<Background>();
    private List<Beetle> beetleList = new ArrayList<Beetle>();
    private List<Barreira> barreiraList = new ArrayList<Barreira>();
    private List<Buraco> buracoList = new ArrayList<Buraco>();
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
        barreirabmp = BitmapFactory.decodeResource(getResources(), R.drawable.barreira);
        buracobmp = BitmapFactory.decodeResource(getResources(), R.drawable.buraco);
        menubmp = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){

        for(Beetle bbeetle: beetleList){
            bbeetle.ontouch(e.getY());
        }

        if (Menu.equals("Mainmenu")) {
            startGame();
        }
        return false;
    }

    public void update(){
        if(Menu.equals("Running")){
            updatetimers();
            pontos+= globalxSpeed;
            contadorBarreira+=globalxSpeed;
            contadorBuraco+=globalxSpeed;
            criaBarreira();
            testaNivel();
        }
        /*if(Menu.equals("Mainmenu")) {
            // TODO
            //updatetimers();
        }*/
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
        /*if(Menu.equals("Mainmenu")) {
            //TODO
        }*/
    }

    public void testaNivel(){
        if(nivelando) {
            if (contaNivel<30) {
                if (contaNivel < 6) {
                    if (buracoNivel == 0) {
                        globalySpeed += this.getHeight() / 9;
                    } else if (buracoNivel ==1) {
                        globalySpeed -= this.getHeight() / 9;
                    }
                }
                contaNivel++;
            } else {
                nivelando = false;
                contaNivel=0;
            }
        }
    }

    public void addbackground(){
        backgroundList.add(new Background(this,background,0,-this.getHeight()*2));
        backgroundList.add(new Background(this,background,this.getWidth()*2,-this.getHeight()*2));
        backgroundList.add(new Background(this,background,0,0));
        backgroundList.add(new Background(this,background,this.getWidth()*2,0));
        backgroundList.add(new Background(this,background,0,this.getHeight()*2));
        backgroundList.add(new Background(this,background,this.getWidth()*2,this.getHeight()*2));
    }

    public void movebackground(){

        for (int i = backgroundList.size()-1;i >= 0; i--) {
            int backgroundx = backgroundList.get(i).returnX();
            //int backgroundy = backgroundList.get(i).returnY();
            if (backgroundx <= -this.getWidth() * 2) {
                // alterando posição x do fundo para contar com o X existente
                backgroundList.get(i).setX(this.getWidth()*2 + (this.getWidth()*2 + backgroundx));
            }

            if (niveis >= 2) {
                backgroundList.get(i).setY(0);
                // TODO verificar essa lógica, há algo errado
                if(i%2==0){
                    backgroundList.get(i+1).setY(0);
                } else {
                    backgroundList.get(i-1).setY(0);
                }
                niveis = 0;
            } else if (niveis <= -2) {
                backgroundList.get(i).setY(1);
                if(i%2==0){
                    backgroundList.get(i+1).setY(1);
                } else {
                    backgroundList.get(i-1).setY(1);
                }
                niveis = 0;
            }
        }

        for (int i = barreiraList.size()-1;i >= 0; i--) {
            int barreirax = barreiraList.get(i).returnX();
            if (barreirax <= -this.getWidth() * 2) {
                barreiraList.remove(i);
            }
        }

        for (int i = buracoList.size()-1;i >= 0; i--) {
            int buracox = buracoList.get(i).returnX();
            if (buracox <= -this.getWidth() * 2) {
                buracoList.remove(i);
            }
        }

    }
   public void startGame(){
       contaNivel = 0;
       nivelando=false;
       contadorBarreira = 0;
       contadorBuraco = 0;
       buracoNivel = 0;
       pontos = 0;
       globalxSpeed = 20;
       globalySpeed = 0;
       niveis = 0;
       nivelBaixo = 0;
       nivelCima = 0;
       contadorx = 0;
       alturaBarreira = 1;
       alturaBuraco = 1;
       Menu="Running";
       addbackground();
       beetleList.add(new Beetle(this,beetlebmp));
    }

    public void criaBarreira() {
        if (contadorBarreira>2000) {
            if (!nivelando) {
                alturaBarreira = barreiraRandom.nextInt(3);
                barreiraList.add(new Barreira(this, barreiraRandom.nextInt(this.getWidth()) + this.getWidth() * 2, alturaBarreira, barreirabmp));
                contadorBarreira = 0;
            }
        }
        if (contadorBuraco>1000) {
            if (!nivelando) {
                alturaBuraco = buracoRandom.nextInt(2);
                buracoList.add(new Buraco(this, buracoRandom.nextInt(this.getWidth()) + this.getWidth() * 2, alturaBuraco, buracobmp));
                contadorBuraco = 0;
            }
        }
    }

   public void endGame(){
       beetleList.remove(0);
       for(int i = 0; i < barreiraList.size(); i++) {
           barreiraList.remove(i);
       }
       for(int i = 0; i < buracoList.size(); i++) {
           buracoList.remove(i);
       }
       for(int i = 0; i < backgroundList.size(); i++) {
           backgroundList.remove(i);
       }
       Menu="Mainmenu";
       //buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2,3));
       //buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2+48,1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        update();
        if (Menu.equals("Running")){
            canvas.drawColor(Color.BLACK);
            for(Background bbackground: backgroundList){
                bbackground.onDraw(canvas);
            }
            for(Beetle bbeetle: beetleList) {
                bbeetle.onDraw(canvas);
            }

            for(int i = 0; i < barreiraList.size(); i++) {
                barreiraList.get(i).onDraw(canvas);
                if (beetleList.size()>0) {
                    Rect beetler = beetleList.get(0).GetBounds();
                    Rect spikesr = barreiraList.get(i).GetBounds();
                    if (barreiraList.get(i).checkCollision(beetler, spikesr)) {
                        endGame();
                        break;
                    }
                }
            }

            for(int i = 0; i < buracoList.size(); i++) {
                buracoList.get(i).onDraw(canvas);
                if (beetleList.size()>0) {
                    Rect beetler = beetleList.get(0).GetBounds();
                    Rect buracor = buracoList.get(i).GetBounds();
                    if (buracoList.get(i).checkCollision(beetler, buracor)) {
                        if(!nivelando) {
                            if (beetleList.get(0).returnY() > (this.getHeight()/4)*3){
                                buracoNivel = 1;
                            } else {
                                buracoNivel = 0;
                            }
                            if (buracoNivel==0) {
                                niveis += 1;
                                nivelCima++;
                            } else if (buracoNivel==1) {
                                niveis -= 1;
                                nivelBaixo++;
                            }
                            nivelando = true;
                        }
                    }
                }
            }

            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(32);
            canvas.drawText("Distância: " +  pontos/10 + " cm ", 0, 40, textpaint);

        }
        // If the menu is Main menu, draw the button
        if (Menu.equals("Mainmenu")) {
            Rect menuSrc = new Rect(0,0,menubmp.getWidth(),menubmp.getHeight());
            Rect menuDst = new Rect(0,0,this.getWidth(), this.getHeight());
            canvas.drawBitmap(menubmp, menuSrc,menuDst,null);
            //Paint textpaint = new Paint();
            //textpaint.setColor(Color.WHITE);
            //textpaint.setTextSize(32);
            //canvas.drawText("Iniciar Jogo", canvas.getWidth()/2-100, canvas.getHeight()/2, textpaint);
        }
    }
}