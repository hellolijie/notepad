package cn.lijie.notepad.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.lijie.notepad.R;
import cn.lijie.notepad.draw.ColorPickerDialog;
import cn.lijie.notepad.draw.ColorPickerDialog.OnColorChangedListener;
import cn.lijie.notepad.draw.DrawBrokenLine;
import cn.lijie.notepad.draw.DrawCircle;
import cn.lijie.notepad.draw.DrawFactory;
import cn.lijie.notepad.draw.DrawRectangle;
import cn.lijie.notepad.draw.DrawRubber;
import cn.lijie.notepad.draw.DrawScrew;
import cn.lijie.notepad.draw.DrawStraightLine;
import cn.lijie.notepad.draw.DrawText;

import com.actionbarsherlock.app.ActionBar;

public class DrawActivity extends BaseActivity{
	private String[] lines={"Ϳѻ","ֱ��","����"};
	private String[] solids={"Բ","����","�����"};
	
	private List<View> tools;
	
	private Spinner lineSpinner,solidSpinner;
	private TextView text,rubber,clear;//select;
	private ImageView color;
	
	private ColorPickerDialog colorPickerDialog;
	
	
	private MenuItemListener clickListener;
	private ItemSelectListener selectListener;
	private TouchListener touchListener;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.draw_layout);
		tools=new ArrayList<View>();
		//��������������
        clickListener=new MenuItemListener();
        selectListener=new ItemSelectListener();
        touchListener=new TouchListener();
        
        init();
	}
	
	private void init(){
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lines);
        lineSpinner=(Spinner)findViewById(R.id.lines);
        lineSpinner.setAdapter(adapter);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, solids);
        solidSpinner=(Spinner)findViewById(R.id.solids);
        solidSpinner.setAdapter(adapter);
        
        lineSpinner.setOnItemSelectedListener(selectListener);
        solidSpinner.setOnItemSelectedListener(selectListener);
        lineSpinner.setOnTouchListener(touchListener);
        solidSpinner.setOnTouchListener(touchListener);
        
        //��ʼ�����߲˵� textview
        text=(TextView) findViewById(R.id.text);
        rubber=(TextView) findViewById(R.id.rubber);
        clear=(TextView) findViewById(R.id.clear);
        color=(ImageView) findViewById(R.id.color);
//        select=(TextView) activity.findViewById(R.id.select);
        
        text.setOnClickListener(clickListener);
        rubber.setOnClickListener(clickListener);
        clear.setOnClickListener(clickListener);
        color.setOnClickListener(clickListener);
        
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        tools.add(lineSpinner);
        tools.add(solidSpinner);
        tools.add(text);
        tools.add(rubber);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		updateToolBarState(lineSpinner);
		solidSpinner.setSelection(0,true);
		lineSpinner.setSelection(0,true);
		DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawScrew());
	}



	//����
    class TouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				if(v.getId()==R.id.lines||v.getId()==R.id.solids){
					Spinner spinner=(Spinner)v;
					selectListener.onItemSelected(spinner, v, spinner.getSelectedItemPosition(), spinner.getSelectedItemId());
					updateToolBarState(spinner);
				}
			}
			return false;
		}
    }
	
    //�޸Ĺ�����״̬
    private void updateToolBarState(View v){
    	for(View view:tools){
    		view.setBackgroundResource(R.drawable.button_clilck_selector);
    	}
    	v.setBackgroundResource(R.drawable.toolbar_selected_selector);
    }
    
	class ItemSelectListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			//ѡ���������ƹ���
			switch(parent.getId()){
			case R.id.lines:
				switch(position){
				case 0:			//Ϳѻ
					DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawScrew());
					break;
				case 1:			//ֱ��
					DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawStraightLine());
					break;
				case 2:			//����
					DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawBrokenLine());
					break;
				}
				break;
			//ѡ��ʵ����ƹ���
			case R.id.solids:
				switch(position){
				case 0:				//Բ
					DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawCircle());
					break;
				case 1:				//����
					DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawRectangle());
					break;
				case 2:				//�����
					break;
				}
				break;
			}
//			updateToolBarState(parent);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
    }
    
    //����
    class MenuItemListener implements View.OnClickListener{
		@Override
		public void onClick(final View v) {
			switch(v.getId()){
			case R.id.text:				//����
				DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawText());
				updateToolBarState(v);
				break;
			case R.id.rubber:			//��Ƥ
				DrawFactory.getInstance(DrawActivity.this).setCurDraw(new DrawRubber());
				updateToolBarState(v);
				break;
			case R.id.color:			//��ɫ
				if(colorPickerDialog==null){
					colorPickerDialog=new ColorPickerDialog(DrawActivity.this, "", new OnColorChangedListener() {
						
						@Override
						public void colorChanged(int color) {
							((ImageView)v).setBackgroundColor(color);
							DrawFactory.getInstance(DrawActivity.this).setColor(color);
						}
					});
				}
				colorPickerDialog.show();
				break;
//			case R.id.select:			//ѡ��
//				DrawFactory.getInstance(MainActivity.this).setCurDraw(new DrawSelect());
//				break;
			}
		}
    }
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		//����˵�
		getSupportMenuInflater().inflate(R.menu.actionmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch(item.getItemId()){
//		case R.id.importFrom:
//			break;
		case R.id.save:
			break;
		case R.id.share:
			break;
//		case R.id.takeNow:
//			break;
		case android.R.id.home:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
