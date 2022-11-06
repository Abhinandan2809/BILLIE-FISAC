package com.hasthik.billi;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
class Item{
    String productName;
    String price;
    Item(String productName, String price)
    {
        this.productName=productName;
        this.price=price;
    }
}
public class addItem extends AppCompatActivity {

    ListView listView;
    Cursor c;
    productdb db=new productdb(this);
    ArrayList<Item> priceList=new ArrayList<Item>();
    EditText pname;
    EditText pvalue;
    Item product;
    itemAdapter iad;
    public void listItems()
    {
        c=db.getRecords();
        priceList.clear();
        while(c.moveToNext())
        {
            priceList.add(new Item(c.getString(1),c.getString(2)));
        }

    }
    public void redirectBill(View view)
    {
        startActivity(new Intent(this,addItemBill.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        listView=findViewById(R.id.listView);
        iad= new itemAdapter(this,R.layout.activity_list_view_row,priceList);
        listItems();
        listView.setAdapter(iad);
        pname=(EditText) findViewById(R.id.productEdit);
        pvalue=(EditText) findViewById(R.id.priceEdit);
    }
    public void addProduct(View view)
    {
        if(pname.getText().toString().equals("")||pvalue.getText().toString().equals(""))
        {
            Toast.makeText(this,"Field is empty",Toast.LENGTH_LONG).show();
        }
        else {
            if (db.checkexist(pname.getText().toString())) {
                Toast.makeText(this, "Item already exists in the list", LENGTH_LONG).show();
                pname.setText("");
                pvalue.setText("");
            } else {
                db.addProduct(pname.getText().toString(), "$" + pvalue.getText().toString());
                listItems();
                listView.setAdapter(iad);
                pname.setText("");
                pvalue.setText("");
            }
        }

    }

    public class itemAdapter extends ArrayAdapter<com.hasthik.billi.Item> {

        ArrayList<com.hasthik.billi.Item> priceList;
        public itemAdapter(Context context, int resource, ArrayList<com.hasthik.billi.Item> priceList) {
            super(context, resource, priceList);
            this.priceList=priceList;
        }
        @Nullable
        @Override
        public com.hasthik.billi.Item getItem(int position)
        {
            return priceList.get(position);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view_row,parent,false);
            TextView productName=(TextView)convertView.findViewById(R.id.productText);
            EditText price=(EditText) convertView.findViewById(R.id.priceEdit);
            productName.setText(getItem(position).productName);
            price.setText(getItem(position).price,TextView.BufferType.EDITABLE);
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
                    listItems();
                    listView.setAdapter(iad);
                }
            });
            return convertView;
        }

    }

}