package cn.bingoogolapple.bottomnavigation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.widget.RxRadioGroup;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.basenote.activity.BaseActivity;
import cn.bingoogolapple.basenote.util.AppManager;
import cn.bingoogolapple.basenote.util.ToastUtil;
import cn.bingoogolapple.bgabanner.BGAViewPager;
import cn.bingoogolapple.bottomnavigation.R;
import cn.bingoogolapple.bottomnavigation.fragment.DiscoverFragment;
import cn.bingoogolapple.bottomnavigation.fragment.HomeFragment;
import cn.bingoogolapple.bottomnavigation.fragment.MeFragment;
import cn.bingoogolapple.bottomnavigation.fragment.MessageFragment;

public class MainActivity extends BaseActivity {

    private BGAViewPager mContentVp;
    private RadioGroup mTabRg;
    private ImageButton mPlusIb;

    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private DiscoverFragment mDiscoverFragment;
    private MeFragment mMeFragment;

    private BGABadgeRadioButton mHomeBrb;
    private BGABadgeRadioButton mMessageBrb;
    private BGABadgeRadioButton mDiscoverBrb;
    private BGABadgeRadioButton mMeBrb;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mContentVp = getViewById(R.id.vp_main_content);
        mTabRg = getViewById(R.id.rg_main_tab);
        mPlusIb = getViewById(R.id.ib_main_plus);

        mHomeBrb = getViewById(R.id.brb_main_home);
        mMessageBrb = getViewById(R.id.brb_main_message);
        mDiscoverBrb = getViewById(R.id.brb_main_discover);
        mMeBrb = getViewById(R.id.brb_main_me);
    }

    @Override
    protected void setListener() {
        setOnClick(mPlusIb, object -> ToastUtil.show("点击了加号按钮"));
        RxRadioGroup.checkedChanges(mTabRg).subscribe(checkedId -> {
            switch (checkedId) {
                case R.id.brb_main_home:
                    mContentVp.setCurrentItem(0, false);
                    break;
                case R.id.brb_main_message:
                    mContentVp.setCurrentItem(1, false);
                    break;
                case R.id.brb_main_discover:
                    mContentVp.setCurrentItem(2, false);
                    break;
                case R.id.brb_main_me:
                    mContentVp.setCurrentItem(3, false);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mContentVp.setAllowUserScrollable(false);
        mContentVp.setAdapter(new ContentAdapter(getSupportFragmentManager()));

        testBadgeView();
    }

    private void testBadgeView() {
        mHomeBrb.showTextBadge("110");
        mMessageBrb.showTextBadge("1");
        mDiscoverBrb.showTextBadge("...");
        mMeBrb.showTextBadge("2");
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().exitWithDoubleClick();
    }

    private class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mHomeFragment == null) {
                        mHomeFragment = new HomeFragment();
                    }
                    return mHomeFragment;
                case 1:
                    if (mMessageFragment == null) {
                        mMessageFragment = new MessageFragment();
                    }
                    return mMessageFragment;
                case 2:
                    if (mDiscoverFragment == null) {
                        mDiscoverFragment = new DiscoverFragment();
                    }
                    return mDiscoverFragment;
                case 3:
                    if (mMeFragment == null) {
                        mMeFragment = new MeFragment();
                    }
                    return mMeFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
