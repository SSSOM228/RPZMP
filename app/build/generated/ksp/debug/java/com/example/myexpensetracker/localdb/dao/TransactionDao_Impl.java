package com.example.myexpensetracker.localdb.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.myexpensetracker.data.enums.TransactionType;
import com.example.myexpensetracker.localdb.entities.CategoryEntity;
import com.example.myexpensetracker.localdb.entities.TransactionEntity;
import com.example.myexpensetracker.localdb.entities.TransactionWithCategory;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __deletionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`amount`,`categoryId`,`date`,`note`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getAmount());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindLong(4, entity.getDate());
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
      }
    };
    this.__deletionAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`amount` = ?,`categoryId` = ?,`date` = ?,`note` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getAmount());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindLong(4, entity.getDate());
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
        statement.bindLong(6, entity.getId());
      }
    };
  }

  @Override
  public Object insertTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransactionEntity.insert(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTransaction(final TransactionEntity transaction,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<TransactionEntity>> $completion) {
    final String _sql = "SELECT * FROM transactions ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TransactionWithCategory> getTransactionById(final int tId) {
    final String _sql = "SELECT * FROM transactions WHERE id = (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"categories",
        "transactions"}, new Callable<TransactionWithCategory>() {
      @Override
      @Nullable
      public TransactionWithCategory call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
              _collectionCategory.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(_collectionCategory);
            final TransactionWithCategory _result;
            if (_cursor.moveToFirst()) {
              final TransactionEntity _tmpTransaction;
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpAmount;
              _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
              final int _tmpCategoryId;
              _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
              final long _tmpDate;
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
              final String _tmpNote;
              if (_cursor.isNull(_cursorIndexOfNote)) {
                _tmpNote = null;
              } else {
                _tmpNote = _cursor.getString(_cursorIndexOfNote);
              }
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
              final CategoryEntity _tmpCategory;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
              _tmpCategory = _collectionCategory.get(_tmpKey_1);
              _result = new TransactionWithCategory(_tmpTransaction,_tmpCategory);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTransactionsByCategoryId(final int cId,
      final Continuation<? super List<TransactionEntity>> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE categoryId = (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TransactionWithCategory>> getFilteredTransactions(final long startDate,
      final long endDate, final boolean isAllTime, final List<? extends TransactionType> types,
      final List<Integer> categoryIds, final boolean isAllCategories) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("\n");
    _stringBuilder.append("    SELECT transactions.* FROM transactions ");
    _stringBuilder.append("\n");
    _stringBuilder.append("    INNER JOIN categories ON transactions.categoryId = categories.id");
    _stringBuilder.append("\n");
    _stringBuilder.append("    WHERE ");
    _stringBuilder.append("\n");
    _stringBuilder.append("        -- 1. Time filter");
    _stringBuilder.append("\n");
    _stringBuilder.append("        (");
    _stringBuilder.append("?");
    _stringBuilder.append(" = 1 OR (date >= ");
    _stringBuilder.append("?");
    _stringBuilder.append(" AND date <= ");
    _stringBuilder.append("?");
    _stringBuilder.append("))");
    _stringBuilder.append("\n");
    _stringBuilder.append("        AND");
    _stringBuilder.append("\n");
    _stringBuilder.append("        -- 2. Type filter");
    _stringBuilder.append("\n");
    _stringBuilder.append("        categories.type IN (");
    final int _inputSize = types.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    _stringBuilder.append("\n");
    _stringBuilder.append("        AND");
    _stringBuilder.append("\n");
    _stringBuilder.append("        -- 3. Category filter");
    _stringBuilder.append("\n");
    _stringBuilder.append("        (");
    _stringBuilder.append("?");
    _stringBuilder.append(" = 1 OR categoryId IN (");
    final int _inputSize_1 = categoryIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize_1);
    _stringBuilder.append("))");
    _stringBuilder.append("\n");
    _stringBuilder.append("    ORDER BY date DESC");
    _stringBuilder.append("\n");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 4 + _inputSize + _inputSize_1;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    final int _tmp = isAllTime ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endDate);
    _argIndex = 4;
    for (TransactionType _item : types) {
      _statement.bindString(_argIndex, __TransactionType_enumToString(_item));
      _argIndex++;
    }
    _argIndex = 4 + _inputSize;
    final int _tmp_1 = isAllCategories ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp_1);
    _argIndex = 5 + _inputSize;
    for (int _item_1 : categoryIds) {
      _statement.bindLong(_argIndex, _item_1);
      _argIndex++;
    }
    return CoroutinesRoom.createFlow(__db, true, new String[] {"categories",
        "transactions"}, new Callable<List<TransactionWithCategory>>() {
      @Override
      @NonNull
      public List<TransactionWithCategory> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
              _collectionCategory.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(_collectionCategory);
            final List<TransactionWithCategory> _result = new ArrayList<TransactionWithCategory>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final TransactionWithCategory _item_2;
              final TransactionEntity _tmpTransaction;
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpAmount;
              _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
              final int _tmpCategoryId;
              _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
              final long _tmpDate;
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
              final String _tmpNote;
              if (_cursor.isNull(_cursorIndexOfNote)) {
                _tmpNote = null;
              } else {
                _tmpNote = _cursor.getString(_cursorIndexOfNote);
              }
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
              final CategoryEntity _tmpCategory;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
              _tmpCategory = _collectionCategory.get(_tmpKey_1);
              _item_2 = new TransactionWithCategory(_tmpTransaction,_tmpCategory);
              _result.add(_item_2);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Double> getTotalBalance() {
    final String _sql = "\n"
            + "    SELECT COALESCE(SUM(\n"
            + "        CASE \n"
            + "            WHEN c.type = 'INCOME' THEN t.amount \n"
            + "            ELSE -t.amount \n"
            + "        END\n"
            + "    ), 0.0)\n"
            + "    FROM transactions AS t\n"
            + "    INNER JOIN categories AS c ON t.categoryId = c.id\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions",
        "categories"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TransactionWithCategory>> getTransactionsForPeriod(final long startDate,
      final long endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM transactions WHERE date >= ? AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"categories",
        "transactions"}, new Callable<List<TransactionWithCategory>>() {
      @Override
      @NonNull
      public List<TransactionWithCategory> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
              _collectionCategory.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(_collectionCategory);
            final List<TransactionWithCategory> _result = new ArrayList<TransactionWithCategory>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final TransactionWithCategory _item;
              final TransactionEntity _tmpTransaction;
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpAmount;
              _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
              final int _tmpCategoryId;
              _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
              final long _tmpDate;
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
              final String _tmpNote;
              if (_cursor.isNull(_cursorIndexOfNote)) {
                _tmpNote = null;
              } else {
                _tmpNote = _cursor.getString(_cursorIndexOfNote);
              }
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
              final CategoryEntity _tmpCategory;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
              _tmpCategory = _collectionCategory.get(_tmpKey_1);
              _item = new TransactionWithCategory(_tmpTransaction,_tmpCategory);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TransactionWithCategory>> getRecentTransactions(final int limit) {
    final String _sql = "\n"
            + "        SELECT * FROM transactions ORDER BY date DESC LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"categories",
        "transactions"}, new Callable<List<TransactionWithCategory>>() {
      @Override
      @NonNull
      public List<TransactionWithCategory> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
              _collectionCategory.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(_collectionCategory);
            final List<TransactionWithCategory> _result = new ArrayList<TransactionWithCategory>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final TransactionWithCategory _item;
              final TransactionEntity _tmpTransaction;
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpAmount;
              _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
              final int _tmpCategoryId;
              _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
              final long _tmpDate;
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
              final String _tmpNote;
              if (_cursor.isNull(_cursorIndexOfNote)) {
                _tmpNote = null;
              } else {
                _tmpNote = _cursor.getString(_cursorIndexOfNote);
              }
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpAmount,_tmpCategoryId,_tmpDate,_tmpNote);
              final CategoryEntity _tmpCategory;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
              _tmpCategory = _collectionCategory.get(_tmpKey_1);
              _item = new TransactionWithCategory(_tmpTransaction,_tmpCategory);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(
      @NonNull final LongSparseArray<CategoryEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipcategoriesAscomExampleMyexpensetrackerLocaldbEntitiesCategoryEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`iconName`,`colorHex`,`type`,`isArchived` FROM `categories` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfIconName = 2;
      final int _cursorIndexOfColorHex = 3;
      final int _cursorIndexOfType = 4;
      final int _cursorIndexOfIsArchived = 5;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final CategoryEntity _item_1;
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          final String _tmpIconName;
          _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
          final long _tmpColorHex;
          _tmpColorHex = _cursor.getLong(_cursorIndexOfColorHex);
          final String _tmpType;
          _tmpType = _cursor.getString(_cursorIndexOfType);
          final boolean _tmpIsArchived;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
          _tmpIsArchived = _tmp != 0;
          _item_1 = new CategoryEntity(_tmpId,_tmpName,_tmpIconName,_tmpColorHex,_tmpType,_tmpIsArchived);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private String __TransactionType_enumToString(@NonNull final TransactionType _value) {
    switch (_value) {
      case EXPENSE: return "EXPENSE";
      case INCOME: return "INCOME";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }
}
