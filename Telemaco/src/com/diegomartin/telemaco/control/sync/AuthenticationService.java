package com.diegomartin.telemaco.control.sync;

import com.diegomartin.telemaco.view.AuthenticatorActivity;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class AuthenticationService extends Service {
	private static AccountAuthenticator accountAuthenticator;
	
	public AuthenticationService(){
		super();
	}
	
	@Override  
	public IBinder onBind(Intent intent) {  
		IBinder binder = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
		
		binder = getAuthenticator().getIBinder();
		return binder;
	}
	
	private AccountAuthenticator getAuthenticator() {
		  if (accountAuthenticator == null)
		   accountAuthenticator = new AccountAuthenticator(this);
		  return accountAuthenticator;
	}
	
	
	private static class AccountAuthenticator extends AbstractAccountAuthenticator {
		private Context context;
		 
		public AccountAuthenticator(Context c) {
			super(c);
			this.context = c;
		}
		
		@Override
		public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
			Intent login = new Intent(this.context, AuthenticatorActivity.class);
			login.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
			
			Bundle reply = new Bundle();
			reply.putParcelable(AccountManager.KEY_INTENT, login);
			
			return reply;
		}
		
		@Override
		public Bundle confirmCredentials(AccountAuthenticatorResponse arg0, Account arg1, Bundle arg2) throws NetworkErrorException {
			return null;
		}
		
		@Override
		public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
			return null;
		}
		
		@Override
		public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
			return null;
		}
		
		@Override
		public String getAuthTokenLabel(String authTokenType) {
			return null;
		}
		
		@Override
		public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
			return null;
		}
		
		@Override
		public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
			return null;
		}
	}
	/*
	package com.diegomartin.telemaco.control.sync;

	import android.accounts.AbstractAccountAuthenticator;
	import android.accounts.Account;
	import android.accounts.AccountAuthenticatorResponse;
	import android.accounts.AccountManager;
	import android.content.Context;
	import android.content.Intent;
	import android.os.Bundle;

	import com.diegomartin.telemaco.R;
	import com.diegomartin.telemaco.view.AuthenticatorActivity;
	//import com.example.android.samplesync.client.NetworkUtilities;

	class Authenticator extends AbstractAccountAuthenticator {

	    // Authentication Service context
	    private final Context mContext;

	    public Authenticator(Context context) {
	        super(context);
	        mContext = context;
	    }

	    @Override
	    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
	        String authTokenType, String[] requiredFeatures, Bundle options) {

	        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
	        intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
	        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
	        final Bundle bundle = new Bundle();
	        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
	        return bundle;
	    }

	    @Override
	    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
	        Bundle options) {

	        if (options != null && options.containsKey(AccountManager.KEY_PASSWORD)) {
	            final String password = options.getString(AccountManager.KEY_PASSWORD);
	            final boolean verified = onlineConfirmPassword(account.name, password);
	            final Bundle result = new Bundle();
	            result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, verified);
	            return result;
	        }
	        // Launch AuthenticatorActivity to confirm credentials
	        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
	        intent.putExtra(AuthenticatorActivity.PARAM_USERNAME, account.name);
	        intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, true);
	        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
	        final Bundle bundle = new Bundle();
	        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
	        return bundle;
	    }

	    @Override
	    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
	        throw new UnsupportedOperationException();
	    }

	    @Override
	    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
	        String authTokenType, Bundle loginOptions) {
	        if (!authTokenType.equals(this.mContext.getString(R.string.package_name))) {
	            final Bundle result = new Bundle();
	            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
	            return result;
	        }
	        final AccountManager am = AccountManager.get(mContext);
	        final String password = am.getPassword(account);
	        if (password != null) {
	            final boolean verified = onlineConfirmPassword(account.name, password);
	            if (verified) {
	                final Bundle result = new Bundle();
	                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
	                result.putString(AccountManager.KEY_ACCOUNT_TYPE, this.mContext.getString(R.string.package_name));
	                result.putString(AccountManager.KEY_AUTHTOKEN, password);
	                return result;
	            }
	        }
	        // the password was missing or incorrect, return an Intent to an
	        // Activity that will prompt the user for the password.
	        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
	        intent.putExtra(AuthenticatorActivity.PARAM_USERNAME, account.name);
	        intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
	        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
	        final Bundle bundle = new Bundle();
	        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
	        return bundle;
	    }

	    @Override
	    public String getAuthTokenLabel(String authTokenType) {
	    	if(authTokenType.equals(this.mContext.getString(R.string.package_name))){
	            //return mContext.getString(R.string.label);
	    	}
	        return null;
	    }

	    @Override
	    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
	        String[] features) {

	        final Bundle result = new Bundle();
	        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
	        return result;
	    }

	    private boolean onlineConfirmPassword(String username, String password) {
	        //boolean result = NetworkUtilities.authenticate(username, password, null, null);
	        //return result;
	        return false;
	    }

	    @Override
	    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
	        String authTokenType, Bundle loginOptions) {

	        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
	        intent.putExtra(AuthenticatorActivity.PARAM_USERNAME, account.name);
	        intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
	        intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, false);
	        final Bundle bundle = new Bundle();
	        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
	        return bundle;
	    }
	}*/
}