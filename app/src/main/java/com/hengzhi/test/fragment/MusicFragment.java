package com.hengzhi.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengzhi.test.R;
import com.hengzhi.test.adapter.ItemAdapter;
import com.hengzhi.test.base.ZBaseFragment;
import com.hengzhi.test.bean.DataBean;
import com.hengzhi.test.utils.LinearLayoutManagerWrapper;
import com.hengzhi.test.utils.UiUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者:张磊
 * 日期:2018/9/19
 * 说明:
 */
public class MusicFragment extends ZBaseFragment implements SwipeMenuCreator, SwipeMenuItemClickListener {

    @BindView(R.id.smrv)
    SwipeMenuRecyclerView mSmrv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResId() {
        return R.layout.music_fragment;
    }

    @Override
    protected void initData() {
        mSmrv.setSwipeMenuCreator(this);
        mSmrv.setSwipeMenuItemClickListener(this);
        mSmrv.setHasFixedSize(true);
        mSmrv.setLayoutManager(new LinearLayoutManagerWrapper(UiUtil.getContext()));
        List<DataBean> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add(new DataBean());
        }
        ItemAdapter adapter = new ItemAdapter(lists);
        mSmrv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        UiUtil.editAndDel(swipeRightMenu);
    }

    @Override
    public void onItemClick(SwipeMenuBridge menuBridge) {
        menuBridge.closeMenu();
        int menuPosition = menuBridge.getPosition();
        final int position = menuBridge.getAdapterPosition();
        switch (menuPosition) {
            case 0:
                UiUtil.showToast(getString(R.string.edit_text), false);
                break;
            case 1:
                UiUtil.showToast(getString(R.string.del_text), false);
                break;
        }
    }
}
