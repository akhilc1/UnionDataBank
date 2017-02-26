package com.sndp.kunnathunadu.uniondatabank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sndp.kunnathunadu.uniondatabank.R;

/**
 * Created by akhil on 26/2/17.
 */

public class SakhaDetailsFragment extends Fragment {
    public static final String SAKHA_NAME_PARAMS = "sakha name";
    private String sakahNameToFetch;

    public static SakhaDetailsFragment newInstance(String sakhaName) {

        Bundle args = new Bundle();
        args.putString(SAKHA_NAME_PARAMS, sakhaName);
        SakhaDetailsFragment fragment = new SakhaDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().get(SAKHA_NAME_PARAMS) != null) {
            sakahNameToFetch = (String) getArguments().get(SAKHA_NAME_PARAMS);
            Toast.makeText(getActivity(), "Sakaha Name: " + sakahNameToFetch, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Sakaha Name: Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_sakha_details, container, false);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
