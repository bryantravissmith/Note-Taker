package com.bryantravissmith.notetaker;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bryantravissmith.notetaker.data.*;

public class MainActivity extends ListActivity {

	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int MENU_DELETE_ID = 1002;
	
	private int currentNoteId;
	
	private NoteDataSource dataSource;
	List<NoteItem> notesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());

		dataSource = new NoteDataSource(this);
		
		refreshDisplay();
	}

	private void refreshDisplay() {
		// TODO Auto-generated method stub
		notesList = dataSource.findAll();
		ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,R.layout.list_item_layout,notesList);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_create) {
			createNote();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void createNote(){
		NoteItem note = NoteItem.getNew();
		Intent intent = new Intent(this,NoteEditorActivity.class);
		intent.putExtra(note.KEY, note.getKey());
		intent.putExtra(note.TEXT, note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		NoteItem note = notesList.get(position);
		Intent intent = new Intent(this,NoteEditorActivity.class);
		intent.putExtra(note.KEY, note.getKey());
		intent.putExtra(note.TEXT, note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK){
			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra(note.KEY));
			note.setText(data.getStringExtra(note.TEXT));
			dataSource.update(note);
			refreshDisplay();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNoteId = (int) info.id;
		menu.add(0, MENU_DELETE_ID, 0, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		if( item.getItemId() == MENU_DELETE_ID){
			NoteItem note = notesList.get(currentNoteId);
			dataSource.remove(note);
			refreshDisplay();
		}
		return super.onContextItemSelected(item);
	}

}
