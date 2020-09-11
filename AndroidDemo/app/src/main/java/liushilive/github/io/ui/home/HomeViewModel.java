package liushilive.github.io.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<UserModel> userModelLiveData;

    public HomeViewModel() {
        UserModel userModel = new UserModel();
        userModelLiveData = new MutableLiveData<>();
        userModelLiveData.setValue(userModel);
    }

    public LiveData<UserModel> getUserModeLiveData() {
        return userModelLiveData;
    }

    public void setUserModelLiveData(UserModel userModel) {
        userModelLiveData.setValue(userModel);
    }
}

