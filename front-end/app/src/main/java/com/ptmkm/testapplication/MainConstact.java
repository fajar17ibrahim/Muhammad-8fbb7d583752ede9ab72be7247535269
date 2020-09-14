package com.ptmkm.testapplication;

import com.ptmkm.testapplication.model.User;

public interface MainConstact {

    interface Model {

        interface OnFinishedListener {

            void onFinished(User user);

            void onFailure(Throwable t);
        }

        void getAccident(OnFinishedListener onFinishedListener, String username);

    }

    interface View {

        void setDataToView(User user);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter{

        void requestDataFromServer(String username);

    }
}
