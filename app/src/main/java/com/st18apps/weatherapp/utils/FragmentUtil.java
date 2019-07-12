package com.st18apps.weatherapp.utils;


import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.st18apps.weatherapp.R;

public class FragmentUtil {
    private static void replaceFragment(final FragmentManager manager, Fragment fragment,
                                        final boolean addToBackStack, final boolean useLeftRightAnimations) {
        final FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        fTrans.replace(R.id.container, fragment, fragment.getClass().getSimpleName());

        fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (useLeftRightAnimations) {
//                    fTrans.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right);
                } else {
                    fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                }
                if (addToBackStack) {
                    fTrans.addToBackStack(null);
                } else {
                    try {
                        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                fTrans.commitAllowingStateLoss();
            }
        });

    }

    private static void replaceFragmentWithBundle(FragmentManager manager, Fragment fragment,
                                                  boolean addToBackStack, Bundle bundle) {
        FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        if (addToBackStack) {
            fTrans.addToBackStack(null);
        } else {
            manager.popBackStackImmediate();
        }
        fragment.setArguments(bundle);
        fTrans.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        fTrans.commitAllowingStateLoss();
    }

    private static void replaceFragmentWithContainer(FragmentManager manager, Fragment fragment,
                                                     boolean addToBackStack, int containerId) {
        FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        if (addToBackStack) {
            fTrans.addToBackStack(null);
        } else {
            manager.popBackStackImmediate();
        }
        fTrans.replace(containerId, fragment, fragment.getClass().getSimpleName());
        fTrans.commitAllowingStateLoss();
    }

    private static void replaceFragmentWithBundleAndContainer(FragmentManager manager, Fragment fragment,
                                                              boolean addToBackStack, int containerId, Bundle bundle) {
        FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        if (addToBackStack) {
            fTrans.addToBackStack(null);
        } else {
            manager.popBackStackImmediate();
        }
        fragment.setArguments(bundle);
        fTrans.replace(containerId, fragment, fragment.getClass().getSimpleName());
        fTrans.commitAllowingStateLoss();
    }


    public static void replaceFragment(FragmentManager manager, Fragment fragment, boolean addToBackStack) {
        replaceFragment(manager, fragment, addToBackStack, false);
    }

    public static void replaceFragment(FragmentManager manager, Fragment fragment, boolean addToBackStack, int containerId) {
        replaceFragmentWithContainer(manager, fragment, addToBackStack, containerId);
    }

    public static void replaceFragment(FragmentManager manager, Fragment fragment, boolean addToBackStack, Bundle bundle) {
        replaceFragmentWithBundle(manager, fragment, addToBackStack, bundle);
    }

    public static void replaceFragment(FragmentManager manager, Fragment fragment, boolean addToBackStack, int containerId, Bundle bundle) {
        replaceFragmentWithBundleAndContainer(manager, fragment, addToBackStack, containerId, bundle);
    }

    public static void addFragment(FragmentManager manager, Fragment fragment) {
        FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        fTrans.addToBackStack(null);
        fTrans.add(R.id.container, fragment, fragment.getClass().getSimpleName());
        fTrans.commit();
    }

    public static void addFragment(FragmentManager manager, Fragment fragment, boolean addToBackStack, int containerId, Bundle bundle) {
        FragmentTransaction fTrans;
        fTrans = manager.beginTransaction();
        if (addToBackStack) {
            fTrans.addToBackStack(null);
        } else {
            manager.popBackStackImmediate();
        }
        fragment.setArguments(bundle);
        fTrans.add(containerId, fragment, fragment.getClass().getSimpleName());
        fTrans.commit();
    }

}
