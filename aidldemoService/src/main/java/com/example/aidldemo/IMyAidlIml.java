package com.example.aidldemo;

import android.os.RemoteException;


public class IMyAidlIml extends IMyAidlInterface.Stub {
    @Override
    public String greet(String someone) throws RemoteException {
        return "hello carter";
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
