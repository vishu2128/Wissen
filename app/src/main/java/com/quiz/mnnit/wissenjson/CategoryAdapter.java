package com.quiz.mnnit.wissenjson;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;


public class CategoryAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public CategoryAdapter(Category category, String[] prgmNameList, int[] prgmImages) {
        Log.d("HI","EXCEPTION THROWN1");

        result = prgmNameList;
        context = category;
        imageId = prgmImages;
        Log.d("HI","EXCEPTION THROWN2");

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("HI","EXCEPTION THROWN3");

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        TextView tv_language;
        ImageView im_language;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("HI","CHECK!");
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.category_card_view, null);

        holder.tv_language=(TextView) view.findViewById(R.id.cattv);
        holder.im_language=(ImageView) view.findViewById(R.id.catim);

        holder.tv_language.setText(result[position]);
        Picasso.with(context).load(imageId[position]).into(holder.im_language);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}