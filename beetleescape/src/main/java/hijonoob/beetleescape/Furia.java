package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by dbarbato on 01/05/14.
 * Hijonoob
 */
public class Furia {
    GameView gameview;
    Paint paint;
    int width;
    int height;
    int encher = 1;
    int baixar = 1000;
    int largura;
    boolean emFuria = false;
    boolean barraCheia = false;
    private Bitmap bmp;

    public Furia(GameView gameview, Bitmap bmp){
        this.gameview = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public void update(){
        if (!emFuria && !barraCheia) {
            if (encher <= 1000) {
                encher += gameview.globalxSpeed / 2;
            } else {
                barraCheia = true;
            }
        }
        if (emFuria) {
            if (baixar >= 0) {
                baixar -= gameview.globalxSpeed;
            } else {
                emFuria = false;
                barraCheia = false;
                encher = 1;
                baixar = 1000;
            }
        }
    }

    public void comecaFuria() {
        emFuria = true;
    }

    public int returnFuria(){
        if(emFuria){
            return 2;
        } else if(barraCheia){
            return 1;
        } else {
            return 0;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        Paint paint = new Paint();
        if (encher<=1000) {
            paint.setColor(Color.GREEN);
            paint.setAlpha(40);
            largura = (gameview.getWidth()*encher)/1000;
            canvas.drawRect(0,gameview.getHeight()-60,largura,gameview.getHeight(), paint);
        } else {
            paint.setColor(Color.RED);
            paint.setAlpha(40);
            largura = (gameview.getWidth()*baixar)/1000;
            canvas.drawRect(0,gameview.getHeight()-60,largura,gameview.getHeight(), paint);
        }
    }
}