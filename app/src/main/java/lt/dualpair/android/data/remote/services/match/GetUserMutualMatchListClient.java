package lt.dualpair.android.data.remote.services.match;

import android.text.TextUtils;

import java.util.Date;

import lt.dualpair.android.data.remote.services.BaseClient;
import lt.dualpair.android.data.resource.Match;
import lt.dualpair.android.data.resource.ResourceCollection;
import retrofit2.Retrofit;
import rx.Observable;

public class GetUserMutualMatchListClient extends BaseClient<ResourceCollection<Match>> {

    private String url;
    private Long userId;

    public GetUserMutualMatchListClient(String url) {
        this.url = url;
    }

    public GetUserMutualMatchListClient(Long userId) {
        this.userId = userId;
    }

    @Override
    protected Observable<ResourceCollection<Match>> getApiObserable(Retrofit retrofit) {
        MatchService matchService = retrofit.create(MatchService.class);
        if (!TextUtils.isEmpty(url)) {
            return matchService.getUserMutualMatches(url);
        } else {
            return matchService.getUserMutualMatches(userId, new Date().getTime()); // TODO timezone
        }
    }
}