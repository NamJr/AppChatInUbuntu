package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.simplifiedlearning.firebaseauth.chat_people.PeopleFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerChatAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerChatAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

//        switch (position){
//            case 0: return new MessagesOfFriendsFragment();
//            case 1: return new FriendListFragment();
//            case 2: return new PeopleFragment();
//            default:
//                return new MessagesOfFriendsFragment();
//        }
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
//        return 3;
        return mFragmentList.size();
    }

    public void addFrag(android.support.v4.app.Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Messages";
//            case 1:
//                return "Friends";
//            case 2:
//                return "People";
//            default:
//                return "Messages";
//        }
        return mFragmentTitleList.get(position);
    }
}
