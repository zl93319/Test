package com.hengzhi.test.fragment;

import android.widget.TextView;

import com.hengzhi.test.R;
import com.hengzhi.test.base.ZBaseFragment;

import butterknife.BindView;

/**
 * 作者:张磊
 * 日期:2018/9/19
 * 说明:
 */
public class VisibilityFragment extends ZBaseFragment {
    @BindView(R.id.tv_show)
    TextView mTvShow;

    @Override
    protected int getLayoutResId() {
        return R.layout.music_fragment;
    }

    @Override
    protected void initData() {
        mTvShow.setText(getString(R.string.visibility));
    }

}
