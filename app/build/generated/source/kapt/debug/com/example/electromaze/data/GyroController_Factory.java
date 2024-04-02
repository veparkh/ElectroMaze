// Generated by Dagger (https://dagger.dev).
package com.example.electromaze.data;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class GyroController_Factory implements Factory<GyroController> {
  private final Provider<Context> contextProvider;

  public GyroController_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GyroController get() {
    return newInstance(contextProvider.get());
  }

  public static GyroController_Factory create(Provider<Context> contextProvider) {
    return new GyroController_Factory(contextProvider);
  }

  public static GyroController newInstance(Context context) {
    return new GyroController(context);
  }
}