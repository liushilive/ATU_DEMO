package liushilive.github.io.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import liushilive.github.io.R;

import static android.view.View.OnLongClickListener;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class HomeFragment extends Fragment implements OnClickListener, OnLongClickListener, OnSeekBarChangeListener {

    private HomeViewModel homeViewModel;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView editText_name = root.findViewById(R.id.editText_name);
        final TextView textView_birthdate = root.findViewById(R.id.textView_birthdate);
        final TextView textView_years_of_working = root.findViewById(R.id.textView_years_of_working);
        final TextView self_score = root.findViewById(R.id.textView_self_score);
        final TextView textView_time = root.findViewById(R.id.textView_time);

        homeViewModel.getUserModeLiveData().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(@NonNull UserModel userModel) {
                editText_name.setText(userModel.name);
                textView_birthdate.setText(userModel.birthdate);
                textView_years_of_working.setText(userModel.years_of_working + " 年");
                self_score.setText(userModel.self_score + " 分");
                textView_time.setText(userModel.time);
            }
        });

        root.findViewById(R.id.button_date_selection).setOnClickListener(this);
        root.findViewById(R.id.button_time).setOnClickListener(this);
        root.findViewById(R.id.button_crash).setOnClickListener(this);
        root.findViewById(R.id.button_not_responding).setOnClickListener(this);
        root.findViewById(R.id.button_permission).setOnClickListener(this);

        root.findViewById(R.id.button_long_press).setOnLongClickListener(this);

        SeekBar seekBar1 = root.findViewById(R.id.seekBar_years_of_working);
        seekBar1.setOnSeekBarChangeListener(this);
        SeekBar seekBar2 = root.findViewById(R.id.seekBar_score);
        seekBar2.setOnSeekBarChangeListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_date_selection:
                showDatePickerDialog();
                break;
            case R.id.button_time:
                showTimePickerDialog();
                break;
            case R.id.button_crash:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case R.id.button_not_responding:
                SystemClock.sleep(20 * 1000);
                break;
            case R.id.button_permission:
                request_permissions();
                break;
            default:
                break;
        }
    }

    // 请求多个权限
    private void request_permissions() {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CALENDAR);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_CALENDAR);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.GET_ACCOUNTS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CALL_PHONE);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CALL_LOG);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_CALL_LOG);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.USE_SIP) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.USE_SIP);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.BODY_SENSORS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.SEND_SMS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECEIVE_SMS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_SMS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.RECEIVE_WAP_PUSH) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECEIVE_WAP_PUSH);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.RECEIVE_MMS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECEIVE_MMS);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            Toast.makeText(getContext(), "共计申请 " + permissionList.size() + " 个权限", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    permissionList.toArray(new String[permissionList.size()]),
                    1002);
        } else {
            Toast.makeText(getContext(), "权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.button_long_press) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("已接收到长按操作");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return false;

    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                UserModel userModel = homeViewModel.getUserModeLiveData().getValue();
                Objects.requireNonNull(userModel).time = String.format("%02d:%02d", hourOfDay, minute);
                homeViewModel.setUserModelLiveData(userModel);
            }
        }, hour, minute, true);
        timePicker.setTitle("选择时间");
        timePicker.show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                UserModel userModel = homeViewModel.getUserModeLiveData().getValue();
                Objects.requireNonNull(userModel).birthdate = String.format("%04d年%02d月%02d日", year, month + 1, dayOfMonth);
                homeViewModel.setUserModelLiveData(userModel);
            }
        }, year, month, dayOfMonth);
        datePicker.setTitle("选择日期");
        datePicker.show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        UserModel userModel = homeViewModel.getUserModeLiveData().getValue();
        switch (seekBar.getId()) {
            case R.id.seekBar_years_of_working:
                Objects.requireNonNull(userModel).years_of_working = progress;
                break;
            case R.id.seekBar_score:
                Objects.requireNonNull(userModel).self_score = progress;
                break;
            default:
                break;
        }
        homeViewModel.setUserModelLiveData(userModel);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}