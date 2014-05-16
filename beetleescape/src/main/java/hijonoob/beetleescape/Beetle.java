package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * Created by dbarbato on 24/04/14.
 * Hijonoob
 */
public class Beetle {

    GameView gameview;
    private int altura;
    private int velocidade;
    private int gravidade = 1;
    private boolean jumping = false;

    private int mcurrentFrame = 0;

    private Bitmap bmp;
    private int width;
    private int height;
    private int colunas = 4;
    Paint paint;

    MediaPlayer player;

    public Beetle(GameView gameview, Bitmap bmp){
        this.gameview = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth()/colunas;
        this.height = bmp.getHeight();
        this.altura = (int) (gameview.getHeight() * 0.75);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        //player = MediaPlayer.create(gameview.getContext(),R.drawable.pulo);
    }

    public void ontouch(float y){
        if (!jumping) {
            if (y < gameview.getHeight() * 0.75) {
                velocidade -= 50;
                //player.start();
            }/* else {
                velocidade += 10;
                gameview.pontos = 0;
            }*/
            jumping = true;
        }
    }

    public void switchanimations(){
        mcurrentFrame++;
        if (mcurrentFrame >= colunas) {
            mcurrentFrame = 0;
        }
    }

    public void update(){
        switchanimations();
        velocidade += gravidade;

        if(altura < gameview.getHeight()*0.25 + height/3){
            if (velocidade < 0) {
                velocidade = (velocidade * -1) / 2;
            }
        }
        if (altura > gameview.getHeight() * 0.82){
            if (velocidade > 0) {
                velocidade = (velocidade * -1) / 5;
            }
            jumping = false;
        }
        altura += velocidade;
    }

    public Rect GetBounds()
    {
        return new Rect(50,altura-height/2,(50 + (width / 2)), altura);
    }

    public int returnY() {
        return altura;
    }

    public boolean returnJumping() {
        return jumping;
    }

    public void onDraw(Canvas canvas)
    {
        update();
        int srcX = mcurrentFrame*width;
        int srcY = 0;
        Rect src = new Rect(srcX,srcY,srcX + width,srcY+height);
        Rect dst = new Rect(50,altura-height/2,(50 + (width / 2)), altura);
        canvas.drawBitmap(bmp,src,dst,null);
    }

}
