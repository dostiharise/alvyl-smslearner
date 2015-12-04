package com.alvyl.smslearner.message;

import android.os.AsyncTask;
import android.util.Log;

import com.alvyl.smslearner.sender.matchers.SenderCategoryMatcher;
import com.alvyl.smslearner.message.model.Message;
import com.alvyl.smslearner.sender.matchers.SenderAddressMatcher;
import com.alvyl.smslearner.sender.model.Sender;
import com.alvyl.smslearner.sender.model.SenderCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author dostiharise
 */
public class MessageProcessor {

    private Sender sender;
    private ArrayList<SenderCategory> senderCategories = new ArrayList<>();

    private SenderAddressMatcher senderAddressMatcher;
    private ArrayList<SenderCategoryMatcher> senderCategoryMatchers = new ArrayList<>();

    public MessageProcessor(Sender sender, List<SenderCategory> senderCategories) {

        this.sender = sender;
        this.senderCategories.addAll(senderCategories);

        this.senderAddressMatcher = new SenderAddressMatcher(sender.getSenderAddress());

        for(final SenderCategory senderCategory: senderCategories) {
            this.senderCategoryMatchers.add(new SenderCategoryMatcher(senderCategory));
        }
    }

    public Summary processMessages(final List<Message> messages) {

        final Summary summary = new Summary();

        for(final Message message: messages) {
            processMessage(message, summary);
        }

        return summary;

    }

    public void processMessagesAsync(final List<Message> messages, final MessageProcessorCallback callback) {

        final AsyncTask<Void, Void, Summary> asyncTask = new AsyncTask<Void, Void, Summary>() {
            @Override
            protected Summary doInBackground(Void... params) {

                final Summary summary = processMessages(messages);

                return summary;
            }

            @Override
            protected void onPostExecute(Summary summary) {
                Log.d("MessageProcessor", summary.toString());
                callback.onMessagesProcessed(messages, summary);
            }
        };

        asyncTask.execute();

    }

    private void processMessage(final Message message, final Summary summary) {

        if(null == message) {
            return;
        }

        processSender(message, summary);

        processCategory(message, summary);

        summary.totalProcessed++;
    }

    private void processSender(final Message message, final Summary summary) {

        if(null == message.getFromAddress()) {
            return;
        }

        if(this.senderAddressMatcher.matches(message.getFromAddress())) {
            message.setSender(this.sender);
            summary.messagesWithKnownSender++;
        }

    }

    private void processCategory(final Message message, final Summary summary) {

        if(null == message.getContent()) {
            return;
        }

        for(final SenderCategoryMatcher senderCategoryMatcher: senderCategoryMatchers) {
            if(senderCategoryMatcher.matches(message.getContent())) {
                message.setSenderCategory(senderCategoryMatcher.getSenderCategory());
                summary.messagesWithKnownCategories++;

                final String categoryName = senderCategoryMatcher
                        .getSenderCategory().getCategory().getName();

                if(null == summary.getCategoriesIdentified().get(categoryName)) {
                    summary.getCategoriesIdentified().put(categoryName, 1);
                } else {
                    summary.getCategoriesIdentified().put(categoryName, summary.getCategoriesIdentified().get(categoryName)+1);
                }
                break;
            }
        }

    }

    public class Summary {

        private int messagesWithKnownSender;
        private int messagesWithKnownCategories;

        private int totalProcessed;

        private HashMap<String, Integer> categoriesIdentified = new HashMap<>();

        public int getMessagesWithKnownSender() {
            return messagesWithKnownSender;
        }

        public void setMessagesWithKnownSender(int messagesWithKnownSender) {
            this.messagesWithKnownSender = messagesWithKnownSender;
        }

        public int getMessagesWithKnownCategories() {
            return messagesWithKnownCategories;
        }

        public void setMessagesWithKnownCategories(int messagesWithKnownCategories) {
            this.messagesWithKnownCategories = messagesWithKnownCategories;
        }

        public int getTotalProcessed() {
            return totalProcessed;
        }

        public void setTotalProcessed(int totalProcessed) {
            this.totalProcessed = totalProcessed;
        }

        public HashMap<String, Integer> getCategoriesIdentified() {
            return categoriesIdentified;
        }

        public void setCategoriesIdentified(HashMap<String, Integer> categoriesIdentified) {
            this.categoriesIdentified = categoriesIdentified;
        }

        @Override
        public String toString() {
            return "Summary{" +
                    "messagesWithKnownSender=" + messagesWithKnownSender +
                    ", messagesWithKnownCategories=" + messagesWithKnownCategories +
                    ", totalProcessed=" + totalProcessed +
                    ", categoriesIdentified=" + categoriesIdentified +
                    '}';
        }
    }

    public interface MessageProcessorCallback {
        void onMessagesProcessed(final List<Message> messages, final Summary summary);
    }
}
