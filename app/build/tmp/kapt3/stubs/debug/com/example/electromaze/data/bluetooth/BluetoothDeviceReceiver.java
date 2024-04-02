package com.example.electromaze.data.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B(\u0012!\u0010\u0002\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001c\u0010\r\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0017R)\u0010\u0002\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/example/electromaze/data/bluetooth/BluetoothDeviceReceiver;", "Landroid/content/BroadcastReceiver;", "deviceFound", "Lkotlin/Function1;", "Landroid/bluetooth/BluetoothDevice;", "Lkotlin/ParameterName;", "name", "device", "", "(Lkotlin/jvm/functions/Function1;)V", "getDevice", "intent", "Landroid/content/Intent;", "onReceive", "context", "Landroid/content/Context;", "app_debug"})
public final class BluetoothDeviceReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<android.bluetooth.BluetoothDevice, kotlin.Unit> deviceFound = null;
    
    public BluetoothDeviceReceiver(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super android.bluetooth.BluetoothDevice, kotlin.Unit> deviceFound) {
        super();
    }
    
    @java.lang.Override
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    public void onReceive(@org.jetbrains.annotations.Nullable
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
    }
    
    private final android.bluetooth.BluetoothDevice getDevice(android.content.Intent intent) {
        return null;
    }
}