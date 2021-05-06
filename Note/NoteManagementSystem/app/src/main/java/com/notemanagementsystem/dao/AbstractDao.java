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

    @Update
    public abstract void update(@NotNull T item);

    public List<T> getAllById(Integer userId) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " where userId = ? and isDeleted = 0",
                new Object[]{userId}
        );
        return dofindAllByUserID(query);
    }

    public T getById(Integer id) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " where id = ? and isDeleted = 0",
                new Object[]{id}
        );
        return dofindById(query);
    }

    public void delete(@NotNull T item) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "update " + getTableName() +
                        " set isDeleted = 1 " +
                        " where userId = ? and id = ?",
                new Object[]{item.getUserId(),item.getId()});


        deleteByItem(query);
    }

    public boolean checkNoteExits(@NotNull T item) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "Select count(*)>0 from note where userId= ? and "+ getTableName()+"Id"+ " = ?",
                new Object[]{item.getUserId(),item.getId()});

        return doCheckExits(query)>0;
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
    @RawQuery
    protected abstract T dofindById(SupportSQLiteQuery query);
    @RawQuery
    protected abstract T deleteByItem(SupportSQLiteQuery query);

    @RawQuery
    protected abstract int doCheckExits(SupportSQLiteQuery query);
}
