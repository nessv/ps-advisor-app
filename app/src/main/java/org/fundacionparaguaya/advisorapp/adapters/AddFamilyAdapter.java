package org.fundacionparaguaya.advisorapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;


public class AddFamilyAdapter extends RecyclerView.Adapter {

    @Override
    public AddFamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewTypr){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_addfamilyquestion, parent, false);
        return new AddFamilyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }



    public static class AddFamilyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout familyInfoItem;
        TextView familyInfoQuestion;
        EditText familyInfoEntry;

        public AddFamilyViewHolder(View itemView) {
            super(itemView);
            familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_addfamily);
            familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_question);
            familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_text_field);

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
