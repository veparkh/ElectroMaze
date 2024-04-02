package com.example.electromaze.data.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/electromaze/data/bluetooth/AutoModeResult;", "", "NewCoordinates", "connectionFailed", "newImage", "Lcom/example/electromaze/data/bluetooth/AutoModeResult$NewCoordinates;", "Lcom/example/electromaze/data/bluetooth/AutoModeResult$connectionFailed;", "Lcom/example/electromaze/data/bluetooth/AutoModeResult$newImage;", "app_debug"})
public abstract interface AutoModeResult {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/electromaze/data/bluetooth/AutoModeResult$NewCoordinates;", "Lcom/example/electromaze/data/bluetooth/AutoModeResult;", "point", "Landroid/graphics/Point;", "(Landroid/graphics/Point;)V", "getPoint", "()Landroid/graphics/Point;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class NewCoordinates implements com.example.electromaze.data.bluetooth.AutoModeResult {
        @org.jetbrains.annotations.NotNull
        private final android.graphics.Point point = null;
        
        public NewCoordinates(@org.jetbrains.annotations.NotNull
        android.graphics.Point point) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Point getPoint() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Point component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.electromaze.data.bluetooth.AutoModeResult.NewCoordinates copy(@org.jetbrains.annotations.NotNull
        android.graphics.Point point) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/electromaze/data/bluetooth/AutoModeResult$connectionFailed;", "Lcom/example/electromaze/data/bluetooth/AutoModeResult;", "()V", "app_debug"})
    public static final class connectionFailed implements com.example.electromaze.data.bluetooth.AutoModeResult {
        @org.jetbrains.annotations.NotNull
        public static final com.example.electromaze.data.bluetooth.AutoModeResult.connectionFailed INSTANCE = null;
        
        private connectionFailed() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/electromaze/data/bluetooth/AutoModeResult$newImage;", "Lcom/example/electromaze/data/bluetooth/AutoModeResult;", "img", "Landroid/graphics/Bitmap;", "(Landroid/graphics/Bitmap;)V", "getImg", "()Landroid/graphics/Bitmap;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class newImage implements com.example.electromaze.data.bluetooth.AutoModeResult {
        @org.jetbrains.annotations.NotNull
        private final android.graphics.Bitmap img = null;
        
        public newImage(@org.jetbrains.annotations.NotNull
        android.graphics.Bitmap img) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Bitmap getImg() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Bitmap component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.electromaze.data.bluetooth.AutoModeResult.newImage copy(@org.jetbrains.annotations.NotNull
        android.graphics.Bitmap img) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}