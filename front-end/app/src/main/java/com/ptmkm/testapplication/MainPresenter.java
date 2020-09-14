package com.ptmkm.testapplication;

import com.ptmkm.testapplication.model.User;

public class MainPresenter implements MainConstact.Presenter, MainConstact.Model.OnFinishedListener {

    private MainConstact.Model model;

    private MainConstact.View view;

    public MainPresenter(MainConstact.View view) {
        this.view = view;
        this.model = new MainRequest();
    }

    @Override
    public void onFinished(User user) {
        view.setDataToView(user);

    }

    @Override
    public void onFailure(Throwable t) {
        view.onResponseFailure(t);

    }

    @Override
    public void requestDataFromServer(String username) {

        model.getAccident(this, username);
    }
}
