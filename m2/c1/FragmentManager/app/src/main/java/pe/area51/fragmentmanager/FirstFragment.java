package pe.area51.fragmentmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FirstFragment extends Fragment {

    private FragmentInterface myInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myInterface != null) {
                    myInterface.onTouch();
                }
            }
        });
        return view;
    }

    public void setFragmentListInterface(
            final FragmentInterface fragmentListInterface) {
        myInterface = fragmentListInterface;
    }

    public static interface FragmentInterface {

        public void onTouch();

    }

}
