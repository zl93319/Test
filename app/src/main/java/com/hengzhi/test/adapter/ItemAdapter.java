package com.hengzhi.test.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengzhi.test.R;
import com.hengzhi.test.bean.DataBean;
import com.hengzhi.test.utils.UiUtil;

import java.util.List;

/**
 * 作者:张磊
 * 日期:2018/9/20
 * 说明:
 */
public class ItemAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {


    private final int mIndex;

    public ItemAdapter(List<DataBean> datas, int index) {
        super(R.layout.item_adapter, datas);
        mIndex = index;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {
            if (mIndex==1){
                Glide.with(UiUtil.getContext()).load(UiUtil.getResources().getDrawable(R.mipmap.ic_launcher))
                        .into((ImageView) helper.getView(R.id.iv_show));
                helper.getView(R.id.tv_text).setVisibility(View.GONE);
                helper.getView(R.id.btn_show).setVisibility(View.GONE);
            }else if (mIndex==2){
                helper.getView(R.id.btn_show).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_text).setVisibility(View.GONE);
                helper.getView(R.id.ll_show).setVisibility(View.GONE);
                helper.getView(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            } else {
                helper.getView(R.id.ll_show).setVisibility(View.GONE);
                helper.getView(R.id.tv_text).setVisibility(View.VISIBLE);
            }
    }
}
