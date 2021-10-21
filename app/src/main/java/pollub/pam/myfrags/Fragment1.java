package pollub.pam.myfrags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    public interface OnButtonClickListener {
        public void onButtonClickShuffle();
        public void onButtonClickClockwise();
        public void onButtonClickHide();
        public void onButtonClickRestore();
    }

    private OnButtonClickListener callback = null;

    public void setOnButtonClickListener(OnButtonClickListener callback) {
        this.callback = callback;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment1.
     */
    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        Button buttonShuffle = (Button) view.findViewById(R.id.button_shuffle);
        Button buttonClockwise = (Button) view.findViewById(R.id.button_clockwise);
        Button buttonHide = (Button) view.findViewById(R.id.button_hide);
        Button buttonRestore = (Button) view.findViewById(R.id.button_restore);

        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickShuffle();
            }
        });

        buttonClockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickClockwise();
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickHide();
            }
        });

        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickRestore();
            }
        });


        return view;
    }
}