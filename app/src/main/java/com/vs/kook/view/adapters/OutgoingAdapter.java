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

import java.math.BigDecimal;
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
        holder.number.setText(model.getNumber());
        holder.date.setText(model.getDate() +" , " + model.getTime());
        String dur = splitToComponentTimes(Long.parseLong(model.getDuration()));
        holder.duration.setText(dur);
    }

    @Override
    public int getItemCount() {
        return callHistoryModelArrayList.size();
    }


    public String splitToComponentTimes(long biggy)
    {
        long longVal = biggy;
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        String s = hours + " Hours, "+mins +" Min, "+ secs +" Sec";
//        int[] ints = {hours , mins , secs};
        return s;
    }

    public class OutgoingViewHolder extends RecyclerView.ViewHolder {

        private final TextView number, date, duration;

        public OutgoingViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.txt_number);
            date = (TextView) view.findViewById(R.id.txt_date);
//            time = (TextView) view.findViewById(R.id.txt_time);
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
