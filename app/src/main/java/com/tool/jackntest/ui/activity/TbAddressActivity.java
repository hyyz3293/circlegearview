package com.tool.jackntest.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.jackntest.R;
import com.tool.jackntest.ui.adapter.ATopAdapter;
import com.tool.jackntest.ui.model.TestEntity;
import com.tool.jackntest.utils.AddressDataUtils;

import java.util.List;

public class TbAddressActivity extends Activity implements View.OnClickListener {

    private RecyclerView mTopRecycleview;
    private ATopAdapter mATopAdapter;
    private List<TestEntity.TopEntity> topList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_addres);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.close_t).setOnClickListener(this);
        mTopRecycleview = findViewById(R.id.recycleview_top);
    }

    private void initData() {
        mTopRecycleview.setLayoutManager(new GridLayoutManager(this, 2));
        mATopAdapter = new ATopAdapter();
        mTopRecycleview.setAdapter(mATopAdapter);
        topList = AddressDataUtils.getAddressTopList();
        mATopAdapter.setNewData(topList);
    }

    private void initListener() {
        mATopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TestEntity.TopEntity c = topList.get(position);
                for (int i = 0; i < topList.size(); i++)  {
                    TestEntity.TopEntity c = topList.get(i);
                    if (i == position)
                        c.isCheck = true;
                    else
                        c.isCheck = false;
                }
                mATopAdapter.setNewData(topList);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
            case R.id.close_t:
                finish();
                break;
        }
    }
}
