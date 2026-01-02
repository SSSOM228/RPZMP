package com.example.myexpensetracker;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.myexpensetracker.localdb.AppDatabase;
import com.example.myexpensetracker.localdb.AppModule_ProvideAppDatabaseFactory;
import com.example.myexpensetracker.localdb.AppModule_ProvideCategoryDaoFactory;
import com.example.myexpensetracker.localdb.AppModule_ProvideTransactionDaoFactory;
import com.example.myexpensetracker.localdb.dao.CategoryDao;
import com.example.myexpensetracker.localdb.dao.TransactionDao;
import com.example.myexpensetracker.repositories.CategoryRepository;
import com.example.myexpensetracker.repositories.TransactionRepository;
import com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionViewModel;
import com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionViewModel_HiltModules;
import com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageViewModel;
import com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageViewModel_HiltModules;
import com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionViewModel;
import com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionViewModel_HiltModules;
import com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionViewModel;
import com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionViewModel_HiltModules;
import com.example.myexpensetracker.ui.screens.main.MainViewModel;
import com.example.myexpensetracker.ui.screens.main.MainViewModel_HiltModules;
import com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel;
import com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerExpenseTrackerApp_HiltComponents_SingletonC {
  private DaggerExpenseTrackerApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public ExpenseTrackerApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements ExpenseTrackerApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements ExpenseTrackerApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements ExpenseTrackerApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements ExpenseTrackerApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements ExpenseTrackerApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements ExpenseTrackerApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements ExpenseTrackerApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public ExpenseTrackerApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends ExpenseTrackerApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends ExpenseTrackerApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends ExpenseTrackerApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends ExpenseTrackerApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(6).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel, AddTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel, CategoryManageViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel, EditTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel, HistoryTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_main_MainViewModel, MainViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel, StatsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel = "com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel = "com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageViewModel";

      static String com_example_myexpensetracker_ui_screens_main_MainViewModel = "com.example.myexpensetracker.ui.screens.main.MainViewModel";

      static String com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel = "com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel = "com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel = "com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel";

      @KeepFieldType
      EditTransactionViewModel com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel2;

      @KeepFieldType
      CategoryManageViewModel com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel2;

      @KeepFieldType
      MainViewModel com_example_myexpensetracker_ui_screens_main_MainViewModel2;

      @KeepFieldType
      HistoryTransactionViewModel com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel2;

      @KeepFieldType
      AddTransactionViewModel com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel2;

      @KeepFieldType
      StatsViewModel com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends ExpenseTrackerApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddTransactionViewModel> addTransactionViewModelProvider;

    private Provider<CategoryManageViewModel> categoryManageViewModelProvider;

    private Provider<EditTransactionViewModel> editTransactionViewModelProvider;

    private Provider<HistoryTransactionViewModel> historyTransactionViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<StatsViewModel> statsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private TransactionRepository transactionRepository() {
      return new TransactionRepository(singletonCImpl.transactionDao());
    }

    private CategoryRepository categoryRepository() {
      return new CategoryRepository(singletonCImpl.categoryDao());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.categoryManageViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.editTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.historyTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.statsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(6).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel, ((Provider) addTransactionViewModelProvider)).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel, ((Provider) categoryManageViewModelProvider)).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel, ((Provider) editTransactionViewModelProvider)).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel, ((Provider) historyTransactionViewModelProvider)).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_main_MainViewModel, ((Provider) mainViewModelProvider)).put(LazyClassKeyProvider.com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel, ((Provider) statsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_example_myexpensetracker_ui_screens_main_MainViewModel = "com.example.myexpensetracker.ui.screens.main.MainViewModel";

      static String com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel = "com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel = "com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel = "com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageViewModel";

      static String com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel = "com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionViewModel";

      static String com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel = "com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel";

      @KeepFieldType
      MainViewModel com_example_myexpensetracker_ui_screens_main_MainViewModel2;

      @KeepFieldType
      AddTransactionViewModel com_example_myexpensetracker_ui_screens_addtransaction_AddTransactionViewModel2;

      @KeepFieldType
      HistoryTransactionViewModel com_example_myexpensetracker_ui_screens_historytransaction_HistoryTransactionViewModel2;

      @KeepFieldType
      CategoryManageViewModel com_example_myexpensetracker_ui_screens_categorymanage_CategoryManageViewModel2;

      @KeepFieldType
      EditTransactionViewModel com_example_myexpensetracker_ui_screens_edittransaction_EditTransactionViewModel2;

      @KeepFieldType
      StatsViewModel com_example_myexpensetracker_ui_screens_statscreen_StatsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionViewModel 
          return (T) new AddTransactionViewModel(viewModelCImpl.transactionRepository(), viewModelCImpl.categoryRepository());

          case 1: // com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageViewModel 
          return (T) new CategoryManageViewModel(viewModelCImpl.categoryRepository());

          case 2: // com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionViewModel 
          return (T) new EditTransactionViewModel(viewModelCImpl.transactionRepository(), viewModelCImpl.categoryRepository(), viewModelCImpl.savedStateHandle);

          case 3: // com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionViewModel 
          return (T) new HistoryTransactionViewModel(viewModelCImpl.transactionRepository(), viewModelCImpl.categoryRepository());

          case 4: // com.example.myexpensetracker.ui.screens.main.MainViewModel 
          return (T) new MainViewModel(viewModelCImpl.transactionRepository());

          case 5: // com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel 
          return (T) new StatsViewModel(viewModelCImpl.transactionRepository());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends ExpenseTrackerApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends ExpenseTrackerApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends ExpenseTrackerApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private TransactionDao transactionDao() {
      return AppModule_ProvideTransactionDaoFactory.provideTransactionDao(provideAppDatabaseProvider.get());
    }

    private CategoryDao categoryDao() {
      return AppModule_ProvideCategoryDaoFactory.provideCategoryDao(provideAppDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 0));
    }

    @Override
    public void injectExpenseTrackerApp(ExpenseTrackerApp expenseTrackerApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.myexpensetracker.localdb.AppDatabase 
          return (T) AppModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
