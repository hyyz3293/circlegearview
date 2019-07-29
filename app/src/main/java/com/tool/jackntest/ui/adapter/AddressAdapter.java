package com.tool.jackntest.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tool.jackntest.R;
import com.tool.jackntest.ui.model.TestEntity;

import java.util.List;

public class AddressAdapter extends BaseMultiItemQuickAdapter<TestEntity, BaseViewHolder> {


    public AddressAdapter(List<TestEntity> data) {
        super(data);
        addItemType(1, R.layout.adapter_address_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestEntity item) {
        switch (item.itemType) {
            case 1:
                initTitle(helper, item);
                break;
        }
    }

    /** 标题 */
    private void initTitle(BaseViewHolder helper, TestEntity item) {
    }
}
