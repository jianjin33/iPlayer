package com.iplayer.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplayer.basiclib.base.BaseFragment;
import com.iplayer.basiclib.http.HttpCallback;
import com.iplayer.basiclib.http.HttpSubscriber;
import com.iplayer.basiclib.http.Response;
import com.iplayer.basiclib.util.LogUtils;
import com.iplayer.basiclib.view.BaseRecycleView;
import com.iplayer.main.R;
import com.iplayer.main.R2;
import com.iplayer.main.api.HttpMethod;
import com.iplayer.main.model.HomeVideoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianzuming on 17/9/17.
 * 首页
 */

public class MainFragment extends BaseFragment {

    @BindView(R2.id.main_rv_content)
    BaseRecycleView mainRvContent;
    private List<Integer> specialItems = new ArrayList<>();
    List<HomeVideoList.HotEntity> allVideoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        HttpMethod.getInstance().queryHomeList(new HttpSubscriber(new HttpCallback<Response<List<HomeVideoList>>>() {

            @Override
            public void onNext(Response<List<HomeVideoList>> homeVideoListResponse) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                List<HomeVideoList.HotEntity> hotEntityList = homeVideoListResponse.getSlices().get(1).getHot();


                mainRvContent.setLayoutManager(gridLayoutManager);


                int size = homeVideoListResponse.getSlices().size();

                for (int i = 1; i < 6; i++) {
                    if (i == 3)
                        continue;
                    specialItems.add(allVideoList.size());
                    HomeVideoList.HotEntity hotEntity = new HomeVideoList.HotEntity();
                    hotEntity.setTitle(homeVideoListResponse.getSlices().get(i).getName());
                    allVideoList.add(hotEntity);
                    allVideoList.addAll(homeVideoListResponse.getSlices().get(i).getHot());
                }


                mainRvContent.setSpecialItem(specialItems);
                mainRvContent.setAdapter(new TestAdapter(getContext(), allVideoList));
            }

            @Override
            public void onError(Throwable e) {

            }
        }), "3");


        return view;
    }
}
