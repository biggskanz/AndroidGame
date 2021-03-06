package edu.iastate.fightthings;

import edu.iastate.fightthings.data.TopscoreContent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class MonsterListActivity extends FragmentActivity implements
		MonsterListFragment.Callbacks, ActionBar.OnNavigationListener {


	private boolean mTwoPane;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monster_list);
		
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		// Set up the dropdown list navigation in the action bar.
				actionBar.setListNavigationCallbacks(
				// Specify a SpinnerAdapter to populate the dropdown list.
						new ArrayAdapter<String>(actionBar.getThemedContext(),
								android.R.layout.simple_list_item_1,
								android.R.id.text1, 
								new String[] {"...", "Top Scores","Exit"}), 
								this);

		if (findViewById(R.id.monster_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((MonsterListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.monster_list))
					.setActivateOnItemClick(true);
		}				

	}

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(MonsterDetailFragment.ARG_ITEM_ID, id);
			MonsterDetailFragment fragment = new MonsterDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.monster_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MonsterDetailActivity.class);
			detailIntent.putExtra(MonsterDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		if(itemPosition == 0)
		{
			// Do nothing
		}
		else if(itemPosition == 1)
		{
			TopscoreContent.setContext(this);
			   
			 Builder dialogBuilder = new AlertDialog.Builder(this);
			 dialogBuilder.setTitle("Top Scores");
			 dialogBuilder.setAdapter(new ArrayAdapter<TopscoreContent.TopscoreItem>(this,
						android.R.layout.simple_list_item_activated_1,
						android.R.id.text1, TopscoreContent.ITEMS), null);
			 dialogBuilder.setPositiveButton(R.string.reset_game,
			    new DialogInterface.OnClickListener()
			    {
			       public void onClick(DialogInterface dialog, int which)
			       { 
			    	  getFragmentManager().popBackStackImmediate(); 
			       } 
			    } 
			 ); 
			 dialogBuilder.show(); 
		}
		else if(itemPosition == 2)
		{
			this.finish();
		}
		
		return true;
	}
}
