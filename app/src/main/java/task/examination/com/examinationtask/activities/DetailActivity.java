package task.examination.com.examinationtask.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import task.examination.com.examinationtask.R;

import task.examination.com.examinationtask.fragments.DetailFragment;

public class DetailActivity  extends AppCompatActivity
{
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // выход из активности при горизонтальной ориентации устройства
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }

        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String url = extras.getString(EXTRA_URL);
            DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            detailFragment.setText(url);
        }
    }


} // class DetailActivity
