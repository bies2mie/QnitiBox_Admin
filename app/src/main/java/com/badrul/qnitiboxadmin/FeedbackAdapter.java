package com.badrul.qnitiboxadmin;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {


    private Context mCtx;
    private List<Feedback> feedbackList;
    private OnItemClicked onClick;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public FeedbackAdapter(Context mCtx, List<Feedback> feedbackList) {
        this.mCtx = mCtx;
        this.feedbackList = feedbackList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.feedback_list, null);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder,final int position) {
        Feedback feedback = feedbackList.get(position);

        holder.textViewTitle.setText(feedback.getNameID()); //getName
        holder.textViewShortDesc.setText(feedback.getPhoneID()); //GetICnum
        holder.textViewRating.setText(feedback.getMatrixID()); //getStatus
        holder.textViewPrice.setText(feedback.getUserFeedback()); //getTotalPrice
        //holder.textViewTrans.setText(feedback.getGivernama()+" ("+feedback.getGivericnumber()+") "+feedback.getTransmsg()+" "+feedback.getReceinama()+" ("+feedback.getReceiicnumber()+")");

       /* holder.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        }); */
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        RelativeLayout test;

        public FeedbackViewHolder(View itemView) {
            super(itemView);

            test=itemView.findViewById(R.id.testing);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            //textViewTrans = itemView.findViewById(R.id.textViewTrans);

        }
    }
}

