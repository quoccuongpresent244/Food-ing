// Generated by view binder compiler. Do not edit!
package com.example.mapstest.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.mapstest.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RestaurantItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView imageView10;

  @NonNull
  public final ImageView imageView11;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  private RestaurantItemBinding(@NonNull LinearLayout rootView, @NonNull ImageView imageView10,
      @NonNull ImageView imageView11, @NonNull ImageView imageView3, @NonNull ImageView imageView8,
      @NonNull ImageView imageView9, @NonNull TextView textView4, @NonNull TextView textView5,
      @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8) {
    this.rootView = rootView;
    this.imageView10 = imageView10;
    this.imageView11 = imageView11;
    this.imageView3 = imageView3;
    this.imageView8 = imageView8;
    this.imageView9 = imageView9;
    this.textView4 = textView4;
    this.textView5 = textView5;
    this.textView6 = textView6;
    this.textView7 = textView7;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RestaurantItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RestaurantItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.restaurant_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RestaurantItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView10;
      ImageView imageView10 = rootView.findViewById(id);
      if (imageView10 == null) {
        break missingId;
      }

      id = R.id.imageView11;
      ImageView imageView11 = rootView.findViewById(id);
      if (imageView11 == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = rootView.findViewById(id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = rootView.findViewById(id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.imageView9;
      ImageView imageView9 = rootView.findViewById(id);
      if (imageView9 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = rootView.findViewById(id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = rootView.findViewById(id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = rootView.findViewById(id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = rootView.findViewById(id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = rootView.findViewById(id);
      if (textView8 == null) {
        break missingId;
      }

      return new RestaurantItemBinding((LinearLayout) rootView, imageView10, imageView11,
          imageView3, imageView8, imageView9, textView4, textView5, textView6, textView7,
          textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
