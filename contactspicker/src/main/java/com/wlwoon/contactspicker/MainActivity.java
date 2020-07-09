//package com.basestonedata.contactspicker;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class MainActivity extends Activity {
//    ArrayList<Contact> listContacts;
//    ArrayList<Contact> listContacts2;
//    ListView lvContacts;
//    private TextView mDialog;
//    private RecyclerView mRecyclerView;
//    private SideBar mSideBar;
//    private ContactsAdapter mAdapterContacts;
//    private CPLinearLayoutManager mLayoutManager;
//    private TextView mTextView;
//    private StringBuilder stringBuilder;
//    private TextView mTextView2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mTextView = (TextView) findViewById(R.id.tv);
//        mTextView2 = (TextView) findViewById(R.id.tv2);
//        mTextView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ContactsPickActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("isMultipleChoice",true);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,20);
//            }
//        });
//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ContactsPickActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("isMultipleChoice",false);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,20);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        stringBuilder = new StringBuilder();
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 20) {
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    ArrayList<Parcelable> listContacts = extras.getParcelableArrayList("listContacts");
//                    if (listContacts!=null&&listContacts.size()>0) {
//                        for (Parcelable listContactse : listContacts) {
//                            Contact listContactse1 = (Contact) listContactse;
//                            stringBuilder.append(listContactse1.name + ",");
//                        }
//                    }
//                }
//            }
//        }
//
//        Toast.makeText(this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
//    }
//}
