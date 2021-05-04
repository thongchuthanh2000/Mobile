package com.notemanagementsystem.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.notemanagementsystem.entity.AbstractEntity;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Dao
public abstract class AbstractDao<T extends AbstractEntity>  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(@NotNull T item);

    @Delete
    public abstract void delete(@NotNull T item);

    @Update
    public abstract void update(@NotNull T item);

    public List<T> getAllById(Integer userId) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " where userId = ?",
                new Object[]{userId}
        );
        return dofindAllByUserID(query);
    }

    public String getTableName() {

        Class clazz = (Class)
                ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
        // tableName = StringUtil.toSnakeCase(clazz.getSimpleName());
        String tableName = clazz.getSimpleName();
        return tableName;
    }
    @RawQuery
    protected abstract List<T> dofindAllByUserID(SupportSQLiteQuery query);
}
