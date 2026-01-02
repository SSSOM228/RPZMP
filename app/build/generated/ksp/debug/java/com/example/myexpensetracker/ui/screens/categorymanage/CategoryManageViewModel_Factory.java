package com.example.myexpensetracker.ui.screens.categorymanage;

import com.example.myexpensetracker.repositories.CategoryRepository;
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
public final class CategoryManageViewModel_Factory implements Factory<CategoryManageViewModel> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public CategoryManageViewModel_Factory(Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public CategoryManageViewModel get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static CategoryManageViewModel_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new CategoryManageViewModel_Factory(categoryRepositoryProvider);
  }

  public static CategoryManageViewModel newInstance(CategoryRepository categoryRepository) {
    return new CategoryManageViewModel(categoryRepository);
  }
}
