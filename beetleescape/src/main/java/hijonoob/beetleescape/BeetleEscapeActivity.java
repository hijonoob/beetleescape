package hijonoob.beetleescape;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class BeetleEscapeActivity extends Activity {
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onPause(){
        super.onPause();
        gameView.gameLoop.running = false;
        finish();
    }
}