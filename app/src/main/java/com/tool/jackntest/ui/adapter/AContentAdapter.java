package com.tool.jackntest.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tool.jackntest.R;
import com.tool.jackntest.ui.model.TestEntity;

public class AContentAdapter extends BaseQuickAdapter<TestEntity.ContetEntity, BaseViewHolder> {

    public AContentAdapter() {
        super(R.layout.adapter_address_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestEntity.ContetEntity item) {
        TextView txtSub = helper.getView(R.id.content_sub);
        TextView txtName = helper.getView(R.id.content_txt);
        ImageView img = helper.getView(R.id.content_img);

        txtName.setText(String.valueOf(item.name));

        if (StringUtils.isEmpty(item.py)) {
            txtSub.setText("A");
            txtSub.setVisibility(View.INVISIBLE);
        } else {
             txtSub.setText(item.py);
            txtSub.setVisibility(View.VISIBLE);
        }



    }
}
