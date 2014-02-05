package net.sledzdev.shoppinglist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ItemChangedEvent;
import net.sledzdev.shoppinglist.model.ShoppingItem;

public class ItemDialogActivity extends Activity {

    public final static String ITEM_ID_ARG = "item_id";

    private EditText name;
    private EditText price;
    private Button ok;
    private Button cancel;
    private long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);

        Bundle extras = getIntent().getExtras();
        itemId = extras.getLong(ITEM_ID_ARG);

        findViews();
        setEvents();
    }

    private void findViews() {
        name = (EditText) findViewById(R.id.item_name);
        price = (EditText) findViewById(R.id.item_price);
        ok = (Button) findViewById(R.id.ok_btn);
        cancel = (Button) findViewById(R.id.cancel_btn);
    }

    private void setEvents() {
        final EventBus eventBus = EventBusFactory.getEventBus();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventBus.post(new ItemChangedEvent(itemId, name.getText().toString(), getPrice()));
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private double getPrice() {
        String priceTxt = price.getText().toString();
        try {
          return Double.parseDouble(priceTxt);
        } catch (NumberFormatException e) {}
        return 0;
    }


}
