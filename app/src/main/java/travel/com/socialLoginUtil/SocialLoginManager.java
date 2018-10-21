package travel.com.socialLoginUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.facebook.FacebookSdk;

import rx.Observable;
import rx.subjects.PublishSubject;

public class SocialLoginManager {

  private static final String ERROR = "You must choose a social platform.";

  @SuppressLint("StaticFieldLeak")
  private static SocialLoginManager instance;
  private PublishSubject<SocialUser> userEmitter;
  private Context appContext;
  private boolean withProfile = true;
  private SocialPlatform socialPlatform;
  private String clientId;

  private SocialLoginManager(Context context) {
    appContext = context.getApplicationContext();
  }

  public static synchronized SocialLoginManager getInstance(Context context) {
    if (instance == null) {
      instance = new SocialLoginManager(context);
    }
    return instance;
  }

  @Deprecated
  public SocialLoginManager withProfile() {
    this.withProfile = true;
    return this;
  }

  public SocialLoginManager withProfile(boolean withProfile) {
    this.withProfile = withProfile;
    return this;
  }

  public SocialLoginManager facebook() {
    this.socialPlatform = SocialPlatform.FACEBOOK;
    return this;
  }

  public SocialLoginManager google(String clientId) {
    this.clientId = clientId;
    this.socialPlatform = SocialPlatform.GOOGLE;
    return this;
  }

  public static void init(Context application) {
    FacebookSdk.sdkInitialize(application);
  }

  public Observable<SocialUser> login() {
    userEmitter = PublishSubject.create();
    appContext.startActivity(getIntent());
    return userEmitter;
  }

  public Intent getIntent() {
    if (socialPlatform == SocialPlatform.FACEBOOK) {
      Intent intent = new Intent(appContext, FbLoginHiddenActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      return intent;
    } else if (socialPlatform == SocialPlatform.GOOGLE) {
      Intent intent = new Intent(appContext, GoogleLoginHiddenActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra(GoogleLoginHiddenActivity.EXTRA_CLIENT_ID, clientId);
      return intent;
    } else {
      throw new IllegalStateException(ERROR);
    }
  }

  boolean isWithProfile() {
    return this.withProfile;
  }

  void onLoginSuccess(SocialUser socialUser) {
    if (userEmitter != null) {
      SocialUser copy = new SocialUser(socialUser);
      userEmitter.onNext(copy);
      userEmitter.onCompleted();
    }
  }

  void onLoginError(Throwable throwable) {
    if (userEmitter != null) {
      Throwable copy = new Throwable(throwable);
      userEmitter.onError(copy);
    }
  }

  void onLoginCancel() {
    if (userEmitter != null) {
      userEmitter.onCompleted();
    }
  }

  enum SocialPlatform {
    FACEBOOK, GOOGLE
  }

}
