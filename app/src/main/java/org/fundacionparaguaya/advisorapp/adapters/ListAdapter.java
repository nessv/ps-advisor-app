package org.fundacionparaguaya.advisorapp.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allfamilies, parent, false);
        ListViewHolder lvh = new ListViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListViewHolder.familyName.setText(families.get(position).getmName());
    }


    List<Family> families;

    ListAdapter(List<Family> families){
        this.families = families;
    }

    @Override
    public int getItemCount() {
        return families.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        CardView familyCard;
        static TextView familyName;
       /* static TextView nextVisitLabel;
        static TextView nextVisitDate;
        static TextView lastVisitLabel;
        static TextView lastVisitDate;*/

        ListViewHolder(View itemView){
            super(itemView);
            familyCard = (CardView) itemView.findViewById(R.id.card_view);
            /*familyName = (TextView) itemView.findViewById(R.id.family_name);
            nextVisitLabel = (TextView) itemView.findViewById(R.id.next_visit);
            nextVisitDate = (TextView) itemView.findViewById(R.id.next_visit_time);
            lastVisitLabel = (TextView) itemView.findViewById(R.id.last_visit);
            lastVisitDate = (TextView) itemView.findViewById(R.id.last_visit_time);*/
        }

    }


}
