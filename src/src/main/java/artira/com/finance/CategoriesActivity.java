package artira.com.finance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class CategoriesActivity extends AppCompatActivity {


    GridView gridView;

    String[] values = {

            "Food",
            "Bills",
            "Transportation",
            "Home",
            "Car",
            "Entertainment",
            "Shopping",
            "Health",
            "Beauty",
            "Travel",
            "Education",
            "Add"
    };

    int[] images = {

            R.drawable.food,
            R.drawable.bills,
            R.drawable.transport,
            R.drawable.home,
            R.drawable.car,
            R.drawable.entertainment,
            R.drawable.shopping,
            R.drawable.health,
            R.drawable.beauty,
            R.drawable.travel,
            R.drawable.education,
            R.drawable.add
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        gridView = (GridView) findViewById(R.id.gridview);

        GridAdapter gridAdapter = new GridAdapter(this, values, images);

        gridView.setAdapter(gridAdapter);
    }
}
