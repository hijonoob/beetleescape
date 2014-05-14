package hijonoob.beetleescape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    public static int width;
    public static int height;
    private GameView gameview;
    private Bitmap bmp;
    private int x;
    private int y;

    public Background(GameView gameview, Bitmap bmp, int x, int y){
        this.gameview = gameview;
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        width = bmp.getWidth();
        height = bmp.getHeight();
    }

    public void update(){
        x -= gameview.globalxSpeed;
    }

    public int returnX(){
        return x;
    }

    //public int returnY() { return y; }

    public void setY(int topo) {
        if (topo == 1){
            y = y-gameview.getHeight()*6;
        } else {
            y = y+gameview.getHeight()*6;
        }
}

    public void setX(int x) {
        this.x = x;
    }


    public void onDraw(Canvas canvas)
    {
        update();

        Rect src = new Rect(0,0,width,height);
        Rect dst = new Rect(x,-gameview.getHeight()/2 + gameview.globalySpeed+y,gameview.getWidth()*2+x, gameview.getHeight()+gameview.getHeight()/2 + gameview.globalySpeed + y);
        canvas.drawBitmap(bmp,src,dst,null);
    }
}