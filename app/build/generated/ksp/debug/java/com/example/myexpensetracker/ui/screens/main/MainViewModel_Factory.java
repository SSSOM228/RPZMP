package com.example.myexpensetracker.ui.screens.main;

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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public MainViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static MainViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new MainViewModel_Factory(transactionRepositoryProvider);
  }

  public static MainViewModel newInstance(TransactionRepository transactionRepository) {
    return new MainViewModel(transactionRepository);
  }
}
