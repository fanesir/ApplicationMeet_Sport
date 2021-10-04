package com.example.myapplication_MeetSport;

import android.text.Editable;
import android.text.NoCopySpan;

public interface TextWatcher extends NoCopySpan {

    void afterTextChanged(Editable var1);
}
