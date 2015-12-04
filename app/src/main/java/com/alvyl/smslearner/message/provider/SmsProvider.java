package com.alvyl.smslearner.message.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;

import com.alvyl.smslearner.message.model.Message;
import com.alvyl.smslearner.sender.model.Sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dostiharise
 */
public class SmsProvider {

    public static final String SMS_INBOX_URI_STRING = "content://sms/inbox";

    private static final String[] SMS_QUERY_PROJECTIONS = {
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.DATE,
            Telephony.Sms.DATE_SENT,
            Telephony.Sms.BODY
    };

    private static SmsProvider selfInstance;

    // marked private to discourage instantiation.
    private SmsProvider() {

    }

    public static SmsProvider getInstance() {

        if(null == selfInstance) {
            selfInstance = new SmsProvider();
        }

        return selfInstance;
    }

    public void readAllTransactionalMessages(final Context context,  final SmsProviderCallback callback) {
        this.queryAndReadOnThread(context, "__-______", callback);
    }

    public void readMessagesBySender(final Context context,  final Sender sender, final SmsProviderCallback callback) {
        this.queryAndReadOnThread(context, "__-"+sender.getSenderAddress().getValue(), callback);
    }

    private void queryAndReadOnThread(final Context context, final String senderAddressPattern, final SmsProviderCallback callback) {

        final AsyncTask<Void, Void, ArrayList<Message>> asyncTask = new AsyncTask<Void, Void, ArrayList<Message>>() {

            @Override
            protected ArrayList<Message> doInBackground(Void... params) {

                final ArrayList<Message> messages = new ArrayList<>();

                final Uri smsInboxUri = Uri.parse(SmsProvider.SMS_INBOX_URI_STRING);

                final String selection = Telephony.Sms.ADDRESS + " LIKE ?";
                final String[] selectionArgs = {senderAddressPattern};

                final Cursor smsInboxCursor = context.getContentResolver().query(smsInboxUri, SMS_QUERY_PROJECTIONS, selection, selectionArgs, null);

                if(null == smsInboxCursor) {
                    Log.d("SmsProvider", "cursor is null");
                } else if (smsInboxCursor.getCount() <= 0) {
                    Log.d("SmsProvider", "no results found ");
                } else {

                    smsInboxCursor.moveToFirst();

                    final int idIndex = smsInboxCursor.getColumnIndex(Telephony.Sms._ID);
                    final int addressIndex = smsInboxCursor.getColumnIndex(Telephony.Sms.ADDRESS);
                    final int dateIndex = smsInboxCursor.getColumnIndex(Telephony.Sms.DATE);
                    final int dateSentIndex = smsInboxCursor.getColumnIndex(Telephony.Sms.DATE_SENT);
                    final int bodyIndex = smsInboxCursor.getColumnIndex(Telephony.Sms.BODY);

                    while(smsInboxCursor.moveToNext()) {

                        final Message message = new Message();

                        message.setExternalId(smsInboxCursor.getString(idIndex));
                        message.setFromAddress(smsInboxCursor.getString(addressIndex));
                        message.setReceivedDate(new Date(smsInboxCursor.getLong(dateIndex)));
                        message.setSentDate(new Date(smsInboxCursor.getLong(dateSentIndex)));
                        message.setContent(smsInboxCursor.getString(bodyIndex));

                        messages.add(message);

//                        Log.d("SmsProvider", message.toString());
                    }
                }

                Log.d("SmsProvider", "Found '"+messages.size()+"' messages for sender '"+senderAddressPattern+"'.");

                return messages;
            }

            @Override
            protected void onPostExecute(ArrayList<Message> messages) {
                callback.onMessagesRead(messages);
            }
        };

        asyncTask.execute();

    }

    public interface SmsProviderCallback {

        void onMessagesRead(List<Message> messages);

    }
}
