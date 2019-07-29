package com.tool.jackntest.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tool.jackntest.R;
import com.tool.jackntest.ui.model.TestEntity;

public class ATopAdapter extends BaseQuickAdapter<TestEntity.TopEntity, BaseViewHolder> {

    public ATopAdapter() {
        super(R.layout.adapter_address_tops);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestEntity.TopEntity item) {
        TextView txt = helper.getView(R.id.tops_txt);
        View view = helper.getView(R.id.tops_view);
        int color = R.color.gary333;
        view.setVisibility(View.INVISIBLE);
        if (item.isCheck) {
            color = R.color.ff5b2c;
            view.setVisibility(View.VISIBLE);
        }
        txt.setTextColor(mContext.getResources().getColor(color));
        view.setBackgroundColor(mContext.getResources().getColor(color));
        txt.setText(String.valueOf(item.name));
    }
}
