package hijonoob.beetleescape;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    SurfaceHolder holder;

    // Velocidades de movimento no eixo X e Y
    public static int globalxSpeed;
    public static int globalySpeed;
    // Variáveis de pontuação máxima
    public static int pontosMax = 0;
    public static int nivelCimaMax = 0;
    public static int nivelBaixoMax = 0;
    public static int barreirasDestruidasMax = 0;
    // Strings das pontuações máximas
    private String pontosMaxS = "pontos";
    private String nivelCimaMaxS = "nivelCima";
    private String nivelBaixoMaxS = "nivelBaixo";
    private String barreirasDestruidasMaxS = "barreirasDestruidas";
    // Bitmaps
    Bitmap background;
    Bitmap beetlebmp;
    Bitmap barreirabmp;
    Bitmap buracobmp;
    Bitmap menubmp;
    Bitmap pausabmp;
    Bitmap estatbmp;
    // variáveis do jogo
    boolean nivelando = false;
    boolean menor;
    boolean maior;
    int contaNivel;
    int buracoNivel;
    int barreirasDestruidas;
    int pontos = 0;
    int contadorx = 0;
    int contadorBarreira;
    int contadorBuraco;
    int alturaBarreira = 1;
    int alturaBuraco = 1;
    int niveis;
    int nivelCima;
    int nivelBaixo;
    // Geradores randômicos
    final Random barreiraRandom = new Random();
    final Random buracoRandom = new Random();
    // Listas de objetos de classes
    private List<Background> backgroundList = new ArrayList<Background>();
    private List<Beetle> beetleList = new ArrayList<Beetle>();
    private List<Barreira> barreiraList = new ArrayList<Barreira>();
    private List<Buraco> buracoList = new ArrayList<Buraco>();
    // Variáveis de classes
    private Furia barraFuria;
    // Configurações de Preferências
    private static SharedPreferences prefs;
    // TAG que controla estados
    private String Menu = "Mainmenu";
    public SoundPool sPool;
    int pulaSomID;
    int furiaSomID;
    int morteSomID;
    int quebraPedraSomID;

    public GameView(final Context context) {
        super(context);
        // Recuperando dados salvas nas configurações de preferências do jogo ao iniciar
        prefs = context.getSharedPreferences("hijonoob.beetleescape",Context.MODE_PRIVATE);
        String spackage ="hijonoob.beetleescape";
        pontosMax= prefs.getInt(pontosMaxS , 0);
        nivelCimaMax= prefs.getInt(nivelCimaMaxS , 0);
        nivelBaixoMax= prefs.getInt(nivelBaixoMaxS , 0);
        barreirasDestruidasMax= prefs.getInt(barreirasDestruidasMaxS , 0);
        sPool = new SoundPool(3,AudioManager.STREAM_MUSIC,0);
        pulaSomID = sPool.load(this.getContext(), R.drawable.pulo, 1);
        furiaSomID = sPool.load(this.getContext(), R.drawable.furious, 1);
        morteSomID = sPool.load(this.getContext(), R.drawable.morreu, 1);
        quebraPedraSomID = sPool.load(this.getContext(), R.drawable.pedraquebrou, 1);
        // preparando loop
        gameLoop = new GameLoop(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceDestroyed(SurfaceHolder arg0) {
                // Salvando os dados nas configurações de preferências do jogo ao sair
                prefs.edit().putInt(pontosMaxS,pontosMax).commit();
                prefs.edit().putInt(nivelCimaMaxS,nivelCimaMax).commit();
                prefs.edit().putInt(nivelBaixoMaxS,nivelBaixoMax).commit();
                prefs.edit().putInt(barreirasDestruidasMaxS,barreirasDestruidasMax).commit();
                gameLoop.running = false;
            }

            public void surfaceCreated(SurfaceHolder arg0) {
                // Iniciar loop
                gameLoop.setRunning();
                gameLoop.start();
            }

            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                //
            }
        });

        // Vinculando imagens às suas variáveis
        background = BitmapFactory.decodeResource(getResources(), R.drawable.cenarioclaro);
        beetlebmp = BitmapFactory.decodeResource(getResources(), R.drawable.beetlesprite);
        barreirabmp = BitmapFactory.decodeResource(getResources(), R.drawable.barreira);
        buracobmp = BitmapFactory.decodeResource(getResources(), R.drawable.buraco);
        menubmp = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        pausabmp = BitmapFactory.decodeResource(getResources(), R.drawable.pausa);
        estatbmp = BitmapFactory.decodeResource(getResources(), R.drawable.estat);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        // Botões do menu
        if (Menu.equals("Mainmenu")) {
            if (e.getY() > this.getHeight()/3*2 && e.getX() > this.getWidth()/3*2) {
                Menu="Estatistica";
            } else {
                startGame();
            }
        // Botões de estatísticas
        } else if (Menu.equals("Estatistica")) {
            Menu="Mainmenu";
        // Botões durante o jogo
        } else if (Menu.equals("Running")) {
            if (e.getY()<this.getHeight()/5 && e.getX() < this.getWidth()/10){
                Menu = "Pausa";
            } else if (e.getY() < this.getHeight() * 0.75) {
                if(!beetleList.get(0).returnJumping()){
                    sPool.play(pulaSomID,0.5f,0.5f,1,0,1);
                }
            } else {
                if(barraFuria.returnFuria()==1) {
                    sPool.play(furiaSomID,1,1,1,0,1);
                    barraFuria.comecaFuria();
                }
            }
            for(Beetle bbeetle: beetleList){
                bbeetle.ontouch(e.getY());
            }
        // Botões de pausa
        } else if (Menu.equals("Pausa")) {
            if (e.getY() < this.getHeight()/2) {
                Menu = "Running";
            } else {
                endGame();
            }
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
            //
        }*/
    }

    public void testaNivel(){
        if(nivelando) {
            if (contaNivel<30) {
                if (contaNivel < 6) {
                    if (buracoNivel == 0) {
                        globalySpeed += this.getHeight() / 9;
                    } else if (buracoNivel == 1) {
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
            int backgroundy = backgroundList.get(i).returnY();
            if (backgroundx <= -this.getWidth() * 2) {
                backgroundList.get(i).setX(this.getWidth()*2 + (this.getWidth()*2 + backgroundx));
            }
            if (niveis >= 3) {
                menor = true;
                for (int j = backgroundList.size()-1;j >= 0; j--) {
                    if(backgroundy<backgroundList.get(j).returnY()){
                        menor=false;
                    }
                }
                if (menor) {
                    backgroundList.get(i).setY(0);
                    if (i % 2 == 0) {
                        backgroundList.get(i + 1).setY(0);
                    } else {
                        backgroundList.get(i - 1).setY(0);
                    }
                    niveis = 0;
                }
            } else if (niveis <= -3) {
                maior = true;
                for (int j = backgroundList.size()-1;j >= 0; j--) {
                    if(backgroundy>backgroundList.get(j).returnY()){
                        maior=false;
                    }
                }
                if (maior) {
                    backgroundList.get(i).setY(1);
                    if (i % 2 == 0) {
                        backgroundList.get(i + 1).setY(1);
                    } else {
                        backgroundList.get(i - 1).setY(1);
                    }
                    niveis = 0;
                }
            }
        }

        // remove barreiras que já passaram do jogador
        for (int i = barreiraList.size()-1;i >= 0; i--) {
            int barreirax = barreiraList.get(i).returnX();
            if (barreirax <= -this.getWidth() * 2) {
                barreiraList.remove(i);
            }
        }

        // remove buracos que já passaram do jogador
        for (int i = buracoList.size()-1;i >= 0; i--) {
            int buracox = buracoList.get(i).returnX();
            if (buracox <= -this.getWidth() * 2) {
                buracoList.remove(i);
            }
        }

    }
   public void startGame(){
       // Coloca todos os valores no padrão
       // para uma nova partida
       contaNivel = 0;
       nivelando=false;
       contadorBarreira = 0;
       contadorBuraco = 0;
       barreirasDestruidas = 0;
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
       Menu="Running";  // coloca em modo de jogo
       addbackground(); // adiciona os fundos
       beetleList.add(new Beetle(this,beetlebmp)); // adiciona o besouro
       barraFuria = new Furia(this,beetlebmp);
    }

    public void criaBarreira() {
        if (contadorBarreira>2000) {
            if (!nivelando) { // apenas criamos se não estiver subindo ou descendo para não gerar em posição intermediária
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

       // Compara pontuações da partida com as melhores
       if (pontos > pontosMax) {
           pontosMax = pontos;
       }
       if (nivelCima > nivelCimaMax) {
           nivelCimaMax = nivelCima;
       }
       if (nivelBaixo > nivelBaixoMax) {
           nivelBaixoMax = nivelBaixo;
       }
       if (barreirasDestruidas > barreirasDestruidasMax) {
           barreirasDestruidasMax = barreirasDestruidas;
       }
       // remove o besouro
       beetleList.remove(0);
       // remove todas as barreiras
       for(int i = 0; i < barreiraList.size(); i++) {
           barreiraList.remove(i);
       }
       // remove todos os buracos
       for(int i = 0; i < buracoList.size(); i++) {
           buracoList.remove(i);
       }
       // remove todos os fundos
       for(int i = 0; i < backgroundList.size(); i++) {
           backgroundList.remove(i);
       }
       // exibe as estatísticas do jogo
       Menu="Estatistica";
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
                        if (barraFuria.returnFuria()==2){
                            barreiraList.remove(i);
                            barreirasDestruidas++;
                            sPool.play(quebraPedraSomID,1,1,1,0,1);
                            break;
                        } else {
                            sPool.play(morteSomID,1,1,1,0,1);
                            endGame();
                            break;
                        }

                    }
                }
            }

            for(int i = 0; i < buracoList.size(); i++) {
                buracoList.get(i).onDraw(canvas);
                if (beetleList.size()>0) {
                    Rect beetler = beetleList.get(0).GetBounds();
                    Rect buracor = buracoList.get(i).GetBounds();
                    if (buracoList.get(i).checkCollision(beetler, buracor)) {
                        // Se não estiver mudando de nível
                        if(!nivelando) {
                            // se o buraco estiver acima da metade da tela é o superior = 0
                            // se o buraco estiver abaixo da metade da tela é o inferior = 1
                            if (buracoList.get(i).returnHeight() < this.getHeight()/2){
                                    buracoNivel = 0;
                            } else {
                                    buracoNivel = 1;
                            }
                            if (buracoNivel==0) {
                                // acrescentamos 1 no controle de nível
                                niveis += 1;
                                // acrescentamos 1 no controle de níveis subidos
                                nivelCima++;
                            } else if (buracoNivel==1) {
                                // diminuimos 1 no controle de nível
                                niveis -= 1;
                                // acrescentamos 1 no control de níveis subidos
                                nivelBaixo++;
                            }
                            // está pulando nível
                            nivelando = true;
                        }
                    }
                }
            }

            barraFuria.onDraw(canvas);
            // gera o paint para a fonte
            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            // escreve a distância percorrida
            textpaint.setTextSize(32);
            canvas.drawText("Distância: " +  pontos/10 + " cm ", this.getWidth()/10, this.getHeight()/10, textpaint);
            // escreve o botão de pausa
            textpaint.setTextSize(40);
            canvas.drawText("|| ", 20, this.getHeight()/10, textpaint);
        }
        // Se estiver no menu principal, imprime imagem
        if (Menu.equals("Mainmenu")) {
            Rect menuSrc = new Rect(0,0,menubmp.getWidth(),menubmp.getHeight());
            Rect menuDst = new Rect(0,0,this.getWidth(), this.getHeight());
            // imagem de fundo do menu - já possui os textos
            canvas.drawBitmap(menubmp, menuSrc,menuDst,null);
        }

        if (Menu.equals("Pausa")) {
            Rect pausaSrc = new Rect(0,0,pausabmp.getWidth(),pausabmp.getHeight());
            Rect pausaDst = new Rect(0,0,this.getWidth(), this.getHeight());
            // imagem de fundo da pausa - já possui os textos
            canvas.drawBitmap(pausabmp, pausaSrc,pausaDst,null);
        }

        // se estiver na página de estatísticas imprimir fundo
        if (Menu.equals("Estatistica")) {
            //Rect estatSrc = new Rect(0,0,estatbmp.getWidth(),estatbmp.getHeight());
            //Rect estatDst = new Rect(0,0,this.getWidth(), this.getHeight());
            //canvas.drawBitmap(estatbmp, estatSrc,estatDst,null);

            Rect pausaSrc = new Rect(0,0,estatbmp.getWidth(),estatbmp.getHeight());
            Rect pausaDst = new Rect(0,0,this.getWidth(), this.getHeight());
            // imagem de fundo de estatística - já possui os textos
            canvas.drawBitmap(estatbmp, pausaSrc,pausaDst,null);
            // textos de estatística
            //canvas.drawColor(Color.BLACK);
            Paint textpaint = new Paint();
            textpaint.setColor(Color.WHITE);
            textpaint.setTextSize(40);
            canvas.drawText("NESTA PARTIDA", canvas.getWidth() / 10, canvas.getHeight() / 2 - 72, textpaint);
            textpaint.setTextSize(32);
            canvas.drawText("Distância (mm): " + pontos, canvas.getWidth() / 10, canvas.getHeight() / 2 - 32, textpaint);
            canvas.drawText("Níveis subidos: " + nivelCima, canvas.getWidth()/10, canvas.getHeight()/2, textpaint);
            canvas.drawText("Níveis descidos: " + nivelBaixo, canvas.getWidth()/10, canvas.getHeight()/2+32, textpaint);
            canvas.drawText("Barreiras destruídas: " + barreirasDestruidas, canvas.getWidth()/10, canvas.getHeight()/2+64, textpaint);

            textpaint.setTextSize(40);
            canvas.drawText("RECORDES", canvas.getWidth()/2, canvas.getHeight()/2 - 72, textpaint);
            textpaint.setTextSize(32);
            canvas.drawText("Distância (mm): " + pontosMax, canvas.getWidth()/2, canvas.getHeight()/2-32, textpaint);
            canvas.drawText("Níveis subidos: " + nivelCimaMax, canvas.getWidth()/2, canvas.getHeight()/2, textpaint);
            canvas.drawText("Níveis descidos: " + nivelBaixoMax, canvas.getWidth()/2, canvas.getHeight()/2+32, textpaint);
            canvas.drawText("Barreiras destruídas: " + barreirasDestruidasMax, canvas.getWidth()/2, canvas.getHeight()/2+64, textpaint);
        }
    }
}