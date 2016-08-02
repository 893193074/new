package com.feicuiedu.gitdroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.hotrepo.HotRepoFragment;
import com.feicuiedu.gitdroid.login.LoginActivity;
import com.feicuiedu.gitdroid.login.UserRepo;
import com.feicuiedu.gitdroid.userinfo.model.UserInfoFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 当前应用主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout; // 抽屉(包含内容+侧滑菜单)
    @BindView(R.id.navigationView)
    NavigationView navigationView; // 侧滑菜单视图

    // 热门仓库Fragment
    private HotRepoFragment hotRepoFragment;
    private UserInfoFragment userInfoFragment;
    private Button btnLogin;
    private ImageView ivIcon;

    private ActivityUtils activityUtils;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        // 设置当前视图(也就是说，更改了当前视图内容,将导至onContentChanged方法触发)
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        // ActionBar处理
        setSupportActionBar(toolbar);
        // 设置navigationView的监听器
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(// 构建抽屉的监听
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();// 根据drawerlayout同步其当前状态
        // 设置抽屉监听
        drawerLayout.setDrawerListener(toggle);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);

//      set
//        View headerView = navigationView.inflateHeaderView(R.layout.layout_nav_header_main);
//        btnLogin = (Button)headerView.findViewById(R.id.btnLogin);
//        ivIcon = (ImageView) headerView.findViewById(R.id.ivIcon);
//       add
//
//        drawerLayout.addDrawerListener(toggle);
//        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
//        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("msg", "btn");
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });
        // 默认显示的是热门仓库fragment
        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // 没有授权的话
        if (UserRepo.isEmpty()) {
            btnLogin.setText(R.string.login_github);
            return;
        }
        btnLogin.setText(R.string.switch_account);
        // 设置Title
        getSupportActionBar().setTitle(UserRepo.getUser().getName());
        // 设置用户头像
        String photoUrl = UserRepo.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl, ivIcon);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.feicuiedu.gitdroid/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    // 侧滑菜单监听器
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 将默认选中项“手动”设置为false
        if (item.isChecked()) {
            item.setChecked(false);
        }
        // 根据选择做切换
        switch (item.getItemId()) {
            // 热门仓库
            case R.id.github_hot_repo:
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;
            case R.id.github_hot_coder:
                if (userInfoFragment == null) userInfoFragment = new UserInfoFragment();
                if (!userInfoFragment.isAdded()) {
                    replaceFragment(userInfoFragment);
                }
                break;
        }
        // 关闭drawerLayout
        drawerLayout.post(new Runnable() {
            @Override public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        // 返回true，代表将该菜单项变为checked状态
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.feicuiedu.gitdroid/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}