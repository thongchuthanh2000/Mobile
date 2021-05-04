package com.notemanagementsystem.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.adapter.GenericAdapter;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.adapter.CategoryAdapter;
import com.notemanagementsystem.entity.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryFragment extends AbstractFragment<Category>{
}