package com.sndp.kunnathunadu.uniondatabank.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.greenrobot.events.ShowSakhaDetailsEvents;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class SakhaListRecyclerAdapter extends RecyclerView.Adapter<SakhaListRecyclerAdapter.ViewHolder> {

    private List<String> sakhaList = null;

    public SakhaListRecyclerAdapter() {
        sakhaList = new ArrayList<>();
    }

    public void setSakhas(List<String> sakhaList) {
        this.sakhaList = sakhaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.sakhaNameTV.setText(sakhaList.get(position));
        holder.sakhaNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ShowSakhaDetailsEvents(sakhaList.get(
                        holder.getAdapterPosition())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sakhaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView sakhaNameTV;

        public ViewHolder(View view) {
            super(view);
            sakhaNameTV = (TextView) view.findViewById(R.id.sakha_name);
        }
    }
}
