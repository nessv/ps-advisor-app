package org.fundacionparaguaya.advisorapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder> {

    List<? extends Family> mFamilyList;
    ArrayList<FamilySelectedHandler> mFamilySelectedHandlers;

    public FamilyAdapter(List<Family> families){
        this.mFamilyList = families;
        mFamilySelectedHandlers = new ArrayList<FamilySelectedHandler>();

    }

    public void addFamilySelectedHandler(FamilySelectedHandler h){
        mFamilySelectedHandlers.add(h);
    }

    public void removeFamilySelectedHandler(FamilySelectedHandler h){
        mFamilySelectedHandlers.remove(h);
    }

    private void notifyFamilySelectedHandlers(FamilySelectedEvent e){
        for(FamilySelectedHandler handler: mFamilySelectedHandlers){
            handler.onFamilySelected(e);
        }
    }

    @Override
    public FamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allfamilies, parent, false);
        return new FamilyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FamilyViewHolder holder, int position) {

        final Family family = mFamilyList.get(position);

        holder.familyName.setText(family.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyFamilySelectedHandlers(new FamilySelectedEvent(family));
            }
        });
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

    public void setFamilyList(final List<? extends Family> families){
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
                            && Objects.equals(newFamily.getName(), oldFamily.getName());
                }
            });
            mFamilyList = families;
            result.dispatchUpdatesTo(this);
        }
    }

    public class FamilySelectedEvent {

        private FamilySelectedHandler mFamilySelectedHandler;

        Family mFamilySelected;

        FamilySelectedEvent(Family f){
            mFamilySelected = f;
        }

        public Family getSelectedFamily(){
            return mFamilySelected;
        }
    }

    public interface FamilySelectedHandler{
        void onFamilySelected(FamilySelectedEvent e);
    }

}
