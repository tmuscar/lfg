package edu.fsu.cs.mysql_test.utility;

import android.content.Context;

import com.amazonaws.regions.Regions;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

public class CognitoSettings {
    private String userPoolId = "us-east-1_3NWsbDHcu";
    private String clientId = "3673a8ll43raevhiv5jij42jji";
    private String clientSecret = "1a4sgf2lkutui6hfte05llfcogfp3g4vmaep304dh1kgejskal9j";
    private Regions cognitoRegion = Regions.US_EAST_1;

    private Context context;

    public CognitoSettings(Context context){
        this.context = context;
    }

    public String getUserPoolId(){
        return userPoolId;
    }

    public String getClientId(){
        return clientId;
    }

    public String getClientSecret(){
        return clientSecret;
    }

    public Regions getCognitoRegion(){
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
    }

}
