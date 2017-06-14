package com.example.ramu.chatfirebase.EventAdmin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ramu.chatfirebase.R;

/**
 * Created by Ramu on 06-06-2017.
 */

public class BottomSheetDialogFragment1 extends android.support.design.widget.BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_modal, container, false);
        return v;
    }
}
