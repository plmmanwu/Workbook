package com.Tool.Function;

import android.content.Context;

import com.Tool.Global.Variable;

import java.nio.ByteOrder;

/**
 * Created by 郑童宇 on 2016/05/24.
 */
public class InitFunction {
    public static void TestCPU() {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            Variable.isBigEnding = true;
        } else {
            Variable.isBigEnding = false;
        }
    }

    public static synchronized void Initialise(Context application) {
        TestCPU();

        FileFunction.InitStorage(application);

        LogFunction.UpdateErrorOutputStream();
    }
}
