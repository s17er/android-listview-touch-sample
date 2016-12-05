package net.kaleidworks.listviewtouchsample;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;


public class CustomLinkMovementMethod extends BaseMovementMethod {

    private final static String TAG = CustomLinkMovementMethod.class.getSimpleName();

    private static CustomLinkMovementMethod sInstance;

    private TextView currentWidget;
    private GestureDetector mGestureDetector;
    private CustomClickableSpan pressingSpan;
    private float pressingSpanX = Integer.MIN_VALUE;
    private float pressingSpanY = Integer.MIN_VALUE;

    public static MovementMethod getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CustomLinkMovementMethod(context);

        }
        return sInstance;
    }

    public CustomLinkMovementMethod(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureListener(), null, true);
    }

    @Override
    public boolean onTouchEvent(TextView widget,
                                Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        Log.v(TAG, "onTouchEvent " + action);

        int x = (int) event.getX();
        int y = (int) event.getY();
        this.currentWidget = widget;

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(
                    off, off, ClickableSpan.class);

            if (link.length != 0) {
                // タッチ位置にスパンが存在する場合
                if (action == MotionEvent.ACTION_UP) {
                    Selection.removeSelection(buffer);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    setPressingSpan(link[0]);
                    this.pressingSpanX = x;
                    this.pressingSpanY = y;
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }
                mGestureDetector.onTouchEvent(event);
                return true;
            } else {
                // タッチ位置にスパンが存在しない場合
                clearPressingSpan();
                Selection.removeSelection(buffer);
                // Touch.onTouchEventを通すとDOWNの時にtrueが返り、
                // ListView#onItemClickを発動できなくなる。
                return false;
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            // 一定以上動いた場合はロングタップイベントを発行しないようんする
            if (Math.abs(this.pressingSpanX - x) > 50 || Math.abs(this.pressingSpanY - y) > 50) {
                clearPressingSpan();
            }
        }

        return Touch.onTouchEvent(widget, buffer, event);
    }

    private void setPressingSpan(ClickableSpan span) {
        this.pressingSpan = (CustomClickableSpan)span;
    }
    private void clearPressingSpan() {
        this.pressingSpan = null;
    }

    private class GestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (pressingSpan != null) {
                pressingSpan.onClick(currentWidget);
                clearPressingSpan();
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2,float distanceX, float distanceY){
            return false;
        }

        @Override
        public void onLongPress (MotionEvent e){
            if (pressingSpan != null) {
                pressingSpan.onLongClick(currentWidget);
                clearPressingSpan();
            }
        }

        @Override
        public boolean onFling (MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
            return false;
        }
    }

}
