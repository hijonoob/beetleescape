package hijonoob.beetleescape;

import android.app.Activity;
import android.os.Bundle;

public class BeetleEscapeActivity extends Activity {
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public void onPause(){
        super.onPause();
        gameView.gameLoop.running = false;
        finish();
    }
}