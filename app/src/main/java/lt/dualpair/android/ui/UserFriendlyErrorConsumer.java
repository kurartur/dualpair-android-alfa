package lt.dualpair.android.ui;

import android.content.Context;
import android.support.v4.app.Fragment;

import io.reactivex.functions.Consumer;
import lt.dualpair.android.utils.ToastUtils;

public class UserFriendlyErrorConsumer implements Consumer<Throwable> {

    private Context context;
    private ErrorConverter converter;

    public UserFriendlyErrorConsumer(Fragment fragment) {
        context = fragment.getContext();
        converter = new ErrorConverter(fragment);
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        ToastUtils.show(context, converter.convert(throwable));
    }
}
