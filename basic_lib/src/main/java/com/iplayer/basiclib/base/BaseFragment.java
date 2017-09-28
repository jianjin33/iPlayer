package com.iplayer.basiclib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2017/9/28.
 */

public class BaseFragment extends Fragment implements FragmentLifecycleProvider {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Context context) {
        this.lifecycleSubject.onNext(FragmentEvent.ATTACH);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @NonNull
    @Override
    public Observable<FragmentEvent> lifecycle() {
        return this.lifecycleSubject.asObservable();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindFragment(this.lifecycleSubject);
    }
    @CallSuper
    public void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(FragmentEvent.START);
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    public void onPause() {
        this.lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    public void onStop() {
        this.lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    public void onDestroy() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }
}
