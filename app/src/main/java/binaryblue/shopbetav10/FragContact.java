package binaryblue.shopbetav10;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class FragContact extends Fragment
{
    ProjectDB pdb = ProjectDB.getInstance();
    ListView lvContacts;
    ArrayList<Contact> list;
    ContactListAdapter contactListAdapter;

    public FragContact()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.frag_l_contact, container, false);
        /////////////////////////////////////////////////////////////////////////////////////
        //ListView Setup

        lvContacts = (ListView) view.findViewById(R.id.c_list);
        list = pdb.getCallDetails(getContext());
        Collections.sort(list);
        contactListAdapter = new ContactListAdapter(lvContacts.getContext(), list);

        lvContacts.setAdapter(contactListAdapter);
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.lv_animation), 0.5f);
        lvContacts.setLayoutAnimation(lac);
        //lvContacts.setOnItemClickListener(itemClickListener);
        //////////////////////////////////////////////////////////////////////////////////////
        return view;
    }

    public class ContactListAdapter extends ArrayAdapter<Contact>
    {
        private final Context context;
        private ArrayList<Contact> itemsArrayList;
        LayoutInflater inflater;



        public ContactListAdapter(Context context, ArrayList<Contact> itemsArrayList) {

            super(context, -1, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
            // 1. Create inflater
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.itemsArrayList.size();
        }

        public Contact getItem(int position) {
            return this.itemsArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.lv_call_list, parent, false);

            // 3. Get the two text view from the rowView
            TextView cName = (TextView) rowView.findViewById(R.id.l_contact_name);
            TextView cNo = (TextView) rowView.findViewById(R.id.l_contact_number);
            TextView cType = (TextView) rowView.findViewById(R.id.l_call_type);
            TextView cDuration = (TextView) rowView.findViewById(R.id.l_call_duration);
            TextView cDate = (TextView) rowView.findViewById(R.id.l_call_date);
            ImageView cImage = (ImageView) rowView.findViewById(R.id.iv_contact);

            // 4. Set the text for textView
            if(itemsArrayList.get(position).getContactName().length()>20)
                cName.setText(itemsArrayList.get(position).getContactName().substring(0,18)+"..");
            else cName.setText(itemsArrayList.get(position).getContactName());
            cNo.setText(itemsArrayList.get(position).getContactNumber());
            cType.setText(itemsArrayList.get(position).getContactCalls().getCallType());
            cDuration.setText(itemsArrayList.get(position).getContactCalls().getCallDuration());

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            cDate.setText(df.format("hh:mm aa dd-MM-yyyy", itemsArrayList.get(position).getContactCalls().getCallDate()));

            cImage.setImageResource(R.drawable.contact_photo);

            // 5. retrn rowView
            //LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.lv_animation), 0.5f);
            //0.5f == time between appearance of listview items.
            //rowView.setAnimation(lac.getAnimation());

            return rowView;
        }
    }//



}


