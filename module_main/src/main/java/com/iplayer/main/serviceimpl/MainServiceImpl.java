package com.iplayer.main.serviceimpl;

import android.support.v4.app.Fragment;

import com.iplayer.componentservice.main.MainService;
import com.iplayer.main.ui.MainFragment;

/**
 * Created by jianzuming on 17/9/17.
 */

public class MainServiceImpl implements MainService {
    @Override
    public Fragment getMainFragment() {
        return new MainFragment();
    }
}
