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
    private int altura = 200;
    private int velocidade;
    private int gravidade = 1;

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
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        player = MediaPlayer.create(gameview.getContext(),R.drawable.efeitosonoro);
    }

    public void ontouch(float y){
        if(y<gameview.getHeight()-60) {
            velocidade -= 10;
        } else {
            velocidade += 10;
        }
        player.start();
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
        if(altura < 20){
            if (velocidade < 0) {
                velocidade = (velocidade * -1) / 2;
            }
        }
        if (altura > gameview.getHeight()-20){
            if (velocidade > 0) {
                velocidade = (velocidade * -1) / 2;
            }
        }
        altura += velocidade;
    }

    public void onDraw(Canvas canvas)
    {
        update();
        //canvas.drawBitmap(bmp, 30, altura, null);
        //canvas.drawCircle(50, altura, 30, paint);

        int srcX = mcurrentFrame*width;
        int srcY = 0;
        Rect src = new Rect(srcX,srcY,srcX + width,srcY+height);
        Rect dst = new Rect(30,altura-height/2,(30 + (width / 2)), altura);
        canvas.drawBitmap(bmp,src,dst,null);
    }

}
