package hijonoob.beetleescape;

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
    int altura;
    int alturaDesenho;
    private Rect bettler;
    private Rect barreirar;

    public Barreira(GameView gameview, int x, int y){
        this.gameview = gameview;
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
        return new Rect(x,altura,x+gameview.getHeight()/2,altura+gameview.getHeight()/2);
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
                alturaDesenho = 0;
                break;
            case 1:
                alturaDesenho = (int) (gameview.getHeight() * 0.5);
                break;
            case 2:
                alturaDesenho = gameview.getHeight();
                break;
            default:
                alturaDesenho = (int) (gameview.getHeight() * 0.5);
                break;
        }
        canvas.drawCircle(x,alturaDesenho,gameview.getHeight()/4,paint);

    }

}
