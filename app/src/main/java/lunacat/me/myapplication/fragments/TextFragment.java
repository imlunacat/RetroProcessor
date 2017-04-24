package lunacat.me.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TextFragment extends Fragment {
    private static final String KEY_TEXT = "key.text";
    public static TextFragment newInstance(String text) {
        Bundle arg = new Bundle();
        arg.putString(KEY_TEXT, text);
        TextFragment tf = new TextFragment();
        tf.setArguments(arg);
        return tf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(container.getContext());
        tv.setText(getArguments().getString(KEY_TEXT));
        return tv;
    }
}
