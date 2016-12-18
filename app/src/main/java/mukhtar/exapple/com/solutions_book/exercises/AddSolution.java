package mukhtar.exapple.com.solutions_book.exercises;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mukhtar.exapple.com.solutions_book.R;

public class AddSolution extends AppCompatActivity {
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);
        editText=(EditText)findViewById(R.id.text_of_solution);
        button= (Button)findViewById(R.id.btn_add_solution);
    }
    public void addSolution(View v){
        String solution = editText.getText().toString();
        finish();
    }
}
