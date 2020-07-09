package com.wlwoon.contactspicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsPickActivity extends Activity implements SideBar.OnTouchingLetterChangedListener, ContactsAdapter.OnChoiceFinished {

    private ArrayList<Contact> listContacts;
    private ArrayList<Contact> listContacts2 = new ArrayList<>();
    private ContactsAdapter mAdapterContacts;
    private RecyclerView mRecyclerView;
    private CPLinearLayoutManager mLayoutManager;
    private TextView mDialog;
    private SideBar mSideBar;
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_pick);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        boolean isMultipleChoice = bundle.getBoolean("isMultipleChoice", false);

        listContacts = new ContactFetcher(this).fetchAll();
        if (listContacts != null && listContacts.size() > 0) {
            Collections.sort(listContacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    String name = o1.name;
                    String pinYin = HanziToPinyin.getPinYin(name);
                    String name2 = o2.name;
                    String pinYin2 = HanziToPinyin.getPinYin(name2);
                    return pinYin.charAt(0) - pinYin2.charAt(0);
                }
            });
        }
        for (Contact listContact : listContacts) {
            String name = listContact.name;
            listContact.setPinyin(HanziToPinyin.getPinYin(name));
        }
        mAdapterContacts = new ContactsAdapter(listContacts, isMultipleChoice);
        mAdapterContacts.setOnChoiceFinished(this);
        initView();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.swapAdapter(mAdapterContacts, true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new StickyItemDecoration(mAdapterContacts));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mLayoutManager = new CPLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.stopScroll();
        mAdapterContacts.notifyDataSetChanged();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cp_listView);
        mDialog = (TextView) findViewById(R.id.school_friend_dialog);
        btn = (TextView) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceOk();
            }
        });
        mSideBar = (SideBar) findViewById(R.id.school_friend_sidrbar);
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
    }

    StringBuilder stringBuilder = new StringBuilder();



    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        int position = mAdapterContacts.getPositionForSection(s.charAt(0));
        if (position != -1) {
            mLayoutManager.scrollToPositionWithOffset(position, 0);
            mLayoutManager.setStackFromEnd(true);
        }
    }

    @Override
    public void choiceFinished(Contact contact) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        bundle.putParcelableArrayList("listContacts", contacts);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }


    public void choiceOk() {
        for (Contact listContact : listContacts) {
            if (listContact.isCheck()) {
                listContacts2.add(listContact);
            }
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listContacts", listContacts2);
        intent.putExtras(bundle);
        if (listContacts2.size() > 0) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }
}
