package com.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ViewGroup;

import com.constant.Constant;
import com.fragment.AFragment;
import com.fragment.BFragment;
import com.fragment.CFragment;
import com.fragment.TransportFrag;
import com.fragment.WaybillFrag;
import com.ry.st.driver.R;

public class MainActivity extends FragmentActivity implements OnPageChangeListener{
	ViewPager pager;
	FragmentPagerAdapter adapter;
	int FRAGMENT_COUNT = 3;
	PagerTabStrip tab;
    List<Fragment> frags = new ArrayList<Fragment>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOnPageChangeListener(this);
		initFragment();
		adapter = new MFragmentAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

	}
	private void initFragment() {
		// TODO Auto-generated method stub
          frags.add(new TransportFrag());
          frags.add(new WaybillFrag());
        
	}

	class MFragmentAdapter extends FragmentPagerAdapter {

		public MFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return frags.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return frags.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return position + "";
		}


		@Override
        public Object instantiateItem(ViewGroup container, int position) {
        	// TODO Auto-generated method stub
        	return super.instantiateItem(container, position);
        }
		
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		Log.d("cyd","onPageSelected" + position);
		Constant.PAGE_INDEX = position;
	}
}
