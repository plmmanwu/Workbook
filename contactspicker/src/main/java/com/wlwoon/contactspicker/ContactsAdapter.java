package com.wlwoon.contactspicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> implements StickyHeaderAdapter<ContactsAdapter.HeaderHolder> {

    List<Contact> mContacts;
    boolean isMultipleChoice;

    public ContactsAdapter(ArrayList<Contact> listContacts, boolean isMultipleChoice) {
        mContacts = listContacts;
        this.isMultipleChoice = isMultipleChoice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Contact contact = mContacts.get(position);
        if (isMultipleChoice) {
            holder.mCb.setVisibility(View.VISIBLE);
            holder.mCb.setChecked(contact.isCheck());
        } else {
            holder.mCb.setVisibility(View.GONE);
        }
        holder.mCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContacts.get(position).setCheck(!contact.isCheck());
            }
        });
        holder.mTvName.setText(contact.name);
        holder.mTvEmail.setText("");
        holder.mTvPhone.setText("");
        if (contact.emails.size() > 0 && contact.emails.get(0) != null) {
            holder.mTvEmail.setText(contact.emails.get(0).address);
        }
        if (contact.numbers.size() > 0 && contact.numbers.get(0) != null) {
            holder.mTvPhone.setText(contact.numbers.get(0).number);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultipleChoice) {
                    contact.setCheck(!contact.isCheck());
                    holder.mCb.setChecked(contact.isCheck());
                } else {
                    contact.setCheck(!contact.isCheck());
                    if (mOnChoiceFinished != null) {
                        mOnChoiceFinished.choiceFinished(contact);
                    }
                }
            }
        });
    }

    public interface OnChoiceFinished{
        void choiceFinished(Contact contact);
    }

    OnChoiceFinished mOnChoiceFinished;

    public void setOnChoiceFinished(OnChoiceFinished onChoiceFinished) {
        mOnChoiceFinished = onChoiceFinished;
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public String getHeaderId(int position) {
        return mContacts.get(position).getFirstChar() + "";
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_decoration, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText(getHeaderId(position));
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < mContacts.size(); i++) {
            char firstChar = mContacts.get(i).getFirstChar();
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvName;
        public TextView mTvEmail;
        public TextView mTvPhone;
        public CheckBox mCb;

        public ViewHolder(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.tvName);
            mTvEmail = (TextView) view.findViewById(R.id.tvEmail);
            mTvPhone = (TextView) view.findViewById(R.id.tvPhone);
            mCb = (CheckBox) view.findViewById(R.id.cb);
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }

}
