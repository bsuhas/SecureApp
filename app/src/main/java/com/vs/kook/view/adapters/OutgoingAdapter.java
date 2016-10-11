package com.vs.kook.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.kook.R;
import com.vs.kook.view.models.CallHistoryModel;
import com.vs.kook.view.widget.RecyclerView;

import java.util.ArrayList;

/*
 * Created by SUHAS on 08/10/2016.
 */

public class OutgoingAdapter extends RecyclerView.Adapter<OutgoingAdapter.OutgoingViewHolder> {

    private final Context mContext;
    private ArrayList<CallHistoryModel> callHistoryModelArrayList;

    public OutgoingAdapter(Context context, ArrayList<CallHistoryModel> list) {
        this.callHistoryModelArrayList = list;
        this.mContext = context;
    }

    @Override
    public OutgoingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.call_hitsory_row, parent, false);

        return new OutgoingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OutgoingViewHolder holder, int position) {
        CallHistoryModel model = callHistoryModelArrayList.get(position);
        holder.number.setText(mContext.getString(R.string.number)+""+model.getNumber());
        holder.date.setText(mContext.getString(R.string.date)+""+model.getDate());
        holder.time.setText(mContext.getString(R.string.time)+""+model.getTime());
        holder.duration.setText(mContext.getString(R.string.duration)+""+model.getDuration());
    }

    @Override
    public int getItemCount() {
        return callHistoryModelArrayList.size();
    }

    public class OutgoingViewHolder extends RecyclerView.ViewHolder {

        private final TextView number, date, time, duration;

        public OutgoingViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.txt_number);
            date = (TextView) view.findViewById(R.id.txt_date);
            time = (TextView) view.findViewById(R.id.txt_time);
            duration = (TextView) view.findViewById(R.id.txt_duration);
        }
//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
////            contextMenu.setHeaderTitle("Select The Action");
//            contextMenu.add(0, view.getId(), 0, R.string.call_back);
//            contextMenu.add(0, view.getId(), 0, R.string.delete);//groupId, itemId, order, title
//        }
    }
    /*

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
*/
}
