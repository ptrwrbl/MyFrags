package pollub.pam.myfrags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment4 extends Fragment {
    private FragmentDataModel fragmentData;
    private Observer<Integer> dataChangeObserver;

    private EditText textInput;
    private TextWatcher textChangerWatcher;
    private boolean wasWatcherDeactivated;

    public Fragment4() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_top));
        setExitTransition(inflater.inflateTransition(R.transition.slide_bottom));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        textInput = view.findViewById(R.id.editTextNumber);

        fragmentData = new ViewModelProvider(requireActivity()).get(FragmentDataModel.class);

        dataChangeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                wasWatcherDeactivated = true;
                textInput.setText(newInteger.toString());
            }
        };
        fragmentData.counter.observe(getViewLifecycleOwner(), dataChangeObserver);

        textChangerWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!wasWatcherDeactivated) {
                    Integer i;
                    try {
                        i = Integer.parseInt( s.toString() );
                    } catch (NumberFormatException e) {
                        i = fragmentData.counter.getValue();
                    }
                    fragmentData.counter.setValue(i);

                } else {
                    wasWatcherDeactivated = !wasWatcherDeactivated;
                }
            }
        };
        textInput.addTextChangedListener(textChangerWatcher);

        return view;
    }
}