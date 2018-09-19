package com.hengzhi.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hengzhi.test.adapter.FxAdapter;
import com.hengzhi.test.fragment.BackupFragment;
import com.hengzhi.test.fragment.FavorFragment;
import com.hengzhi.test.fragment.MusicFragment;
import com.hengzhi.test.fragment.VisibilityFragment;
import com.hengzhi.test.utils.MessageEvent;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.hengzhi.test.utils.Constants.STATE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnve)
    BottomNavigationViewEx mBnve;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    List<Pair<String, Fragment>> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_fab);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);



        StatusBarUtil.setColor(this,getResources().getColor(android.R.color.tab_indicator_text));
        mBnve.enableItemShiftingMode(false);
        mBnve.enableShiftingMode(false);
        mBnve.enableAnimation(false);
        initTab();
        mVp.addOnPageChangeListener(new VPPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position >= 2)
                    position++;
                mBnve.setCurrentItem(position);
            }
        });
        // TODO: 2018/9/19  
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Center", Toast.LENGTH_SHORT).show();
            }
        });
        addBadgeAt(0, 5);
        addBadgeAt(1, 2);
        addBadgeAt(2, -1);
        addBadgeAt(3, -1);
        addBadgeAt(4, 1);
        mBnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_music:
                        position = 0;
                        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(android.R.color.tab_indicator_text));
                        break;
                    case R.id.i_backup:
                        position = 1;
                        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(android.R.color.holo_blue_dark));
                        break;
                    case R.id.i_favor:
                        position = 2;
                        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(android.R.color.holo_orange_light));
                        break;
                    case R.id.i_visibility:
                        position = 3;
                        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(android.R.color.widget_edittext_dark));
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if (previousPosition != position) {
                    mVp.setCurrentItem(position, false);
                    previousPosition = position;
                }
                return true;
            }
        });

    }

    private Badge addBadgeAt(int position, int number) {
        // add badge
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(12, 2, true)
                .bindTarget(mBnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                            Toast.makeText(getApplicationContext(), R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTab() {
        mItems.add(new Pair<String, Fragment>(getString(R.string.music), new MusicFragment()));
        mItems.add(new Pair<String, Fragment>(getString(R.string.backup), new BackupFragment()));
        mItems.add(new Pair<String, Fragment>(getString(R.string.favor), new FavorFragment()));
        mItems.add(new Pair<String, Fragment>(getString(R.string.visibility), new VisibilityFragment()));
        mVp.setAdapter(new FxAdapter(getSupportFragmentManager(), mItems));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void getCode(MessageEvent event) {
        switch (event.mCode) {
            case STATE:

                break;
        }
    }
}
