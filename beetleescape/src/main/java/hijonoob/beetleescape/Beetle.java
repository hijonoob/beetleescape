package hijonoob.beetleescape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by dbarbato on 24/04/14.
 * Hijonoob
 */
public class Beetle {

    GameView gameview;
    private int altura;
    Paint paint;

    public Beetle(GameView gameview){
        this.gameview = gameview;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    public void ontouch(float y){
        if(y<gameview.getHeight()-60) {
            altura += 10;
        }
        else {
            altura -=20;
        }
    }

    public void update(){
       // checar clique
    }

    public void onDraw(Canvas canvas)
    {
        //update();
        canvas.drawCircle(50, gameview.getHeight()/3 - altura, 30, paint);
    }

}