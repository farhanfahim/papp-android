package co.chatsdk.core.base;

import java.io.File;

import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.handlers.AudioMessageHandler;
import co.chatsdk.core.rx.ObservableConnector;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.FileUploadResult;
import co.chatsdk.core.types.MessageSendProgress;
import co.chatsdk.core.types.MessageSendStatus;
import co.chatsdk.core.types.MessageType;
import co.chatsdk.core.utils.StringChecker;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BaseAudioMessageHandler implements AudioMessageHandler {

    @Override
    public Observable<MessageSendProgress> sendMessage(File file, Thread thread) {
        return Observable.create((ObservableOnSubscribe<MessageSendProgress>) e -> {

            final Message message = AbstractThreadHandler.newMessage(MessageType.Audio, thread);

            // First pass back an empty result so that we add the cell to the table view
            message.setMessageStatus(MessageSendStatus.Uploading);
            message.update();

            ChatSDK.upload().uploadFile(file).subscribe(new Observer<FileUploadResult>() {
                @Override
                public void onSubscribe(Disposable d) {}

                @Override
                public void onNext(FileUploadResult result) {
                    if(!StringChecker.isNullOrEmpty(result.url))  {

                        message.setValueForKey(result.url, Keys.MessageAudioURL);

                        message.update();

                        Timber.v("ProgressListener: " + result.progress.asFraction());
                    }

                    e.onNext(new MessageSendProgress(message, result.progress));

                }

                @Override
                public void onError(Throwable ex) {
                    e.onError(ex);
                }

                @Override
                public void onComplete() {

                    message.setMessageStatus(MessageSendStatus.Sending);
                    message.update();

                    e.onNext(new MessageSendProgress(message));

                    ObservableConnector<MessageSendProgress> connector = new ObservableConnector<>();
                    connector.connect(ChatSDK.thread().sendMessage(message), e);

                }
            });
        }).subscribeOn(Schedulers.single());

    }




}
