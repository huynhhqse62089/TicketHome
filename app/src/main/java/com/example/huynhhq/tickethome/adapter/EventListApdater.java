package com.example.huynhhq.tickethome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huynhhq.tickethome.R;
import com.example.huynhhq.tickethome.model.Event;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public class EventListApdater extends RecyclerView.Adapter<EventListApdater.MyViewHolder>{

    private List<Event> listEvent;
    private Context context;
    private ItemRowClickListener itemRowClickListener;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public EventListApdater(List listEvent, Context context, ItemRowClickListener rowClickListener) {
        this.listEvent = listEvent;
        this.context = context;
        this.itemRowClickListener = rowClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(myView, itemRowClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Event event = listEvent.get(position);
        Long startDate = Long.parseLong(event.getStartDate());
        Long endDate = Long.parseLong(event.getEndDate());
        String sDate = getCurrentDate(startDate);
        String eDate = getCurrentDate(endDate);
        String startEndDate = sDate + " - " + eDate;
        holder.title_event.setText(event.getName());
        holder.txt_eventType.setText("Community");
        if(event.getCityId() == 1){
            holder.txt_location.setText("Ho Chi Minh");
        }else if(event.getCityId() == 2){
            holder.txt_location.setText("Ha Noi");
        }else if(event.getCityId() == 3) {
            holder.txt_location.setText("Da Nang");
        }
        double amount = Double.parseDouble(event.getPrice());
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(amount);
        holder.txt_price.setText(formatted + " VND");
        holder.txt_dateStartEnd.setText(startEndDate);
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_event, txt_dateStartEnd, txt_eventType, txt_location, txt_price;
        private ItemRowClickListener itemRowClickListener;

        public MyViewHolder(View itemView, ItemRowClickListener itemRowClickListener) {
            super(itemView);
            title_event = (TextView) itemView.findViewById(R.id.title_event);
            txt_dateStartEnd = (TextView) itemView.findViewById(R.id.txt_dateStartEnd);
            txt_eventType = (TextView) itemView.findViewById(R.id.txt_eventType);
            txt_location = (TextView) itemView.findViewById(R.id.txt_location);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            this.itemRowClickListener = itemRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemRowClickListener.itemClick(v, getAdapterPosition());
        }
    }

    public interface ItemRowClickListener {
        void itemClick(View view, int position);
    }

//    private String getDate(long timeStamp){
//
//        try{
//            DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//            Date netDate = (new Date(timeStamp));
//            return sdf.format(netDate);
//        }
//        catch(Exception ex){
//            System.out.println(ex);
//            return null;
//        }
//    }

    private String getCurrentDate(long timeStamp){
        String str= null;
        Calendar calendar = Calendar.getInstance();
        Date netDate = (new Date(timeStamp));
        calendar.setTime(netDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case Calendar.MONDAY:
                str = "Thứ hai, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.TUESDAY:
                str = "Thứ ba, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.WEDNESDAY:
                str = "Thứ tư, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.THURSDAY:
                str = "Thứ năm, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.FRIDAY:
                str = "Thứ sáu, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.SATURDAY:
                str = "Thứ bảy, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.SUNDAY:
                str = "Chủ nhật, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
        }
        return str;
    }
}
