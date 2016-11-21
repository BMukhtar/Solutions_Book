package mukhtar.exapple.com.solutions_book.forCategories;

import java.util.ArrayList;
import java. util. HashMap;
import java. util. Map;
import android.content.Context;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import mukhtar.exapple.com.solutions_book.DatabaseInteraction;
import mukhtar.exapple.com.solutions_book.R;


public class AdapterHelper {
    AdapterHelper(Context _ctx) {ctx = _ctx;}
    final String GROUP_CATEGORY_NAME = "groupName";
    final String CHILD_ID = "childId";
    final String GROUP_ID = "groupId";
    final String CHILD_CATEGORY_NAME = "childName";
    HashMap<String,String> current_child_hashmap = new HashMap<>();
    HashMap<String,String> current_group_hashmap = new HashMap<>();
    ArrayList<Map<String, String>> groupData = new ArrayList<>();
    ArrayList<Map<String, String>> childDataItem = new ArrayList<>();
    ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();
    ArrayList <String> counting_groups = new ArrayList<>();

    SimpleExpandableListAdapter adapter = null;
    Context ctx;

    //Methods
    SimpleExpandableListAdapter getAdapter() {

        String url1 = "https://arcane-peak-68343.herokuapp.com/for_result.php";
        String query1 = "SELECT * FROM categories;";
        String method1 = "POST";
        DatabaseInteraction select = new DatabaseInteraction(ctx);
        select.execute(url1,method1,query1);


        try {
            JSONObject res = select.get();
            int o = res.getInt("success");
            String text = "";
            if(o==1){
                JSONArray args = res.getJSONArray("products");
                for(int i = 0; i<args.length();i++){
                    String child_id = ((JSONArray)args.get(i)).getString(0);
                    String child_name = ((JSONArray)args.get(i)).getString(1);
                    String group_id = ((JSONArray)args.get(i)).getString(2);
                    String group_name = ((JSONArray)args.get(i)).getString(3);
                    if(!counting_groups.contains(group_id)&&!counting_groups.isEmpty()){
                        counting_groups.add(group_id);
                        childData.add(childDataItem);
                        childDataItem = new ArrayList<>();

                        current_group_hashmap = new HashMap<>();
                        current_group_hashmap.put(GROUP_ID,group_id);
                        current_group_hashmap.put(GROUP_CATEGORY_NAME,group_name);
                        groupData.add(current_group_hashmap);
                    }else if(!counting_groups.contains(group_id)&&counting_groups.isEmpty()){
                        counting_groups.add(group_id);
                        current_group_hashmap = new HashMap<>();
                        current_group_hashmap.put(GROUP_ID,group_id);
                        current_group_hashmap.put(GROUP_CATEGORY_NAME,group_name);
                        groupData.add(current_group_hashmap);
                    }
                    
                    current_child_hashmap = new HashMap<>();
                    current_child_hashmap.put(CHILD_ID,child_id);
                    current_child_hashmap.put(CHILD_CATEGORY_NAME,child_name);
                    childDataItem.add(current_child_hashmap);
                }
                childData.add(childDataItem);

                String groupFrom[] = new String[]{GROUP_CATEGORY_NAME};
                int groupTo[] = new int[]{R.id.name_of_category};
                String childFrom[] = new String[]{CHILD_ID,CHILD_CATEGORY_NAME};
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
            }else{
                Toast.makeText(ctx,"No result from server",Toast.LENGTH_SHORT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return adapter;

    }

}