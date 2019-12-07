package edu.fsu.cs.mysql_test.utility;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.PutItemOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;

public class DatabaseAccess {

    private String TAG = "DynamoDb";

    private final String COGNITO_IDENTIY_POOL_ID = "us-east-1_3NWsbDHcu";
    private final Regions COGNITO_IDENTITY_POOL_REGION = Regions.US_EAST_1;
    private final String DYNAMODB_TABLE = "LfgApplication";
    private Context context;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;


    private static volatile DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.context = context;
//        AWSMobileClient.getInstance().initialize(context).execute();


        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();

        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        dbClient = new AmazonDynamoDBClient(credentialsProvider);

        dbClient.setRegion(Region.getRegion(Regions.US_EAST_1));

        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }

    public static synchronized DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public boolean createContact(Document doc){
        PutItemOperationConfig putItemOperationConfig = new PutItemOperationConfig();
        putItemOperationConfig.withReturnValues(ReturnValue.ALL_OLD);
        Document result = dbTable.putItem(doc, putItemOperationConfig);

        return true;
    }

}
