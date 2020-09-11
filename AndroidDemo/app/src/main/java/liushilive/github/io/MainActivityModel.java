package liushilive.github.io;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityModel extends ViewModel {

    private final MutableLiveData<String> toolbarTitle = new MutableLiveData<>();

    public LiveData<String> getToolbar() {
        return toolbarTitle;
    }

    public void setToolbar(String title) {
        toolbarTitle.setValue(title);
    }

}

