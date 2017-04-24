package lunacat.me.myapplication;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.test.ApiClient;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager(), new String[]{"1", "2", "3"});
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(vpa);
    }
}
