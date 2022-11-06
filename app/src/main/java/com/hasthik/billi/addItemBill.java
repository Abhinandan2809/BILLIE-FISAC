package com.hasthik.billi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import java.util.ArrayList;
class BillItem{
    String productName;
    String qty;
    BillItem(String productName, String qty)
    {
        this.productName=productName;
        this.qty=qty;
    }
}
public class addItemBill extends AppCompatActivity {

    ListView listView;
    int total;
    Cursor c;
    billdb db=new billdb(this);
    productdb pdb=new productdb(this);
    ArrayList<BillItem> billList=new ArrayList<BillItem>();
    EditText pname;
    TextView totaltext;
    EditText pqty;
    Item product;
    itemAdapter iad;
    public void listItems(TextView totaltext)
    {
        c=db.getRecords();
        billList.clear();
        total=0;
        int qty;
        while(c.moveToNext())
        {
            if(c.getString(2).equals(""))
            {
                qty=0;
            }
            else
            {
                qty=Integer.parseInt(c.getString(2));
            }
            total+=pdb.getPrice(c.getString(1))*qty;
            billList.add(new BillItem(c.getString(1),c.getString(2)));
        }
        totaltext.setText("Total: $"+String.valueOf(total));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_bill);
        listView=findViewById(R.id.listView);
        iad= new itemAdapter(this,R.layout.activity_list_view_row,billList);
        totaltext=(TextView) findViewById(R.id.totalText);
        listItems(totaltext);
        listView.setAdapter(iad);
        pname=(EditText) findViewById(R.id.productEdit);
        pqty=(EditText) findViewById(R.id.qtyEdit);
    }
    public void addProduct(View view)
    {
        if(pname.getText().toString().equals("")||pqty.getText().toString().equals(""))
        {
            Toast.makeText(this,"Field is empty",Toast.LENGTH_LONG).show();
        }
        else
        {
        if(db.checkexist(pname.getText().toString()))
        {
            Toast.makeText(this,"Item already exists in the list",Toast.LENGTH_LONG).show();
            pname.setText("");
            pqty.setText("");
        }
        else {
            if (pdb.checkexist(pname.getText().toString())) {
                db.addProduct(pname.getText().toString(), pqty.getText().toString());
                listItems(totaltext);
                listView.setAdapter(iad);
                listItems(totaltext);
                totaltext.setText("Total: $" + String.valueOf(total));
                pname.setText("");
                pqty.setText("");
            } else {
                Toast.makeText(this, "Item does not exist in inventory", Toast.LENGTH_LONG).show();
                pname.setText("");
                pqty.setText("");

            }
        }
        }

    }

    public class itemAdapter extends ArrayAdapter<com.hasthik.billi.BillItem> {

        ArrayList<com.hasthik.billi.BillItem> billList;
        public itemAdapter(Context context, int resource, ArrayList<com.hasthik.billi.BillItem> billList) {
            super(context, resource, billList);
            this.billList=billList;
        }
        @Nullable
        @Override
        public com.hasthik.billi.BillItem getItem(int position)
        {
            return billList.get(position);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view_row,parent,false);
            TextView productName=(TextView)convertView.findViewById(R.id.productText);
            EditText price=(EditText) convertView.findViewById(R.id.priceEdit);
            productName.setText(getItem(position).productName);
            price.setText(getItem(position).qty,TextView.BufferType.EDITABLE);
            ImageButton imgbtn=convertView.findViewById(R.id.deleteButton);
            price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    db.updatePrice(productName.getText().toString(),price.getText().toString());
                    listItems(totaltext);
                }
            });
            productName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(imgbtn.getVisibility()==View.INVISIBLE) {
                        imgbtn.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        imgbtn.setVisibility(View.INVISIBLE);
                    }
                }
            });
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deletePrice(productName.getText().toString());
                    listItems(totaltext);
                    listView.setAdapter(iad);
                }
            });
            return convertView;
        }

    }

}