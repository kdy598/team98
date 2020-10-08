package com.example.team98;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import androidx.viewpager2.adapter.FragmentStateAdapter;
        import androidx.viewpager2.widget.ViewPager2;

        import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Menu menu;
    FragmentStateAdapter pagerAdatper;
    CircleIndicator3 mIndicator;
    ViewPager2 viewPager2;


    Fragment fragment1,fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h);



        fragment1 = new list_frag();
        fragment2 = new Homefrag();
        bottomNavigationView = findViewById(R.id.navigation);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.home);

    }// onCreate()..


    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.list:
                    menuItem.setIcon(R.drawable.ic_content_paste_black_24dp);    // 선택한 이미지 변경
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_content_paste_black_24dp);
                    menu.findItem(R.id.user).setIcon(R.drawable.ic_content_paste_black_24dp);
                    getSupportFragmentManager().beginTransaction().replace(R.id.m2frame,fragment1).commitAllowingStateLoss();
                    break;

                case R.id.home:
                    menuItem.setIcon(R.drawable.ic_content_paste_black_24dp);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_content_paste_black_24dp);
                    menu.findItem(R.id.user).setIcon(R.drawable.ic_content_paste_black_24dp);
                    getSupportFragmentManager().beginTransaction().replace(R.id.m2frame,fragment2).commitAllowingStateLoss();
                    break;

                case R.id.user:
                    menuItem.setIcon(R.drawable.ic_content_paste_black_24dp);    // 선택한 이미지 변경
                    menu.findItem(R.id.list).setIcon(R.drawable.ic_content_paste_black_24dp);
                    menu.findItem(R.id.home).setIcon(R.drawable.ic_content_paste_black_24dp);
                    Intent intent = new Intent(HomeActivity.this,Catlistactivity.class);
                    startActivity(intent);
                    //액티비티 전환 애니메이션 설정하는 부분
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
            }// switch()..
            return true;
        }
    }// ItemSelectedListener class..


}// MainActivity class..