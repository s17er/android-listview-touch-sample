package net.kaleidworks.listviewtouchsample;

import android.text.style.ClickableSpan;
import android.view.View;

public abstract class CustomClickableSpan extends ClickableSpan {

    public abstract void onLongClick(View widget);

}
