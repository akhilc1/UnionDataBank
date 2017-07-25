package com.sndp.kunnathunadu.uniondatabank.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.models.Member;
import com.sndp.kunnathunadu.uniondatabank.utils.Constants;

import java.util.ArrayList;

public class UpdateMemberDialogFragment extends DialogFragment {
    private Member member;
    private String sakhaName;
    private EditText nameET;
    private EditText phoneET;
    private EditText houseNameET;
    private int adapterPosition = -1;
    private Button updateButton;
    private String TAG = getClass().getSimpleName();

    public void setSakhaName(String sakhaName) {
        this.sakhaName = sakhaName;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.edit_member, container, false);
        updateButton = (Button) dialogView.findViewById(R.id.submit_buttton);
        nameET = (EditText) dialogView.findViewById(R.id.nameET);
        phoneET = (EditText) dialogView.findViewById(R.id.phoneET);
        houseNameET = (EditText) dialogView.findViewById(R.id.houseNameET);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                String name = nameET.getText().toString();
                final String phone = phoneET.getText().toString();
                String houseName = houseNameET.getText().toString();
                //Member member = new Member();
                if (name.length() > 0) {
                    member.setName(name);
                }
                if (phone.length() > 0) {
                    ArrayList<String> phoneList = new ArrayList<>();
                    phoneList.add(phone);
                    member.setPhoneNumbers(phoneList);
                }
                if (houseName.length() > 0) {
                    member.setHouseName(houseName);
                }
                FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_SAKHA_DETAILS_TAG)
                        .child(sakhaName)
                        .child(Constants.FIREBASE_SAKHA_MEMBERS_TAG)
                        .child(String.valueOf(adapterPosition))
                        .setValue(member)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        });

            }
        });
        return dialogView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameET.setText(member.getName());
        if (member.getHouseName() != null && member.getPhoneNumbers().size() > 0) {
            phoneET.setText(member.getPhoneNumbers().get(0));
        }
        houseNameET.setText(member.getHouseName());
    }
}
