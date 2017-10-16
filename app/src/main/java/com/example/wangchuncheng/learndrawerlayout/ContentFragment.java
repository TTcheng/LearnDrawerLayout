package com.example.wangchuncheng.learndrawerlayout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentFragment extends Fragment {
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content,container,false);
        textView = rootView.findViewById(R.id.textView);

        String text = getArguments().getString("text");
        textView.setText(text);
        return rootView;

    }

}
