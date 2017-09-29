package com.iplayer.main.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/21.
 */

class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    private Context context;

    public TestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(position + "");
    }

    @Override
    public int getItemCount() {
        return 80;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;

        }
    }
}
