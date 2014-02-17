package com.example.encryptionalgorithms;

import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends ExpandableListActivity {
	
	List<String> arrGroupElementsList = new ArrayList<String>();
	String[] arrGroupElements;
	
	//private List<List<String>> arrChildElementsList = new ArrayList<List<String>>();
	private List<String[]> arrChildElementsList = new ArrayList<String[]>();
	String arrChildElements[][];
	/*private static final String arrChildElements[][] = {
		{ "Details1 A","Details1 B", "Details1 C" },
		{ "Details2 A","Details2 B", "Details2 C" },
		{ "Details3 A","Details3 B", "Details3 C" },
		{ "Details4 A","Details4 B", "Details4 C" },
		{ "Details5 A","Details5 B", "Details5 C" },
		{ "Details6 A","Details6 B", "Details6 C" },
		{ "Details7" },
		{ "Details8" },
		{ "Details9" }
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_screen);
		SecurityListings();
		setListAdapter(new ExpandableListAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void SecurityListings() {
		for (Provider provider : Security.getProviders()) {
			Log.d("encryption", "Provider: " + provider.getName());
			arrGroupElementsList.add(provider.getName());
			
			ArrayList<String> providerServices = new ArrayList<String>();
	        for (Provider.Service service : provider.getServices()) {
	        	Log.d("encryption", "  Algorithm: " + service.getAlgorithm());
	        	providerServices.add(service.getAlgorithm());
	        }
	        
	        String[] providerServicesArray = new String[ providerServices.size() ];
			providerServices.toArray( providerServicesArray );
	        arrChildElementsList.add(providerServicesArray);
	    }
		
		arrGroupElements = new String[ arrGroupElementsList.size() ];
		arrGroupElementsList.toArray( arrGroupElements );
		
		arrChildElements = new String[arrChildElementsList.size()][arrChildElementsList.size()];
		arrChildElementsList.toArray( arrChildElements );
		
	}
	
	public class ExpandableListAdapter extends BaseExpandableListAdapter {
		private Context myContext;
		
		public ExpandableListAdapter(Context context) {
			myContext = context;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.article_list_child_item_layout, null);
			}
	
			TextView yourSelection = (TextView) convertView.findViewById(R.id.articleContentTextView);
			yourSelection.setText(arrChildElements[groupPosition][childPosition]);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return arrChildElements[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}
		
		@Override
		public int getGroupCount() {
			return arrGroupElements.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.article_list_item_layout, null);
			}
			
			TextView groupName = (TextView) convertView.findViewById(R.id.articleHeaderTextView);
			groupName.setText(arrGroupElements[groupPosition]);
			return convertView;
		}
		
		@Override
		public boolean hasStableIds() {
			return false;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
