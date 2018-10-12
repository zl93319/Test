package com.hengzhi.test.generator;


import com.hengzhi.test.utils.MakeUtils;

import java.util.ArrayList;
import java.util.List;


public class DimenGenerator {

    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 375;

    /**
     * 设计稿的高度  （将自己设计师的设计稿的高度填入）
     */
    private static final int DESIGN_HEIGHT = 667;

    public static void main(String[] args) {
            for (int value : getDimens()) {
                MakeUtils.makeAll(DESIGN_WIDTH, value, "/androidUI/adapter");
            }
    }
    /**
     *  适配Android 3.2以上   大部分手机的sw值集中在  300-460之间
     *  从300 ~ 500
     **/
    private static List<Integer> getDimens() {
        List<Integer> dimens = new ArrayList<>();
        for (int i = 300; i <= 500; i += 10) {
            dimens.add(i);
        }
        return dimens;
    }
}
