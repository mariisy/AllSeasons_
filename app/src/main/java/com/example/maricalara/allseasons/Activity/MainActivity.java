package com.example.maricalara.allseasons.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.maricalara.allseasons.Fragment.GoodsSold;
import com.example.maricalara.allseasons.Fragment.BoughtGoods;
import com.example.maricalara.allseasons.Fragment.SalesReport;
import com.example.maricalara.allseasons.Fragment.Settings;
import com.example.maricalara.allseasons.Fragment.Warehouse;
import com.example.maricalara.allseasons.Other.CircleTransform;
import com.example.maricalara.allseasons.R;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://ssl.c.photoshelter.com/img-get2/I0000aCgoE3a5zgo/fit=1000x750/rice-paddy-phil-00384.jpg";
    private static final String urlProfileImg = "https://scontent.fmnl3-2.fna.fbcdn.net/v/t1.0-9/19424025_1500439273311616_1169667763987267857_n.jpg?oh=07b6114dfcad7af698baa0151df81d84&oe=5AE29524";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PURCHASING = "goodsPurchasing";
    private static final String TAG_PURCHASED = "purchasedGoods";
    private static final String TAG_SALES = "SalesReport";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_WAREHOUSE = "warehouse";
    private static final String TAG_LOGOUT = "logOut";
    public static String CURRENT_TAG = TAG_PURCHASING;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PURCHASING;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, transaction action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Mari Calara");
        txtWebsite.setText("Capstone: All Seasons APP");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to transaction label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button


            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                GoodsSold goodsPurchasing = new GoodsSold();
                return goodsPurchasing;
            case 1:

                BoughtGoods boughtGoods = new BoughtGoods();
                return boughtGoods;
            case 2:
                Warehouse warehouse = new Warehouse();
                return warehouse;

            case 3:

                SalesReport salesReport = new SalesReport();
                return salesReport;

            case 4:

                Settings settings = new Settings();
                return settings;

            default:
                return new GoodsSold();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_purchasing:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PURCHASING;
                        break;
                    case R.id.nav_sold:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PURCHASED;
                        break;
                    case R.id.nav_warehouse:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_WAREHOUSE;
                        break;
                    case R.id.nav_sales:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SALES;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUs.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_logoff:
                        // launch new intent instead of loading fragment
                        // startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Are you sure you want to log off?")
                                .setMessage("Note that you will have to re-log in to regain access.")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();


                        drawer.closeDrawers();

                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_SALES;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // return super.onCreateOptionsMenu(menu);

        // when fragment is transaction, load the menu created for transaction
        /*
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.transactions_purchasing, menu);
        }

        if (navItemIndex == 2) {
            getMenuInflater().inflate(R.menu.transactions_purchased, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // user is in transaction fragment
        // and selected 'Mark all as Read'

        if (id == R.id.action_sort_by) {
            Toast.makeText(getApplicationContext(), "Sort By!", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.action_mark_all_delivered) {
            Toast.makeText(getApplicationContext(), "All transaction marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in transaction fragment
        // and selected 'Clear All'
        if (id == R.id.action_mark_all_received) {
            Toast.makeText(getApplicationContext(), "Clear all transaction!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
