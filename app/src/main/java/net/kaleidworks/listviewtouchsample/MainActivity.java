package net.kaleidworks.listviewtouchsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);

        final ListAdapter adapter = new ListAdapter(this);
        adapter.setEntityList(createEntityList());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entity e = adapter.getItem(position);
                Toast.makeText(MainActivity.this, String.format("Item Click %s %s", e.no,
                        System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Entity e = adapter.getItem(position);
                Toast.makeText(MainActivity.this, String.format("Item Long Click %s %s", e.no,
                        System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private List<Entity> createEntityList() {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Entity e = new Entity();
            e.no = i;
            e.title = "Title";
            e.contents = "dummy text. dummy text. dummy text. dummy text. dummy text. dummy text. dummy text. dummy text. ";
            e.url = "http://blog.s17er.com";
            entities.add(e);
        }
        return entities;
    }
}
