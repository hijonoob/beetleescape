package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by dbarbato on 01/05/14.
 * Hijonoob
 */
public class Buraco {
    GameView gameview;
    Paint paint;
    int x;
    int y;
    int width;
    int height;
    int altura;
    int alturaDesenho;
    int alturaRef;
    private Rect bettler;
    private Rect buracor;
    private Bitmap bmp;

    public Buraco(GameView gameview, int x, int y, Bitmap bmp){
        this.gameview = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = x;
        this.y = y;
        altura = (int) (gameview.getHeight() * 0.75);
        alturaRef = -gameview.globalySpeed;
    }

    public void update(){
        x -= gameview.globalxSpeed;
    }

    public Rect GetBounds() {
        switch (y){
            case 0:
                alturaDesenho = (int) (gameview.getHeight() * 0.25) + alturaRef;
                break;
            case 1:
                alturaDesenho = (int) (gameview.getHeight() * 0.75) + alturaRef;
                break;
            default:
                alturaDesenho = (int) (gameview.getHeight() * 0.75) + alturaRef;
                break;
        }
        return new Rect(x-gameview.getHeight()/3,alturaDesenho-gameview.getHeight()/10 + gameview.globalySpeed,x + gameview.getHeight()/3, alturaDesenho+gameview.getHeight()/10 + gameview.globalySpeed);
    }

    public boolean checkCollision(Rect bettler, Rect buracor){
        this.bettler = bettler;
        this.buracor = buracor;
        return Rect.intersects(bettler, buracor);
    }

    public int returnX(){
        return x;
    }

    /*public int returnY() {
        return y;
    }*/

    public void onDraw(Canvas canvas) {
        update();
        switch (y){
            case 0:
                alturaDesenho = (int) (gameview.getHeight() * 0.18) + alturaRef;
                break;
            case 1:
                alturaDesenho = (int) (gameview.getHeight() * 0.85) + alturaRef;
                break;
            default:
                alturaDesenho = (int) (gameview.getHeight() * 0.85) + alturaRef;
                break;
        }

        Rect src = new Rect(0,0,width,height);
        Rect dst = new Rect(x-gameview.getHeight()/3,alturaDesenho-gameview.getHeight()/10 + gameview.globalySpeed,x + gameview.getHeight()/3, alturaDesenho+gameview.getHeight()/10 + gameview.globalySpeed);
        canvas.drawBitmap(bmp,src,dst,null);

    }

}
