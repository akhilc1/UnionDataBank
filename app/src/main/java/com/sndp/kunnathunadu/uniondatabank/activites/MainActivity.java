package com.sndp.kunnathunadu.uniondatabank.activites;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.fragments.SakhaDetailsFragment;
import com.sndp.kunnathunadu.uniondatabank.fragments.UnionSakhaBranchesFragment;
import com.sndp.kunnathunadu.uniondatabank.greenrobot.events.ShowSakhaDetailsEvents;
import com.sndp.kunnathunadu.uniondatabank.models.Member;
import com.sndp.kunnathunadu.uniondatabank.models.Sakha;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String UNION_SAKHAS_FRAGMEN = "UnionSakhaBranchesFragment";
    public static final String SAKHA_DETAILS_FRAGMENT = "SakhaDetailsFragment";
    private Sakha mulamkuzhi;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            Workbook workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory() + "/union.xls"));
            Sheet parsableSheetMulamkuzhi = workbook.getSheet(8);
            mulamkuzhi = new Sakha();

            readMainMembers(parsableSheetMulamkuzhi, mulamkuzhi);
            Log.d(TAG, "onCreate: ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }

    private void readMainMembers(Sheet readSheet, Sakha sakhaObject) {
        try {
            int presidentRow = readSheet.findCell("PRESIDENT").getRow();

            Member president = new Member();
            Cell[] recordPres = readSheet.getRow(presidentRow);

            president.setName(recordPres[2].getContents());
            if (recordPres[3].getContents() != null) {
                president.setHouseName(recordPres[3].getContents());
            }

            if (recordPres[5].getContents() != null) {
                List<String> phNos = new ArrayList<>();
                phNos.add(recordPres[5].getContents());
                president.setPhoneNumbers(phNos);
            }
            sakhaObject.setPresident(president);


            int vpRow = readSheet.findCell("V. PRESIDENT").getRow();
            Cell[] recordVP = readSheet.getRow(vpRow);
            Member vicePresident = new Member();
            vicePresident.setName(recordVP[2].getContents());
            if (recordPres[3].getContents() != null) {
                vicePresident.setHouseName(recordVP[3].getContents());
            }

            if (recordVP[5].getContents() != null) {
                List<String> phNos = new ArrayList<>();
                phNos.add(recordVP[5].getContents());
                vicePresident.setPhoneNumbers(phNos);
            }
            sakhaObject.setVicePresident(vicePresident);


            int secRow = readSheet.findCell("SECRETARY").getRow();
            Cell[] recordSec = readSheet.getRow(secRow);
            Member secretary = new Member();
            secretary.setName(recordSec[2].getContents());
            if (recordSec[3].getContents() != null) {
                secretary.setHouseName(recordSec[3].getContents());
            }

            if (recordSec[5].getContents() != null) {
                List<String> phNos = new ArrayList<>();
                phNos.add(recordSec[5].getContents());
                secretary.setPhoneNumbers(phNos);
            }

            sakhaObject.setSecretary(secretary);


            int unComm = readSheet.findCell("U. COMMITTEE").getRow();
            Cell[] recordunComm = readSheet.getRow(unComm);
            Member unionComm = new Member();

            unionComm.setName(recordunComm[2].getContents());
            if (recordSec[3].getContents() != null) {
                unionComm.setHouseName(recordunComm[3].getContents());
            }

            if (recordunComm[5].getContents() != null) {
                List<String> phNos = new ArrayList<>();
                phNos.add(recordunComm[5].getContents());
                unionComm.setPhoneNumbers(phNos);
            }
            sakhaObject.setUnionCommittee(unionComm);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.union_samithi) {
            // Handle the camera action
        } else if (id == R.id.youth_samithi) {

        } else if (id == R.id.union_branches) {
            FragmentManager manager = getSupportFragmentManager();
            UnionSakhaBranchesFragment fragment = UnionSakhaBranchesFragment.newInstance(1);
            manager.beginTransaction()
                    .replace(R.id.content_main, fragment, UNION_SAKHAS_FRAGMEN)
                    .addToBackStack(UNION_SAKHAS_FRAGMEN)
                    .commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(ShowSakhaDetailsEvents event) {
        Toast.makeText(this, "Sakha: Activity " + event.getSakhaName(), Toast.LENGTH_SHORT).show();
        SakhaDetailsFragment fragment = SakhaDetailsFragment.newInstance(event.getSakhaName());
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content_main, fragment, SAKHA_DETAILS_FRAGMENT)
                .addToBackStack(SAKHA_DETAILS_FRAGMENT)
                .commit();
    }
}
