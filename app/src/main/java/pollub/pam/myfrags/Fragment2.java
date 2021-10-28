package pollub.pam.myfrags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    private FragmentDataModel fragmentData;
    private Observer<Integer> dataChangeObserver;

    private TextView dataOutputView;
    private Button increaseButton;

    public Fragment2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_left));
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        dataOutputView = (TextView) view.findViewById(R.id.current);
        increaseButton = (Button) view.findViewById(R.id.button_plus);

        fragmentData = new ViewModelProvider(requireActivity()).get(FragmentDataModel.class);

        dataChangeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                dataOutputView.setText(newInteger.toString());
            }
        };
        fragmentData.counter.observe(getViewLifecycleOwner(), dataChangeObserver);

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer i = fragmentData.counter.getValue();
                fragmentData.counter.setValue(++i);
            }
        });

        return view;
    }
}