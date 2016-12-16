package mukhtar.exapple.com.solutions_book.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;


public class ShowMyBooks extends Fragment {
    ListView lv;
    SimpleAdapter sa;
    ArrayList<Map<String, String>> data = new ArrayList();
    String id_of_selected_menu;
    final int MENU_EDIT = 1;
    final int MENU_DELETE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_my_books, container, false);
        lv = (ListView)view.findViewById(R.id.listview_my_books);

        registerForContextMenu(lv);
        sa = new SimpleAdapter(getActivity(), data, R.layout.item_my_book,
                new String[]{"id", "name","phone"}, new int[]{R.id.textview_name_of_book, R.id.textview_author_of_book,
                R.id.textview_link_of_book});
        sa.notifyDataSetChanged();
        lv.setAdapter(sa);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo ad=(AdapterView.AdapterContextMenuInfo)menuInfo;
        id_of_selected_menu=(String) ((HashMap)lv.getItemAtPosition(ad.position)).get("id");

        menu.add(0, MENU_EDIT, 0, "Edit");
        menu.add(0, MENU_DELETE, 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_EDIT:

                break;
            case MENU_DELETE:

                break;
        }
        return super.onContextItemSelected(item);
    }





}
