package com.app.ebank.services;

import com.app.ebank.domain.bindingModels.UserBindingModel;
import com.app.ebank.domain.entities.User;

public interface UserService {

    boolean registerUser(UserBindingModel userBindingModel);

    User findUserByName(String name);
}
