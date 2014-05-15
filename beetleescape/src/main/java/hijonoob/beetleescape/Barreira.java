package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by dbarbato on 01/05/14.
 * Hijonoob
 */
public class Barreira {
    GameView gameview;
    //Paint paint;
    int x;
    int y;
    int width;
    int height;
    int altura;
    int alturaDesenho;
    int alturaRef;
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
        altura = (int) (gameview.getHeight() * 0.75);
        alturaRef = -gameview.globalySpeed;
    }

    public void update(){
        x -= gameview.globalxSpeed;
    }

    public Rect GetBounds() {
        switch (y){
            case 0:
                altura = (int) (-gameview.getHeight()*0.25) + alturaRef;
                break;
            case 1:
                altura = (int) (gameview.getHeight()*0.4) + alturaRef;
                break;
            case 2:
                altura = (int) (gameview.getHeight()*1.1) + alturaRef;
                break;
            default:
                altura = (int) (gameview.getHeight()*0.75) + alturaRef;
                break;
        }

        return new Rect(x-gameview.getHeight()/10,altura + gameview.globalySpeed - gameview.getHeight()/6,x+gameview.getHeight()/10,altura+gameview.getHeight()/3 + gameview.globalySpeed);
    }

    public boolean checkCollision(Rect bettler, Rect barreirar){
        //Log.i("info", String.valueOf(alturaRef));
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
                alturaDesenho = (int) (gameview.getHeight() * -0.15) + alturaRef;
                break;
            case 1:
                alturaDesenho = (int) (gameview.getHeight() * 0.5) + alturaRef;
                break;
            case 2:
                alturaDesenho = (int) (gameview.getHeight() * 1.15) + alturaRef;
                break;
            default:
                alturaDesenho = (int) (gameview.getHeight() * 0.5) + alturaRef;
                break;
        }
        //canvas.drawCircle(x,alturaDesenho,gameview.getHeight()/4,paint);

        Rect src = new Rect(0,0,width,height);
        Rect dst = new Rect(x-gameview.getHeight()/3,alturaDesenho-gameview.getHeight()/2 + gameview.globalySpeed,x + gameview.getHeight()/3, alturaDesenho+gameview.getHeight()/2 + gameview.globalySpeed);
        canvas.drawBitmap(bmp,src,dst,null);

        //Paint paint = new Paint();
        //paint.setColor(Color.GREEN);
        //canvas.drawRect(x-gameview.getHeight()/10,altura + gameview.globalySpeed - gameview.getHeight()/6,x+gameview.getHeight()/10,altura+gameview.getHeight()/3 + gameview.globalySpeed, paint);

    }

}
