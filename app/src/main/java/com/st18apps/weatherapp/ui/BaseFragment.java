package com.st18apps.weatherapp.ui;

import android.Manifest;
import android.animation.LayoutTransition;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BaseFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;

    protected boolean checkReadWritePermissions() {
        return !needPermissions() || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    protected boolean checkCameraPermissions() {
        return !needPermissions() || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    protected boolean needPermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    protected boolean isAttached() {
        return getActivity() != null && !getActivity().isFinishing() && isAdded();
    }

    protected View inflateWithLoadingIndicator(int resId, ViewGroup parent) {
        swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        swipeRefreshLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout.setEnabled(false);
        View view = LayoutInflater.from(getActivity()).inflate(resId, parent, false);
        swipeRefreshLayout.addView(view);
        return swipeRefreshLayout;
    }

    protected boolean isLoading() {
        return swipeRefreshLayout.isRefreshing();
    }

    protected void setLoading(boolean loading) {
        swipeRefreshLayout.setRefreshing(loading);
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    protected void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    protected View animateLayout(View layout) {
        return animateLayout((ViewGroup) layout);
    }

    protected View animateLayout(ViewGroup layout) {
        LayoutTransition layoutTransition = layout.getLayoutTransition();
        if (layoutTransition == null) {
            layout.setLayoutTransition(new LayoutTransition());
            layoutTransition = layout.getLayoutTransition();
        }
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        return layout;
    }

    protected void animateLoadingView() {
        animateLayout(swipeRefreshLayout);
    }


    protected View inflateWithLoadingIndicatorAndAnimate(int resId, ViewGroup parent) {
        return animateLayout(inflateWithLoadingIndicator(resId, parent));
    }

}
