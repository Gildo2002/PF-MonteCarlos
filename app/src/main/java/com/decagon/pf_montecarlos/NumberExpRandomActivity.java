package com.decagon.pf_montecarlos;

import android.net.Uri;
import android.os.Bundle;

import com.decagon.pf_montecarlos.ClassFragment.tabs1;
import com.decagon.pf_montecarlos.ClassFragment.tabs2;
import com.decagon.pf_montecarlos.ClassFragment.tabs3;
import com.decagon.pf_montecarlos.ClassFragment.tabs4;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.decagon.pf_montecarlos.ui.main.SectionsPagerAdapter;

public class NumberExpRandomActivity extends AppCompatActivity implements
        tabs1.OnFragmentInteractionListener,
        tabs2.OnFragmentInteractionListener,
        tabs3.OnFragmentInteractionListener,
        tabs4.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_exp_random);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}