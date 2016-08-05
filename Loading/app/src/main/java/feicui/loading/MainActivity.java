package feicui.loading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnGoods;
    private Button mBtnAnimal;
    private Button mBtnScenery;
    private Button mBtnCircleJump;
    private Button mBtnShapeChange;
    private Button mBtnCircleRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(app.dinus.com.example.R.layout.activity_main);

        mBtnGoods = (Button) findViewById(app.dinus.com.example.R.id.goods);
        mBtnAnimal = (Button) findViewById(app.dinus.com.example.R.id.animal);
        mBtnScenery = (Button) findViewById(app.dinus.com.example.R.id.scenery);
        mBtnCircleJump = (Button) findViewById(app.dinus.com.example.R.id.circle_jump);
        mBtnShapeChange = (Button) findViewById(app.dinus.com.example.R.id.shape_change);
        mBtnCircleRotate = (Button) findViewById(app.dinus.com.example.R.id.circle_rotate);

        mBtnGoods.setOnClickListener(this);
        mBtnAnimal.setOnClickListener(this);
        mBtnScenery.setOnClickListener(this);
        mBtnCircleJump.setOnClickListener(this);
        mBtnShapeChange.setOnClickListener(this);
        mBtnCircleRotate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case app.dinus.com.example.R.id.shape_change:
                ShapeChangeActivity.startActivity(this);
                break;
            case app.dinus.com.example.R.id.goods:
                GoodsActivity.startActivity(this);
                break;
            case app.dinus.com.example.R.id.animal:
                AnimalActivity.startActivity(this);
                break;
            case app.dinus.com.example.R.id.scenery:
                SceneryActivity.startActivity(this);
                break;
            case app.dinus.com.example.R.id.circle_jump:
                CircleJumpActivity.startActivity(this);
                break;
            case app.dinus.com.example.R.id.circle_rotate:
                CircleRotateActivity.startActivity(this);
                break;
            default:
                break;
        }
    }
}
