package com.hengzhi.test.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengzhi.test.R;
import com.hengzhi.test.bean.DataBean;

import java.util.List;

/**
 * 作者:张磊
 * 日期:2018/9/20
 * 说明:
 */
public class ItemAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {


    public ItemAdapter(List<DataBean> datas) {
        super(R.layout.item_adapter,datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {

    }
}
