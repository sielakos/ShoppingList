package net.sledzdev.shoppinglist.handlers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.ShoppingListDetailActivity;
import net.sledzdev.shoppinglist.ShoppingListDetailFragment;
import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.ListSelectedEvent;

public class ListSelectedEventHandler {
    private static long ANIM_DURATION = 300;

    private final ShoppingListsActivity shoppingListsActivity;
    private View emptyView;
    private View shoppingFragment;

    public ListSelectedEventHandler(ShoppingListsActivity shoppingListsActivity) {
        this.shoppingListsActivity = shoppingListsActivity;
    }

    @Subscribe
    public void onItemSelected(ListSelectedEvent event) {
        long id = event.selectId;
        Bundle arguments = new Bundle();
        arguments.putLong(ShoppingListDetailFragment.LIST_ID, id);

        if (shoppingListsActivity.ismTwoPane()) {
            ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
            fragment.setArguments(arguments);
            shoppingListsActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shoppinglist_detail_container, fragment)
                    .commit();

            animate();
        } else {
            Intent detailIntent = new Intent(shoppingListsActivity, ShoppingListDetailActivity.class);
            detailIntent.putExtras(arguments);
            shoppingListsActivity.startActivity(detailIntent);
        }
    }

    private void animate() {
        if (emptyView == null) {
            emptyView = shoppingListsActivity.findViewById(R.id.empty_list);
            shoppingFragment = shoppingListsActivity.findViewById(R.id.shoppping_fragment);
        }

        emptyView.animate().setDuration(ANIM_DURATION).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                emptyView.setAlpha(1);
                emptyView.setVisibility(View.GONE);
                shoppingFragment.setVisibility(View.VISIBLE);
            }
        });
    }
}