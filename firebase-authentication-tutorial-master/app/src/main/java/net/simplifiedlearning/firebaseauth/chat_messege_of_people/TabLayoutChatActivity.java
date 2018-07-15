package net.simplifiedlearning.firebaseauth.chat_messege_of_people;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.simplifiedlearning.firebaseauth.R;
import net.simplifiedlearning.firebaseauth.User;
import net.simplifiedlearning.firebaseauth.chat_friends.FriendListFragment;
import net.simplifiedlearning.firebaseauth.chat_people.PeopleFragment;

public class TabLayoutChatActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerChatAdapter viewPagerChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("userProfile");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        FragmentManager manager = getSupportFragmentManager();

        viewPagerChatAdapter = new ViewPagerChatAdapter(manager);
        //bundle
        Bundle bundle =new Bundle();
        bundle.putString("CurrentUserID",user.getIdUser());

        PeopleFragment frag_in = new PeopleFragment();
        MessagesOfFriendsFragment frag_out = new MessagesOfFriendsFragment();
        FriendListFragment friend = new FriendListFragment();

        frag_in.setArguments(bundle);
        frag_out.setArguments(bundle);
        friend.setArguments(bundle);
        viewPagerChatAdapter.addFrag(frag_out,"Messages");
        viewPagerChatAdapter.addFrag(friend,"Friends");
        viewPagerChatAdapter.addFrag(frag_in,"People");
//        viewPager.setAdapter(adapter);
        viewPager.setAdapter(viewPagerChatAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icons_message_40);
        tabLayout.getTabAt(1).setIcon(R.drawable.friendship);
        tabLayout.getTabAt(2).setIcon(R.drawable.icons_friend_80);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
