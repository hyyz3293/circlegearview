package com.tool.jackntest.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.tool.jackntest.R;
import com.tool.jackntest.ui.widget.CircleGearView;
import com.tool.jackntest.ui.widget.CircleGearView2;

public class ViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {

    private CircleGearView circle;
    private CircleGearView2 circle2;
    private SeekBar mSeekBar;
    private boolean mSences = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mSeekBar = findViewById(R.id.seekBar);
        circle = findViewById(R.id.circle);
        circle2 = findViewById(R.id.test);

        mSeekBar.setOnSeekBarChangeListener(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle.setProgressX(2000, mSences);
                circle2.setProgressX(2000, mSences);
                mSences = !mSences;
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        circle.setProgress(progress);
        circle2.setProgress(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
