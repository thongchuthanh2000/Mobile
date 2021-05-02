package com.notemanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
/*
 *PriorityAdapter Of RecyclerView Priority
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class PriorityAdapter extends GenericAdapter<Priority> {
    public PriorityAdapter(IClickItem iClickItem) {
        super(iClickItem);
    }
}
