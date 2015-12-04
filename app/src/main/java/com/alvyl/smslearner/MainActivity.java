package com.alvyl.smslearner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alvyl.smslearner.message.MessageProcessor;
import com.alvyl.smslearner.message.model.Message;
import com.alvyl.smslearner.message.provider.SmsProvider;
import com.alvyl.smslearner.sender.model.Sender;
import com.alvyl.smslearner.sender.provider.MockSenderCategoryProvider;
import com.alvyl.smslearner.sender.provider.MockSenderProvider;
import com.alvyl.smslearner.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SmsProvider.SmsProviderCallback,
        MessageProcessor.MessageProcessorCallback {

    public static final String SMS_INBOX = "content://sms/inbox";

    private TextView activityLog;

    private MockSenderProvider senderProvider;
    private MockSenderCategoryProvider senderCategoryProvider;
    private SmsProvider smsProvider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activityLog = (TextView) this.findViewById(R.id.activityLog);

        this.senderProvider = MockSenderProvider.getInstance();
        this.senderCategoryProvider = MockSenderCategoryProvider.getInstance();
        this.smsProvider = SmsProvider.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        processMessages();
    }

    // begin processing
    private void processMessages() {
        readMessages();
    }

    // step 1
    private void readMessages() {

        this.activityLog.append("####\n");
        this.activityLog.append("Step 1\n");
        this.activityLog.append("####\n");

        this.activityLog.append("Reading all transactional text messages");
        this.activityLog.append(" ...\n");

        this.smsProvider.readAllTransactionalMessages(this, this);
    }


    @Override
    public void onMessagesRead(List<Message> messages) {

        this.activityLog.append("Total transactional messages read: " + messages.size());
        this.activityLog.append(".\n");
        this.activityLog.append("Done reading");
        this.activityLog.append(".\n");

        categorizeMessages(messages);
    }

    // step 2
    private void categorizeMessages(final List<Message> messages) {

        this.activityLog.append("####\n");
        this.activityLog.append("Step 2\n");
        this.activityLog.append("####\n");

        final List<Sender> senders = senderProvider.findAllSenders();

        for(final Sender sender: senders) {

            this.activityLog.append("Processing text messages for sender: " + sender.getName());
            this.activityLog.append(".\n");

            // TODO: Add support for multiple sender processing
            final MessageProcessor messageProcessor = new MessageProcessor(sender, senderCategoryProvider.findBySender(sender));

            final MessageProcessor.Summary summary = messageProcessor.processMessages(messages);

            onMessagesProcessed(messages, summary);

            this.activityLog.append("####\n");
        }

    }

    @Override
    public void onMessagesProcessed(final List<Message> messages, final MessageProcessor.Summary summary) {

        this.activityLog.append("Total processing summary: " + summary);
        this.activityLog.append(".\n");
        this.activityLog.append("Done categorizing");
        this.activityLog.append(".\n");

        doMore();
    }

    // step 3
    public void doMore() {
        // TODO: List un categorized messages
        // TODO: Correct Classification Ratio vs Incorrect Classification Ratio

        // TODO: Extract trasaction data from nessages
    }


}
