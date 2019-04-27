package com.jkasztur.android.moviedatabase.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jkasztur.android.moviedatabase.R;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class CustomBindings {
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = null;
        switch (recyclerView.getResources().getConfiguration().orientation) {
            case ORIENTATION_LANDSCAPE:
                manager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            default:
                manager = new GridLayoutManager(recyclerView.getContext(), 3);
                break;
        }
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

    @BindingAdapter("setFilter")
    public static void bindEditFilter(EditText editText, InputFilter filter) {
        if (filter != null) {
            editText.setFilters(new InputFilter[]{filter});
        }
    }

    @BindingAdapter("android:background")
    public static void setButtonBackground(ImageButton button, int resource) {
        button.setBackground(button.getResources().getDrawable(resource));
    }

    @BindingAdapter("android:text")
    public static void bindListToText(TextView view, List<String> list) {
        if (list != null) {
            String str = list.toString();
            str = str.replace("[","");
            str = str.replace("]","");
            view.setText(str);
        }
    }
}
