package com.hengzhi.test.utils;


/**
 * 作者: l on 2017/8/5 10:00
 * 邮箱: xjs250@163.com
 * 描述: 66666666666666666666666666666666
 */
public class MessageEvent {
    public Object mData;
    public int mCode;
    private static class MessageEventClass {
        private static final MessageEvent INSTANCE = new MessageEvent();
    }

    public static MessageEvent getInstance() {
        return MessageEventClass.INSTANCE;
    }
}