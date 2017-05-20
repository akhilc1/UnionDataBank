package com.sndp.kunnathunadu.uniondatabank.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.models.Member;
import com.sndp.kunnathunadu.uniondatabank.models.Sakha;

/**
 * Created by akhil on 20/5/17.
 */

public class SakhaDetailsAdapter extends RecyclerView.Adapter<SakhaDetailsAdapter.SakhaDetailsViewHolder> {
    private Sakha sakha;
    private Context context;

    public SakhaDetailsAdapter(Sakha sakha, Context context) {
        this.sakha = sakha;
        this.context = context;
    }

    @Override
    public SakhaDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_cell, parent, false);
        return new SakhaDetailsViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(SakhaDetailsViewHolder holder, int position) {
        if (sakha != null) {
            final Member member = sakha.getSakhaMembers().get(position);
            holder.designationTV.setText(member.getOfficialDesignation());
            String nameHouseString = member.getName();
            if (!member.getHouseName().isEmpty()) {
                nameHouseString += member.getHouseName();
            }
            holder.nameHouseTV.setText(nameHouseString);
            if (member.getPhoneNumbers() != null) {
                holder.firstPhoneTV.setText(member.getPhoneNumbers().get(0));
                holder.firstPhoneTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        String phoneNumber = member.getPhoneNumbers().get(0);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        context.startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        if (sakha.getSakhaMembers().size() > 0)
            return sakha.getSakhaMembers().size();
        return 0;
    }

    public class SakhaDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameHouseTV;
        private TextView firstPhoneTV;
        private TextView secondPhoneTV;
        private TextView designationTV;

        public SakhaDetailsViewHolder(View itemView) {
            super(itemView);
            nameHouseTV = (TextView) itemView.findViewById(R.id.name_houseTV);
            firstPhoneTV = (TextView) itemView.findViewById(R.id.first_phoneTV);
            secondPhoneTV = (TextView) itemView.findViewById(R.id.second_phoneTV);
            designationTV = (TextView) itemView.findViewById(R.id.designationTV);
        }
    }
}
