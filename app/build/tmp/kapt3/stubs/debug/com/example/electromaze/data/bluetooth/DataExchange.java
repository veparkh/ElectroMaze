package com.example.electromaze.data.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0004J\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00020\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u000e\u0010!\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u0004J\b\u0010#\u001a\u0004\u0018\u00010$J\u0019\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010(R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006)"}, d2 = {"Lcom/example/electromaze/data/bluetooth/DataExchange;", "", "()V", "anglesCommand", "", "getAnglesCommand", "()[B", "autoControlCommand", "getAutoControlCommand", "chooseModeCommand", "getChooseModeCommand", "currentClientSocket", "Landroid/bluetooth/BluetoothSocket;", "getCurrentClientSocket", "()Landroid/bluetooth/BluetoothSocket;", "setCurrentClientSocket", "(Landroid/bluetooth/BluetoothSocket;)V", "manualControlCommand", "getManualControlCommand", "mapBuildingCommand", "getMapBuildingCommand", "addCRC", "array", "anglesToByteArrayWithCRC", "angles", "Lcom/example/electromaze/data/bluetooth/Angles;", "byteArrayToCoordinates", "Landroid/graphics/Point;", "bArr", "countCRC", "Lkotlin/UInt;", "countCRC-OGnWXxg", "([B)I", "isCorrectCRC", "", "receiveImage", "Landroid/graphics/Bitmap;", "sendAndReceive", "", "arr", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@kotlin.OptIn(markerClass = {kotlin.ExperimentalStdlibApi.class})
@kotlin.ExperimentalUnsignedTypes
public final class DataExchange {
    @org.jetbrains.annotations.Nullable
    private android.bluetooth.BluetoothSocket currentClientSocket;
    @org.jetbrains.annotations.NotNull
    private final byte[] autoControlCommand = null;
    @org.jetbrains.annotations.NotNull
    private final byte[] manualControlCommand = null;
    @org.jetbrains.annotations.NotNull
    private final byte[] mapBuildingCommand = null;
    @org.jetbrains.annotations.NotNull
    private final byte[] chooseModeCommand = null;
    @org.jetbrains.annotations.NotNull
    private final byte[] anglesCommand = {(byte)100, (byte)1};
    
    public DataExchange() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.bluetooth.BluetoothSocket getCurrentClientSocket() {
        return null;
    }
    
    public final void setCurrentClientSocket(@org.jetbrains.annotations.Nullable
    android.bluetooth.BluetoothSocket p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] getAutoControlCommand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] getManualControlCommand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] getMapBuildingCommand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] getChooseModeCommand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] getAnglesCommand() {
        return null;
    }
    
    public final boolean isCorrectCRC(@org.jetbrains.annotations.NotNull
    byte[] array) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] addCRC(@org.jetbrains.annotations.NotNull
    byte[] array) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final byte[] anglesToByteArrayWithCRC(@org.jetbrains.annotations.NotNull
    com.example.electromaze.data.bluetooth.Angles angles) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.graphics.Point byteArrayToCoordinates(@org.jetbrains.annotations.NotNull
    byte[] bArr) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object sendAndReceive(@org.jetbrains.annotations.NotNull
    byte[] arr, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.graphics.Bitmap receiveImage() {
        return null;
    }
}