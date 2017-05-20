package com.sndp.kunnathunadu.uniondatabank.activites;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sndp.kunnathunadu.uniondatabank.R;
import com.sndp.kunnathunadu.uniondatabank.fragments.SakhaDetailsFragment;
import com.sndp.kunnathunadu.uniondatabank.fragments.UnionSakhaBranchesFragment;
import com.sndp.kunnathunadu.uniondatabank.greenrobot.events.ShowSakhaDetailsEvents;
import com.sndp.kunnathunadu.uniondatabank.models.Member;
import com.sndp.kunnathunadu.uniondatabank.models.Sakha;
import com.sndp.kunnathunadu.uniondatabank.utils.Constants;
import com.sndp.kunnathunadu.uniondatabank.utils.UtilityMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

public class
MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String UNION_SAKHAS_FRAGMEN = "UnionSakhaBranchesFragment";
    public static final String SAKHA_DETAILS_FRAGMENT = "SakhaDetailsFragment";
    private Sakha sakha;
    private List<Sakha> sakhaList = new ArrayList<>();
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

        /*try {
            Workbook workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory() + "/union.xls"));
            //Sheet parsableSheetMulamkuzhi = workbook.getSheet(8);
            int sakhaCounter = 0;
            for (Sheet sheet : workbook.getSheets()) {
                Log.d(TAG, "onCreate: Sakhas Recorded: " + sakhaList.size());
                if (sakhaCounter == 0) {
                    sakhaCounter++;
                    continue;
                }
                sakhaCounter++;
                sakha = new Sakha();

                String name = "";
                //Cell[] cell0 = sheet.getRow(0);
                Cell[] cell = sheet.getRow(1);
                //Cell[] cell1 = sheet.getRow(2);
                name = cell[0].getContents();
                name = UtilityMethods.toCamelCase(name);
                sakha.setSakhaName(name);
                Log.d(TAG, "onCreate: SakahName: " + name);

                sakha.setSakhaName(name);
                sakha = readMainMembers(sheet, sakha);
                sakha.setCommitteeMembers(read7CommitteeMembers(sheet, sakha));
                sakha.setPanchyathCommittee(readPanchayathCommittee(sheet, sakha));
                sakhaList.add(sakha);
            }

            writeSakhaDetailsToFirebase(sakhaList);
            Log.d(TAG, "onCreate: ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }*/

    }

    private void writeSakhaDetailsToFirebase(List<Sakha> sakhaList) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        for (Sakha sakha : sakhaList) {
            if (sakha.getSakhaName().length() > 3) {
                databaseReference.child(Constants.FIREBASE_SAKHA_DETAILS_TAG).child(sakha.getSakhaName())
                        .setValue(sakha).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: ");
                    }
                });
            }
        }

//        DatabaseReference rootReference = firebaseDatabase.getReference();
//        rootReference.setValue(sakhaList, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                Log.d(TAG, "onComplete: ");
//            }
//        });
    }

    private List<Member> readPanchayathCommittee(Sheet inputSheet, Sakha sakhaObject) {
        Log.d(TAG, "readPanchayathCommittee: Sheet no" + inputSheet.getName());
        try {
            int initRow = inputSheet.findCell("PANCHAYATH COMMITTEE MEMBERS").getRow();
            List<Member> panchayathCommitteeMembers = new ArrayList<>();
            initRow += 2;
            for (int i = initRow; i < initRow + 3; i++) {
                try {
                    Cell[] current = inputSheet.getRow(i);
                    Member member = new Member();
                    member.setName(UtilityMethods.toCamelCase(current[1].getContents()));
                    List<String> numbers = new ArrayList<>();
                    numbers.add(current[5].getContents());
                    member.setPhoneNumbers(numbers);
                    String houseName = current[2].getContents();
                    member.setHouseName(houseName);
                    panchayathCommitteeMembers.add(member);

                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            Log.d(TAG, "readPanchayathCommittee: ");
            return panchayathCommitteeMembers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Member> read7CommitteeMembers(Sheet inputSheet, Sakha sakhaObject) {
        try {
            int initRow = inputSheet.findCell("COMMITTEE MEMBERS").getRow();
            List<Member> committeeMembers = new ArrayList<>();

            initRow += 2;
            for (int i = initRow; i < initRow + 7; i++) {
                try {
                    Cell[] current = inputSheet.getRow(i);
                    Member cMember = new Member();
                    cMember.setName(UtilityMethods.toCamelCase(current[2].getContents()));
                    String phone = current[5].getContents();
                    List<String> phoneNumbers = new ArrayList<>();
                    phoneNumbers.add(phone);
                    cMember.setPhoneNumbers(phoneNumbers);
                    String houseName = current[3].getContents();
                    cMember.setHouseName(houseName);
                    committeeMembers.add(cMember);
                    Log.d(TAG, "read7CommitteeMembers: " + current.length);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            Log.d(TAG, "read7CommitteeMembers: ");
            return committeeMembers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Naveen
    private Sakha readMainMembers(Sheet readSheet, Sakha sakhaObject) {
        try {
            int presidentRow = readSheet.findCell("PRESIDENT").getRow();

            Member president = new Member();
            Cell[] recordPres = readSheet.getRow(presidentRow);

            president.setName(UtilityMethods.toCamelCase(recordPres[2].getContents()));
            if (recordPres[3].getContents() != null) {
                president.setHouseName(UtilityMethods.toCamelCase(recordPres[3].getContents()));
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
            vicePresident.setName(UtilityMethods.toCamelCase(recordVP[2].getContents()));
            if (recordPres[3].getContents() != null) {
                vicePresident.setHouseName(UtilityMethods.toCamelCase(recordVP[3].getContents()));
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
            secretary.setName(UtilityMethods.toCamelCase(recordSec[2].getContents()));
            if (recordSec[3].getContents() != null) {
                secretary.setHouseName(UtilityMethods.toCamelCase(recordSec[3].getContents()));
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

            unionComm.setName(UtilityMethods.toCamelCase(recordunComm[2].getContents()));
            if (recordSec[3].getContents() != null) {
                unionComm.setHouseName(UtilityMethods.toCamelCase(recordunComm[3].getContents()));
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
        return sakhaObject;
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
