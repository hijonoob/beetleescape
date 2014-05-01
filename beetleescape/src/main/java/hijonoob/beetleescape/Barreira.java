package hijonoob.beetleescape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by dbarbato on 01/05/14.
 * Hijonoob
 */
public class Barreira {
    GameView gameview;
    Paint paint;
    int x;
    int altura;

    public Barreira(GameView gameview, int x){
        this.gameview = gameview;
        this.x = x;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        altura = (int) (gameview.getHeight() * 0.75);
    }

    public void update(){
        x -= gameview.globalxSpeed;
    }

    public int returnX(){
        return x;
    }

    public void onDraw(Canvas canvas)
    {
        update();
        canvas.drawCircle(x,gameview.getHeight()/2,gameview.getHeight()/4,paint);
    }

}
