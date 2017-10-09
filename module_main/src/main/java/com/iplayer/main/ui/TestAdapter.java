package com.iplayer.main.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iplayer.basiclib.util.StringUtils;
import com.iplayer.basiclib.util.UIUtils;
import com.iplayer.basiclib.view.BaseRecycleView;
import com.iplayer.main.R;
import com.iplayer.main.model.HomeVideoList;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/21.
 */

class TestAdapter extends RecyclerView.Adapter {
    private List<String> mData;
    private Context mContext;
    private LinearViewHolder lholder;
    private GridViewHolder gholder;
    private StaggeredViewHolder sholder;
    private SpecialViewHolder specialHolder;
    private BaseRecycleView.OnItemTouchListener mListener;
    private Map<Integer, Integer> staggeredData = new HashMap<>();
    private List<HomeVideoList.HotEntity> list;

    public TestAdapter(Context context, List<HomeVideoList.HotEntity> list) {
        mContext = context;
        this.list = list;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public void setListener(BaseRecycleView.OnItemTouchListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseRecycleView.TYPE_NORMAL) {
            lholder = new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_item_video, parent, false));
            return lholder;
        } else if (viewType == BaseRecycleView.TYPE_GRID) {
            gholder = new GridViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_item_video, parent, false));
            return gholder;
        } else if (viewType == BaseRecycleView.TYPE_STAGGERED) {
            sholder = new StaggeredViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_item_video, parent, false));
            return sholder;
        } else {
            specialHolder = new SpecialViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_item_video_special, parent, false));
            return specialHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeVideoList.HotEntity hotEntity = list.get(position);
        if (holder instanceof LinearViewHolder) {
            lholder = (LinearViewHolder) holder;
            lholder.position = position;
            lholder.name.setText(list.get(position).getTitle());
        } else if (holder instanceof GridViewHolder) {
            gholder = (GridViewHolder) holder;
            gholder.position = position;
            if (!StringUtils.isEmpty(hotEntity.getTitle()))
                gholder.name.setText(list.get(position).getTitle());

            if (!StringUtils.isEmpty(hotEntity.getBrief()))
                gholder.brief.setText(list.get(position).getBrief());

            if (!StringUtils.isEmpty(hotEntity.getImg_url()))
                Picasso.with(mContext)
                        .load(hotEntity.getImg_url())
                        .config(Bitmap.Config.RGB_565)
                        .resizeDimen(R.dimen.dp_100, R.dimen.dp_130)
                        .centerInside()
                        .into(gholder.pic);
        } else if (holder instanceof StaggeredViewHolder) {
            sholder = (StaggeredViewHolder) holder;
            sholder.position = position;
            sholder.name.setText(mData.get(position));
            if (!staggeredData.containsKey(position))
                staggeredData.put(position, (int) (Math.random() * 100 + 101));//记住高度
            sholder.name.setMinHeight(staggeredData.get(position));
        } else if (holder instanceof SpecialViewHolder) {
            specialHolder = (SpecialViewHolder) holder;
            specialHolder.position = position;
            specialHolder.name.setText(list.get(position).getTitle());
            specialHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class LinearViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView name;
        private ImageView pic;

        public LinearViewHolder(View itemView) {
            super(itemView, mListener);
            pic = itemView.findViewById(R.id.item_video_img);
            name = itemView.findViewById(R.id.item_video_name);
        }
    }

    private class GridViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView name, brief;
        private ImageView pic;

        public GridViewHolder(View itemView) {
            super(itemView, mListener);
            pic = itemView.findViewById(R.id.item_video_img);
            name = itemView.findViewById(R.id.item_video_name);
            brief = itemView.findViewById(R.id.item_video_brief);
        }
    }

    private class StaggeredViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView name;
        private ImageView pic;


        public StaggeredViewHolder(View itemView) {
            super(itemView, mListener);
            pic = itemView.findViewById(R.id.item_video_img);
            name = itemView.findViewById(R.id.item_video_name);
        }
    }

    private class SpecialViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView name;
        private TextView more;


        public SpecialViewHolder(View itemView) {
            super(itemView, mListener);
            more = itemView.findViewById(R.id.item_video_more);
            name = itemView.findViewById(R.id.item_video_name);
        }
    }
}
