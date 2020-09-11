package liushilive.github.io;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    public static String url = "";
    private AppBarConfiguration mAppBarConfiguration;
    private MainActivityModel mainActivityModel;

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     */
    private static void hideKeyboard(MotionEvent event, View view, Activity activity) {
        try {
            if (view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(token,
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivityModel = new ViewModelProvider(this).get(MainActivityModel.class);
        mainActivityModel.getToolbar().observe(this, new Observer<String>() { //注册观察者,观察数据的变化
            @Override
            public void onChanged(String title) {
                if (title != null && !title.equals("")) {
                    toolbar.setTitle(title);
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_webview, R.id.nav_dsc).setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            //调用方法判断是否需要隐藏键盘
            hideKeyboard(ev, view, this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void settingClick(MenuItem item) {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("IP")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "IP 不能为空！" + input, Toast.LENGTH_LONG).show();
                        } else {
                            url = input;
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}