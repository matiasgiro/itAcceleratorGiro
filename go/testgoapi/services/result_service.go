package services

import (
	"../domains"
)

func GetResult(user *domains.User, country *domains.Country, site *domains.Site) (*domains.Result ){
	result := &domains.Result{
		User: user,
		Country: country,
		Site:site,
	}

	return result
}
