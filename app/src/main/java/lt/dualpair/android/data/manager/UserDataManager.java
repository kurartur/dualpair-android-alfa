package lt.dualpair.android.data.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lt.dualpair.android.data.resource.Choice;
import lt.dualpair.android.data.resource.Location;
import lt.dualpair.android.data.resource.Photo;
import lt.dualpair.android.data.resource.PurposeOfBeing;
import lt.dualpair.android.data.resource.RelationshipStatus;
import lt.dualpair.android.data.resource.SearchParameters;
import lt.dualpair.android.data.resource.Sociotype;
import lt.dualpair.android.data.resource.User;
import lt.dualpair.android.data.task.LogoutTask;
import lt.dualpair.android.data.task.Task;
import lt.dualpair.android.data.task.socionics.EvaluateTestTask;
import lt.dualpair.android.data.task.user.AddPhotoTask;
import lt.dualpair.android.data.task.user.DeletePhotoTask;
import lt.dualpair.android.data.task.user.GetAvailablePhotosTask;
import lt.dualpair.android.data.task.user.GetSearchParametersTask;
import lt.dualpair.android.data.task.user.GetUserPrincipalTask;
import lt.dualpair.android.data.task.user.ReportUserTask;
import lt.dualpair.android.data.task.user.SavePhotosTask;
import lt.dualpair.android.data.task.user.SetDateOfBirthTask;
import lt.dualpair.android.data.task.user.SetLocationTask;
import lt.dualpair.android.data.task.user.SetSearchParametersTask;
import lt.dualpair.android.data.task.user.SetUserSociotypesTask;
import lt.dualpair.android.data.task.user.UpdateUserTask;
import lt.dualpair.android.ui.accounts.AccountType;
import rx.Observable;

public class UserDataManager extends DataManager {

    public UserDataManager(Context context) {
        super(context);
    }

    public Observable<User> getUser() {
        return getUser(false);
    }

    public Observable<User> getUser(final boolean forceUpdate) {
        return execute(context, new DataRequest<>("getUser", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new GetUserPrincipalTask(authToken, forceUpdate);
            }
        }));
    }

    public Observable<User> setSociotypes(final Set<Sociotype> sociotypes) {
        return execute(context, new DataRequest<>("setSociotypes", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new SetUserSociotypesTask(authToken, sociotypes);
            }
        }));
    }

    public Observable<User> setDateOfBirth(final Date dateOfBirth) {
        return execute(context, new DataRequest<>("setDateOfBirth", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new SetDateOfBirthTask(authToken, dateOfBirth);
            }
        }));
    }

    public Observable<User> deletePhoto(final Photo photo) {
        return execute(context, new DataRequest<>("deletePhoto" + photo, new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new DeletePhotoTask(authToken, photo);
            }
        }));
    }

    public Observable<Void> setLocation(final Location location) {
        return execute(context, new DataRequest<>("setLocation" + location, new AuthenticatedTaskCreator<Void>() {
            @Override
            protected Task<Void> doCreateTask(String authToken) {
                return new SetLocationTask(authToken, location);
            }
        }));
    }

    public Observable<User> updateUser(final User user) {
        return execute(context, new DataRequest<>("updateUser", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new UpdateUserTask(authToken, user);
            }
        }));
    }

    public Observable<User> updateUser(final String name,
                                       final Date dateOfBirth,
                                       final String description,
                                       final RelationshipStatus relationshipStatus,
                                       final Set<PurposeOfBeing> purposesOfBeing) {
        return execute(context, new DataRequest<>("updateUser", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new UpdateUserTask(authToken, name, dateOfBirth, description, relationshipStatus, purposesOfBeing);
            }
        }));
    }

    public Observable<User> addPhoto(final Photo photo) {
        return execute(context, new DataRequest<>("addPhoto", new AuthenticatedTaskCreator<User>() {
            @Override
            protected Task<User> doCreateTask(String authToken) {
                return new AddPhotoTask(authToken, photo);
            }
        }));
    }

    public Observable<List<Photo>> getAvailablePhotos(final AccountType accountType) {
        return execute(context, new DataRequest<List<Photo>>("getAvailablePhotos" + accountType.name(), new AuthenticatedTaskCreator<List<Photo>>() {
            @Override
            protected Task<List<Photo>> doCreateTask(String authToken) {
                return new GetAvailablePhotosTask(authToken, accountType);
            }
        }));
    }

    public Observable<Void> logout() {
        return execute(context, new DataRequest<>("logout", new AuthenticatedTaskCreator<Void>() {
            @Override
            protected Task<Void> doCreateTask(String authToken) {
                return new LogoutTask(authToken);
            }
        }));
    }

    public Observable<Sociotype> evaluateTest(final Map<String, Choice> choices) {
        return execute(context, new DataRequest<>("evaluateTest", new AuthenticatedTaskCreator<Sociotype>() {
            @Override
            protected Task<Sociotype> doCreateTask(String authToken) {
                return new EvaluateTestTask(authToken, choices);
            }
        }));
    }

    public Observable<List<Photo>> savePhotos(final List<Photo> photos) {
        return execute(context, new DataRequest<>("savePhotos", new AuthenticatedTaskCreator<List<Photo>>() {
            @Override
            protected Task<List<Photo>> doCreateTask(String authToken) {
                return new SavePhotosTask(authToken, photos);
            }
        }));
    }

    public Observable<Void> reportUser(final Long userId) {
        return execute(context, new DataRequest<Void>("reportUser" + userId, new AuthenticatedTaskCreator<Void>() {
            @Override
            protected Task<Void> doCreateTask(String authToken) {
                return new ReportUserTask(authToken, userId);
            }
        }));
    }

    public Observable<SearchParameters> getSearchParameters() {
        return execute(context, new DataRequest<>("getSearchParameters", new AuthenticatedTaskCreator<SearchParameters>() {
            @Override
            protected Task<SearchParameters> doCreateTask(String authToken) {
                return new GetSearchParametersTask(authToken);
            }
        }));
    }

    public Observable<SearchParameters> setSearchParameters(final SearchParameters sp) {
        return execute(context, new DataRequest<>("setSearchParameters", new AuthenticatedTaskCreator<SearchParameters>() {
            @Override
            protected Task<SearchParameters> doCreateTask(String authToken) {
                return new SetSearchParametersTask(authToken, sp);
            }
        }));
    }
}
