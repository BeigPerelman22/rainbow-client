package com.example.finalproject_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TypeMeetingAdapter  extends BaseAdapter
{

    private Context context;
    private List<TypeMeeting> typeMeetings;

    public TypeMeetingAdapter(Context context, List<TypeMeeting> typeMeetings) {
        this.context = context;
        this.typeMeetings = typeMeetings;
    }


    public int getCount() {
        return typeMeetings != null ? typeMeetings.size() : 0;
    }


    public Object getItem(int i) {
        return i;
    }


    public long getItemId(int i) {
        return i;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_fruit, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(typeMeetings.get(i).getName());
        image.setImageResource(typeMeetings.get(i).getImage());

        return rootView;
    }
}