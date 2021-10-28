package pollub.pam.myfrags;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentDataModel extends ViewModel {
    public final MutableLiveData<Integer> counter = new MutableLiveData<>(0);
}
