package com.example.myexpensetracker.ui.screens.edittransaction;

import androidx.lifecycle.SavedStateHandle;
import com.example.myexpensetracker.repositories.CategoryRepository;
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
public final class EditTransactionViewModel_Factory implements Factory<EditTransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public EditTransactionViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public EditTransactionViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static EditTransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new EditTransactionViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, savedStateHandleProvider);
  }

  public static EditTransactionViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, SavedStateHandle savedStateHandle) {
    return new EditTransactionViewModel(transactionRepository, categoryRepository, savedStateHandle);
  }
}
