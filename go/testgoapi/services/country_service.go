package services

import (
	"../domains"
	"../utils/apierrors"
)

func GetCountryFromAPI(userID string) (*domains.Country, *apierrors.ApiError ){
	country := &domains.Country{
		ID:userID,
	}
	if err := country.Get(); err != nil{
		return nil, err
	}
	return country,nil
}
func GetCountry(userID string, ch chan *domains.Result, chError chan *apierrors.ApiError) (){
	country := &domains.Country{
		ID:userID,
	}
	result := domains.Result{}

	if err := country.Get(); err != nil{
		ch <- &result
		chError <- err
	}
	result.Country = country
	ch <- &result
}
