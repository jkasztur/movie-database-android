package com.jkasztur.android.moviedatabase.adapter;

import android.text.InputFilter;
import android.text.Spanned;

public class RangeNumFilter implements InputFilter {
    private int min;
    private int max;

    public RangeNumFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        try {
            int input = Integer.parseInt(spanned.toString() + charSequence.toString());
            if (isInRange(input)) {
                return null;
            }
            return "";
        } catch (NumberFormatException ex) {
            return "";
        }
    }

    private boolean isInRange(int num) {
        return min <= max &&
                min <= num &&
                num <= max;
    }
}
