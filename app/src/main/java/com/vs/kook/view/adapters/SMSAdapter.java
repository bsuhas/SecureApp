package com.vs.kook.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vs.kook.R;
import com.vs.kook.view.models.SMSHistoryModel;
import com.vs.kook.view.widget.RecyclerView;

import java.util.ArrayList;

/*
 * Created by SUHAS on 08/10/2016.
 */

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private final Context mContext;
    private ArrayList<SMSHistoryModel> SMSHistoryModelArrayList;

    public SMSAdapter(Context context, ArrayList<SMSHistoryModel> list) {
        this.SMSHistoryModelArrayList = list;
        this.mContext = context;
    }

    @Override
    public SMSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_hitsory_row, parent, false);

        return new SMSViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SMSViewHolder holder, int position) {
        SMSHistoryModel model = SMSHistoryModelArrayList.get(position);
        holder.number.setText(model.getNumber());
        holder.date.setText(model.getTime());
        holder.smsBody.setText(model.getBody());
    }

    @Override
    public int getItemCount() {
        return SMSHistoryModelArrayList.size();
    }

    public class SMSViewHolder extends RecyclerView.ViewHolder {

        private final TextView number, date, smsBody;

        public SMSViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.txt_number);
            date = (TextView) view.findViewById(R.id.txt_sms_date);
            smsBody = (TextView) view.findViewById(R.id.txt_sms_body);
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
