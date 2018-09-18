package com.hcpt.multileagues.objects;


import android.support.v4.app.Fragment;

public class TabItem {
    private String title;
    private int icon;
    private Fragment fragment;

    public TabItem(String title, int icon, Fragment fragment) {
        super();
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }
    public Fragment getFragment() { return fragment; }

    public void setFragment(Fragment fragment) { this.fragment = fragment; }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
