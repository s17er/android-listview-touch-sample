package net.kaleidworks.listviewtouchsample;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private final Context context;
    protected final LayoutInflater inflater;
    private List<Entity> entityList = new ArrayList<>();

    public ListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public int getCount() {
        return entityList.size();
    }

    @Override
    public Entity getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView view;
        if(convertView != null && convertView instanceof ListItemView) {
            view = (ListItemView)convertView;
        } else {
            view = (ListItemView)this.inflater.inflate(R.layout.listitem, null);
        }

        final Entity entity = getItem(position);

        view.setTitle(String.format("No.%02d %s", entity.no, entity.title));

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(entity.url);
        builder.setSpan(new CustomClickableSpan() {
            @Override
            public void onLongClick(View widget) {
                Toast.makeText(context, "Url long click " + entity.url + " " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View widget) {
                Toast.makeText(context, "Url click " + entity.url + " " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
            }
        }, 0, entity.url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" " + entity.contents);
        view.setContents(builder);

        return view;
    }
}
