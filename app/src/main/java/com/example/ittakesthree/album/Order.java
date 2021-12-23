package com.example.ittakesthree.album;

import java.io.File;

public class Order {
    public interface OnChangeListener {	// 创建interface类
        void onChange();    // 值改变
    }
    private static OnChangeListener onChangeListener;	// 声明interface接口
    public static void setOnChangeListener(OnChangeListener onChange){	// 创建setListener方法
        onChangeListener = onChange;
    }

    private static File returnFile;
    public static File getReturnFile() {
        return returnFile;
    }
    public static void setReturnFile(File returnFile) {
        Order.returnFile = returnFile;
        onChangeListener.onChange();
    }
}
