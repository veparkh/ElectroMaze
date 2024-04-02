package com.example.electromaze.presentation.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a8\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a(\u0010\t\u001a\u00020\u00012\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u0018\u0010\f\u001a\u00020\u00012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a=\u0010\r\u001a\u00020\u00012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2#\b\u0002\u0010\u000e\u001a\u001d\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u00a8\u0006\u0014"}, d2 = {"AutoModeScreen", "", "image", "Landroidx/compose/runtime/State;", "Landroid/graphics/Bitmap;", "coord", "Landroid/graphics/Point;", "backButtonPressed", "Lkotlin/Function0;", "ManualModeScreen", "angles", "Lcom/example/electromaze/data/bluetooth/Angles;", "MapBuildingModeScreen", "ModeChoiceScreen", "modePressed", "Lkotlin/Function1;", "Lcom/example/electromaze/presentation/screens/Modes;", "Lkotlin/ParameterName;", "name", "mode", "app_debug"})
public final class ModeChoiceScreenKt {
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void ModeChoiceScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> backButtonPressed, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.electromaze.presentation.screens.Modes, kotlin.Unit> modePressed) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AutoModeScreen(@org.jetbrains.annotations.NotNull
    androidx.compose.runtime.State<android.graphics.Bitmap> image, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.State<? extends android.graphics.Point> coord, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> backButtonPressed) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ManualModeScreen(@org.jetbrains.annotations.NotNull
    androidx.compose.runtime.State<com.example.electromaze.data.bluetooth.Angles> angles, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> backButtonPressed) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MapBuildingModeScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> backButtonPressed) {
    }
}