package com.iplayer.mine.serviceimpl;

import android.support.v4.app.Fragment;

import com.iplayer.componentservice.main.MainService;
import com.iplayer.componentservice.main.MineService;
import com.iplayer.mine.ui.MineFragment;

/**
 * Created by jianzuming on 17/9/17.
 */

public class MineServiceImpl implements MineService {
    @Override
    public Fragment getMineFragment() {
        return new MineFragment();
    }
}
