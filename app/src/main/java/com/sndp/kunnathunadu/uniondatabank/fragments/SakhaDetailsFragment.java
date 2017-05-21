package com.sndp.kunnathunadu.uniondatabank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.adapters.SakhaDetailsAdapter;
import com.sndp.kunnathunadu.uniondatabank.models.Sakha;
import com.sndp.kunnathunadu.uniondatabank.utils.Constants;

import net.bohush.geometricprogressview.GeometricProgressView;

/**
 * Created by akhil on 26/2/17.
 */

public class SakhaDetailsFragment extends Fragment {
    public static final String SAKHA_NAME_PARAMS = "sakha name";
    private String sakahNameToFetch;
    private Sakha sakhaObject;
    private GeometricProgressView progressBar;
    private RecyclerView recyclerView;
    private SakhaDetailsAdapter detailsAdapter;
    private String TAG = "Sakha Details";

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
        } else {
            Toast.makeText(getActivity(), "Sakaha Name: Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_sakha_details, container, false);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview_sakha);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (GeometricProgressView) parentView.findViewById(R.id.progress_bar);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child(Constants.FIREBASE_SAKHA_DETAILS_TAG).child(sakahNameToFetch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    sakhaObject = dataSnapshot.getValue(Sakha.class);
                    detailsAdapter = new SakhaDetailsAdapter(sakhaObject, getActivity());
                    recyclerView.setAdapter(detailsAdapter);
                    detailsAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "onDataChange: Data is available");
                } else {
                    Toast.makeText(getActivity(), "Data Unavailable", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onDataChange:  Data is not Available");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
