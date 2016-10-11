package lt.dualpair.android.data;

import android.content.Context;

import java.util.List;

import lt.dualpair.android.data.resource.Link;
import lt.dualpair.android.data.resource.ResourceCollection;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public abstract class ResourceCollectionLoader<T> {

    private BehaviorSubject<List<T>> subject = BehaviorSubject.create();
    private Context context;
    private String nextUrl;

    public ResourceCollectionLoader(Context context) {
        this.context = context;
    }

    protected abstract Observable<ResourceCollection<T>> resourceObservable(Context context, String url);

    protected void load(String url) {
        Observable<ResourceCollection<T>> observable = resourceObservable(context, null);
        observable.doOnNext(new Action1<ResourceCollection<T>>() {
            @Override
            public void call(ResourceCollection<T> resourceCollection) {
                Link next = resourceCollection.getLink("next");
                nextUrl = next == null ? null : next.getHref();
            }
        }).map(new Func1<ResourceCollection<T>, List<T>>() {
            @Override
            public List<T> call(ResourceCollection<T> tResourceCollection) {
                return tResourceCollection.getContent();
            }
        }).subscribe(subject);
    }

    public Observable<List<T>> load() {
        load(null);
        return subject.asObservable();
    }

    public void loadNext() {
        load(nextUrl);
    }

    public void reload() {
        load();
    }

}
