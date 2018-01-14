package org.fundacionparaguaya.advisorapp.adapters;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
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
import java.util.Objects;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder> {

    List<? extends Family> mFamilyList;


    @Override
    public FamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allfamilies, parent, false);
        return new FamilyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FamilyViewHolder holder, int position) {
        holder.familyName.setText(families.get(position).getmName());
    }


    List<Family> families;

    public FamilyAdapter(List<Family> families){
        this.families = families;
    }

    @Override
    public int getItemCount() {
        return mFamilyList == null ? 0: mFamilyList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FamilyViewHolder extends RecyclerView.ViewHolder{
        CardView familyCard;
        static TextView familyName;
       /* static TextView nextVisitLabel;
        static TextView nextVisitDate;
        static TextView lastVisitLabel;
        static TextView lastVisitDate;*/

        FamilyViewHolder(View itemView){
            super(itemView);
            familyCard = (CardView) itemView.findViewById(R.id.card_view);
            /*familyName = (TextView) itemView.findViewById(R.id.family_name);
            nextVisitLabel = (TextView) itemView.findViewById(R.id.next_visit);
            nextVisitDate = (TextView) itemView.findViewById(R.id.next_visit_time);
            lastVisitLabel = (TextView) itemView.findViewById(R.id.last_visit);
            lastVisitDate = (TextView) itemView.findViewById(R.id.last_visit_time);*/
        }

    }

    public void setmFamilyList(final List<? extends Family> families){
        if(mFamilyList == null){
            mFamilyList = families;
            notifyItemRangeInserted(0, families.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFamilyList.size();
                }

                @Override
                public int getNewListSize() {
                    return families.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFamilyList.get(oldItemPosition)
                            .equals(families.get(newItemPosition));

                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Family newFamily = families.get(newItemPosition);
                    Family oldFamily = mFamilyList.get(oldItemPosition);
                    return newFamily == oldFamily
                            && Objects.equals(newFamily.getmName(), oldFamily.getmName());
                }
            });
            mFamilyList = families;
            result.dispatchUpdatesTo(this);
        }
    }


}
