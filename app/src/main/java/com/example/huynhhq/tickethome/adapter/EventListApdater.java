package com.example.huynhhq.tickethome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huynhhq.tickethome.R;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Type;

import android.text.format.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public class EventListApdater extends RecyclerView.Adapter<EventListApdater.MyViewHolder> {

    private List<Event> listEvent;
    private Context context;
    private ItemRowClickListener itemRowClickListener;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private int idItemList;

    public EventListApdater(List listEvent, Context context, ItemRowClickListener rowClickListener, int idItemList) {
        this.listEvent = listEvent;
        this.context = context;
        this.itemRowClickListener = rowClickListener;
        this.idItemList = idItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View myView = LayoutInflater.from(parent.getContext()).inflate(idItemList, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(myView, itemRowClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Event event = listEvent.get(position);
        List<Type> typeEvent = event.getType();
        Long startDate = Long.MIN_VALUE;
        Long endDate = Long.MIN_VALUE;
        try {
            startDate = Long.parseLong(event.getStartDate());
            endDate = Long.parseLong(event.getEndDate());

            String sDate = getCurrentDate(startDate);
            String eDate = getCurrentDate(endDate);
            String startEndDate = sDate + " - " + eDate;
            holder.title_event.setText(event.getName());
            if (typeEvent != null) {
                if (typeEvent.size() == 1) {
                    holder.txt_eventType.setText(typeEvent.get(0).getName());
                } else {
                    String type = typeEvent.get(0).getName() + ", ";
                    for (int i = 1; i < typeEvent.size(); i++) {
                        type += typeEvent.get(i).getName();
                    }
                    holder.txt_eventType.setText(type);
                }
            }
            if (event.getCityId() == 1) {
                holder.txt_location.setText("Ho Chi Minh");
            } else if (event.getCityId() == 2) {
                holder.txt_location.setText("Ha Noi");
            } else if (event.getCityId() == 3) {
                holder.txt_location.setText("Da Nang");
            }
            if(event.getPrice().length() != 0){
                double amount = Double.parseDouble(event.getPrice());
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatted = formatter.format(amount);
                holder.txt_price.setText(formatted + " VND");
            }else{
                holder.txt_price.setText("Miễn phí");
            }

            holder.txt_dateStartEnd.setText(startEndDate);
            if (idItemList == R.layout.item_notify_event) {
                Date dateObj = new Date(startDate);
                Date currentTime = Calendar.getInstance().getTime();
                String nowDateStr = sdf.format(currentTime);
                String countDay = getCountOfDays(sdf.format(new Date(startDate)), sdf.format(new Date(startDate)));
                holder.timeBegin.setText(countDay);
            }
        } catch (NumberFormatException se) {
            System.out.println(se);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_event, txt_dateStartEnd, txt_eventType, txt_location, txt_price, timeBegin;
        private ItemRowClickListener itemRowClickListener;

        public MyViewHolder(View itemView, ItemRowClickListener itemRowClickListener) {
            super(itemView);
            title_event = (TextView) itemView.findViewById(R.id.title_event);
            txt_dateStartEnd = (TextView) itemView.findViewById(R.id.txt_dateStartEnd);
            txt_eventType = (TextView) itemView.findViewById(R.id.txt_eventType);
            txt_location = (TextView) itemView.findViewById(R.id.txt_location);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            if (idItemList == R.layout.item_notify_event) {
                timeBegin = (TextView) itemView.findViewById(R.id.time_begin);
            }
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

    private String getCurrentDate(long timeStamp) throws ParseException {
        String str = null;
        Calendar calendar = Calendar.getInstance();

        String date = getDate(timeStamp);
        Date netDate = sdf.parse(date);
        String dayStr = (String) DateFormat.format("dd", netDate);
        String monthString  = (String) DateFormat.format("MMMM",  netDate);
        calendar.setTime(netDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                str = "Thứ hai, " + dayStr + " " + monthString;
                break;
            case Calendar.TUESDAY:
                str = "Thứ ba, " + dayStr + " " +  monthString;;
                break;
            case Calendar.WEDNESDAY:
                str = "Thứ tư, " + dayStr + " " + monthString;
                break;
            case Calendar.THURSDAY:
                str = "Thứ năm, " + dayStr + " " + monthString;
                break;
            case Calendar.FRIDAY:
                str = "Thứ sáu, " + dayStr + " " + monthString;
                break;
            case Calendar.SATURDAY:
                str = "Thứ bảy, " + dayStr + " " + monthString;
                break;
            case Calendar.SUNDAY:
                str = "Chủ nhật, " + dayStr + " " + monthString;
                break;
        }
        return str;
    }

    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = (Date)sdf.parse(createdDateString);
            expireCovertedDate = (Date)sdf.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        if(dayCount < 0){
            dayCount *= -1;
            return ("" + (int) dayCount + " ngày trước");
        }

        return ("Còn " + (int) dayCount + " ngày nữa");
    }

    private String getDate(long timeStamp){
        try{
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
