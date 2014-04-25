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
    private int altura = 200;
    private int velocidade;
    private int gravidade = 1;
    Paint paint;

    public Beetle(GameView gameview){
        this.gameview = gameview;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    public void ontouch(float y){
        if(y<gameview.getHeight()-60) {
            velocidade -= 10;
        } else {
            velocidade += 10;
        }
    }

    public void update(){
        velocidade += gravidade;
        if(altura < 20){
            if (velocidade < 0) {
                velocidade = 0;
            }
        }
        if (altura > gameview.getHeight()-20){
            if (velocidade > 0) {
                velocidade = 0;
            }
        }
        altura += velocidade;
    }

    public void onDraw(Canvas canvas)
    {
        update();
        canvas.drawCircle(50, altura, 30, paint);
    }

}
