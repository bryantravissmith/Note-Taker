package com.bryantravissmith.notetaker;

import com.bryantravissmith.notetaker.data.NoteItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteEditorActivity extends Activity {

	private NoteItem note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		note = new NoteItem();
		note.setKey(intent.getStringExtra(note.KEY));
		note.setText(intent.getStringExtra(note.TEXT));
		
		EditText et = (EditText) findViewById(R.id.noteText);
		et.setText(note.getText());
		et.setSelection(note.getText().length());
	}
	
	private void saveAndFinish(){
		EditText et = (EditText) findViewById(R.id.noteText);
		String noteText = et.getText().toString();
		
		Intent intent = new Intent();
		intent.putExtra(note.KEY, note.getKey());
		intent.putExtra(note.TEXT, noteText);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			saveAndFinish();
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		saveAndFinish();
	}
}
