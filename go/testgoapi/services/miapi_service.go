package services

import (
	"../domains"
	"../utils/apierrors"
)

func GetUserFromAPI(userID int64) (*domains.User, *apierrors.ApiError ){
	user := &domains.User{
		ID:userID,
	}
	if err := user.Get(); err != nil{
		return nil, err
	}
	return user,nil
}

func GetUser(userID int64) (*domains.User, *apierrors.ApiError ) {
	user := &domains.User{
		ID:userID,
	}
	if err := user.Get(); err != nil{
		return nil, err
	}
	return user,nil
}
