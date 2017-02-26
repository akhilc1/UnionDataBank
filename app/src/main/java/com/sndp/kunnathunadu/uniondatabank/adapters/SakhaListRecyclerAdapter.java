package com.sndp.kunnathunadu.uniondatabank.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.fragments.UnionSakhaBranchesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class SakhaListRecyclerAdapter extends RecyclerView.Adapter<SakhaListRecyclerAdapter.ViewHolder> {

    private List<String> sakhaList = null;
    private UnionSakhaBranchesFragment.OnListFragmentInteractionListener mListener = null;

    public SakhaListRecyclerAdapter(UnionSakhaBranchesFragment.OnListFragmentInteractionListener
                                            listener) {
        sakhaList = new ArrayList<>();
        mListener = listener;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.sakhaNameTV.setText(sakhaList.get(position));
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
