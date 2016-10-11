package lt.dualpair.android.data.task.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import lt.dualpair.android.accounts.AccountUtils;
import lt.dualpair.android.accounts.AuthenticatedUserTask;
import lt.dualpair.android.data.remote.client.user.GetUserPrincipalClient;
import lt.dualpair.android.data.repo.DatabaseHelper;
import lt.dualpair.android.data.repo.UserRepository;
import lt.dualpair.android.data.resource.User;

public class GetUserPrincipalTask extends AuthenticatedUserTask<User> {

    private UserRepository userRepository;
    private static final int EXPIRATION_TIME_MS = 1000 * 60 * 5; // 5 minutes

    public GetUserPrincipalTask(Context context) {
        super(context);
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        userRepository = new UserRepository(db);
    }

    @Override
    protected User run() {
        User user = userRepository.get(AccountUtils.getUserId(context));
        if (user == null || isExpired(user.getUpdateTime())) {
            user = new GetUserPrincipalClient().observable().toBlocking().first();
            userRepository.save(user);
            return user;
        } else {
            return user;
        }
    }

    private boolean isExpired(Date updateTime) {
        return new Date().getTime() - updateTime.getTime() > EXPIRATION_TIME_MS;
    }
}
