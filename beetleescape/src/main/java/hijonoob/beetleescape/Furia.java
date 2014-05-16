package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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
    private Bitmap bmp;

    public Furia(GameView gameview, Bitmap bmp){
        this.gameview = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public void update(){
        if(encher<=1000){
            Log.i("info", "encher " + encher);
            encher += gameview.globalxSpeed/2;
        } else if (baixar>=0) {
            Log.i("info", "baixar " + baixar);
            baixar -= gameview.globalxSpeed;
        } else {
            encher = 1;
            baixar = 1000;
        }
    }

    public int returnState(){
        if(baixar>=1){
            return 2;
        } else if (encher>=1000){
            return 1;
        } else {
            return 0;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        //Rect src = new Rect(0,0,width,height);
        //Rect dst = new Rect(x-gameview.getHeight()/3,alturaDesenho-gameview.getHeight()/2 + gameview.globalySpeed,x + gameview.getHeight()/3, alturaDesenho+gameview.getHeight()/2 + gameview.globalySpeed);
        //canvas.drawBitmap(bmp,src,dst,null);

        //gameview.getWidth = 100


        Paint paint = new Paint();
        if (encher<=1000) {
            paint.setColor(Color.GREEN);
            largura = gameview.getWidth() - ((gameview.getWidth()*encher)/1000);
            Log.i("info", "largura verde " + largura);
            canvas.drawRect(0,gameview.getHeight()-60,largura,gameview.getHeight(), paint);
        } else {
            paint.setColor(Color.RED);
            largura = (gameview.getWidth()*encher)/1000;
            Log.i("info", "largura vermelho " + largura);
            //canvas.drawRect(0,gameview.getHeight()-60,(gameview.getWidth() / 1000) * baixar, gameview.getHeight(), paint);
            canvas.drawRect(0,gameview.getHeight()-60,gameview.getWidth()/10,gameview.getHeight(), paint);
        }


    }

}
