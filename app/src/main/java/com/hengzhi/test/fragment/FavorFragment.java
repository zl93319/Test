package com.hengzhi.test.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanco.screen_control.ScreenAdminReceiver;
import com.ethanco.screen_control.ScreenControl;
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

/**
 * 作者:张磊
 * 日期:2018/9/19
 * 说明:
 */
public class FavorFragment extends ZBaseFragment implements SwipeMenuCreator, SwipeMenuItemClickListener {

    private static final String TAG = "z";
    @BindView(R.id.smrv)
    SwipeMenuRecyclerView mSmrv;
    private ScreenControl mScreenControl;

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


        // 屏幕相关
        mScreenControl = ScreenControl.getInstance()
                .init(UiUtil.getContext(), ScreenAdminReceiver.class);
        if (!mScreenControl.isAdminActive()) {
            mScreenControl.openScreenPermission(getActivity(), "程序操作需要相应权限，请激活设备管理器");
        }
        List<DataBean> lists = new ArrayList<>();
        // // TODO: 2018/9/22
        for (int i = 0; i < 1; i++) {
            lists.add(new DataBean());
        }
        ItemAdapter adapter = new ItemAdapter(lists, 2);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mScreenControl.isAdminActive()){
                    mScreenControl.turnOff();
                    UiUtil.postDelay(new Runnable() {
                        @Override
                        public void run() {
                            mScreenControl.turnOn();
                        }
                    },3000);
                }else {
                    Toast.makeText(UiUtil.getContext(), "没有设备管理权限", Toast.LENGTH_LONG).show();
                    UiUtil.postDelay(new Runnable() {
                        @Override
                        public void run() {
                            mScreenControl.turnOn();
                        }
                    },3000);
                }
            }
        });
        mSmrv.setAdapter(adapter);
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {


    }

    @Override
    public void onItemClick(SwipeMenuBridge menuBridge) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScreenControl.SCREEN_REQUEST_CODE) {
            if (mScreenControl.isAdminActive()) {
                Log.i(TAG, "设备已被激活");
            } else {
                Log.i(TAG, "设备没有被激活");
            }
        }
    }
}
