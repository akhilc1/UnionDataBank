package com.sndp.kunnathunadu.uniondatabank.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.adapters.SakhaListRecyclerAdapter;
import com.sndp.kunnathunadu.uniondatabank.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class UnionSakhaBranchesFragment extends Fragment {
    public static final String shared_pref = "my pref";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private String TAG = getClass().getSimpleName();
    private int mColumnCount = 1;
    private List<String> sakhasList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SakhaListRecyclerAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseDatabaseReference;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UnionSakhaBranchesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UnionSakhaBranchesFragment newInstance(int columnCount) {
        UnionSakhaBranchesFragment fragment = new UnionSakhaBranchesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }
        sakhasList = new ArrayList<>();
        adapter = new SakhaListRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (sakhasList.size() == 0) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabaseReference = firebaseDatabase.getReference(Constants.FIREBASE_SAKHAS_TAG);
            firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: ");
                    sakhasList = (List<String>) dataSnapshot.getValue();
                    adapter.setSakhas(sakhasList);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: ");
                }
            });

            Log.d("", "onViewCreated: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    private void writeSharedPref(Set<String> sakahas) {
        SharedPreferences preferences = getContext().getSharedPreferences(shared_pref, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("sakhas", sakahas);
        editor.apply();

    }

    private Set<String> readSharedPrefs() {
        SharedPreferences prefs = getContext().getSharedPreferences(shared_pref, 0);
        return prefs.getStringSet("sakhas", null);
    }

}
