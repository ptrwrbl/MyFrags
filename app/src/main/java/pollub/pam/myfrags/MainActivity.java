package pollub.pam.myfrags;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends FragmentActivity implements Fragment1.OnButtonClickListener {

    private int[] frames;
    private int[] framesSequence;
    private boolean areFramesHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            frames = new int[]{ R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4 };
            framesSequence = new int[]{ 0, 1, 2, 3 };
            areFramesHidden = false;

            Fragment[] fragments = new Fragment[]{ new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4() };
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            for (int i = 0; i < 4; i++) {
                transaction.add(frames[i], fragments[i]);
            }

            transaction.addToBackStack(null);
            transaction.commit();

        } else {
            frames = savedInstanceState.getIntArray("FRAMES");
            framesSequence = savedInstanceState.getIntArray("SEQUENCE");
            areFramesHidden = savedInstanceState.getBoolean("ARE_HIDDEN");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("FRAMES", frames);
        outState.putIntArray("SEQUENCE", framesSequence);
        outState.putBoolean("ARE_HIDDEN", areFramesHidden);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof Fragment1) {
            ((Fragment1) fragment).setOnButtonClickListener(this);
        }
    }

    private void redrawFragments() {
        Fragment[] newFragments = new Fragment[] { new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4() };
        newFragments = new Fragment[] { newFragments[framesSequence[0]], newFragments[framesSequence[1]], newFragments[framesSequence[2]], newFragments[framesSequence[3]] };

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < 4; i++) {
            transaction.replace(frames[i], newFragments[i]);
            if (areFramesHidden && !(newFragments[i] instanceof Fragment1)) transaction.hide(newFragments[i]);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onButtonClickShuffle() {
        List<Integer> newSequence = new ArrayList<>(Arrays.asList(framesSequence[0], framesSequence[1], framesSequence[2], framesSequence[3]));
        Collections.shuffle(newSequence);
        for(int i = 0; i < 4; i++) framesSequence[i] = newSequence.get(i);

        redrawFragments();
    }

    @Override
    public void onButtonClickClockwise() {
        List<Integer> newFrames = new ArrayList<>(Arrays.asList(frames[1], frames[2], frames[3], frames[0]));

        for(int i = 0; i < 4; i++) frames[i] = newFrames.get(i);

        redrawFragments();
    }

    @Override
    public void onButtonClickHide() {
        if(areFramesHidden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (Fragment f : fragmentManager.getFragments()) {
            if (f instanceof Fragment1 ) continue;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(f);

            transaction.addToBackStack(null);
            transaction.commit();
        }

        areFramesHidden = true;
    }

    @Override
    public void onButtonClickRestore() {

        if (!areFramesHidden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment f : fragmentManager.getFragments()) {
            if (f instanceof Fragment1) continue;
            transaction.show(f);
        }

        transaction.addToBackStack(null);
        transaction.commit();

        areFramesHidden = false;
    }
}