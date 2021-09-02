// Generated by view binder compiler. Do not edit!
package com.example.mapstest.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.mapstest.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDiscoveryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton imageButton5;

  @NonNull
  public final ImageView imageView10;

  @NonNull
  public final ImageView imageView11;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final TextView textView5;

  private ActivityDiscoveryBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton imageButton5, @NonNull ImageView imageView10,
      @NonNull ImageView imageView11, @NonNull ImageView imageView2, @NonNull ImageView imageView8,
      @NonNull ImageView imageView9, @NonNull TextView textView5) {
    this.rootView = rootView;
    this.imageButton5 = imageButton5;
    this.imageView10 = imageView10;
    this.imageView11 = imageView11;
    this.imageView2 = imageView2;
    this.imageView8 = imageView8;
    this.imageView9 = imageView9;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDiscoveryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDiscoveryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_discovery, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDiscoveryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageButton5;
      ImageButton imageButton5 = rootView.findViewById(id);
      if (imageButton5 == null) {
        break missingId;
      }

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

      id = R.id.imageView2;
      ImageView imageView2 = rootView.findViewById(id);
      if (imageView2 == null) {
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

      id = R.id.textView5;
      TextView textView5 = rootView.findViewById(id);
      if (textView5 == null) {
        break missingId;
      }

      return new ActivityDiscoveryBinding((ConstraintLayout) rootView, imageButton5, imageView10,
          imageView11, imageView2, imageView8, imageView9, textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
