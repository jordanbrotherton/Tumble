package com.jordanjbrotherton.tumble.drawer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.jordanjbrotherton.tumble.R;

import java.util.ArrayList;
import java.util.List;

public class AppDrawerFragment extends BottomSheetDialogFragment {
    RecyclerView appDrawer;
    AppDrawerAdapter appAdapter;
    TextInputEditText appSearch;

    public List<ApplicationInfo> appList;

    public AppDrawerFragment(AppDrawerAdapter appAdapterMain) {
        appAdapter = appAdapterMain;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.app_drawer_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ApplicationInfo> filteredList = new ArrayList<>();

        appDrawer = view.findViewById(R.id.appDrawer);
        appDrawer.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        appDrawer.setAdapter(appAdapter);

        appSearch = view.findViewById(R.id.appSearchEditText);
        appSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterText = editable.toString();
                filter(filterText);
            }
        });
    }

    private void filter(String text) {
        ArrayList<ApplicationInfo> filteredList = new ArrayList<>();
        for (ApplicationInfo item : appAdapter.applications) {
            if (item.label.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        appAdapter.filterList(filteredList);
    }
}
