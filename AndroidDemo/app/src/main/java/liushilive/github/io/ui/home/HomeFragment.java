package liushilive.github.io.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import liushilive.github.io.R;

import static android.view.View.OnLongClickListener;

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
            default:
                break;
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
                Objects.requireNonNull(userModel).birthdate = String.format("%04d年%02d月%02d日", year, month, dayOfMonth);
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