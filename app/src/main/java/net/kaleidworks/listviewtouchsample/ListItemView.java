package net.kaleidworks.listviewtouchsample;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListItemView extends LinearLayout {
    
    private TextView title;
    private TextView contents;
    
    public ListItemView(Context context) {
        super(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(isInEditMode()) return; // for preview on Android Studio
        title = (TextView)findViewById(R.id.title);
        contents = (TextView)findViewById(R.id.contents);
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public void setContents(CharSequence contents) {
        this.contents.setText(contents);
    }

    public void setContents(SpannableStringBuilder builder) {
        contents.setText(builder);
        contents.setMovementMethod(CustomLinkMovementMethod.getInstance(getContext()));
        contents.setClickable(false);
        contents.setLongClickable(false);
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }

}
