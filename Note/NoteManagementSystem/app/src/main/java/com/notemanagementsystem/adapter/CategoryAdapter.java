package com.notemanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Category;

import java.text.SimpleDateFormat;
import java.util.List;
/*
*CategoryAdapter Of RecyclerView Category
*@author  Quang Hung
* @version 1.0
* @since   2021-04-24
 */
public class CategoryAdapter extends GenericAdapter<Category> {
    public CategoryAdapter(IClickItem iClickItem) {
        super(iClickItem);
    }
}
