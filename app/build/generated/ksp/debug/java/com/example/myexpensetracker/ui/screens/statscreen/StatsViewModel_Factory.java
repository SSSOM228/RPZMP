package com.example.myexpensetracker.ui.screens.statscreen;

import com.example.myexpensetracker.repositories.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class StatsViewModel_Factory implements Factory<StatsViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public StatsViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public StatsViewModel get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static StatsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new StatsViewModel_Factory(transactionRepositoryProvider);
  }

  public static StatsViewModel newInstance(TransactionRepository transactionRepository) {
    return new StatsViewModel(transactionRepository);
  }
}
