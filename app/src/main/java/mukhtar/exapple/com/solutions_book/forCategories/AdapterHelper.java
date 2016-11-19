package mukhtar.exapple.com.solutions_book.forCategories;

import java.util.ArrayList;
import java. util. HashMap;
import java. util. Map;
import android.content.Context;
import android.widget.SimpleExpandableListAdapter;
import mukhtar.exapple.com.solutions_book.R;


public class AdapterHelper {
    AdapterHelper(Context _ctx) {ctx = _ctx;}
    final String PARENT_CATEGORY_NAME = "groupName";
    final String CHILD_ID = "childId";
    final String PARENT_ID = "childId";
    final String CHILD_PARENT_CATEGORY_NAME = "childName";
    HashMap<String,Integer> cat_id = new HashMap<>();
    ArrayList<Map<String, String>> groupData = new ArrayList<>();
    ArrayList<Map<String, String>> childDataItem ;
    ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();
    Map<String, String> m;

    SimpleExpandableListAdapter adapter;
    Context ctx;

    //Methods
    SimpleExpandableListAdapter getAdapter() {

        HashMap <String,String[]> parent_categories = new HashMap<>();
        parent_categories.put("Math",new String[]{"Discrete Mathematics","Mathematical Analysis"});
        parent_categories.put("Physics",new String[]{"Electricity","Magnetizm","Mechanics"});
        parent_categories.put("Programming",new String[]{"Java","Linux","Python","C++"});


        groupData = new ArrayList<>();
        childData = new ArrayList<>();

        //Filling ArrayLists
        int groupCounter = 1;
        for(String cat : parent_categories.keySet()){
            HashMap hp = new HashMap();
            hp.put(PARENT_CATEGORY_NAME,cat);
            hp.put(PARENT_ID,groupCounter+++"");
            groupData.add(hp);

            int childCounter = 1;
            childDataItem = new ArrayList<>();
            for(String child:parent_categories.get(cat)){
                HashMap hc = new HashMap();
                hc.put(CHILD_PARENT_CATEGORY_NAME,child);
                hc.put(CHILD_ID,childCounter+++"");
                childDataItem.add(hc);
            }
            childData.add(childDataItem);
        }

        String groupFrom[] = new String[]{PARENT_ID,PARENT_CATEGORY_NAME};
        int groupTo[] = new int[]{R.id.ID,R.id.name_of_category};
        String childFrom[] = new String[]{CHILD_ID,CHILD_PARENT_CATEGORY_NAME};
        int childTo[] = new int[]{R.id.ID,R.id.name_of_category};
        adapter = new SimpleExpandableListAdapter(
                ctx,
                groupData,
                R.layout.item_of_category,
                groupFrom,
                groupTo,
                childData,
                R.layout.item_of_category,
                childFrom,
                childTo);
        return adapter;
    }

}