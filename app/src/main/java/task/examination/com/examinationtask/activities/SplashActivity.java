package task.examination.com.examinationtask.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try {
            SystemClock.sleep(1000);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }
}
