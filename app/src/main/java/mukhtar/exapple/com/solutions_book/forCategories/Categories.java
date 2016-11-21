package mukhtar.exapple.com.solutions_book.forCategories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import mukhtar.exapple.com.solutions_book.R;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ExpandableListView lv = (ExpandableListView) findViewById(R.id.elv_for_categories);
        SimpleExpandableListAdapter adapter = (new AdapterHelper(this)).getAdapter();
        if(adapter!=null)
        lv.setAdapter(adapter);

    }
}
