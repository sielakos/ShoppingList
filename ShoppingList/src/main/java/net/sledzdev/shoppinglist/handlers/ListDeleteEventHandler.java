package net.sledzdev.shoppinglist.handlers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.ListDeleteEvent;

/**
 * Created by Mariusz on 22.01.14.
 */
public class ListDeleteEventHandler {
    private static long ANIM_DURATION = 300;

    private View emptyView;
    private View shoppingFragment;

    private ShoppingListsActivity activity;

    public ListDeleteEventHandler(ShoppingListsActivity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onListDelete(ListDeleteEvent event) {
        event.adapter.deleteItem(event.listId);
        activity.getContentManager().removeList(event.listId);

        if (activity.ismTwoPane()) {
            if (emptyView == null) {
                emptyView = activity.findViewById(R.id.empty_list);
                shoppingFragment = activity.findViewById(R.id.shoppping_fragment);
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
}
