package lt.dualpair.android.ui.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lt.dualpair.android.R;
import lt.dualpair.android.data.local.entity.UserAccount;
import lt.dualpair.android.ui.accounts.AccountType;
import lt.dualpair.android.ui.accounts.AccountTypeAdapter;
import lt.dualpair.android.ui.accounts.AccountTypeListDialog;

public class AccountGridAdapter extends BaseAdapter {

    private List<UserAccount> userAccounts;
    private List<AccountType> linkableAccountTypes;
    private Activity activity;
    private AccountTypeAdapter.OnAccountTypeClickListener onAccountTypeClickListener;

    public AccountGridAdapter(Activity activity, List<UserAccount> userAccounts, AccountTypeAdapter.OnAccountTypeClickListener onAccountTypeClickListener) {
        this.activity = activity;
        this.onAccountTypeClickListener = onAccountTypeClickListener;
        this.userAccounts = userAccounts;
        linkableAccountTypes = getNotLinkedAccountTypes(userAccounts);
    }

    @Override
    public int getCount() {
        return linkableAccountTypes.size() == 0 ? userAccounts.size() : userAccounts.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position >= userAccounts.size() ? null : userAccounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == userAccounts.size()) {
            return getAddButtonView(position, convertView, parent);
        } else {
            return getNormalView(position, convertView, parent);
        }
    }

    private View getNormalView(int position, View convertView, ViewGroup parent) {
        UserAccount account = (UserAccount) getItem(position);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.account_grid_item, parent, false);
        ImageView icon = view.findViewById(R.id.account_icon);
        icon.setImageResource(AccountType.valueOf(account.getAccountType()).getIcon());
        return view;
    }

    private View getAddButtonView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.account_grid_item, parent, false);
        ImageView icon = view.findViewById(R.id.account_icon);
        icon.setImageResource(R.drawable.square_add);
        view.setPadding(20, 20, 20, 20);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountTypeListDialog dialog = AccountTypeListDialog.getInstance(onAccountTypeClickListener, linkableAccountTypes, true);
                dialog.show(activity.getFragmentManager(), "AccountTypeListDialog");
            }
        });
        return view;
    }

    private List<AccountType> getNotLinkedAccountTypes(List<UserAccount> userAccounts) {
        List<AccountType> accountTypes = new ArrayList<>(Arrays.asList(AccountType.values()));
        for (UserAccount userAccount : userAccounts) {
            accountTypes.remove(accountTypes.indexOf(AccountType.valueOf(userAccount.getAccountType())));
        }
        return accountTypes;
    }
}
