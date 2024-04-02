package com.example.electromaze.data.bluetooth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/example/electromaze/data/bluetooth/ConnectionResult;", "", "connecionEstablished", "connectionFailed", "Lcom/example/electromaze/data/bluetooth/ConnectionResult$connecionEstablished;", "Lcom/example/electromaze/data/bluetooth/ConnectionResult$connectionFailed;", "app_debug"})
public abstract interface ConnectionResult {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/electromaze/data/bluetooth/ConnectionResult$connecionEstablished;", "Lcom/example/electromaze/data/bluetooth/ConnectionResult;", "()V", "app_debug"})
    public static final class connecionEstablished implements com.example.electromaze.data.bluetooth.ConnectionResult {
        @org.jetbrains.annotations.NotNull
        public static final com.example.electromaze.data.bluetooth.ConnectionResult.connecionEstablished INSTANCE = null;
        
        private connecionEstablished() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/electromaze/data/bluetooth/ConnectionResult$connectionFailed;", "Lcom/example/electromaze/data/bluetooth/ConnectionResult;", "()V", "app_debug"})
    public static final class connectionFailed implements com.example.electromaze.data.bluetooth.ConnectionResult {
        @org.jetbrains.annotations.NotNull
        public static final com.example.electromaze.data.bluetooth.ConnectionResult.connectionFailed INSTANCE = null;
        
        private connectionFailed() {
            super();
        }
    }
}