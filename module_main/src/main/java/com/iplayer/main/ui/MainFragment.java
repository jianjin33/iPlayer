package com.iplayer.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplayer.basiclib.base.BaseFragment;
import com.iplayer.main.R;
import com.iplayer.main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianzuming on 17/9/17.
 * 首页
 */

public class MainFragment extends BaseFragment {

    @BindView(R2.id.main_rv_content)
    RecyclerView mainRvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mainRvContent.setAdapter(new TestAdapter(getContext()));

        return view;
    }


}
