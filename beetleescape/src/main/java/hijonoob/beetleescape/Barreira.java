package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by dbarbato on 01/05/14.
 * Hijonoob
 */
public class Barreira {
    GameView gameview;
    Paint paint;
    int x;
    int y;
    int width;
    int height;
    int altura;
    int alturaDesenho;
    private Rect bettler;
    private Rect barreirar;
    private Bitmap bmp;

    public Barreira(GameView gameview, int x, int y, Bitmap bmp){
        this.gameview = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        altura = (int) (gameview.getHeight() * 0.75);
    }

    public void update(){
        x -= gameview.globalxSpeed;
    }

    public Rect GetBounds() {
        switch (y){
            case 0:
                altura = (int) (-gameview.getHeight()*0.25);
                break;
            case 1:
                altura = (int) (gameview.getHeight()*0.75);
                break;
            case 2:
                altura = (int) (gameview.getHeight()*1.25);
                break;
            default:
                altura = (int) (gameview.getHeight()*0.75);
                break;
        }
        return new Rect(x-gameview.getHeight()/16,altura,x+gameview.getHeight()/2,altura+gameview.getHeight()/4);
    }

    public boolean checkCollision(Rect bettler, Rect barreirar){
        this.bettler = bettler;
        this.barreirar = barreirar;
        return Rect.intersects(bettler, barreirar);
    }

    public int returnX(){
        return x;
    }

    public void onDraw(Canvas canvas) {
        update();
        switch (y){
            case 0:
                alturaDesenho = (int) (gameview.getHeight() * -0.15);
                break;
            case 1:
                alturaDesenho = (int) (gameview.getHeight() * 0.5);
                break;
            case 2:
                alturaDesenho = (int) (gameview.getHeight() * 1.15);
                break;
            default:
                alturaDesenho = (int) (gameview.getHeight() * 0.5);
                break;
        }
        //canvas.drawCircle(x,alturaDesenho,gameview.getHeight()/4,paint);

        Rect src = new Rect(0,0,width,height);
        Rect dst = new Rect(x-gameview.getHeight()/3,alturaDesenho-gameview.getHeight()/3,x + gameview.getHeight()/3, alturaDesenho+gameview.getHeight()/2);
        canvas.drawBitmap(bmp,src,dst,null);

    }

}
