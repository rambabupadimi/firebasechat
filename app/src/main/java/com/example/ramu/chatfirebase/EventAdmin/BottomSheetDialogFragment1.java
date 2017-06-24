package com.example.ramu.chatfirebase.EventAdmin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramu.chatfirebase.R;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

/**
 * Created by Ramu on 06-06-2017.
 */

public class BottomSheetDialogFragment1 extends android.support.design.widget.BottomSheetDialogFragment {

    ImageView bottomSheet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_modal, container, false);
        bottomSheet = (ImageView) v.findViewById(R.id.bottom_qrcode);
        VCard abhay=new VCard("Abhay")
                .setEmail("anand.abhay1910@gmail.com")
                .setAddress("India")
                .setTitle("Tutorial")
                .setCompany("studytutorial")
                .setPhoneNumber("258999")
                .setWebsite("www.studytutorial.in");
        Bitmap myBitmap= QRCode.from(abhay).bitmap();
        bottomSheet.setImageBitmap(myBitmap);
        return v;
    }
}
